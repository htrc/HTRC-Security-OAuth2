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

package org.wso2.carbon.identity.oauth.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.identity.oauth.cache.OAuthCache;
import org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.user.core.service.RealmService;

/**
 * @scr.component name="identity.oauth.component" immediate="true"
 * @scr.reference name="registry.service"
 * interface="org.wso2.carbon.registry.core.service.RegistryService"
 * cardinality="1..1" policy="dynamic" bind="setRegistryService"
 * unbind="unsetRegistryService"
 * @scr.reference name="user.realmservice.default"
 * interface="org.wso2.carbon.user.core.service.RealmService" cardinality="1..1"
 * policy="dynamic" bind="setRealmService" unbind="unsetRealmService"
 */
public class OAuthServiceComponent {
    private static Log log = LogFactory.getLog(OAuthServiceComponent.class);

    protected void activate(ComponentContext context) {
        // initialize the OAuth Server configuration
        OAuthServerConfiguration oauthServerConfig = OAuthServerConfiguration.getInstance();

        if (oauthServerConfig.isCacheEnabled()) {
            log.debug("OAuth Caching is enabled. Initializing the cache.");
            // initialize the cache
            OAuthCache cache = OAuthCache.getInstance();
            if (cache != null) {
                log.debug("OAuth Cache initialization was successful.");
            } else {
                log.debug("OAuth Cache initialization was unsuccessful.");
            }
        }

        if (log.isDebugEnabled()) {
            log.info("Identity OAuth bundle is activated");
        }
    }

    protected void deactivate(ComponentContext context) {
        if (log.isDebugEnabled()) {
            log.info("Identity OAuth bundle is deactivated");
        }
    }

    protected void setRegistryService(RegistryService registryService) {
        if (log.isDebugEnabled()) {
            log.info("RegistryService set in Identity OAuth bundle");
        }
        OAuthComponentServiceHolder.setRegistryService(registryService);
    }

    protected void unsetRegistryService(RegistryService registryService) {
        if (log.isDebugEnabled()) {
            log.info("RegistryService unset in Identity OAuth bundle");
        }
        OAuthComponentServiceHolder.setRegistryService(null);
    }

    protected void setRealmService(RealmService realmService) {
        if (log.isDebugEnabled()) {
            log.info("Setting the Realm Service");
        }
        OAuthComponentServiceHolder.setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {
        if (log.isDebugEnabled()) {
            log.info("Unsetting the Realm Service");
        }
        OAuthComponentServiceHolder.setRealmService(null);
    }
}