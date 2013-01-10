/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.sts.store;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rahas.STSConstants;
import org.apache.rahas.Token;
import org.apache.rahas.TokenStorage;
import org.apache.rahas.TrustException;
import org.wso2.carbon.caching.core.CacheInvalidator;
import org.wso2.carbon.identity.sts.store.dao.DBStsDAO;
import org.wso2.carbon.identity.sts.store.internal.STSStoreComponent;
import org.wso2.carbon.identity.sts.store.util.STSStoreUtils;
import org.wso2.carbon.utils.CarbonUtils;

import javax.xml.stream.XMLStreamException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBTokenStore implements TokenStorage {

    private DBStsDAO dbStsDAO;
    private static Log log = LogFactory.getLog(DBTokenStore.class);
    private static int poolSize = 100;
    private static Cache tokenCache = CarbonUtils.getLocalCache(STSMgtConstants.TOKEN_CACHE_ID);
    private static ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public void add(Token token) throws TrustException {
        //put the Token to cache.
        tokenCache.put(token.getId(), STSStoreUtils.getSerializableToken(token));
        executorService.submit(new TokenPersisterTask(token));
    }

    public void update(Token token) throws TrustException {
        initDao();
        dbStsDAO.updateToken(token);
        //update the cache is that token present in cache
        if (tokenCache != null && tokenCache.containsKey(token.getId())) {
            tokenCache.put(token.getId(), STSStoreUtils.getSerializableToken(token));
        }
    }

    public String[] getTokenIdentifiers() throws TrustException {
        initDao();
        return dbStsDAO.getAllTokenKeys();
    }

    public Token[] getExpiredTokens() throws TrustException {
        initDao();
        return dbStsDAO.getExpiredTokens(Token.EXPIRED);
    }

    public Token[] getValidTokens() throws TrustException {
        initDao();
        return dbStsDAO.getValidTokens(new int[]{Token.ISSUED, Token.RENEWED});
    }

    public Token[] getRenewedTokens() throws TrustException {
        initDao();
        return dbStsDAO.getRenewedTokens(Token.RENEWED);
    }

    public Token[] getCancelledTokens() throws TrustException {
        initDao();
        return dbStsDAO.getCancelledTokens(Token.CANCELLED);
    }

    public Token getToken(String id) throws TrustException {
        if (tokenCache != null && tokenCache.containsKey(id)) {
            try {
                return STSStoreUtils.getToken((SerializableToken) tokenCache.get(id));
            } catch (XMLStreamException e) {
               throw new TrustException("Failed to get Token from cache",e);
            }
        }
        initDao();
        Token token = dbStsDAO.getToken(id);

        if(token==null){
            log.debug("Token is not present in cache or database");
        }

        if (tokenCache != null && token!=null) {
            tokenCache.put(id, STSStoreUtils.getSerializableToken(token));
        }
        return token;
    }

    public void removeToken(String id) throws TrustException {
        initDao();
        dbStsDAO.removeToken(id);
        //remove token from cache and send cache invalidation msg
        if (tokenCache != null && tokenCache.size() > 0) {
            CacheInvalidator cacheInvalidator =
                    STSStoreComponent.getCacheInvalidator();
            try {
                cacheInvalidator.invalidateCache(STSConstants.KEY_ISSUER_CONFIG, id);
            } catch (CacheException e) {
                String msg = "Failed to invalidate token from cache";
                log.error(msg, e);
                throw new TrustException(msg, e);
            }
        }
    }

    public List<Token> getStorageTokens() throws TrustException {
        initDao();
        return dbStsDAO.getTokens();
    }

    public void handlePersistence(List<?> persistingTokens) throws TrustException {
        //TODO
        //If we have distributed caching mechanism, we don't need to store token immediately
        //in database. We can periodically take token from local cache and store to database.
    }

    public void handlePersistenceOnShutdown() throws TrustException {
        //TODO
        // If we don't immediately persist token to database,
        // we have to persist before the server shut down.
    }

    private void initDao() {
        if (dbStsDAO == null) {
            this.dbStsDAO = new DBStsDAO();
        }
    }

    public static Cache getTokenCache() {
        return tokenCache;
    }

    /**
     * This task used to persist the token.
     */
    protected static class TokenPersisterTask implements Runnable {

        private Token token;

        public TokenPersisterTask(Token token) {
            this.token = token;
        }

        @Override
        public void run() {
            try {
                persist();
            } catch (TrustException e) {
                log.error("Failed to persist token");
            }
        }

        private synchronized void persist() throws TrustException {
            try {
                new DBStsDAO().addToken(token);
            } catch (TrustException e) {
                throw new TrustException("Failed to persist token", e);
            }

        }
    }
}
