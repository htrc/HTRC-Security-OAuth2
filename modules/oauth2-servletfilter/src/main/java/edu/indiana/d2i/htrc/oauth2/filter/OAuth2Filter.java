/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/

package edu.indiana.d2i.htrc.oauth2.filter;


import edu.indiana.d2i.htrc.audit.Auditor;
import edu.indiana.d2i.htrc.audit.AuditorFactory;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.amber.oauth2.common.message.types.TokenType;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.amber.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.amber.oauth2.rs.response.OAuthRSResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.wso2.carbon.identity.oauth2.dto.xsd.OAuth2TokenValidationRequestDTO;
import org.wso2.carbon.identity.oauth2.dto.xsd.OAuth2TokenValidationResponseDTO;

import javax.net.ssl.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OAuth2Filter implements Filter {
    private static Log log = LogFactory.getLog(OAuth2Filter.class);

    public static final String OAUTH2_PROVIDER_URL = "oauth2.provider.url";
    public static final String OAUTH2_PROVIDER_USERS = "oauth2.provider.user";
    public static final String OAUTH2_PROVIDER_PASSWORD = "oauth2.provider.password";
    public static final String OAUTH2_RESOURCE_REALM = "oauth2.resource.realm";
    public static final String TRUST_STORE = "javax.net.ssl.trustStore";
    public static final String TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";
    public static final String PN_LOG4J_PROPERTIES_PATH = "log4j.properties.path";
    public static final String PN_AUDITOR_CLASS = "auditor.class";
    public static final String KEY_REMOTE_USER = "remoteuser";

    private String providerUrl;
    private String userName;
    private String password;
    private String realm;
    private String trustStore;
    private String trustStorePassword;
    protected FilterConfig config;
    protected AuditorFactory auditorFactory;


    public void init(FilterConfig filterConfig) throws ServletException {

        try {
            config = filterConfig;
            String log4jPath = config.getInitParameter(PN_LOG4J_PROPERTIES_PATH);
            AuditorFactory.init(config.getInitParameter(PN_AUDITOR_CLASS));
            auditorFactory = new AuditorFactory();

            if (log4jPath != null) {
                PropertyConfigurator.configure(log4jPath);

            }


            providerUrl = filterConfig.getInitParameter(OAUTH2_PROVIDER_URL);
            if(providerUrl == null || providerUrl.isEmpty()){
                log.error("Cannot find OAuth2 provider URL in filter configuration!");
                throw new RuntimeException("Cannot find OAuth2 provider URL in filter configuration!");
            }
            userName = filterConfig.getInitParameter(OAUTH2_PROVIDER_USERS);
            if(userName == null || userName.isEmpty()){
                log.error("Cannot find OAuth2 provider username in filter configuration!");
                throw new RuntimeException("Cannot find OAuth2 provider username in filter configuration!");
            }

            password = filterConfig.getInitParameter(OAUTH2_PROVIDER_PASSWORD);
            if(password == null || password.isEmpty()){
                log.error("Cannot find OAuth2 provider password in filter configuration!");
                throw new RuntimeException("Cannot find OAuth2 provider password in filter configuration!");
            }
        // Trust store can be used when WSO2 IS is deployed with self-signed certificates
        trustStore = filterConfig.getInitParameter(TRUST_STORE);
        trustStorePassword = filterConfig.getInitParameter(TRUST_STORE_PASSWORD);

        if(trustStore != null && trustStorePassword != null){
            System.setProperty(TRUST_STORE, trustStore);
            System.setProperty(TRUST_STORE_PASSWORD, trustStorePassword);
        }

            realm = filterConfig.getInitParameter(OAUTH2_RESOURCE_REALM);

        }catch (Exception e){
            e.printStackTrace();
            throw new ServletException(e);
        }

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        ContextExtractor contextExtractor = new ContextExtractor(req);
        Auditor auditor = AuditorFactory.getAuditor(contextExtractor.getContextMap());

        String accessToken = null;
        OAuth2TokenValidationResponseDTO responseDTO = null;
        try {
            OAuthAccessResourceRequest accessResourceRequest = new OAuthAccessResourceRequest(req, TokenType.BEARER);
            accessToken = accessResourceRequest.getAccessToken();

            auditor.log("REQUEST_RECEIVED", accessToken);

            OAuth2ServiceClient client = new OAuth2ServiceClient(providerUrl, userName, password);
            OAuth2TokenValidationRequestDTO oauthReq = new OAuth2TokenValidationRequestDTO();
            oauthReq.setAccessToken(accessToken);
            oauthReq.setTokenType("bearer");

            // Need to fix this to return user information (reverse lookup)
            responseDTO = client.validateAuthenticationRequest(oauthReq);
            List<String> registered_user = new ArrayList<String>();
            registered_user.add(responseDTO.getAuthorizedUser());
            Map<String, List<String>> contextMap = contextExtractor.getContextMap();
            contextMap.put(KEY_REMOTE_USER, registered_user);

            auditor = AuditorFactory.getAuditor(contextMap);
            auditor.log("REQUEST_AUTHENTICATED", accessToken);


        } catch (OAuthProblemException e) {
            log.error("OAuth exception.", e);
            auditor.log("UNAUTHENTICATED_REQUEST", accessToken);
            auditor.error("OAuthProblemException", accessToken, e.getError(), e.getMessage());
            respondWithError(res, e);
            return;
        } catch (OAuthSystemException e) {
            log.error("OAuth system exeception.", e);
            auditor.log("UNAUTHENTICATED_REQUEST", accessToken);
            auditor.error("OAuthSystemException", accessToken, e.getMessage());
            throw new ServletException(e);
        } catch (RemoteException re) {
            re.printStackTrace();
            log.error("Error occurred during token validation.", re);
            auditor.log("UNAUTHENTICATED_REQUEST", accessToken);
            auditor.error("Error occurred during token validation.", accessToken, re.getMessage());
            throw new ServletException("Error occurred during token validation.", re);
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }

    private void respondWithError(HttpServletResponse resp, OAuthProblemException error)
            throws IOException, ServletException {

        OAuthResponse oauthResponse = null;

        try {
            if (OAuthUtils.isEmpty(error.getError())) {
                oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm(realm)
                        .buildHeaderMessage();

            } else {

                int responseCode = 401;
                if (error.getError().equals(OAuthError.CodeResponse.INVALID_REQUEST)) {
                    responseCode = 400;
                } else if (error.getError().equals(OAuthError.ResourceResponse.INSUFFICIENT_SCOPE)) {
                    responseCode = 403;
                }

                oauthResponse = OAuthRSResponse
                        .errorResponse(responseCode)
                        .setRealm(realm)
                        .setError(error.getError())
                        .setErrorDescription(error.getDescription())
                        .setErrorUri(error.getUri())
                        .buildBodyMessage();
            }
            resp.addHeader(OAuth.HeaderType.WWW_AUTHENTICATE,
                    oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
            resp.setContentType("text/html");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().println(oauthResponse.getBody());

        } catch (OAuthSystemException e) {
            throw new ServletException(e);
        }
    }
}
