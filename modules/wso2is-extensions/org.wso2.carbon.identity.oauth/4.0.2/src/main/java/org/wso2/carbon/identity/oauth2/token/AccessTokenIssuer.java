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

package org.wso2.carbon.identity.oauth2.token;

import net.sf.jsr107cache.Cache;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.message.types.GrantType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.core.model.OAuthAppDO;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.identity.oauth.IdentityOAuthAdminException;
import org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration;
import org.wso2.carbon.identity.oauth.dao.OAuthAppDAO;
import org.wso2.carbon.identity.oauth.internal.OAuthComponentServiceHolder;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.ResponseHeader;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenReqDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenRespDTO;
import org.wso2.carbon.identity.oauth2.token.handlers.*;
import org.wso2.carbon.identity.oauth2.util.OAuth2Util;
import org.wso2.carbon.user.api.Claim;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.carbon.utils.CarbonUtils;

import java.util.*;

public class AccessTokenIssuer {

    private Map<String, AuthorizationGrantHandler> authzGrantHandlers =
            new Hashtable<String, AuthorizationGrantHandler>();

    private List<String> supportedGrantTypes;

    private static AccessTokenIssuer instance;

    private static Log log = LogFactory.getLog(AccessTokenIssuer.class);
    private Cache userClaimsCache;
    private Cache appInfoCache;

    public static AccessTokenIssuer getInstance() throws IdentityOAuth2Exception {

        CarbonUtils.checkSecurity();

        if (instance == null) {
            synchronized (AccessTokenIssuer.class) {
                if (instance == null) {
                    instance = new AccessTokenIssuer();
                }
            }
        }
        return instance;
    }

    private AccessTokenIssuer() throws IdentityOAuth2Exception {

        supportedGrantTypes = OAuthServerConfiguration.getInstance().getSupportedGrantTypes();

        authzGrantHandlers.put(GrantType.AUTHORIZATION_CODE.toString(),
                new AuthorizationCodeHandler());
        authzGrantHandlers.put(GrantType.PASSWORD.toString(),
                new PasswordGrantHandler());
        authzGrantHandlers.put(GrantType.CLIENT_CREDENTIALS.toString(),
                new ClientCredentialsGrantHandler());
        authzGrantHandlers.put(GrantType.REFRESH_TOKEN.toString(),
                new RefreshGrantTypeHandler());
        authzGrantHandlers.put(GrantType.SAML20_BEARER_ASSERTION.toString(),
                new SAML2BearerGrantTypeHandler());

        //TODO: check userClaimsCache = PrivilegedCarbonContext.getCurrentContext().getCache("UserClaimsCache");
        //in org.wso2.carbon.apimgt.impl.token.DefaultClaimsRetriever
        userClaimsCache = PrivilegedCarbonContext.getCurrentContext().getCache("UserClaimsCache");
        appInfoCache = PrivilegedCarbonContext.getCurrentContext().getCache("AppInfoCache");
    }

    public OAuth2AccessTokenRespDTO issue(OAuth2AccessTokenReqDTO tokenReqDTO)
            throws IdentityException {

        String grantType = tokenReqDTO.getGrantType();
        OAuth2AccessTokenRespDTO tokenRespDTO;

        if (!supportedGrantTypes.contains(grantType)) {
            //Do not change this log format as these logs use by external applications
            log.debug("Unsupported Grant Type : " + grantType +
                    " for client id : " + tokenReqDTO.getClientId());
            tokenRespDTO = handleError(OAuthError.TokenResponse.UNSUPPORTED_GRANT_TYPE,
                    "Unsupported Grant Type!", tokenReqDTO);
            return tokenRespDTO;
        }

        AuthorizationGrantHandler authzGrantHandler = authzGrantHandlers.get(
                grantType);
        OAuthAppDO oAuthAppDO = getAppInformation(tokenReqDTO);
        OAuthTokenReqMessageContext tokReqMsgCtx = new OAuthTokenReqMessageContext(tokenReqDTO);
        String applicationName = oAuthAppDO.getApplicationName();
        boolean isAuthenticated = authzGrantHandler.authenticateClient(tokReqMsgCtx);
        /**
         * In the SAML2 bearer OAuth handling scenario, we're setting the resource owner username in the
         * authenticateClient() method. So, we need to call it before getting the username.
         */
        String userName = tokenReqDTO.getResourceOwnerUsername();
        //boolean isAuthenticated = true;
        if (!isAuthenticated) {
            //Do not change this log format as these logs use by external applications
            log.debug("Client Authentication Failed for client id=" + tokenReqDTO.getClientId() + ", " +
                    "user-name=" + userName + " to application=" + applicationName);
            tokenRespDTO = handleError(OAuthError.TokenResponse.INVALID_CLIENT,
                    "Client credentials are invalid.", tokenReqDTO);
            return tokenRespDTO;
        }

        boolean isValidGrant = authzGrantHandler.validateGrant(tokReqMsgCtx);
        //boolean isValidGrant = true;
        if (!isValidGrant) {
            //Do not change this log format as these logs use by external applications
            log.debug("Invalid Grant provided by the client, id=" + tokenReqDTO.getClientId() + ", " +
                    "" + "user-name=" + userName + " to application=" + applicationName);
            tokenRespDTO = handleError(OAuthError.TokenResponse.INVALID_GRANT,
                    "Provided Authorization Grant is invalid.", tokenReqDTO);
            return tokenRespDTO;
        }

        boolean isAuthorized = authzGrantHandler.authorizeAccessDelegation(tokReqMsgCtx);
        //boolean isAuthorized = true;
        if (!isAuthorized) {
            //Do not change this log format as these logs use by external applications
            log.debug("Resource owner is not authorized to grant access, client-id="
                    + tokenReqDTO.getClientId() + " " + "user-name=" + userName + " to application=" + applicationName);
            tokenRespDTO = handleError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT,
                    "Unauthorized Client!", tokenReqDTO);
            return tokenRespDTO;
        }

        boolean isValidScope = authzGrantHandler.validateScope(tokReqMsgCtx);
        //boolean isValidScope = true;
        if (!isValidScope) {
            //Do not change this log format as these logs use by external applications
            log.debug("Invalid Scope provided. client-id=" + tokenReqDTO.getClientId() + " " +
                    "" + "user-name=" + userName + " to application=" + applicationName);
            tokenRespDTO = handleError(OAuthError.TokenResponse.INVALID_SCOPE, "Invalid Scope!", tokenReqDTO);
            return tokenRespDTO;
        }

        int tenantId;
        
        ArrayList<ResponseHeader> respHeaders = new ArrayList<ResponseHeader>();
         if (tokenReqDTO.getResourceOwnerUsername() != null) {
         // this is only with the resource owner grant type
        try {
            tenantId = IdentityUtil.getTenantIdOFUser(tokenReqDTO.getResourceOwnerUsername());
            RealmService realmService = OAuthComponentServiceHolder.getRealmService();
            UserStoreManager userStoreManager = realmService.getTenantUserRealm(tenantId)
                    .getUserStoreManager();

            // Read the required claim configuration.
            List<String> reqRespHeaderClaims = getClaimUrisRequiredInResponseHeader();

            if (reqRespHeaderClaims != null && reqRespHeaderClaims.size() > 0) {
                // Get user's claim values from the default profile.
                Claim[] mapClaimValues = getUserClaimValues(tokenReqDTO, userStoreManager);
                ResponseHeader header;
                int i = 0;
                for (Iterator<String> iterator = reqRespHeaderClaims.iterator(); iterator.hasNext(); ) {

                    String claimUri = iterator.next();

                    for (int j = 0; j < mapClaimValues.length; j++) {
                        Claim claim = mapClaimValues[j];
                        if (claimUri.equals(claim.getClaimUri())) {
                            header = new ResponseHeader();
                            header.setKey(claim.getDisplayTag());
                            header.setValue(claim.getValue());
                            respHeaders.add(header);
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new IdentityOAuth2Exception(e.getMessage(), e);
        }
        }

        tokenRespDTO = authzGrantHandler.issue(tokReqMsgCtx);
        tokenRespDTO.setCallbackURI(oAuthAppDO.getCallbackUrl());

        ResponseHeader[] respHeadersArr = new ResponseHeader[respHeaders.size()];

        tokenRespDTO.setRespHeaders(respHeaders.toArray(respHeadersArr));

        //Do not change this log format as these logs use by external applications
        if (log.isDebugEnabled()) {
            log.debug("Access Token issued to client. client-id=" + tokenReqDTO.getClientId() + " " +
                    "" + "user-name=" + userName + " to application=" + applicationName);
        }
        return tokenRespDTO;
    }

    private OAuthAppDO getAppInformation(OAuth2AccessTokenReqDTO tokenReqDTO) throws IdentityOAuthAdminException {
        OAuthAppDO oAuthAppDO;
        Object obj = appInfoCache.get(tokenReqDTO.getClientId());
        if(obj != null){
            oAuthAppDO = (OAuthAppDO)obj;
            return oAuthAppDO;
        }else{
            oAuthAppDO = new OAuthAppDAO().getAppInformation(tokenReqDTO.getClientId());
            appInfoCache.put(tokenReqDTO.getClientId(),oAuthAppDO);
            return oAuthAppDO;
        }
    }

    private Claim[] getUserClaimValues(OAuth2AccessTokenReqDTO tokenReqDTO, UserStoreManager userStoreManager) throws UserStoreException {
        Claim[] userClaims;
        Object obj = userClaimsCache.get(tokenReqDTO.getResourceOwnerUsername());
        if(obj != null){
            userClaims = (Claim[])obj;
            return userClaims;
        }else{
            System.out.println("Cache miss for user claims. Username :"+tokenReqDTO.getResourceOwnerUsername());
            userClaims = userStoreManager.getUserClaimValues(
                    tokenReqDTO.getResourceOwnerUsername(), null);
            userClaimsCache.put(tokenReqDTO.getResourceOwnerUsername(),userClaims);
            return userClaims;
        }
    }

    private List<String> getClaimUrisRequiredInResponseHeader() {
        return OAuthServerConfiguration.getInstance().getRequiredHeaderClaimUris();
    }

    private OAuth2AccessTokenRespDTO handleError(String errorCode,
                                                 String errorMsg,
                                                 OAuth2AccessTokenReqDTO tokenReqDTO) {
        if (log.isDebugEnabled()) {
            log.debug("OAuth-Error-Code=" + errorCode + " client-id=" + tokenReqDTO.getClientId()
                    + " grant-type=" + tokenReqDTO.getGrantType()
                    + " scope=" + OAuth2Util.buildScopeString(tokenReqDTO.getScope()));
        }
        OAuth2AccessTokenRespDTO tokenRespDTO;
        tokenRespDTO = new OAuth2AccessTokenRespDTO();
        tokenRespDTO.setError(true);
        tokenRespDTO.setErrorCode(errorCode);
        tokenRespDTO.setErrorMsg(errorMsg);
        return tokenRespDTO;
    }

}
