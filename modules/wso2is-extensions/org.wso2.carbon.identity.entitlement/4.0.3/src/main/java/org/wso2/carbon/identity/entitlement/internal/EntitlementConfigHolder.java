/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.identity.entitlement.internal;

import org.wso2.carbon.identity.entitlement.dto.ModuleDataHolder;
import org.wso2.carbon.identity.entitlement.pap.PolicyEditorDataFinderModule;
import org.wso2.carbon.identity.entitlement.pip.PIPAttributeFinder;
import org.wso2.carbon.identity.entitlement.pip.PIPExtension;
import org.wso2.carbon.identity.entitlement.pip.PIPResourceFinder;
import org.wso2.carbon.identity.entitlement.policy.collection.PolicyCollection;
import org.wso2.carbon.identity.entitlement.policy.finder.CarbonPolicyFinderModule;
import org.wso2.carbon.identity.entitlement.policy.publisher.PolicyPublisherModule;
import org.wso2.carbon.identity.entitlement.policy.store.CarbonPolicyStore;

import javax.xml.validation.Schema;
import java.util.*;

/**
 * keeps track of the configuration found in entitlement-config.xml
 */
public class EntitlementConfigHolder {

    /**
     * PIPExtensions will be fired for each and every XACML request - which will give a handle to
     * the incoming request.
     */
    private Map<PIPExtension, Properties> extensions = new HashMap<PIPExtension, Properties>();

    /**
     * This will be fired by CarbonAttributeFinder whenever it finds an attribute supported by this
     * module and missing in the XACML request.
     */
    private Map<PIPAttributeFinder, Properties> designators = new HashMap<PIPAttributeFinder, Properties>();

    /**
     * This will be fired by CarbonResourceFinder whenever it wants to find a descendant or child resource
     * of a given resource
     */
    private Map<PIPResourceFinder, Properties> resourceFinders  = new HashMap<PIPResourceFinder, Properties>();

    /**
     * This will be fired by PolicyEditorDataFinder, whenever it wants to retrieve an attribute values to build the
     * XACML policy
     */
    private Map<PolicyEditorDataFinderModule, Properties> policyMetaDataFinderModules  =
                                                        new HashMap<PolicyEditorDataFinderModule, Properties>();

    /**
     * Will be fired by PolicyPublisher, whenever it wants to publish a policy
     */
    private Map<PolicyPublisherModule, Properties> policyPublisherModules =
                                                new HashMap<PolicyPublisherModule, Properties>();

    /**
     * Will be fired by CarbonPolicyFinder, whenever it wants to find policies
     */
    private Map<CarbonPolicyFinderModule, Properties> policyFinderModules =
                                                new HashMap<CarbonPolicyFinderModule, Properties>();

    /**
     * This holds all the policies of entitlement engine
     */
    private Map<PolicyCollection, Properties> policyCollections =
                                                new HashMap<PolicyCollection, Properties>();

    /**
     * This holds all the policy storing logic of entitlement engine
     */
    private Map<CarbonPolicyStore, Properties> policyStore =
                                                new HashMap<CarbonPolicyStore, Properties>();
    
    /**
     * This holds the policy schema against its version
     */
    private Map<String, Schema> policySchemaMap = new HashMap<String, Schema>();

    /**
     * Holds all caching related configurations
     */
    private Properties engineProperties;

    /**
     * Holds the properties of all modules.
     */
    private Map<String ,List<ModuleDataHolder>> modulePropertyHolderMap =
                                                new HashMap<String, List<ModuleDataHolder>>();


    
    public Map<PIPExtension, Properties> getExtensions() {
        return extensions;
    }

    public void addExtensions(PIPExtension extension, Properties properties) {
        this.extensions.put(extension, properties);
    }

    public Map<PIPAttributeFinder, Properties> getDesignators() {
        return designators;
    }

    public void addDesignators(PIPAttributeFinder attributeFinder,  Properties properties) {
        this.designators.put(attributeFinder, properties);
    }

    public Map<PIPResourceFinder, Properties> getResourceFinders() {
        return resourceFinders;
    }

    public void addResourceFinders(PIPResourceFinder resourceFinder, Properties properties) {
        this.resourceFinders.put(resourceFinder, properties);
    }

    public Map<PolicyEditorDataFinderModule, Properties> getPolicyMetaDataFinderModules() {
        return policyMetaDataFinderModules;
    }

    public void addPolicyMetaDataFinderModules(PolicyEditorDataFinderModule metaDataFinderModule,
                                               Properties properties) {
        this.policyMetaDataFinderModules.put(metaDataFinderModule, properties);
    }

    public Properties getEngineProperties() {
        return engineProperties;
    }

    public void setEngineProperties(Properties engineProperties) {
        this.engineProperties = engineProperties;
    }

    public Map<String, Schema> getPolicySchemaMap() {
        return policySchemaMap;
    }

    public void setPolicySchema(String schemaNS, Schema schema) {
        this.policySchemaMap.put(schemaNS, schema);
    }

    public Map<PolicyPublisherModule, Properties> getPolicyPublisherModules() {
        return policyPublisherModules;
    }

    public void addPolicyPublisherModule(PolicyPublisherModule policyPublisherModules,
                                                                            Properties properties) {
        this.policyPublisherModules.put(policyPublisherModules, properties);
    }

    public List<ModuleDataHolder> getModulePropertyHolders(String type) {
        return modulePropertyHolderMap.get(type);
    }
        
    public void addModulePropertyHolder(String type, ModuleDataHolder holder) {
        if(this.modulePropertyHolderMap.get(type) == null){
            List<ModuleDataHolder> holders = new ArrayList<ModuleDataHolder>();
            holders.add(holder);
            this.modulePropertyHolderMap.put(type, holders);
        } else {
            this.modulePropertyHolderMap.get(type).add(holder);    
        }
    }

    public Map<CarbonPolicyFinderModule, Properties> getPolicyFinderModules() {
        return policyFinderModules;
    }

    public void addPolicyFinderModule(CarbonPolicyFinderModule policyFinderModule,
                                                                            Properties properties) {
        this.policyFinderModules.put(policyFinderModule, properties);
    }

    public Map<PolicyCollection, Properties> getPolicyCollections() {
        return policyCollections;
    }

    public void addPolicyCollection(PolicyCollection collection, Properties properties) {
        this.policyCollections.put(collection, properties);
    }

    public Map<CarbonPolicyStore, Properties> getPolicyStore() {
        return policyStore;
    }

    public void addPolicyStore(CarbonPolicyStore policyStore, Properties properties) {
        this.policyStore.put(policyStore, properties);
    }
}
