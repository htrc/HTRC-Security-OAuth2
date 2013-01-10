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
package org.wso2.carbon.identity.sts.store.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.caching.core.CacheInvalidator;
import org.wso2.carbon.identity.sts.store.DBTokenStore;

/*
* @scr.reference name="cache.invalidation.service"
* interface="org.wso2.carbon.caching.core.CacheInvalidator"
* cardinality="0..1" policy="dynamic"
* bind="setCacheInvalidator"
* unbind="unsetCacheInvalidator"
*/
public class STSStoreComponent {

    private static Log log = LogFactory.getLog(STSStoreComponent.class);
    private static CacheInvalidator cacheInvalidator;

    protected void activate(ComponentContext context) {
        if (log.isDebugEnabled()) {
            log.info("Identity STS Mgt bundle is activated");
        }
    }

    protected void setCacheInvalidator(CacheInvalidator invalidator) {
        cacheInvalidator = invalidator;
    }

    protected void unsetCacheInvalidator(CacheInvalidator invalidator) {
        DBTokenStore.getExecutorService().shutdown();
        cacheInvalidator = null;
    }

    public static CacheInvalidator getCacheInvalidator() {
        return cacheInvalidator;
    }
}
