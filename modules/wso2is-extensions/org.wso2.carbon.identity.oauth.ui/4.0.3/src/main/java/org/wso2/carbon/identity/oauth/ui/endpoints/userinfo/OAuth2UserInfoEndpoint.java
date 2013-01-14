package org.wso2.carbon.identity.oauth.ui.endpoints.userinfo;

import org.apache.amber.oauth2.as.request.OAuthTokenRequest;
import org.apache.amber.oauth2.as.response.OAuthASResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth.common.OAuth2ErrorCodes;
import org.wso2.carbon.identity.oauth.ui.OAuthClientException;
import org.wso2.carbon.identity.oauth.ui.OAuthConstants;
import org.wso2.carbon.identity.oauth.ui.endpoints.token.OAuth2TokenClient;
import org.wso2.carbon.identity.oauth.ui.endpoints.token.OAuthRequestWrapper;
import org.wso2.carbon.identity.oauth.ui.util.OAuthUIUtil;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2AccessTokenRespDTO;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoRespDTO;
import org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader;

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

    public Response issueUserInformation(@Context HttpServletRequest request, MultivaluedMap<String, String> paramMap) throws OAuthSystemException {

        HttpServletRequestWrapper httpRequest = new OAuthRequestWrapper(request, paramMap);

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
            OAuth2UserInfoRespDTO oAuth2UserInfoRespDTOResp = userInfoClient.getUserInfo(oauthRequest);
            // if there BE has returned an error
//            if (oAuth2UserInfoRespDTOResp.ge()) {
//                // if the client has used Basic Auth and if there is an auth failure, HTTP 401 Status
//                // Code should be sent back to the client.
//                if (basicAuthUsed && OAuth2ErrorCodes.INVALID_CLIENT.equals(
//                        oAuth2UserInfoRespDTOResp.getErrorCode())) {
//                    return handleBasicAuthFailure();
//                }
//                // Otherwise send back HTTP 400 Status Code
//                OAuthResponse response = OAuthASResponse.errorResponse(
//                        HttpServletResponse.SC_BAD_REQUEST).setError(
//                        oAuth2UserInfoRespDTOResp.getErrorCode()).setErrorDescription(
//                        oAuth2UserInfoRespDTOResp.getErrorMsg()).buildJSONMessage();
//                return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
//            } else {
//                OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
//                        .setAccessToken(oAuth2UserInfoRespDTOResp.getAccessToken())
//                        .setRefreshToken(oAuth2UserInfoRespDTOResp.getRefreshToken())
//                        .setExpiresIn(Long.toString(oAuth2UserInfoRespDTOResp.getExpiresIn()))
//                        .setTokenType("bearer").buildJSONMessage();
//                ResponseHeader[] headers = oAuth2UserInfoRespDTOResp.getRespHeaders();
//
//                Response.ResponseBuilder respBuilder = Response
//                        .status(response.getResponseStatus())
//                        .header(OAuthConstants.HTTP_RESP_HEADER_CACHE_CONTROL,
//                                OAuthConstants.HTTP_RESP_HEADER_VAL_CACHE_CONTROL_NO_STORE)
//                        .header(OAuthConstants.HTTP_RESP_HEADER_PRAGMA,
//                                OAuthConstants.HTTP_RESP_HEADER_VAL_PRAGMA_NO_CACHE);
//
//                if (headers != null && headers.length > 0) {
//                    for (int i = 0; i < headers.length; i++) {
//                        if (headers[i] != null) {
//                            respBuilder.header(headers[i].getKey(), headers[i].getValue());
//                        }
//                    }
//                }
//
//                return respBuilder.entity(response.getBody()).build();
//            }

        } catch (Exception e) {

        }

        return null;
    }

    private Response handleBasicAuthFailure() throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setError(OAuth2ErrorCodes.INVALID_CLIENT)
                .setErrorDescription("Client Authentication was failed.").buildJSONMessage();
        return Response.status(response.getResponseStatus())
                .header(OAuthConstants.HTTP_RESP_HEADER_AUTHENTICATE, OAuthUIUtil.getRealmInfo())
                .entity(response.getBody()).build();
    }

    private void logUserInfoRequest(HttpServletRequestWrapper httpRequest) {
    }
}
