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

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
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

    /**
     * Setup servlet filter instance after reading filter configuration in web.xml. In a production scenario where
     * WSO2 IS is deployed with a valid certificate filter doesn't need trust store and trust store password
     * configuration values.
     *
     * Also it's better have a web app specific user account to use with filter. This will make it easier to audit the
     * security logs.
     *
     * @param filterConfig  OAuth2 filter configuration
     * @throws ServletException
     */
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
            if (providerUrl == null || providerUrl.isEmpty()) {
                log.error("Cannot find OAuth2 provider URL in filter configuration!");
                throw new RuntimeException("Cannot find OAuth2 provider URL in filter configuration!");
            }
            userName = filterConfig.getInitParameter(OAUTH2_PROVIDER_USERS);
            if (userName == null || userName.isEmpty()) {
                log.error("Cannot find OAuth2 provider username in filter configuration!");
                throw new RuntimeException("Cannot find OAuth2 provider username in filter configuration!");
            }

            password = filterConfig.getInitParameter(OAUTH2_PROVIDER_PASSWORD);
            if (password == null || password.isEmpty()) {
                log.error("Cannot find OAuth2 provider password in filter configuration!");
                throw new RuntimeException("Cannot find OAuth2 provider password in filter configuration!");
            }
            // Trust store can be used when WSO2 IS is deployed with self-signed certificates
            trustStore = filterConfig.getInitParameter(TRUST_STORE);
            trustStorePassword = filterConfig.getInitParameter(TRUST_STORE_PASSWORD);

            if (trustStore != null && trustStorePassword != null) {
                System.setProperty(TRUST_STORE, trustStore);
                System.setProperty(TRUST_STORE_PASSWORD, trustStorePassword);
            }

            realm = filterConfig.getInitParameter(OAUTH2_RESOURCE_REALM);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

    }

    /**
     * Extract the access token from servlet request header and validate it via WSO2 IS. If token is invalid or
     * expired filter will throw a error and stop of handing over the request to next filter in chain. If there is no
     * token header filter will reject the request without any checks.
     * @param servletRequest in-coming request object
     * @param servletResponse servlet response object
     * @param filterChain servlet filter chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        OAuth2RequestWrapper modifiedRequest = new OAuth2RequestWrapper(req);
        String errMsg = null;

        ContextExtractor contextExtractor = new ContextExtractor(req);
        Auditor auditor = AuditorFactory.getAuditor(contextExtractor.getContextMap());

        String accessToken = null;
        OAuth2TokenValidationResponseDTO responseDTO = null;

        try {
            OAuthAccessResourceRequest accessResourceRequest = new OAuthAccessResourceRequest(req, TokenType.BEARER);
            accessToken = accessResourceRequest.getAccessToken();

            logRequestInformation(accessToken, req, auditor);

            responseDTO =  validateToken(accessToken);

            List<String> registered_user = new ArrayList<String>();
            List<String> app_name = new ArrayList<String>();

            registered_user.add(responseDTO.getAuthorizedUser());
            app_name.add(responseDTO.getAppName());

            if (responseDTO.getAuthorizedUser() != null) {
                modifiedRequest.setRemoteUser(responseDTO.getAuthorizedUser());
            } else {
                modifiedRequest.setRemoteUser(responseDTO.getAppName());
            }

            Map<String, List<String>> contextMap = contextExtractor.getContextMap();
            if (responseDTO.getAuthorizedUser() != null) {
                contextMap.put(KEY_REMOTE_USER, registered_user);
            } else {
                contextMap.put(KEY_REMOTE_USER, app_name);
            }

            // We need to create new auditor instance after we create the context map out of servlet request.
            auditor = AuditorFactory.getAuditor(contextMap);
            auditor.log("REQUEST_AUTHENTICATED", accessToken);
        } catch (OAuthProblemException e) {
            errMsg = "OAuth exception.";
            log.error(errMsg, e);
            auditor.log("UNAUTHENTICATED_REQUEST", accessToken);
            auditor.error(errMsg, accessToken, e.getError(), e.getMessage());
            respondWithError(res, e);
            return;
        } catch (OAuthSystemException e) {
            errMsg = "OAuth system exception." ;
            log.error(errMsg, e);
            auditor.log("UNAUTHENTICATED_REQUEST", accessToken);
            auditor.error(errMsg, accessToken, e.getMessage());
            throw new ServletException(e);
        } catch (RemoteException re) {
            errMsg = "Error occurred while invoking token validation service.";
            log.error(errMsg, re);
            auditor.log("UNAUTHENTICATED_REQUEST", accessToken);
            auditor.error(errMsg, accessToken, re.getMessage());
            throw new ServletException(errMsg, re);
        }


        filterChain.doFilter(modifiedRequest, servletResponse);
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

    private void logRequestInformation(String accessToken, HttpServletRequest req, Auditor auditor){
        String url = req.getRequestURL().toString();
        int port = req.getRemotePort();
        String host = req.getRemoteHost().toString();
        String address = req.getRemoteAddr().toString();

        auditor.log("REQUEST_RECEIVED", accessToken, "URL:", url, "HOST:", host, "ADDRESS:", address, "PORT:", String.valueOf(port));
    }

    private OAuth2TokenValidationResponseDTO validateToken(String accessToken) throws RemoteException, OAuthProblemException {
        OAuth2TokenValidationServiceClient client = new OAuth2TokenValidationServiceClient(providerUrl, userName, password);

        OAuth2TokenValidationRequestDTO oauthReq = new OAuth2TokenValidationRequestDTO();
        oauthReq.setAccessToken(accessToken);
        oauthReq.setTokenType("bearer");

        return client.validateAuthenticationRequest(oauthReq);
    }
}
