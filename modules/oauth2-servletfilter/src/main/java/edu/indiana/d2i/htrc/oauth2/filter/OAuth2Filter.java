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


//import edu.indiana.d2i.htrc.audit.Auditor;
//import edu.indiana.d2i.htrc.audit.AuditorFactory;
//import edu.indiana.d2i.htrc.oauth2.common.ContextExtractor;
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
import org.wso2.carbon.identity.oauth2.dto.xsd.OAuth2TokenValidationRequestDTO;

import javax.net.ssl.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class OAuth2Filter implements Filter {
    private static Log log = LogFactory.getLog(OAuth2Filter.class);

    public static final String OAUTH2_PROVIDER_URL = "oauth2.provider.url";
    public static final String OAUTH2_PROVIDER_USERS = "oauth2.provider.user";
    public static final String OAUTH2_PROVIDER_PASSWORD = "oauth2.provider.password";
    public static final String OAUTH2_RESOURCE_REALM = "oauth2.resource.realm";
    public static final String TRUST_STORE = "javax.net.ssl.trustStore";
    public static final String TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";
    private String providerUrl;
    private String userName;
    private String password;
    private String realm;
//    private String trustStore;
//    private String trustStorePassword;

    public void init(FilterConfig filterConfig) throws ServletException {
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

//        trustStore = filterConfig.getInitParameter(TRUST_STORE);
//        trustStorePassword = filterConfig.getInitParameter(TRUST_STORE_PASSWORD);
//
//        if(trustStore != null && trustStorePassword != null){
//            System.setProperty(TRUST_STORE, trustStore);
//            System.setProperty(TRUST_STORE_PASSWORD, trustStorePassword);
//        }

        realm = filterConfig.getInitParameter(OAUTH2_RESOURCE_REALM);
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        //ContextExtractor contextExtractor = new ContextExtractor(req, null);
        //Auditor auditor = AuditorFactory.getAuditor(contextExtractor.getContextMap());

        SSLContext sc;
        // Get SSL context
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            log.error("SSLContext.getInstance failed.", e);
            throw new ServletException(e);
        }
        // Create empty HostnameVerifier
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                                           String authType) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                           String authType) {
            }
        }};

        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            log.error("SSLContext init error.", e);
            throw new ServletException(e);
        }

        SSLSocketFactory sslSocketFactory = sc.getSocketFactory();

        HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
        HttpsURLConnection.setDefaultHostnameVerifier(hv);

        String accessToken = null;
        try {
            OAuthAccessResourceRequest accessResourceRequest = new OAuthAccessResourceRequest(req, TokenType.BEARER);
            accessToken = accessResourceRequest.getAccessToken();

            //auditor.log("UNAUTHENTICATED_REQUEST", accessToken);

            OAuth2ServiceClient client = new OAuth2ServiceClient(providerUrl, userName, password);
            OAuth2TokenValidationRequestDTO oauthReq = new OAuth2TokenValidationRequestDTO();
            oauthReq.setAccessToken(accessToken);
            oauthReq.setTokenType("bearer");

            // Need to fix this to return user information (reverse lookup)
            client.validateAuthenticationRequest(oauthReq);

            // Need to add extra information about client which does the request.
            // Need improvements to IS API.
        } catch (OAuthProblemException e) {
            log.error("OAuth exception.", e);
            //auditor.error("OAuthProblemException", accessToken, e.getError(), e.getMessage());
            respondWithError(res, e);
            return;
        } catch (OAuthSystemException e) {
            log.error("OAuth system exeception.", e);
            //auditor.error("OAuthSystemException", accessToken, e.getMessage());
            throw new ServletException(e);
        } catch (RemoteException re) {
            re.printStackTrace();
            log.error("Error occurred during token validation.", re);
            //auditor.error("Error occurred during token validation.", accessToken, re.getMessage());
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
            //resp.sendError(oauthResponse.getResponseStatus());
            resp.setContentType("text/html");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().println(oauthResponse.getBody());
//            PrintWriter out = resp.getWriter();
//            out.print(oauthResponse.getBody());
//            //out.print(oauthResponse.getResponseStatus());
//            out.flush();
//            resp.sendError(oauthResponse.getResponseStatus());
        } catch (OAuthSystemException e) {
            throw new ServletException(e);
        }
    }
}
