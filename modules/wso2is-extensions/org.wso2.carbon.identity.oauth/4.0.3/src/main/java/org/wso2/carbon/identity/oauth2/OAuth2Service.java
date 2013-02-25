/*
*Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.carbon.identity.oauth2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.caching.core.CacheEntry;
import org.wso2.carbon.caching.core.CacheKey;
import org.wso2.carbon.core.AbstractAdmin;
import org.wso2.carbon.identity.core.model.OAuthAppDO;
import org.wso2.carbon.identity.oauth.IdentityOAuthAdminException;
import org.wso2.carbon.identity.oauth.cache.OAuthCache;
import org.wso2.carbon.identity.oauth.cache.OAuthCacheKey;
import org.wso2.carbon.identity.oauth.common.OAuth2ErrorCodes;
import org.wso2.carbon.identity.oauth.common.exception.InvalidOAuthClientException;
import org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration;
import org.wso2.carbon.identity.oauth.dao.OAuthAppDAO;
import org.wso2.carbon.identity.oauth.preprocessor.TokenPersistencePreprocessor;
import org.wso2.carbon.identity.oauth2.authz.AuthorizationHandlerManager;
import org.wso2.carbon.identity.oauth2.dao.TokenMgtDAO;
import org.wso2.carbon.identity.oauth2.dto.*;
import org.wso2.carbon.identity.oauth2.model.AccessTokenDO;
import org.wso2.carbon.identity.oauth2.token.AccessTokenIssuer;
import org.wso2.carbon.identity.oauth2.util.OAuth2Util;
import org.wso2.carbon.user.core.UserRealm;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.UserStoreManager;

/**
 * OAuth2 Service which is used to issue authorization codes or access tokens upon authorizing by the
 * user and issue/validateGrant access tokens.
 */
@SuppressWarnings("unused")
public class OAuth2Service extends AbstractAdmin {

    private static Log log = LogFactory.getLog(OAuth2Service.class);

    /**
     * Process the authorization request and issue an authorization code or access token depending
     * on the Response Type available in the request.
     *
     * @param oAuth2AuthorizeReqDTO <code>OAuth2AuthorizeReqDTO</code> containing information about the authorization
     *                              request.
     * @return <code>OAuth2AuthorizeRespDTO</code> instance containing the access token/authorization code
     *         or an error code.
     */
    public OAuth2AuthorizeRespDTO authorize(OAuth2AuthorizeReqDTO oAuth2AuthorizeReqDTO) {

        if (log.isDebugEnabled()) {
            log.debug("Authorization Request received for user : " + oAuth2AuthorizeReqDTO.getUsername() +
                    ", Client ID : " + oAuth2AuthorizeReqDTO.getConsumerKey() +
                    ", Authorization Response Type : " + oAuth2AuthorizeReqDTO.getResponseType() +
                    ", Requested callback URI : " + oAuth2AuthorizeReqDTO.getCallbackUrl() +
                    ", Requested Scope : " + OAuth2Util.buildScopeString(
                    oAuth2AuthorizeReqDTO.getScopes()));
        }

        try {
            AuthorizationHandlerManager authzHandlerManager =
                    AuthorizationHandlerManager.getInstance();
            return authzHandlerManager.handleAuthorization(oAuth2AuthorizeReqDTO);
        } catch (Exception e) {
            log.error("Error occurred when processing the authorization request. " +
                    "Returning an error back to client.", e);
            OAuth2AuthorizeRespDTO authorizeRespDTO = new OAuth2AuthorizeRespDTO();
            authorizeRespDTO.setAuthorized(false);
            authorizeRespDTO.setErrorCode(OAuth2ErrorCodes.SERVER_ERROR);
            authorizeRespDTO.setErrorMsg("Error occurred when processing the authorization " +
                    "request. Returning an error back to client.");
            authorizeRespDTO.setCallbackURI(oAuth2AuthorizeReqDTO.getCallbackUrl());
            return authorizeRespDTO;
        }
    }

    /**
     * Check Whether the provided client_id and the callback URL are valid.
     *
     * @param clientId    client_id available in the request, Not null parameter.
     * @param callbackURI callback_uri available in the request, can be null.
     * @return <code>OAuth2ClientValidationResponseDTO</code> bean with validity information,
     *         callback, App Name, Error Code and Error Message when appropriate.
     */
    public OAuth2ClientValidationResponseDTO validateClientInfo(String clientId, String callbackURI) {
        OAuth2ClientValidationResponseDTO validationResponseDTO =
                new OAuth2ClientValidationResponseDTO();

        if (log.isDebugEnabled()) {
            log.debug("Validate Client information request for client_id : " + clientId +
                    " and callback_uri " + callbackURI);
        }

        try {
            OAuthAppDAO oAuthAppDAO = new OAuthAppDAO();
            OAuthAppDO appDO = oAuthAppDAO.getAppInformation(clientId);

            // Valid Client, No callback has provided. Use the callback provided during the registration.
            if (callbackURI == null) {
                validationResponseDTO.setValidClient(true);
                validationResponseDTO.setCallbackURL(appDO.getCallbackUrl());
                validationResponseDTO.setApplicationName(appDO.getApplicationName());
                return validationResponseDTO;
            }

            if (log.isDebugEnabled()) {
                log.debug("Registered App found for the given Client Id : " + clientId +
                        " ,App Name : " + appDO.getApplicationName() +
                        ", Callback URL : " + appDO.getCallbackUrl());
            }

            // Valid Client with a callback url in the request. Check whether they are equal.
            if (appDO.getCallbackUrl().equals(callbackURI)) {
                validationResponseDTO.setValidClient(true);
                validationResponseDTO.setApplicationName(appDO.getApplicationName());
                validationResponseDTO.setCallbackURL(callbackURI);
                return validationResponseDTO;
            } else {    // Provided callback URL does not match the registered callback url.
                log.warn("Provided Callback URL does not match with the provided one.");
                validationResponseDTO.setValidClient(false);
                validationResponseDTO.setErrorCode(OAuth2ErrorCodes.INVALID_CALLBACK);
                validationResponseDTO.setErrorMsg("Registered callback does not match with the provided url.");
                return validationResponseDTO;
            }
        } catch (InvalidOAuthClientException e) {
            // There is no such Client ID being registered. So it is a request from an invalid client.
            log.debug(e.getMessage());
            validationResponseDTO.setValidClient(false);
            validationResponseDTO.setErrorCode(OAuth2ErrorCodes.INVALID_CLIENT);
            validationResponseDTO.setErrorMsg(e.getMessage());
            return validationResponseDTO;
        } catch (IdentityOAuthAdminException e) {
            log.error("Error when reading the Application Information.", e);
            validationResponseDTO.setValidClient(false);
            validationResponseDTO.setErrorCode(OAuth2ErrorCodes.SERVER_ERROR);
            validationResponseDTO.setErrorMsg("Error when processing the authorization request.");
            return validationResponseDTO;
        }
    }

    /**
     * Issue access token in exchange to an Authorization Grant.
     *
     * @param tokenReqDTO <Code>OAuth2AccessTokenReqDTO</Code> representing the Access Token request
     * @return <Code>OAuth2AccessTokenRespDTO</Code> representing the Access Token response
     */
    public OAuth2AccessTokenRespDTO issueAccessToken(OAuth2AccessTokenReqDTO tokenReqDTO) {

        if (log.isDebugEnabled()) {
            log.debug("Access Token Request Received with the Client Id : " + tokenReqDTO.getClientId() +
                    ", Grant Type : " + tokenReqDTO.getGrantType());
        }

        try {
            AccessTokenIssuer tokenIssuer = AccessTokenIssuer.getInstance();
            return tokenIssuer.issue(tokenReqDTO);
        } catch (InvalidOAuthClientException e) {
            log.debug(e.getMessage());
            OAuth2AccessTokenRespDTO tokenRespDTO = new OAuth2AccessTokenRespDTO();
            tokenRespDTO.setError(true);
            tokenRespDTO.setErrorCode(OAuth2ErrorCodes.INVALID_CLIENT);
            tokenRespDTO.setErrorMsg(e.getMessage());
            return tokenRespDTO;
        } catch (Exception e) { // in case of an error, consider it as a system error
            log.error("Error when issuing the access token. ", e);
            OAuth2AccessTokenRespDTO tokenRespDTO = new OAuth2AccessTokenRespDTO();
            tokenRespDTO.setError(true);
            tokenRespDTO.setErrorCode(OAuth2ErrorCodes.SERVER_ERROR);
            tokenRespDTO.setErrorMsg("Error when issuing the access token");
            return tokenRespDTO;
        }
    }

    /**
     * Issue user information by validating the access token.
     *
     * @param userInfoReqDTO <Code>OAuth2UserInfoReqDTO</Code> representing the User Information request
     * @return <Code>OAuth2UserInfoRespDTO</Code> representing the User Information response
     */
    public OAuth2UserInfoRespDTO getUserInformation(OAuth2UserInfoReqDTO userInfoReqDTO) {

        TokenMgtDAO tokenMgtDAO = new TokenMgtDAO();
        OAuth2UserInfoRespDTO userInfoRespDTO = new OAuth2UserInfoRespDTO();

        String accessToken = userInfoReqDTO.getAccessToken();
        String clientId = userInfoReqDTO.getClientId();

        // incomplete user information request
        if (accessToken == null || clientId == null) {
            log.warn("Client Id or Access Token is not present");
            userInfoRespDTO.setError(true);
            userInfoRespDTO.setErrorCode(OAuth2ErrorCodes.INVALID_REQUEST);
            userInfoRespDTO.setErrorMsg("Client Id or Access Token is not present " +
                    "in the user information request.");
            return userInfoRespDTO;
        }

        AccessTokenDO accessTokenDO = null;

        try {
            TokenPersistencePreprocessor persistencePreprocessor = OAuthServerConfiguration.getInstance().getTokenPersistencePreprocessor();
            String preprocessedAccessToken = persistencePreprocessor.getPreprocessedToken(accessToken);

            boolean cacheHit = false;
            // Check the cache, if caching is enabled.
            if (OAuthServerConfiguration.getInstance().isCacheEnabled()) {
                OAuthCache oauthCache = OAuthCache.getInstance();
                CacheKey cacheKey = new OAuthCacheKey(preprocessedAccessToken);
                CacheEntry result = oauthCache.getValueFromCache(cacheKey);
                // cache hit, do the type check.
                if (result instanceof AccessTokenDO) {
                    accessTokenDO = (AccessTokenDO) result;
                    cacheHit = true;
                }
            }
            // Cache miss, load the access token info from the database.
            if (accessTokenDO == null) {
                accessTokenDO = tokenMgtDAO.validateBearerToken(preprocessedAccessToken);
                if (accessTokenDO != null){
                    UserRealm ur = getUserRealm();
                    UserStoreManager um = ur.getUserStoreManager();
                    accessTokenDO.setUserFullName(um.getUserClaimValue(accessTokenDO.getAuthzUser(), "http://wso2.org/claims/givenname" , "default")
                    + " "+ um.getUserClaimValue(accessTokenDO.getAuthzUser(),"http://wso2.org/claims/lastname" , "default"));
                    accessTokenDO.setUserEmail(um.getUserClaimValue(accessTokenDO.getAuthzUser(), "http://wso2.org/claims/emailaddress", "default"));
                }
            }

            // if the access token or client id is not valid
            if (accessTokenDO == null) {
                log.warn("Invalid Access Token or Client Id. " +
                        "Access Token : " + accessToken);
                userInfoRespDTO.setErrorMsg("Invalid Access Token or Client Id.");
                userInfoRespDTO.setError(true);
                userInfoRespDTO.setErrorCode(OAuth2ErrorCodes.INVALID_REQUEST);
                return userInfoRespDTO;
            }

            // Check whether the grant is expired
            long issuedTimeInMillis = accessTokenDO.getIssuedTime().getTime();
            long validityPeriodInMillis = accessTokenDO.getValidityPeriod();
            long timestampSkew = OAuthServerConfiguration.getInstance()
                    .getDefaultTimeStampSkewInSeconds() * 1000;
            long currentTimeInMillis = System.currentTimeMillis();

            if ((currentTimeInMillis - timestampSkew) > (issuedTimeInMillis + validityPeriodInMillis)) {
                log.warn("Access Token is expired.");
                if (log.isDebugEnabled()) {
                    log.debug("Access Token : " + accessToken + " is expired." +
                            " Issued Time(ms) : " + issuedTimeInMillis +
                            ", Validity Period : " + validityPeriodInMillis +
                            ", Timestamp Skew : " + timestampSkew +
                            ", Current Time : " + currentTimeInMillis);
                }
                userInfoRespDTO.setError(true);
                userInfoRespDTO.setErrorMsg("Access Token is expired");
                userInfoRespDTO.setErrorCode(OAuth2ErrorCodes.INVALID_REQUEST);
                return userInfoRespDTO;
            }

            userInfoRespDTO.setAuthorizedUser(accessTokenDO.getAuthzUser());
            userInfoRespDTO.setUserFullName(accessTokenDO.getUserFullName());
            userInfoRespDTO.setUserEmail(accessTokenDO.getUserEmail());

            // Add the token back to the cache in the case of a cache miss
            if (OAuthServerConfiguration.getInstance().isCacheEnabled() && !cacheHit) {
                OAuthCache oauthCache = OAuthCache.getInstance();
                CacheKey cacheKey = new OAuthCacheKey(preprocessedAccessToken);
                oauthCache.addToCache(cacheKey, accessTokenDO);
                if (log.isDebugEnabled()) {
                    log.debug("Access Token Info object was added back to the cache.");
                }
            }
            return userInfoRespDTO;
        } catch (IdentityOAuth2Exception e) {
            log.error("Error when reading the Request Information.", e);
            userInfoRespDTO.setErrorMsg("Error when processing the user information request.");
            return userInfoRespDTO;
        } catch (UserStoreException e) {
            log.error("Error when getting user information",e);
            userInfoRespDTO.setErrorMsg("Error ahen getting user information");
            return userInfoRespDTO;
        }

    }


}
