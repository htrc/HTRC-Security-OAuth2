/*
#
# Copyright 2012 The Trustees of Indiana University
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or areed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# -----------------------------------------------------------------
#
# Project: OAuth2
# File:  TokenEndpoint.java
# Description:  
#
# -----------------------------------------------------------------
# 
*/

package edu.indiana.d2i.htrc.oauth2.endpoints;

/**
 * @author Milinda Pathirage
 * @author Yiming Sun
 */

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.amber.oauth2.as.request.OAuthTokenRequest;
import org.apache.amber.oauth2.as.response.OAuthASResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.apache.log4j.Logger;

import edu.indiana.d2i.htrc.audit.Auditor;
import edu.indiana.d2i.htrc.oauth2.OAuth2Application;
import edu.indiana.d2i.htrc.oauth2.common.ContextExtractor;
import edu.indiana.d2i.htrc.oauth2.common.Database;
import edu.indiana.d2i.htrc.oauth2.common.TokenStore;
import edu.indiana.d2i.htrc.oauth2.common.UserStore;

@Path("/token")
public class TokenEndpoint {
    
    private static Logger log = Logger.getLogger(TokenEndpoint.class);
    
    @Context
    protected ServletConfig config;

    @Context
    protected ServletContext context;
    
    
    protected TokenStore ts;
    
    protected UserStore us;
    
    public TokenEndpoint() throws NamingException {
        Database db = new Database(OAuth2Application.getDSName());
        ts = new TokenStore(db);
        us = new UserStore(db);
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response authorize(@Context HttpServletRequest request, @Context HttpHeaders headers) throws OAuthSystemException {
        OAuthTokenRequest tokenRequest = null;
        if (log.isDebugEnabled()) log.debug("Got request");
        
        ContextExtractor contextExtractor = new ContextExtractor(request, headers);
        Auditor auditor = OAuth2Application.getAuditor(contextExtractor.getContextMap());

        String clientId = null;
        String grantType = null;
        
        try {
            
            
            tokenRequest = new OAuthTokenRequest(request);

            grantType = tokenRequest.getParam(OAuth.OAUTH_GRANT_TYPE);
            clientId = tokenRequest.getClientId();
            
            auditor.log("TOKEN_REQUEST", clientId, grantType);

            
            if (grantType.equals(GrantType.CLIENT_CREDENTIALS.toString())) {
                if (!isAuthenticated(clientId, tokenRequest.getClientSecret())) {
                    OAuthResponse response = OAuthASResponse
                            .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                            .setErrorDescription("Invalid client id and client secret combination")
                            .buildJSONMessage();
                    auditor.error("Bad Request", "Invalid client credentials", clientId, "********");
                    return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
                }
            } else {
                // This OAuth 2.0 Token endpoint implementation doesn't support other grant types except
                // "Client Credentials". Support for other grant types will be added as needed.
                OAuthResponse response = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_GRANT)
                        .setErrorDescription("Invalid grant type.")
                        .buildJSONMessage();
                auditor.error("Bad Request", "Invalid grant type", clientId, grantType);
                return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
            }

            int tokenTTLSeconds = OAuth2Application.getTokenTTLSeconds();
            
            String clientToken = getTokenForClient(clientId, tokenTTLSeconds);
            
            OAuthResponse response = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(clientToken)
                    .setExpiresIn(Integer.toString(tokenTTLSeconds))
                    .buildJSONMessage();

            auditor.log("ACCESS_TOKEN", clientId, grantType, clientToken);
            return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
        } catch (OAuthProblemException e) {
            OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
                    .buildJSONMessage();
            auditor.error("OAuthProblemException", clientId, grantType, e.getError(), e.getMessage());
            return Response.status(res.getResponseStatus()).entity(res.getBody()).build();
        }
    }

    /**
     * Check whether the given user has permission to get a token.
     *
     * @param clientId Client ID(When it comes to client credentials grant type this is equal to user name)
     * @param password Client password(in client credentials grant type this is equal to user's password.)
     * @return true if user is there in the user store, false otherwise.
     */
    private boolean isAuthenticated(String clientId, String password) {
        return us.isUserAuthenticated(clientId, password);
    }

    /**
     * Generate a token for given client.
     *
     * @param clientId ID of the client requesting a token.
     * @param tokenTTLMinute token time to live in minutes
     * @return token
     */
    private String getTokenForClient(String clientId, int tokenTTLMinute) throws OAuthSystemException {
        return ts.newToken(clientId, tokenTTLMinute);
    }
}
