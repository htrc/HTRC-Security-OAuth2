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

package org.wso2.carbon.identity.entitlement.policy.store;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.dto.AttributeDTO;
import org.wso2.carbon.identity.entitlement.dto.PolicyStoreDTO;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.registry.core.Collection;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 */
public class CarbonRegistryPolicyStore implements CarbonPolicyStore {

    private String policyStorePath;

	private static Log log = LogFactory.getLog(CarbonRegistryPolicyStore.class);

    @Override
    public void init(Properties properties) {
        policyStorePath = properties.getProperty("policyStorePath");
        if(policyStorePath == null){
            policyStorePath = "/repository/identity/Entitlement/actualStore/";
        }
    }

    @Override
    public boolean addPolicy(PolicyStoreDTO policy) {

        Registry registry;
        String policyPath;
        Collection policyCollection;
        Resource resource;
        int tenantId = CarbonContext.getCurrentContext().getTenantId();

        if(policy == null || policy.getPolicy() == null || policy.getPolicy().trim().length() == 0
                 || policy.getPolicyId() == null || policy.getPolicyId().trim().length() == 0){
            return false;
        }

        try {
            registry = EntitlementServiceComponent.getRegistryService().
                                                            getGovernanceSystemRegistry(tenantId);

            if(registry.resourceExists(policyStorePath)){
                policyCollection = (Collection) registry.get(policyStorePath);
            } else {
                policyCollection = registry.newCollection();
            }

            registry.put(policyStorePath, policyCollection);

            policyPath = policyStorePath + policy.getPolicyId();

            if (registry.resourceExists(policyPath)) {
                resource = registry.get(policyPath);
            } else {
                resource = registry.newResource();
            }

            resource.setProperty("policyOrder", Integer.toString(policy.getPolicyOrder()));
            resource.setContent(policy.getPolicy());
            resource.setMediaType("application/xacml-policy+xml");
            AttributeDTO[] attributeDTOs = policy.getAttributeDTOs();
            if(attributeDTOs != null){
                setAttributesAsProperties(attributeDTOs, resource);
            }
            registry.put(policyPath, resource);
            return true;
        } catch (RegistryException e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public void setPolicyCombiningAlgorithm(String algorithm) {

        Registry registry;
        Collection policyCollection;
        int tenantId = CarbonContext.getCurrentContext().getTenantId();

        if(algorithm == null || algorithm.trim().length() == 0){
            return;
        }

        try {
            registry = EntitlementServiceComponent.getRegistryService().
                                                            getGovernanceSystemRegistry(tenantId);

            if(registry.resourceExists(policyStorePath)){
                policyCollection = (Collection) registry.get(policyStorePath);
            } else {
                policyCollection = registry.newCollection();
            }
            policyCollection.setProperty("globalPolicyCombiningAlgorithm", algorithm);
            registry.put(policyStorePath, policyCollection);

        } catch (RegistryException e) {
            log.error(e);
        }
    }

    @Override
    public boolean deletePolicy(String policyIdentifier) {

        Registry registry;
        String policyPath;
        int tenantId = CarbonContext.getCurrentContext().getTenantId();

        if(policyIdentifier == null || policyIdentifier.trim().length() == 0){
            return false;
        }

        try {
            registry = EntitlementServiceComponent.getRegistryService().
                                                            getGovernanceSystemRegistry(tenantId);

            policyPath = policyStorePath + policyIdentifier;
            registry.delete(policyPath);
            return true;
        } catch (RegistryException e) {
            log.error(e);
            return false;
        }
    }


    /**
     * This helper method creates properties object which contains the policy meta data.
     *
     * @param attributeDTOs List of AttributeDTO
     * @param resource registry resource
     */
    private void setAttributesAsProperties(AttributeDTO[] attributeDTOs, Resource resource) {
        
        int attributeElementNo = 0;
        if(attributeDTOs != null){
            for(AttributeDTO attributeDTO : attributeDTOs){
                resource.setProperty("policyMetaData" + attributeElementNo,
                       attributeDTO.getAttributeType() + "," +
                       attributeDTO.getAttributeValue() + "," +
                       attributeDTO.getAttributeId() + "," +
                       attributeDTO.getAttributeDataType());
                attributeElementNo ++;
            }
        }
    }

}
