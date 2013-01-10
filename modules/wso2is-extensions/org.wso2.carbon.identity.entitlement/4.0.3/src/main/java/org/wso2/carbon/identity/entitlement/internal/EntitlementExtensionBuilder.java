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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.pap.PolicyEditorDataFinderModule;
import org.wso2.carbon.identity.entitlement.pip.PIPAttributeFinder;
import org.wso2.carbon.identity.entitlement.pip.PIPExtension;
import org.wso2.carbon.identity.entitlement.pip.PIPResourceFinder;
import org.wso2.carbon.identity.entitlement.policy.collection.PolicyCollection;
import org.wso2.carbon.identity.entitlement.policy.finder.CarbonPolicyFinderModule;
import org.wso2.carbon.identity.entitlement.policy.publisher.PolicyPublisherModule;
import org.wso2.carbon.identity.entitlement.policy.store.CarbonPolicyStore;
import org.wso2.carbon.utils.CarbonUtils;

/**
 * Build Entitlement configuration from entitlement.properties. First this will try to find the
 * configuration file from [CARBON_HOME]\repository\conf - failing to do so will load the file from
 * this bundle it self.The default file ships with the bundle only includes
 * org.wso2.carbon.identity.entitlement.pip.DefaultAttributeFinder as an AttributeDesignator and
 * default caching configurations.
 * 
 * 
 * PDP.OnDemangPolicyLoading.Enable=false 
 * PDP.OnDemangPolicyLoading.MaxInMemoryPolicies=1000
 * PDP.DecisionCaching.Enable=true 
 * PDP.DecisionCaching.CachingInterval=30000
 * PDP.AttributeCaching.Enable=true 
 * PDP.DecisionCaching.CachingInterval=30000
 * PDP.ResourceCaching.Enable=true 
 * PDP.ResourceCaching.CachingInterval=30000
 * 
 * PDP.Extensions.Extension.1=org.wso2.carbon.identity.entitlement.pdp.DefaultExtension
 * 
 * PIP.AttributeDesignators.Designator.1=org.wso2.carbon.identity.entitlement.pip.DefaultAttributeFinder
 * PIP.ResourceFinders.Finder.1="org.wso2.carbon.identity.entitlement.pip.DefaultResourceFinder
 * 
 * PAP.MetaDataFinders.Finder.1=org.wso2.carbon.identity.entitlement.pap.CarbonPolicyEditorDataFinder
 * PAP.PolicyPublishers.Publisher.1=org.wso2.carbon.identity.entitlement.policy.publisher.CarbonBasicPolicyPublisherModule
 * 
 * # Properties needed for each extension. #
 * org.wso2.carbon.identity.entitlement.pip.DefaultAttributeFinder.1=name,value #
 * org.wso2.carbon.identity.entitlement.pip.DefaultAttributeFinder.2=name,value #
 * org.wso2.carbon.identity.entitlement.pip.DefaultResourceFinder.1=name.value #
 * org.wso2.carbon.identity.entitlement.pip.DefaultResourceFinder.2=name,value #
 * org.wso2.carbon.identity.entitlement.pap.CarbonPolicyEditorDataFinder.1=name,value #
 * org.wso2.carbon.identity.entitlement.pap.CarbonPolicyEditorDataFinder.2=name,value
 */
public class EntitlementExtensionBuilder {

    public static final String LOCAL_NAME_PIP_CONFIG = "PIPConfig";
    public static final String LOCAL_NAME_CACHING_CONFIG = "CachingConfig";
    public static final String LOCAL_NAME_PAP_CONFIG = "PAPConfig";

    public static final String LOCAL_NAME_EXTENSIONS = "Extensions";
    public static final String LOCAL_NAME_EXTENSION = "Extension";
    public static final String LOCAL_NAME_EXTENSION_CLASS_ATTR = "class";

    public static final String LOCAL_NAME_ATTR_DESIGNATORS = "AttributeDesignators";
    public static final String LOCAL_NAME_ATTR_DESIGNATOR = "Designator";
    public static final String LOCAL_NAME_ATTR_CLASS_ATTR = "class";

    public static final String LOCAL_NAME_RESOURCE_FINDERS = "ResourceFinders";
    public static final String LOCAL_NAME_RESOURCE_FINDER = "Finder";
    public static final String LOCAL_NAME_RESOURCE_FINDER_CLASS = "class";

    public static final String LOCAL_NAME_PUBLISHER_FINDERS = "PAP.PolicyPublishers";
    public static final String LOCAL_NAME_PUBLISHER_FINDER = "Publisher";
    public static final String LOCAL_NAME_PUBLISHER_FINDER_CLASS = "class";

    public static final String LOCAL_NAME_META_DATA_FINDERS = "MetaDataFinders";
    public static final String LOCAL_NAME_META_DATA_FINDER = "Finder";
    public static final String LOCAL_NAME_META_DATA_FINDER_CLASS = "class";

    public static final String LOCAL_NAME_PROPERTY = "Property";
    public static final String LOCAL_NAME_PROPERTY_NAME = "name";

    public static final String LOCAL_NAME_ATTRIBUTE_CACHING = "PDP.AttributeCaching";
    public static final String LOCAL_NAME_RESOURCE_CACHING = "PDP.ResourceCaching";
    public static final String LOCAL_NAME_DECISION_CACHING = "PDP.DecisionCaching";
    public static final String LOCAL_NAME_ON_DEMAND_LOADING = "PDP.OnDemandPolicyLoading";
    public static final String LOCAL_NAME_MAX_POLICIES = "MaxInMemoryPolicies";
    public static final String LOCAL_NAME_CACHING_INTERVAL = "CachingInterval";
    public static final String LOCAL_NAME_ENABLE = "Enable";
    
    public static final String PDP_SCHEMA_VALIDATION ="PDP.SchemaValidation.Enable";

    private static final String ENTITLEMENT_CONFIG = "entitlement.properties";

    private static Log log = LogFactory.getLog(EntitlementExtensionBuilder.class);

    private BundleContext bundleContext;

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public void buildEntitlementConfig(EntitlementConfigHolder holder) throws Exception {

        Properties properties;

        if ((properties = loadProperties()) != null) {
            populateCachingAttributes(properties, holder);
            populatePDPExtensions(properties, holder);
            populateAttributeFinders(properties, holder);
            populateMetadataFinders(properties, holder);
            populateResourceFinders(properties, holder);
            populatePolicyPublishers(properties, holder);
            populatePolicyFinders(properties, holder);
            populatePolicyCollection(properties, holder);
            populatePolicyStore(properties, holder);
        }
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    private Properties loadProperties() throws IOException {

        Properties properties = new Properties();
        InputStream inStream = null;
        String warningMessage = null;

        File pipConfigXml = new File(CarbonUtils.getCarbonSecurityConfigDirPath(), ENTITLEMENT_CONFIG);
        if (pipConfigXml.exists()) {
            inStream = new FileInputStream(pipConfigXml);
        } else {
            URL url;
            if (bundleContext != null) {
                if ((url = bundleContext.getBundle().getResource(ENTITLEMENT_CONFIG)) != null) {
                    inStream = url.openStream();
                } else {
                    warningMessage = "Bundle context could not find resource "
                            + ENTITLEMENT_CONFIG
                            + " or user does not have sufficient permission to access the resource.";
                }

            } else {

                if ((url = this.getClass().getClassLoader().getResource(ENTITLEMENT_CONFIG)) != null) {
                    inStream = url.openStream();
                } else {
                    warningMessage = "PIP Config Builder could not find resource "
                            + ENTITLEMENT_CONFIG
                            + " or user does not have sufficient permission to access the resource.";
                }
            }
        }

        if (inStream == null) {
            log.warn(warningMessage);
            return null;
        }

        properties.load(inStream);

        return properties;
    }

    /**
     * 
     * @param properties
     * @param holder
     */
    private void populateCachingAttributes(Properties properties, EntitlementConfigHolder holder) {

        Properties pdpProperties = new Properties();
        
        setProperty(properties,pdpProperties,"PDP.OnDemangPolicyLoading.Enable");
        setProperty(properties,pdpProperties,"PDP.OnDemangPolicyLoading.MaxInMemoryPolicies");
        setProperty(properties,pdpProperties,"PDP.DecisionCaching.Enable");
        setProperty(properties,pdpProperties,"PDP.DecisionCaching.CachingInterval");
        setProperty(properties,pdpProperties,"PDP.AttributeCaching.Enable");
        setProperty(properties,pdpProperties,"PDP.DecisionCaching.CachingInterval");
        setProperty(properties,pdpProperties,"PDP.ResourceCaching.Enable");
        setProperty(properties,pdpProperties,"PDP.ResourceCaching.CachingInterval");
        setProperty(properties,pdpProperties, EntitlementConstants.PDP_ENABLE);
        setProperty(properties,pdpProperties, EntitlementConstants.PAP_ENABLE);
        setProperty(properties,pdpProperties,PDP_SCHEMA_VALIDATION);

        holder.setEngineProperties(pdpProperties);
    }
    
    private void setProperty(Properties inProp, Properties outProp, String name) {
        String value;
        if ((value = inProp.getProperty(name)) != null) {
            outProp.setProperty(name, value);
        }
    }

    /**
     * 
     * @param properties
     * @param holder
     * @throws Exception
     */
    private void populateAttributeFinders(Properties properties, EntitlementConfigHolder holder)
            throws Exception {
        int i = 1;
        PIPAttributeFinder designator = null;

        while (properties.getProperty("PIP.AttributeDesignators.Designator." + i) != null) {
            String className = properties.getProperty("PIP.AttributeDesignators.Designator." + i++);
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            designator = (PIPAttributeFinder) clazz.newInstance();

            int j = 1;
            Properties designatorProps = new Properties();
            while (properties.getProperty(className + "." + j) != null) {
                String[] props = properties.getProperty(className + "." + j++).split(",");
                designatorProps.put(props[0], props[1]);
            }

            designator.init(designatorProps);
            holder.addDesignators(designator, designatorProps);
        }
    }

    /**
     * 
     * @param properties
     * @param holder
     * @throws Exception
     */
    private void populatePDPExtensions(Properties properties, EntitlementConfigHolder holder)
            throws Exception {

        int i = 1;
        PIPExtension extension = null;

        while (properties.getProperty("PDP.Extensions.Extension." + i) != null) {
            String className = properties.getProperty("PDP.Extensions.Extension." + i++);
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            extension = (PIPExtension) clazz.newInstance();

            int j = 1;
            Properties extensionProps = new Properties();
            while (properties.getProperty(className + "." + j) != null) {
                String[] props = properties.getProperty(className + "." + j++).split(",");
                extensionProps.put(props[0], props[1]);
            }

            extension.init(extensionProps);
            holder.addExtensions(extension, extensionProps);
        }
    }

    /**
     * 
     * @param properties
     * @param holder
     * @throws Exception
     */
    private void populateResourceFinders(Properties properties, EntitlementConfigHolder holder)
            throws Exception {

        int i = 1;
        PIPResourceFinder resource = null;

        while (properties.getProperty("PIP.ResourceFinders.Finder." + i) != null) {
            String className = properties.getProperty("PIP.ResourceFinders.Finder." + i++);
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            resource = (PIPResourceFinder) clazz.newInstance();

            int j = 1;
            Properties resourceProps = new Properties();
            while (properties.getProperty(className + "." + j) != null) {
                String[] props = properties.getProperty(className + "." + j++).split(",");
                resourceProps.put(props[0], props[1]);
            }

            resource.init(resourceProps);
            holder.addResourceFinders(resource, resourceProps);
        }
    }

    /**
     * 
     * @param properties
     * @param holder
     * @throws Exception
     */
    private void populateMetadataFinders(Properties properties, EntitlementConfigHolder holder)
            throws Exception {
        int i = 1;
        PolicyEditorDataFinderModule metadata = null;

        while (properties.getProperty("PAP.MetaDataFinders.Finder." + i) != null) {
            String className = properties.getProperty("PAP.MetaDataFinders.Finder." + i++);
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            metadata = (PolicyEditorDataFinderModule) clazz.newInstance();

            int j = 1;
            Properties metadataProps = new Properties();
            while (properties.getProperty(className + "." + j) != null) {
                String[] props = properties.getProperty(className + "." + j++).split(",");
                metadataProps.put(props[0], props[1]);
            }

            metadata.init(metadataProps);
            holder.addPolicyMetaDataFinderModules(metadata, metadataProps);
        }
    }

    /**
     * 
     * @param properties
     * @param holder
     * @throws Exception
     */
    private void populatePolicyPublishers(Properties properties, EntitlementConfigHolder holder)
            throws Exception {

        int i = 1;
        PolicyPublisherModule publisher = null;

        while (properties.getProperty("PAP.PolicyPublishers.Publisher." + i) != null) {
            String className = properties.getProperty("PAP.PolicyPublishers.Publisher." + i++);
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            publisher = (PolicyPublisherModule) clazz.newInstance();

            int j = 1;
            Properties publisherProps = new Properties();
            while (properties.getProperty(className + "." + j) != null) {
                String[] props = properties.getProperty(className + "." + j++).split(",");
                publisherProps.put(props[0], props[1]);
            }

            publisher.init(publisherProps);
            holder.addPolicyPublisherModule(publisher, publisherProps);
        }
    }

    /**
     *
     * @param properties
     * @param holder
     * @throws Exception
     */
    private void populatePolicyFinders(Properties properties, EntitlementConfigHolder holder)
            throws Exception {

        int i = 1;
        CarbonPolicyFinderModule finderModule = null;

        while (properties.getProperty("PDP.PolicyFinders.Finder." + i) != null) {
            String className = properties.getProperty("PDP.PolicyFinders.Finder." + i++);
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            finderModule = (CarbonPolicyFinderModule) clazz.newInstance();

            int j = 1;
            Properties finderModuleProps = new Properties();
            while (properties.getProperty(className + "." + j) != null) {
                String[] props = properties.getProperty(className + "." + j++).split(",");
                finderModuleProps.put(props[0], props[1]);
            }

            finderModule.init(finderModuleProps);
            holder.addPolicyFinderModule(finderModule, finderModuleProps);
        }
    }

    /**
     *
     * @param properties
     * @param holder
     * @throws Exception
     */
    private void populatePolicyCollection(Properties properties, EntitlementConfigHolder holder)
            throws Exception {

        PolicyCollection collection = null;

        //only one policy collection can be there
        if(properties.getProperty("PDP.PolicyCollection") != null) {
            String className = properties.getProperty("PDP.PolicyCollection");
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            collection = (PolicyCollection) clazz.newInstance();

            int j = 1;
            Properties collectionProps = new Properties();
            while (properties.getProperty(className + "." + j) != null) {
                String[] props = properties.getProperty(className + "." + j++).split(",");
                collectionProps.put(props[0], props[1]);
            }

            collection.init(collectionProps);
            holder.addPolicyCollection(collection, collectionProps);
        }
    }

    /**
     *
     * @param properties
     * @param holder
     * @throws Exception
     */
    private void populatePolicyStore(Properties properties, EntitlementConfigHolder holder)
            throws Exception {

        CarbonPolicyStore policyStore = null;

        //only one policy collection can be there
        if(properties.getProperty("PDP.Policy.Store") != null) {
            String className = properties.getProperty("PDP.Policy.Store");
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            policyStore = (CarbonPolicyStore) clazz.newInstance();

            int j = 1;
            Properties storeProps = new Properties();
            while (properties.getProperty(className + "." + j) != null) {
                String[] props = properties.getProperty(className + "." + j++).split(",");
                storeProps.put(props[0], props[1]);
            }

            policyStore.init(storeProps);
            holder.addPolicyStore(policyStore, storeProps);
        }
    }
}
