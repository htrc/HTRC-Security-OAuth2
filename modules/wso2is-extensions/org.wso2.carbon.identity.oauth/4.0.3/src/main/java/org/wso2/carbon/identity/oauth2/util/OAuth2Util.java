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

package org.wso2.carbon.identity.oauth2.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.caching.core.CacheEntry;
import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
import org.wso2.carbon.identity.oauth.IdentityOAuthAdminException;
import org.wso2.carbon.identity.oauth.cache.OAuthCache;
import org.wso2.carbon.identity.oauth.cache.OAuthCacheKey;
import org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration;
import org.wso2.carbon.identity.oauth.dao.OAuthConsumerDAO;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.model.AccessTokenDO;
import org.wso2.carbon.identity.oauth2.model.ClientCredentialDO;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

import java.sql.Timestamp;

/**
 * Utility methods for OAuth 2.0 implementation
 */
public class OAuth2Util {

    private static Log log = LogFactory.getLog(OAuth2Util.class);
    private static boolean cacheEnabled = OAuthServerConfiguration.getInstance().isCacheEnabled();
    private static OAuthCache cache = OAuthCache.getInstance();
    public static String USER_TYPE_FOR_USER_TOKEN = "APPLICATION_USER";

    /**
     * Build a comma separated list of scopes passed as a String set by Amber.
     *
     * @param scopes set of scopes
     * @return Comma separated list of scopes
     */
    public static String buildScopeString(String[] scopes) {
        StringBuilder scopeString = new StringBuilder("");
        if (scopes != null) {
            for (int i=0; i<scopes.length; i++) {
                scopeString.append(scopes[i].trim());
                if(i != scopes.length-1){
                    scopeString.append(" ");
                }
            }
        }
        return scopeString.toString();
    }

    public static String[] buildScopeArray(String scopeStr) {
        scopeStr = scopeStr.trim();
        return scopeStr.split("\\s");
    }

    public static boolean authenticateUser(String username, String password) throws
            IdentityOAuth2Exception {
        try {
            String tenantUser = MultitenantUtils.getTenantAwareUsername(username);
            String domainName = MultitenantUtils.getTenantDomain(username);
            return IdentityTenantUtil.getRealm(domainName, username).getUserStoreManager()
                    .authenticate(tenantUser, password);
        } catch (Exception e) {
            log.error("Error when authenticating the user for OAuth Authorization.", e);
            // This is until the ReadOnlyLDAPUserStoreManager properly handles authentication
            // failures and return false instead of an exception. Otherwise authentication failures
            // will be sent back to client as server errors. So this is a temporary fix.
            return false;
//            throw new IdentityOAuth2Exception("Error when authenticating the user " +
//                    "for OAuth Authorization.", e);
        }
    }

    /**
     * Authenticate the OAuth Consumer
     *
     * @param clientId             Consumer Key/Id
     * @param clientSecretProvided Consumer Secret issued during the time of registration
     * @return true, if the authentication is successful, false otherwise.
     * @throws IdentityOAuthAdminException Error when looking up the credentials from the database
     */
    public static boolean authenticateClient(String clientId, String clientSecretProvided)
            throws IdentityOAuthAdminException {

        boolean cacheHit = false;
        String clientSecret = null;

        // Check the cache first.
        if (cacheEnabled) {
            CacheEntry cacheResult = cache.getValueFromCache(new OAuthCacheKey(clientId));
            if (cacheResult != null && cacheResult instanceof ClientCredentialDO) {
                // cache hit
                clientSecret = ((ClientCredentialDO) cacheResult).getClientSecret();
                cacheHit = true;
                if (log.isDebugEnabled()) {
                    log.debug("Client credentials were available in the cache for client id : " +
                            clientId);
                }
            }
        }
        // Cache miss
        if (clientSecret == null) {
            OAuthConsumerDAO oAuthConsumerDAO = new OAuthConsumerDAO();
            clientSecret = oAuthConsumerDAO.getOAuthConsumerSecret(clientId);
            if (log.isDebugEnabled()) {
                log.debug("Client credentials were fetched from the database.");
            }
        }

        if (clientSecret == null) {
            if (log.isDebugEnabled()) {
                log.debug("Provided Client ID : " + clientId + "is not valid.");
            }
            return false;
        }

        if (!clientSecret.equals(clientSecretProvided)) {
            if (log.isDebugEnabled()) {
                log.debug("Provided the Client ID : " + clientId +
                        " and Client Secret do not match with the issued credentials.");
            }
            return false;
        }

        if (log.isDebugEnabled()) {
            log.debug("Successfully authenticated the client with client id : " + clientId);
        }

        if (cacheEnabled && !cacheHit) {
            cache.addToCache(new OAuthCacheKey(clientId), new ClientCredentialDO(clientSecret));
            if (log.isDebugEnabled()) {
                log.debug("Client credentials were added to the cache for client id : " + clientId);
            }
        }

        return true;
    }

    /**
     * Authenticate the OAuth consumer and return the username of user which own the provided client id and client
     * secret.
     *
     * @param clientId             Consumer Key/Id
     * @param clientSecretProvided Consumer Secret issued during the time of registration
     * @return Username of the user which own client id and client secret if authentication is
     *         successful. Empty string otherwise.
     * @throws IdentityOAuthAdminException Error when looking up the credentials from the database
     */
    public static String getAuthenticatedUsername(String clientId, String clientSecretProvided)
            throws IdentityOAuthAdminException {

        boolean cacheHit = false;
        String username = null;

        if (OAuth2Util.authenticateClient(clientId, clientSecretProvided)) {
            // check cache
            if (cacheEnabled) {
                CacheEntry cacheResult = cache.getValueFromCache(new OAuthCacheKey(clientId + ":" + username));
                if (cacheResult != null && cacheResult instanceof ClientCredentialDO) {
                    // Ugh. This is fugly. Have to have a generic way of caching a key:value pair
                    username = ((ClientCredentialDO) cacheResult).getClientSecret();
                    cacheHit = true;
                    if (log.isDebugEnabled()) {
                        log.debug("Username was available in the cache : " +
                                username);
                    }
                }
            }

            if (username == null) {
                // Cache miss
                OAuthConsumerDAO oAuthConsumerDAO = new OAuthConsumerDAO();
                username = oAuthConsumerDAO.getAuthenticatedUsername(clientId, clientSecretProvided);
                log.debug("Username fetch from the database");
            }

            if ((username != null) && cacheEnabled && !cacheHit) {
                /**
                 * Using the same ClientCredentialDO to host username. Semantically wrong since ClientCredentialDo
                 * accept a client secret and we're storing a username in the secret variable. Do we have to make our
                 * own cache key and cache entry class every time we need to put something to it? Ideal solution is
                 * to have a generalized way of caching a key:value pair
                 */
                cache.addToCache(new OAuthCacheKey(clientId + ":" + username), new ClientCredentialDO(username));
                log.debug("Caching username : " + username);
            }
        }
        return username;
    }

    /**
     * Build the cache key string when storing Authz Code info in cache
     * @param clientId Client Id representing the client
     * @param authzCode Authorization Code issued to the client
     * @return concatenated <code>String</code> of clientId:authzCode
     */
    public static String buildCacheKeyStringForAuthzCode(String clientId, String authzCode) {
        return clientId + ":" + authzCode;
    }

    public static AccessTokenDO validateAccessTokenDO(AccessTokenDO accessTokenDO) {
        long timestampSkew;
        long currentTime;
        long validityPeriod = accessTokenDO.getValidityPeriod() * 1000;
        long issuedTime = accessTokenDO.getIssuedTime().getTime();
        timestampSkew = OAuthServerConfiguration.getInstance().getDefaultTimeStampSkewInSeconds() * 1000;
        currentTime = System.currentTimeMillis();
        //check the validity of cached OAuth2AccessToken Response
        if (((issuedTime + validityPeriod) - (currentTime)) > 1000) {
            //Set new validity period to response object
            accessTokenDO.setValidityPeriod(((issuedTime + validityPeriod) - (currentTime)) / 1000);
            //Set issued time period to response object
            accessTokenDO.setIssuedTime(new Timestamp(currentTime));
            return accessTokenDO;
        }
        //returns null if cached OAuth2AccessToken response object is expired
        return null;
    }
}
