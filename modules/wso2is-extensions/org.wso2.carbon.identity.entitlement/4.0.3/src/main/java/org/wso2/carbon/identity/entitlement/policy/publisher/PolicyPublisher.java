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
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.dto.ModuleDataHolder;
import org.wso2.carbon.identity.entitlement.dto.ModulePropertyDTO;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.identity.entitlement.pap.store.PAPPolicyStore;
import org.wso2.carbon.registry.core.Collection;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.RegistryConstants;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is policy publisher. There can be different modules that have been plugged with this.
 * This module currently is bound with the WSO2 registry, as some meta data is store there,
 */
public class PolicyPublisher{

    private Registry registry;

    public static final String SUBSCRIBER_ID = "subscriberId";

    public static final String SUBSCRIBER_DISPLAY_NAME = "Subscriber Id";

    private static Log log = LogFactory.getLog(PolicyPublisher.class);

    /**
     * List of meta data finder modules
     */
    Set<PolicyPublisherModule> publisherModules = new HashSet<PolicyPublisherModule>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    /**
     * Creates PolicyPublisher instance
     * @param registry  WSO2 governance registry instance
     */
    public PolicyPublisher(Registry registry) {
        this.registry = registry;
    }

    /**
     * init policy publisher
     */
    public void init(){

		Map<PolicyPublisherModule, Properties> publisherModuleConfigs = EntitlementServiceComponent.
                getEntitlementConfig().getPolicyPublisherModules();
        if(publisherModuleConfigs != null && !publisherModuleConfigs.isEmpty()){
            publisherModules = publisherModuleConfigs.keySet();
        }
    }

    /**
     * publish policy
     *
     * @param policyIds policy ids to publish,
     * @param subscriberIds subscriber ids to publish,
     * @throws IdentityException throws if can not be created PolicyPublishExecutor instant
     */
    public void publishPolicy(String[] policyIds, String[] subscriberIds) throws IdentityException {

        PolicyPublishExecutor executor =
                new PolicyPublishExecutor(policyIds, subscriberIds, this, new PAPPolicyStore(registry));
        threadPool.execute(executor);
    }


    public void persistSubscriber(ModuleDataHolder holder, boolean update) throws IdentityException {

        Collection policyCollection;
        String subscriberPath;
        String subscriberId = null;

        if(holder == null || holder.getPropertyDTOs() == null){
            return;
        }

        for(ModulePropertyDTO dto  : holder.getPropertyDTOs()){
            if(SUBSCRIBER_ID.equals(dto.getId())){
                subscriberId = dto.getValue();
            }
        }

        if(subscriberId == null){
            return;
        }

        try{
            if(registry.resourceExists(EntitlementConstants.ENTITLEMENT_POLICY_PUBLISHER)){
                policyCollection = registry.newCollection();
                registry.put(EntitlementConstants.ENTITLEMENT_POLICY_PUBLISHER, policyCollection);
            }

            subscriberPath =  EntitlementConstants.ENTITLEMENT_POLICY_PUBLISHER +
                        RegistryConstants.PATH_SEPARATOR + subscriberId;

            Resource resource;

            if(registry.resourceExists(subscriberPath)){
                if(update == true){
                    resource = registry.get(subscriberPath);
                } else {
                    throw new IdentityException("Subscriber ID already exists!");
                }
            } else {
                resource = registry.newResource();
            }

            populateProperties(holder, resource);
            registry.put(subscriberPath, resource);
            
        } catch (RegistryException e) {
            log.error("Error while persisting subscriber details", e);
            throw new IdentityException("Error while persisting subscriber details", e);
        }
    }


    public void deleteSubscriber(String subscriberId) throws IdentityException {

        String subscriberPath;
        
        if(subscriberId == null){
            return;
        }

        try{
            subscriberPath =  EntitlementConstants.ENTITLEMENT_POLICY_PUBLISHER +
                        RegistryConstants.PATH_SEPARATOR + subscriberId;

            if(registry.resourceExists(subscriberPath)){
                registry.delete(subscriberPath);
            }
        } catch (RegistryException e) {
            log.error("Error while deleting subscriber details", e);
            throw new IdentityException("Error while deleting subscriber details", e);
        }
    }

    public ModuleDataHolder retrieveSubscriber(String id) throws IdentityException {

        try{
            if(registry.resourceExists(EntitlementConstants.ENTITLEMENT_POLICY_PUBLISHER +
                                                        RegistryConstants.PATH_SEPARATOR + id)){
                Resource resource = registry.get(EntitlementConstants.ENTITLEMENT_POLICY_PUBLISHER +
                                                        RegistryConstants.PATH_SEPARATOR + id);

                return new ModuleDataHolder(resource);
            }
        } catch (RegistryException e) {
            log.error("Error while retrieving subscriber detail of id : " + id, e);
            throw new IdentityException("Error while retrieving subscriber detail of id : " + id, e);
        }

        return null;
    }

    public String[] retrieveSubscriberIds() throws IdentityException {

        try{
            if(registry.resourceExists(EntitlementConstants.ENTITLEMENT_POLICY_PUBLISHER +
                                                        RegistryConstants.PATH_SEPARATOR)){
                Resource resource = registry.get(EntitlementConstants.ENTITLEMENT_POLICY_PUBLISHER +
                                                        RegistryConstants.PATH_SEPARATOR);
                Collection collection = (Collection) resource;
                List<String> list = new ArrayList<String>();
                if(collection.getChildCount() > 0){
                    for(String path : collection.getChildren()){
                        String id = registry.get(path).getProperty(SUBSCRIBER_ID);
                        if(id != null){
                            list.add(id);
                        }
                    }
                }
                return list.toArray(new String[list.size()]);
            }
        } catch (RegistryException e) {
            log.error("Error while retrieving subscriber of ids" , e);
            throw new IdentityException("Error while retrieving subscriber ids", e);

        }

        return null;
    }

    private void populateProperties(ModuleDataHolder holder, Resource resource){

        ModulePropertyDTO[] propertyDTOs = holder.getPropertyDTOs();
        for(ModulePropertyDTO dto : propertyDTOs){
            if(dto.getId() != null && dto.getValue() != null) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(dto.getValue());
                list.add(dto.getDisplayName());
                list.add(Integer.toString(dto.getDisplayOrder()));
                list.add(Boolean.toString(dto.isRequired()));
                resource.setProperty(dto.getId(), list);
            }
        }
        resource.setProperty(ModuleDataHolder.MODULE_NAME, holder.getModuleName());

    }

    public Set<PolicyPublisherModule> getPublisherModules() {
        return publisherModules;
    }
}
