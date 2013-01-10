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

package org.wso2.carbon.identity.oauth2.token.handlers;

import org.apache.amber.oauth2.as.issuer.MD5Generator;
import org.apache.amber.oauth2.as.issuer.OAuthIssuer;
import org.apache.amber.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.caching.core.CacheKey;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.core.persistence.JDBCPersistenceManager;
import org.wso2.carbon.identity.core.util.IdentityDatabaseUtil;
import org.wso2.carbon.identity.oauth.IdentityOAuthAdminException;
import org.wso2.carbon.identity.oauth.cache.OAuthCache;
import org.wso2.carbon.identity.oauth.cache.OAuthCacheKey;
import org.wso2.carbon.identity.oauth.callback.OAuthCallback;
import org.wso2.carbon.identity.oauth.callback.OAuthCallbackManager;
import org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration;
import org.wso2.carbon.identity.oauth.preprocessor.TokenPersistencePreprocessor;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.dao.TokenMgtDAO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenReqDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenRespDTO;
import org.wso2.carbon.identity.oauth2.model.AccessTokenDO;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.identity.oauth2.util.OAuth2Constants;
import org.wso2.carbon.identity.oauth2.util.OAuth2Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractAuthorizationGrantHandler implements AuthorizationGrantHandler {

    private static Log log = LogFactory.getLog(AbstractAuthorizationGrantHandler.class);

    protected TokenMgtDAO tokenMgtDAO = new TokenMgtDAO();
    protected final OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
    protected OAuthCallbackManager callbackManager;
    protected TokenPersistencePreprocessor tokenPersistencePreprocessor;
    protected boolean cacheEnabled;
    protected OAuthCache oauthCache;

    protected AbstractAuthorizationGrantHandler() throws IdentityOAuth2Exception {
        callbackManager = new OAuthCallbackManager();
        tokenPersistencePreprocessor = OAuthServerConfiguration
                .getInstance().getTokenPersistencePreprocessor();
        // Set the cache instance if caching is enabled.
        if (OAuthServerConfiguration.getInstance().isCacheEnabled()) {
            cacheEnabled = true;
            oauthCache = OAuthCache.getInstance();
        }
    }

    public boolean authenticateClient(OAuthTokenReqMessageContext tokReqMsgCtx)
            throws IdentityOAuth2Exception {
        OAuth2AccessTokenReqDTO oAuth2AccessTokenReqDTO = tokReqMsgCtx.getOauth2AccessTokenReqDTO();
        try {
            return OAuth2Util.authenticateClient(
                    oAuth2AccessTokenReqDTO.getClientId(),
                    oAuth2AccessTokenReqDTO.getClientSecret());
        } catch (IdentityOAuthAdminException e) {
            throw new IdentityOAuth2Exception(e.getMessage(), e);
        }
    }

    public abstract boolean validateGrant(OAuthTokenReqMessageContext tokReqMsgCtx)
            throws IdentityOAuth2Exception;

    public OAuth2AccessTokenRespDTO issue(OAuthTokenReqMessageContext tokReqMsgCtx)
            throws IdentityOAuth2Exception {

        OAuth2AccessTokenRespDTO tokenRespDTO;
        OAuth2AccessTokenReqDTO oAuth2AccessTokenReqDTO = tokReqMsgCtx.getOauth2AccessTokenReqDTO();

        String consumerKey = tokReqMsgCtx.getOauth2AccessTokenReqDTO().getClientId();
        String authorizedUser = tokReqMsgCtx.getAuthorizedUser();
        CacheKey cacheKey = new OAuthCacheKey(consumerKey + ":" + authorizedUser);

        synchronized ((consumerKey + ":" + authorizedUser).intern()) {
            try {
                //TODO Need to refactor this logic
                //First serve from the cache
                if (cacheEnabled) {
                    AccessTokenDO newAccessTokenDO = (AccessTokenDO) oauthCache.getValueFromCache(cacheKey);
                    if (newAccessTokenDO != null) {
                        AccessTokenDO accessTokenDO = OAuth2Util.validateAccessTokenDO(newAccessTokenDO);
                        if (accessTokenDO != null) {
                            tokenRespDTO = new OAuth2AccessTokenRespDTO();
                            tokenRespDTO.setAccessToken(accessTokenDO.getAccessToken());
                            tokenRespDTO.setRefreshToken(accessTokenDO.getRefreshToken());
                            tokenRespDTO.setExpiresIn(accessTokenDO.getValidityPeriod());
                            if (log.isDebugEnabled()) {
                                log.debug("Access Token info retrieved from the cache and served to client with client id : " +
                                        oAuth2AccessTokenReqDTO.getClientId());
                            }
                            oauthCache.addToCache(cacheKey, accessTokenDO);
                            return tokenRespDTO;
                        } else {
                            oauthCache.clearCacheEntry(cacheKey);
                            //Token is expired. Mark it as expired on database
                            //TODO : Read token state from a constant
                            tokenMgtDAO.setAccessTokenState(consumerKey, authorizedUser, "EXPIRED",
                                    UUID.randomUUID().toString(), OAuth2Util.USER_TYPE_FOR_USER_TOKEN);
                        }
                    }
                }

                //Check if previously issued token exists in database
                tokenRespDTO = tokenMgtDAO.getValidAccessTokenIfExist(oAuth2AccessTokenReqDTO.getClientId(),
                        tokReqMsgCtx.getAuthorizedUser());
                if (tokenRespDTO != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("Retrieving existing valid access token for client ID" + oAuth2AccessTokenReqDTO.getClientId());
                    }
                    if (cacheEnabled) {
                        AccessTokenDO accessTokenDO = new AccessTokenDO(tokReqMsgCtx.getAuthorizedUser(),
                                tokReqMsgCtx.getScope(), new Timestamp(System.currentTimeMillis()), tokenRespDTO.getExpiresIn());
                        accessTokenDO.setRefreshToken(tokenRespDTO.getRefreshToken());
                        accessTokenDO.setTokenState(OAuth2Constants.TokenStates.TOKEN_STATE_ACTIVE);
                        accessTokenDO.setAccessToken(tokenRespDTO.getAccessToken());
                        if (log.isDebugEnabled()) {
                            log.debug("Access Token info was added to the cache for the client id : " +
                                    oAuth2AccessTokenReqDTO.getClientId());
                        }
                        oauthCache.addToCache(cacheKey, accessTokenDO);
                    }
                    return tokenRespDTO;
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Marking old token as expired for client Id : "
                                + consumerKey + " AuthorizedUser : " + authorizedUser);
                    }
                    //Token is expired. Mark it as expired on database
                    //TODO : Read token state from a constant
                    //TODO : This should move to validation check of getValidAccessTokenIfExist() method
                    tokenMgtDAO.setAccessTokenState(consumerKey, authorizedUser, "EXPIRED",
                            UUID.randomUUID().toString(), OAuth2Util.USER_TYPE_FOR_USER_TOKEN);
                }
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Error while getting existing token for client ID" + oAuth2AccessTokenReqDTO.getClientId());
                }
            }

            //No valid access token is found in cache or database.
            //Need to issue a new access token.
            if (log.isDebugEnabled()) {
                log.debug("Issuing a new access token for "
                        + consumerKey + " AuthorizedUser : " + authorizedUser);
            }
            String accessToken;
            String refreshToken;
            try {
                accessToken = oauthIssuerImpl.accessToken();
                refreshToken = oauthIssuerImpl.refreshToken();
            } catch (OAuthSystemException e) {
                throw new IdentityOAuth2Exception("Error when generating the tokens.", e);
            }

            Timestamp timestamp = new Timestamp(new Date().getTime());
            // Default Validity Period (in seconds)
            long validityPeriod = OAuthServerConfiguration.getInstance()
                    .getDefaultAccessTokenValidityPeriodInSeconds();

            // if a VALID validity period is set through the callback, then use it
            long callbackValidityPeriod = tokReqMsgCtx.getValidityPeriod();
            if ((callbackValidityPeriod != OAuth2Constants.UNASSIGNED_VALIDITY_PERIOD)
                    && callbackValidityPeriod > 0) {
                validityPeriod = callbackValidityPeriod;
            }

            // validityPeriod = validityPeriod * 1000;
            // Get the secured versions of the tokens to persist and to cache.
            String preprocessedAccessToken = tokenPersistencePreprocessor
                    .getPreprocessedToken(accessToken);
            String preprocessedRefreshToken = tokenPersistencePreprocessor
                    .getPreprocessedToken(refreshToken);

            AccessTokenDO accessTokenDO = new AccessTokenDO(tokReqMsgCtx.getAuthorizedUser(),
                    tokReqMsgCtx.getScope(), timestamp, validityPeriod);
            accessTokenDO.setRefreshToken(preprocessedRefreshToken);
            accessTokenDO.setTokenState(OAuth2Constants.TokenStates.TOKEN_STATE_ACTIVE);
            accessTokenDO.setAccessToken(preprocessedAccessToken);

            // store new token
            tokenMgtDAO.storeAccessToken(preprocessedAccessToken,
                    oAuth2AccessTokenReqDTO.getClientId(),
                    accessTokenDO);

            if (log.isDebugEnabled()) {
                log.debug("Persisted an access token with " +
                        "Client ID : " + oAuth2AccessTokenReqDTO.getClientId() +
                        "authorized user : " + tokReqMsgCtx.getAuthorizedUser() +
                        "timestamp : " + timestamp +
                        "validity period : " + validityPeriod +
                        "scope : " + OAuth2Util.buildScopeString(tokReqMsgCtx.getScope()) +
                        "Token State : " + OAuth2Constants.TokenStates.TOKEN_STATE_ACTIVE);
            }

            //update cache with newly added token
            if (cacheEnabled) {
                oauthCache.addToCache(cacheKey, accessTokenDO);
                if (log.isDebugEnabled()) {
                    log.debug("Access Token info was added to the cache for the client id : " +
                            oAuth2AccessTokenReqDTO.getClientId());
                }
            }
            tokenRespDTO = new OAuth2AccessTokenRespDTO();
            tokenRespDTO.setAccessToken(accessToken);
            tokenRespDTO.setRefreshToken(refreshToken);
            tokenRespDTO.setExpiresIn(validityPeriod);
            return tokenRespDTO;
        }

       
    }

    public boolean authorizeAccessDelegation(OAuthTokenReqMessageContext tokReqMsgCtx)
            throws IdentityOAuth2Exception {
        OAuthCallback authzCallback = new OAuthCallback(
                tokReqMsgCtx.getAuthorizedUser(),
                tokReqMsgCtx.getOauth2AccessTokenReqDTO().getClientId(),
                OAuthCallback.OAuthCallbackType.ACCESS_DELEGATION_TOKEN);
        authzCallback.setRequestedScope(tokReqMsgCtx.getScope());
        authzCallback.setGrantType(GrantType.valueOf(
                tokReqMsgCtx.getOauth2AccessTokenReqDTO().getGrantType().toUpperCase()));

        callbackManager.handleCallback(authzCallback);
        tokReqMsgCtx.setValidityPeriod(authzCallback.getValidityPeriod());
        return authzCallback.isAuthorized();
    }

    public boolean validateScope(OAuthTokenReqMessageContext tokReqMsgCtx)
            throws IdentityOAuth2Exception {
        OAuthCallback scopeValidationCallback = new OAuthCallback(
                tokReqMsgCtx.getAuthorizedUser(),
                tokReqMsgCtx.getOauth2AccessTokenReqDTO().getClientId(),
                OAuthCallback.OAuthCallbackType.SCOPE_VALIDATION_TOKEN);
        scopeValidationCallback.setRequestedScope(tokReqMsgCtx.getScope());
        scopeValidationCallback.setGrantType(GrantType.valueOf(
                tokReqMsgCtx.getOauth2AccessTokenReqDTO().getGrantType().toUpperCase()));

        callbackManager.handleCallback(scopeValidationCallback);
        tokReqMsgCtx.setValidityPeriod(scopeValidationCallback.getValidityPeriod());
        tokReqMsgCtx.setScope(scopeValidationCallback.getApprovedScope());
        return scopeValidationCallback.isValidScope();
    }
}
