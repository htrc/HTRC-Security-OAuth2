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


import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.amber.oauth2.common.message.types.TokenType;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.amber.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.amber.oauth2.rs.response.OAuthRSResponse;
import org.wso2.carbon.identity.oauth2.dto.xsd.OAuth2TokenValidationRequestDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2Filter implements Filter {
    public static final String OAUTH2_PROVIDER_URL = "oauth2.provider.url";
    public static final String OAUTH2_PROVIDER_USERS = "oauth2.provider.user";
    public static final String OAUTH2_PROVIDER_PASSWORD = "oauth2.provider.password";
    public static final String OAUTH2_RESOURCE_REALM = "oauth2.resource.realm";
    private String providerUrl;
    private String userName;
    private String password;
    private String realm;

    public void init(FilterConfig filterConfig) throws ServletException {
        providerUrl = filterConfig.getInitParameter(OAUTH2_PROVIDER_URL);
        if(providerUrl == null || providerUrl.isEmpty()){
            throw new RuntimeException("Cannot find OAuth2 provider URL in filter configuration!");
        }
        userName = filterConfig.getInitParameter(OAUTH2_PROVIDER_USERS);
        if(userName == null || userName.isEmpty()){
            throw new RuntimeException("Cannot find OAuth2 provider username in filter configuration!");
        }

        password = filterConfig.getInitParameter(OAUTH2_PROVIDER_PASSWORD);
        if(password == null || password.isEmpty()){
            throw new RuntimeException("Cannot find OAuth2 provider password in filter configuration!");
        }

        realm = filterConfig.getInitParameter(OAUTH2_RESOURCE_REALM);
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        try {
            OAuthAccessResourceRequest accessResourceRequest = new OAuthAccessResourceRequest(req, TokenType.BEARER);
            String accessToken = accessResourceRequest.getAccessToken();

            OAuth2ServiceClient client = new OAuth2ServiceClient(providerUrl, userName, password);
            OAuth2TokenValidationRequestDTO oauthReq = new OAuth2TokenValidationRequestDTO();
            oauthReq.setAccessToken(accessToken);
            oauthReq.setTokenType(TokenType.BEARER.toString());

            // Need to fix this to return user information (reverse lookup)
            client.validateAuthenticationRequest(oauthReq);

            // Need to add extra information about client which does the request.
            // Need improvements to IS API.
        } catch (OAuthProblemException e) {
            respondWithError(res, e);
        } catch (OAuthSystemException e) {
            throw new ServletException(e);
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
                        .buildHeaderMessage();
            }
            resp.addHeader(OAuth.HeaderType.WWW_AUTHENTICATE,
                    oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
            resp.sendError(oauthResponse.getResponseStatus());
        } catch (OAuthSystemException e) {
            throw new ServletException(e);
        }
    }
}
