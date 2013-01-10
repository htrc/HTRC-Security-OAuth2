/*
*  Copyright (c)  WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.identity.entitlement.cache;

import net.sf.jsr107cache.Cache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.caching.core.identity.IdentityCacheKey;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.policy.search.SearchResult;
import org.wso2.carbon.utils.CarbonUtils;

/**
 *
 */
public class PolicySearchCache {

    private Cache cache = null;

    private static PolicySearchCache policySearchCache = new PolicySearchCache();

    private PolicySearchCache() {
        this.cache =  CarbonUtils.getLocalCache(EntitlementConstants.POLICY_SEARCH_CACHE);
    }

    /**
     * the logger we'll use for all messages
     */
    private static Log log = LogFactory.getLog(PolicySearchCache.class);

    /**
     * Gets a new instance of EntitlementPolicyCache.
     *
     * @return A new instance of EntitlementPolicyCache.
     */
    public static PolicySearchCache getInstance() {
        return policySearchCache;
    }

    public void addToCache(String key, SearchResult result){
        int tenantId = CarbonContext.getCurrentContext().getTenantId();
        IdentityCacheKey cacheKey = new IdentityCacheKey(tenantId, key);
        cache.put(cacheKey, result);
        if (log.isDebugEnabled()) {
            log.debug("Cache entry is added");
        }
    }

    public SearchResult getFromCache(String key){

        SearchResult searchResult = null;
        int tenantId = CarbonContext.getCurrentContext().getTenantId();
        IdentityCacheKey cacheKey = new IdentityCacheKey(tenantId, key);
        Object entry = this.cache.get(cacheKey);
        if(entry != null){
            searchResult = (SearchResult) entry;
            if (log.isDebugEnabled()) {
                log.debug("Cache entry is found");
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Cache entry is not found");
            }
        }

        return searchResult;
    }

    public void invalidateCache(){
        cache.clear();
    }

}
