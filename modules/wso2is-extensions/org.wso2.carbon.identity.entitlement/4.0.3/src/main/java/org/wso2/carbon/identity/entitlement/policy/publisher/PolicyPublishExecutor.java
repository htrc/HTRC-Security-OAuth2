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

package org.wso2.carbon.identity.entitlement.policy.publisher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.dto.ModuleDataHolder;
import org.wso2.carbon.identity.entitlement.dto.ModuleStatusHolder;
import org.wso2.carbon.identity.entitlement.pap.store.PAPPolicyStore;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Policy publish executor
 */
public class PolicyPublishExecutor implements Runnable {

    private String[] policyIds;

    private String[] subscriberIds;

    private PolicyPublisher publisher;

    private PAPPolicyStore policyStore;

    private static Log log = LogFactory.getLog(PolicyPublishExecutor.class);

    public PolicyPublishExecutor(String[] policyIds, String[] subscriberIds,
                                 PolicyPublisher publisher, PAPPolicyStore policyStore) {
        this.policyIds = policyIds;
        this.subscriberIds = subscriberIds;
        this.publisher = publisher;
        this.policyStore = policyStore;
    }

    public void run() {

        PolicyPublisherModule policyPublisherModule = null;
        Set<PolicyPublisherModule> publisherModules = publisher.getPublisherModules();

        if(publisherModules == null){
            return;
        }

        ModuleDataHolder holder = null;
        for(String subscriberId : subscriberIds){
            try{
                holder = publisher.retrieveSubscriber(subscriberId);
            } catch (IdentityException e) {
                log.error("Subscriber details can not be retrieved. So skip publishing policies " +
                        "for subscriber : " + subscriberId);      
            }
            if(holder != null){
                List<ModuleStatusHolder> statusHolders = new ArrayList<ModuleStatusHolder>();
                for(PolicyPublisherModule publisherModule : publisherModules){
                    if(publisherModule.getModuleName().equals(holder.getModuleName())){
                        policyPublisherModule = publisherModule;
                        if(policyPublisherModule instanceof AbstractPolicyPublisherModule){
                            try {
                                ((AbstractPolicyPublisherModule)policyPublisherModule).init(holder);
                            } catch (Exception e) {
                                statusHolders.add(new ModuleStatusHolder(subscriberId, e.getMessage()));
                                continue;
                            }
                        }
                        break;
                    }
                }

                if(policyPublisherModule == null){
                    statusHolders.add(new ModuleStatusHolder(subscriberId,
                        "No policy publish module is defined for subscriber : " + subscriberId));
                    continue;
                }

                for(String policyId : policyIds){
                    Resource resource;
                    try {
                        resource = policyStore.getPolicy(policyId);
                    } catch (IdentityException e) {
                        statusHolders.add(new ModuleStatusHolder(policyId, e.getMessage()));
                        continue;
                    }

                    if(resource == null){
                        statusHolders.add(new ModuleStatusHolder(policyId,
                                            "Can not found policy under policy id : " + policyId));
                        continue;
                    }

                    String policy;
                    try {
                        policy = new String((byte[]) resource.getContent());
                    } catch (RegistryException e) {
                        statusHolders.add(new ModuleStatusHolder(policyId, e.getMessage()));
                        continue;
                    }
                    try {
                        policyPublisherModule.publish(policy);
                    } catch (Exception e) {
                        statusHolders.add(new ModuleStatusHolder(policyId, e.getMessage()));
                        continue;
                    }
                    statusHolders.add(new ModuleStatusHolder(policyId));
                }
                holder.setStatusHolders(statusHolders.toArray(new ModuleStatusHolder[statusHolders.size()]));
            }
        }
    }
}
