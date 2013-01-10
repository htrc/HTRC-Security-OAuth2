/*
 *  Copyright (c) Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.identity.entitlement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.core.AbstractAdmin;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.cache.DecisionCache;
import org.wso2.carbon.identity.entitlement.cache.EntitlementPolicyCache;
import org.wso2.carbon.identity.entitlement.dto.ModuleDataHolder;
import org.wso2.carbon.identity.entitlement.dto.PDPDataHolder;
import org.wso2.carbon.identity.entitlement.dto.PIPFinderDataHolder;
import org.wso2.carbon.identity.entitlement.dto.PolicyFinderDataHolder;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.identity.entitlement.pap.store.PAPPolicyFinder;
import org.wso2.carbon.identity.entitlement.pap.store.PAPPolicyStore;
import org.wso2.carbon.identity.entitlement.pdp.EntitlementEngine;
import org.wso2.carbon.identity.entitlement.pip.*;
import org.wso2.carbon.identity.entitlement.policy.finder.CarbonPolicyFinderModule;
import org.wso2.carbon.identity.entitlement.policy.publisher.PolicyPublisher;
import org.wso2.carbon.identity.entitlement.policy.publisher.PolicyPublisherModule;

import java.util.*;

/**
 * Entitlement PDP, PIP, Publisher related admin services are exposed 
 */
public class EntitlementAdminService extends AbstractAdmin {

	private static Log log = LogFactory.getLog(EntitlementAdminService.class);    

    /**
     * Clears the decision cache.
     *
     * @throws org.wso2.carbon.identity.base.IdentityException throws
     */
    public void clearDecisionCache() throws IdentityException {
        EntitlementEngine.getInstance().clearDecisionCache(true);
        if (log.isDebugEnabled()) {
            log.debug("Decision Caching is cleared by using admin service");
        }
    }


    /**
     * Clears Carbon attribute finder cache and All the attribute cache implementations in each
     * PIP attribute finder level
     *
     * @throws IdentityException throws
     */
    public void clearAllAttributeCaches() throws IdentityException {
        CarbonAttributeFinder finder = EntitlementEngine.getInstance().getCarbonAttributeFinder();
        if (finder != null) {
            finder.clearAttributeCache();
            // we need invalidate decision cache as well.
            clearDecisionCache();
        } else {
            throw new IdentityException("Can not clear all attribute caches - Carbon Attribute Finder "
                    + "is not initialized");
        }

        Map<PIPAttributeFinder, Properties> designators = EntitlementServiceComponent.getEntitlementConfig()
                .getDesignators();
        if(designators != null && !designators.isEmpty()){
            Set<PIPAttributeFinder> pipAttributeFinders = designators.keySet();
            for (PIPAttributeFinder pipAttributeFinder : pipAttributeFinders) {
                pipAttributeFinder.clearCache();
            }
        }
    }


    /**
     * Clears the carbon attribute cache
     *
     * @throws IdentityException throws
     */
    public void clearCarbonAttributeCache() throws IdentityException {
        
        CarbonAttributeFinder finder = EntitlementEngine.getInstance().getCarbonAttributeFinder();
        if (finder != null) {
            finder.clearAttributeCache();
            // we need invalidate decision cache as well.
            clearDecisionCache();
        } else {
            throw new IdentityException("Can not clear attribute cache - Carbon Attribute Finder "
                    + "is not initialized");
        }

        Map<PIPAttributeFinder, Properties> designators = EntitlementServiceComponent.getEntitlementConfig()
                .getDesignators();
        if(designators != null && !designators.isEmpty()){
            Set<PIPAttributeFinder> pipAttributeFinders = designators.keySet();
            for (PIPAttributeFinder pipAttributeFinder : pipAttributeFinders) {
                if(pipAttributeFinder instanceof AbstractPIPAttributeFinder){
                    pipAttributeFinder.clearCache();
                }
            }
        }
    }

    /**
     * Clears the cache maintained by the attribute finder.
     *
     * @param attributeFinder Canonical name of the attribute finder class.
     */
    public void clearAttributeFinderCache(String attributeFinder) {

        Map<PIPAttributeFinder, Properties> designators = EntitlementServiceComponent.getEntitlementConfig()
                .getDesignators();
        if(designators != null && !designators.isEmpty()){
            Set<PIPAttributeFinder> pipAttributeFinders = designators.keySet();
            for (PIPAttributeFinder pipAttributeFinder : pipAttributeFinders) {
                if(pipAttributeFinder instanceof AbstractPIPAttributeFinder){
                    if (pipAttributeFinder.getClass().getCanonicalName().equals(attributeFinder)) {
                        pipAttributeFinder.clearCache();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Clears the cache maintained by the attribute finder - by attributes
     *
     * @param attributeFinder Canonical name of the attribute finder class.
     * @param attributeIds An array of attribute id.
     */
    public void clearAttributeFinderCacheByAttributes(String attributeFinder, String[] attributeIds) {

        Map<PIPAttributeFinder, Properties> designators = EntitlementServiceComponent.getEntitlementConfig()
                .getDesignators();
        if(designators != null && !designators.isEmpty()){
            Set<PIPAttributeFinder> pipAttributeFinders = designators.keySet();
            for (PIPAttributeFinder pipAttributeFinder : pipAttributeFinders) {
                if (pipAttributeFinder.getClass().getCanonicalName().equals(attributeFinder)) {
                    pipAttributeFinder.clearCache(attributeIds);
                    break;
                }
            }
        }
    }

    /**
     * Clears Carbon resource finder cache and All the resource cache implementations in each
     * PIP resource finder level
     *
     * @throws IdentityException throws
     */
    public void clearAllResourceCaches() throws IdentityException {
        CarbonResourceFinder finder = EntitlementEngine.getInstance().getCarbonResourceFinder();
        if (finder != null) {
            finder.clearAttributeCache();
            // we need invalidate decision cache as well.
            clearDecisionCache();
        } else {
            throw new IdentityException("Can not clear attribute cache - Carbon Attribute Finder "
                    + "is not initialized");
        }
    }

    /**
     * Clears the carbon resource cache
     *
     * @throws IdentityException throws
     */
    public void clearCarbonResourceCache() throws IdentityException {
        CarbonResourceFinder finder = EntitlementEngine.getInstance().getCarbonResourceFinder();
        if (finder != null) {
            finder.clearAttributeCache();
            // we need invalidate decision cache as well.
            clearDecisionCache();
        } else {
            throw new IdentityException("Can not clear attribute cache - Carbon Attribute Finder "
                    + "is not initialized");
        }

        Map<PIPResourceFinder, Properties> resourceConfigs = EntitlementServiceComponent.getEntitlementConfig()
                .getResourceFinders();
        if(resourceConfigs != null && !resourceConfigs.isEmpty()){
            Set<PIPResourceFinder> resourceFinders = resourceConfigs.keySet();
            for (PIPResourceFinder pipResourceFinder : resourceFinders) {
                pipResourceFinder.clearCache();
            }
        }
    }

    /**
     * Clears the cache maintained by the resource finder.
     *
     * @param resourceFinder Canonical name of the resource finder class.
     */
    public void clearResourceFinderCache(String resourceFinder) {

        Map<PIPResourceFinder, Properties> resourceConfigs = EntitlementServiceComponent.getEntitlementConfig()
                .getResourceFinders();
        if(resourceConfigs != null && !resourceConfigs.isEmpty()){
            Set<PIPResourceFinder> resourceFinders = resourceConfigs.keySet();
            for (PIPResourceFinder pipResourceFinder : resourceFinders) {
                if (resourceFinder.getClass().getCanonicalName().equals(resourceFinder)) {
                    pipResourceFinder.clearCache();
                    break;
                }
            }
        }
    }


    /**
     * Refreshes the supported Attribute ids of a given attribute finder module
     *
     * @param attributeFinder Canonical name of the attribute finder class.
     * @throws IdentityException throws if fails to  refresh
     */
    public void refreshAttributeFinder(String attributeFinder) throws IdentityException {

        Map<PIPAttributeFinder, Properties> designators = EntitlementServiceComponent.getEntitlementConfig()
                .getDesignators();
        if(attributeFinder != null && designators != null && !designators.isEmpty()){
            Set<Map.Entry<PIPAttributeFinder, Properties>> pipAttributeFinders = designators.entrySet();
            for (Map.Entry<PIPAttributeFinder, Properties> entry : pipAttributeFinders) {
                if (attributeFinder.equals(entry.getKey().getClass().getName()) ||
                                            attributeFinder.equals(entry.getKey().getModuleName())) {
                    try {
                        entry.getKey().init(entry.getValue());
                        entry.getKey().clearCache();
                        CarbonAttributeFinder carbonAttributeFinder = EntitlementEngine.
                                                        getInstance().getCarbonAttributeFinder();
                        carbonAttributeFinder.init();
                    } catch (Exception e) {
                        throw new IdentityException("Error while refreshing attribute finder - " +
                                                    attributeFinder);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Refreshes the supported resource id of a given resource finder module
     *
     * @param resourceFinder Canonical name of the resource finder class.
     * @throws IdentityException throws if fails to  refresh
     */
    public void refreshResourceFinder(String resourceFinder) throws IdentityException {

        Map<PIPResourceFinder, Properties> resourceFinders = EntitlementServiceComponent.getEntitlementConfig()
                .getResourceFinders();
        if(resourceFinder != null && resourceFinders != null && !resourceFinders.isEmpty()){
            for (Map.Entry<PIPResourceFinder, Properties> entry : resourceFinders.entrySet()) {
                if (resourceFinder.equals(entry.getKey().getClass().getName()) ||
                                            resourceFinder.equals(entry.getKey().getModuleName())) {
                    try {
                        entry.getKey().init(entry.getValue());
                        entry.getKey().clearCache();
                        CarbonAttributeFinder carbonAttributeFinder = EntitlementEngine.
                                                        getInstance().getCarbonAttributeFinder();
                        carbonAttributeFinder.init();
                    } catch (Exception e) {
                        throw new IdentityException("Error while refreshing attribute finder - " +
                                                    resourceFinder);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Refreshes the supported resource id of a given resource finder module
     *
     * @param policyFinder Canonical name of the resource finder class.
     * @throws IdentityException throws if fails to  refresh
     */
    public void refreshPolicyFinders(String policyFinder) throws IdentityException {

        Map<CarbonPolicyFinderModule, Properties> policyFinders = EntitlementServiceComponent.getEntitlementConfig()
                .getPolicyFinderModules();
        if(policyFinder != null && policyFinders != null && !policyFinders.isEmpty()){
            for (Map.Entry<CarbonPolicyFinderModule, Properties> entry : policyFinders.entrySet()) {
                if (policyFinder.equals(entry.getKey().getClass().getName()) ||
                                            policyFinder.equals(entry.getKey().getModuleName())) {
                    try {
                        entry.getKey().init(entry.getValue());
                        EntitlementEngine.getInstance().getCarbonPolicyFinder().init();
                        // need to re init all policy finder modules in the cluster.
                        // therefore calling invalidation cache
                        DecisionCache.getInstance().invalidateCache();
                        EntitlementPolicyCache.getInstance().invalidateCache();
                        EntitlementPolicyCache.getInstance().addToCache(1);
                    } catch (Exception e) {
                        throw new IdentityException("Error while refreshing attribute finder - " +
                                                    policyFinder);
                    }
                    break;
                }
            }
        }
    }
      
    /**
     * Gets subscriber details
     *
     * @param id  subscriber id
     * @return subscriber details as SubscriberDTO
     * @throws IdentityException throws, if any error
     */
    public ModuleDataHolder getSubscriber(String id) throws IdentityException {

        PolicyPublisher publisher = EntitlementEngine.getInstance().getPolicyPublisher();
        return publisher.retrieveSubscriber(id);
    }

    /**
     * Gets all subscribers ids that is registered,
     *
     * @return subscriber's ids as String array
     * @throws IdentityException throws, if fails
     */
    public String[] getSubscriberIds() throws IdentityException {

        PolicyPublisher publisher = EntitlementEngine.getInstance().getPolicyPublisher();
        String[] ids = publisher.retrieveSubscriberIds();
        if(ids != null){
            return ids;
        } else {
            return new String[0];
        }
    }


    /**
     * Set or update subscriber details in to registry
     *
     * @param holder subscriber data as ModuleDataHolder object
     * @param update boolean indicating if this is an update or add
     * @throws IdentityException throws, if fails
     */
    public void updateSubscriber(ModuleDataHolder holder,boolean update) throws IdentityException {

        PolicyPublisher publisher = EntitlementEngine.getInstance().getPolicyPublisher();
        publisher.persistSubscriber(holder,update);

    }

    /**
     * delete subscriber details from registry
     *
     * @param subscriberId subscriber id
     * @throws IdentityException throws, if fails
     */
    public void deleteSubscriber(String subscriberId) throws IdentityException {

        PolicyPublisher publisher = EntitlementEngine.getInstance().getPolicyPublisher();
        publisher.deleteSubscriber(subscriberId);

    }

    /**
     * Publishes given set of policies to all subscribers
     *
     * @param policyIds policy ids to publish,  if null or empty, all policies are published
     * @param subscriberIds subscriber ids to publish,  if null or empty, all policies are published
     * @throws IdentityException throws, if fails
     */
    public void publishPolicies(String[] policyIds, String[] subscriberIds)  throws IdentityException {

        EntitlementEngine engine = EntitlementEngine.getInstance();
        PolicyPublisher publisher = engine.getPolicyPublisher();
        PAPPolicyStore policyStore = new PAPPolicyStore();
        if(policyIds == null || policyIds.length < 1){
            policyIds = policyStore.getAllPolicyIds();
        }
        if(subscriberIds == null || subscriberIds.length < 1){
            subscriberIds = publisher.retrieveSubscriberIds();
        }

        if(policyIds == null || policyIds.length  < 1){
            throw new IdentityException("There are no policies to publish");
        }

        if(subscriberIds == null || subscriberIds.length < 1){
            throw new IdentityException("There are no subscribers to publish");
        }

        publisher.publishPolicy(policyIds, subscriberIds);
    }


    /**
     * Gets policy publisher module data to populate in the UI
     *
     * @return
     */
    public ModuleDataHolder[]  getPublisherModuleProperties(){

        List<ModuleDataHolder> holders = EntitlementServiceComponent.
            getEntitlementConfig().getModulePropertyHolders(PolicyPublisherModule.class.getName());
        if(holders != null){
            return holders.toArray(new ModuleDataHolder[holders.size()]);
        }

        return null;
    }

    /**
     * Tests engine of PAP policy store 
     *
     * @param xacmlRequest
     * @return
     * @throws IdentityException
     */
    public String doTestRequest(String xacmlRequest) throws IdentityException {
        return EntitlementEngine.getInstance().test(xacmlRequest);
    }
    /**
     * Tests engine of PAP policy store
     *
     * @param xacmlRequest
     * @param policies policy ids that is evaluated
     * @return
     * @throws IdentityException
     */
    public String doTestRequestForGivenPolicies(String xacmlRequest, String[] policies)
                                                                        throws IdentityException {
        EntitlementEngine engine = EntitlementEngine.getInstance();
        Iterator iterator = engine.getPapPolicyFinder().getModules().iterator();
        if(iterator.hasNext()){
            ((PAPPolicyFinder)iterator.next()).setPolicyIds(Arrays.asList(policies));
        }
        String response = EntitlementEngine.getInstance().test(xacmlRequest);
        engine.getPapPolicyFinder().init();
        return response;
    }

    /**
     * 
     * @return
     */
    public PDPDataHolder getPDPData(){

        PDPDataHolder pdpDataHolder = new PDPDataHolder();

		Map<CarbonPolicyFinderModule, Properties> finderModules = EntitlementServiceComponent.
                                                getEntitlementConfig().getPolicyFinderModules();
 		Map<PIPAttributeFinder, Properties> attributeModules = EntitlementServiceComponent.
                                                getEntitlementConfig().getDesignators();
		Map<PIPResourceFinder, Properties> resourceModules = EntitlementServiceComponent.
                                                getEntitlementConfig().getResourceFinders();

        if(finderModules != null){
            List<String> list = new ArrayList<String>();
            for(Map.Entry<CarbonPolicyFinderModule, Properties> entry : finderModules.entrySet()){
                CarbonPolicyFinderModule module = entry.getKey();
                if(module != null){
                    if(module.getModuleName() != null){
                        list.add(module.getModuleName());
                    } else {
                        list.add(module.getClass().getName());
                    }
                }
            }
            pdpDataHolder.setPolicyFinders(list.toArray(new String[list.size()]));
        }

        if(attributeModules != null){
            List<String> list = new ArrayList<String>();
            for(Map.Entry<PIPAttributeFinder, Properties> entry : attributeModules.entrySet()){
                PIPAttributeFinder module = entry.getKey();
                if(module != null){
                    if(module.getModuleName() != null){
                        list.add(module.getModuleName());
                    } else {
                        list.add(module.getClass().getName());
                    }
                }
            }
            pdpDataHolder.setPipAttributeFinders(list.toArray(new String[list.size()]));
        }

        if(resourceModules != null){
            List<String> list = new ArrayList<String>();
            for(Map.Entry<PIPResourceFinder, Properties> entry : resourceModules.entrySet()){
                PIPResourceFinder module = entry.getKey();
                if(module != null){
                    if(module.getModuleName() != null){
                        list.add(module.getModuleName());
                    } else {
                        list.add(module.getClass().getName());
                    }
                }
            }
            pdpDataHolder.setPipResourceFinders(list.toArray(new String[list.size()]));
        }

        return pdpDataHolder;
    }

    /**
     *
     * @param finder
     * @return
     */
    public PolicyFinderDataHolder getPolicyFinderData(String finder){

        PolicyFinderDataHolder holder = null;
        // get registered finder modules
		Map<CarbonPolicyFinderModule, Properties> finderModules = EntitlementServiceComponent.
                                                getEntitlementConfig().getPolicyFinderModules();
        if(finderModules == null || finder == null){
            return null;
        }

        for(Map.Entry<CarbonPolicyFinderModule, Properties> entry : finderModules.entrySet()){
            CarbonPolicyFinderModule module = entry.getKey();
            if(module != null && (finder.equals(module.getModuleName()) ||
                                                        finder.equals(module.getClass().getName()))){
                holder = new PolicyFinderDataHolder();
                if(module.getModuleName() != null){
                    holder.setModuleName(module.getModuleName());
                } else {
                    holder.setModuleName(module.getClass().getName());
                }
                holder.setClassName(module.getClass().getName());
                holder.setPriority(module.getModulePriority());
                holder.setPolicyIdentifiers(module.getPolicyIdentifiers());
                holder.setCombiningAlgorithm(module.getPolicyCombiningAlgorithm());
                break;
            }

        }
        return holder;
    }

    /**
     * 
     * @param finder
     * @return
     */
    public PIPFinderDataHolder getPIPAttributeFinderData(String finder){

        PIPFinderDataHolder holder = null;
        // get registered finder modules
		Map<PIPAttributeFinder, Properties> attributeModules = EntitlementServiceComponent.
                                                getEntitlementConfig().getDesignators();
        if(attributeModules == null || finder == null){
            return null;
        }

        for(Map.Entry<PIPAttributeFinder, Properties> entry : attributeModules.entrySet()){
            PIPAttributeFinder module = entry.getKey();
            if(module != null && (finder.equals(module.getModuleName()) ||
                                                    finder.equals(module.getClass().getName()))){
                holder = new PIPFinderDataHolder();
                if(module.getModuleName() != null){
                    holder.setModuleName(module.getModuleName());
                } else {
                    holder.setModuleName(module.getClass().getName());
                }
                holder.setClassName(module.getClass().getName());
                holder.setSupportedAttributeIds(module.getSupportedAttributes().
                                    toArray(new String[module.getSupportedAttributes().size()]));
                break;
            }
        }
        return holder;
    }

    /**
     *
     * @param finder
     * @return
     */
    public PIPFinderDataHolder getPIPResourceFinderData(String finder){

        PIPFinderDataHolder holder = null;
        // get registered finder modules
		Map<PIPResourceFinder, Properties> resourceModules = EntitlementServiceComponent.
                                                getEntitlementConfig().getResourceFinders();

        if(resourceModules == null || finder == null){
            return null;
        }

        for(Map.Entry<PIPResourceFinder, Properties> entry : resourceModules.entrySet()){
            PIPResourceFinder module = entry.getKey();
            if(module != null){
                holder = new PIPFinderDataHolder();
                if(module.getModuleName() != null){
                    holder.setModuleName(module.getModuleName());
                } else {
                    holder.setModuleName(module.getClass().getName());
                }
                holder.setClassName(module.getClass().getName());
                break;
            }
        }
        return holder;
    }
}