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
import net.sf.jsr107cache.CacheException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.caching.core.CacheInvalidator;
import org.wso2.carbon.caching.core.identity.IdentityCacheEntry;
import org.wso2.carbon.caching.core.identity.IdentityCacheKey;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.utils.CarbonUtils;

/**
 * 
 */
public class EntitlementPolicyCache {

	private Cache cache = null;

    private static EntitlementPolicyCache entitlementPolicyCache = new EntitlementPolicyCache();

    private EntitlementPolicyCache() {
        this.cache =  CarbonUtils.getLocalCache(EntitlementConstants.ENTITLEMENT_POLICY_CACHE); 
    }

    /**
     * the logger we'll use for all messages
     */
	private static Log log = LogFactory.getLog(EntitlementPolicyCache.class);

	/**
	 * Gets a new instance of EntitlementPolicyCache.
	 *
	 * @return A new instance of EntitlementPolicyCache.
	 */
	public static EntitlementPolicyCache getInstance() {
		return entitlementPolicyCache;
	}

    public void addToCache(int hashCode){

        int tenantId = CarbonContext.getCurrentContext().getTenantId();
        IdentityCacheKey cacheKey = new IdentityCacheKey(tenantId, "");
        IdentityCacheEntry cacheEntry = new IdentityCacheEntry(hashCode);
        this.cache.put(cacheKey, cacheEntry);
        if (log.isDebugEnabled()) {
            log.debug("Cache entry is added");
        }
    }

    public int getFromCache(){

        int hashCode = 0;
        int tenantId = CarbonContext.getCurrentContext().getTenantId();
        IdentityCacheKey cacheKey = new IdentityCacheKey(tenantId, "");
        Object entry = this.cache.get(cacheKey);
        if(entry != null){
            IdentityCacheEntry cacheEntry = (IdentityCacheEntry) entry;
            hashCode =  cacheEntry.getHashEntry();
            if (log.isDebugEnabled()) {
                log.debug("Cache entry is found");
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Cache entry is not found");
            }
        }

        return hashCode;
    }

    public void invalidateCache(){

        int tenantId = CarbonContext.getCurrentContext().getTenantId();
        IdentityCacheKey cacheKey = new IdentityCacheKey(tenantId, "");

        if(this.cache.containsKey(cacheKey)){
            
            this.cache.remove(cacheKey);

            if (log.isDebugEnabled()) {
                log.debug("Local cache is invalidated");
            }
            //sending cluster message
            CacheInvalidator invalidator = EntitlementServiceComponent.getCacheInvalidator();
            try {
                if (invalidator != null) {
                    invalidator.invalidateCache(EntitlementConstants.ENTITLEMENT_POLICY_CACHE, cacheKey);
                    if (log.isDebugEnabled()) {
                        log.debug("Calling invalidation cache");
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Not calling invalidation cache");
                    }
                }
            } catch (CacheException e) {
                log.error("Error while invalidating cache", e);
            }
        }
    }
}
