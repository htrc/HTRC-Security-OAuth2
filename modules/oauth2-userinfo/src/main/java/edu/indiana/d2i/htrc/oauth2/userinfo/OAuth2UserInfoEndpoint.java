/*
*
* Copyright 2013 The Trustees of Indiana University
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/

package edu.indiana.d2i.htrc.oauth2.userinfo;

import org.apache.amber.oauth2.as.response.OAuthASResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth.common.OAuth2ErrorCodes;
import org.wso2.carbon.identity.oauth.ui.OAuthClientException;
import org.wso2.carbon.identity.oauth.ui.OAuthConstants;
import org.wso2.carbon.identity.oauth.ui.util.OAuthUIUtil;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoRespDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;


@Path("/")
public class OAuth2UserInfoEndpoint {
    private static Log log = LogFactory.getLog(OAuth2UserInfoEndpoint.class);

    @POST
    @Path("/")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response getUserInformation(@Context HttpServletRequest request, MultivaluedMap<String, String> paramMap) throws OAuthSystemException {

        HttpServletRequestWrapper httpRequest = new OAuthUserInfoRequestWrapper(request, paramMap);

        if (log.isDebugEnabled()) {
            logUserInfoRequest(httpRequest);
        }

        boolean basicAuthUsed = false;
        if (request.getHeader(OAuthConstants.HTTP_REQ_HEADER_AUTHZ) != null) {
            try {
                String[] clientCredentials = OAuthUIUtil.extractCredentialsFromAuthzHeader(request.getHeader(OAuthConstants.HTTP_REQ_HEADER_AUTHZ));

                if (paramMap.containsKey(OAuth.OAUTH_CLIENT_ID) && paramMap.containsKey(OAuth.OAUTH_CLIENT_SECRET)) {

                    return handleBasicAuthFailure();

                }

                paramMap.add(OAuth.OAUTH_CLIENT_ID, clientCredentials[0]);
                paramMap.add(OAuth.OAUTH_CLIENT_SECRET, clientCredentials[1]);

                basicAuthUsed = true;

                log.debug("HTTP Authorization Header is available which will take precedence " +
                        "over the client credentials available as request parameters.");


            } catch (OAuthClientException e) {
                return handleBasicAuthFailure();
            }
        }


        try {
            OAuthUserInfoRequest oauthRequest = new OAuthUserInfoRequest(httpRequest);
            OAuth2UserInfoClient userInfoClient = new OAuth2UserInfoClient();
            // exchange the user information for the Access token.
            OAuth2UserInfoRespDTO oAuth2UserInfoRespDTO = userInfoClient.getUserInfo(oauthRequest);
            // if there BE has returned an error
            if (oAuth2UserInfoRespDTO.getError()) {
                // if the client has used Basic Auth and if there is an auth failure, HTTP 401 Status
                // Code should be sent back to the client.
                if (basicAuthUsed && OAuth2ErrorCodes.INVALID_CLIENT.equals(
                        oAuth2UserInfoRespDTO.getErrorCode())) {
                    return handleBasicAuthFailure();
                }
                // Otherwise send back HTTP 400 Status Code
                OAuthResponse response = OAuthResponse.errorResponse(
                        HttpServletResponse.SC_BAD_REQUEST).setError(
                        oAuth2UserInfoRespDTO.getErrorCode()).setErrorDescription(
                        oAuth2UserInfoRespDTO.getErrorMsg()).buildJSONMessage();
                return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
            } else {
                OAuthResponse response = OAuthUserInfoResponse.userInfoResponse(HttpServletResponse.SC_OK)
                        .setAuthroizedUser(oAuth2UserInfoRespDTO.getAuthorizedUser())
                        .setUserFullName(oAuth2UserInfoRespDTO.getUserFullName())
                        .setUserEmail(oAuth2UserInfoRespDTO.getUserEmail())
                        .buildJSONMessage();

                Response.ResponseBuilder respBuilder = Response
                        .status(response.getResponseStatus())
                        .header(OAuthConstants.HTTP_RESP_HEADER_CACHE_CONTROL,
                                OAuthConstants.HTTP_RESP_HEADER_VAL_CACHE_CONTROL_NO_STORE)
                        .header(OAuthConstants.HTTP_RESP_HEADER_PRAGMA,
                                OAuthConstants.HTTP_RESP_HEADER_VAL_PRAGMA_NO_CACHE);

                return respBuilder.entity(response.getBody()).build();
            }

        } catch (OAuthClientException e) {
            return handleOAuthClientException();
        } catch (OAuthProblemException e) {
            return handleOAuthProblemException();
        }
    }

    private Response handleBasicAuthFailure() throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setError(OAuth2ErrorCodes.INVALID_CLIENT)
                .setErrorDescription("Client Authentication was failed.").buildJSONMessage();
        return Response.status(response.getResponseStatus())
                .header(OAuthConstants.HTTP_RESP_HEADER_AUTHENTICATE, OAuthUIUtil.getRealmInfo())
                .entity(response.getBody()).build();
    }

    private Response handleOAuthProblemException() throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuth2ErrorCodes.INVALID_REQUEST)
                .setErrorDescription("Cannot find required parameters.").buildJSONMessage();

        return Response.status(response.getResponseStatus())
                .header(OAuthConstants.HTTP_RESP_HEADER_AUTHENTICATE, OAuthUIUtil.getRealmInfo())
                .entity(response.getBody()).build();
    }

    private Response handleOAuthClientException() throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                .setError(OAuth2ErrorCodes.SERVER_ERROR)
                .setErrorDescription("Error while invoking OAuth2 service.").buildJSONMessage();

        return Response.status(response.getResponseStatus())
                .header(OAuthConstants.HTTP_RESP_HEADER_AUTHENTICATE, OAuthUIUtil.getRealmInfo())
                .entity(response.getBody()).build();
    }

    private void logUserInfoRequest(HttpServletRequestWrapper httpRequest) {
    }
}
