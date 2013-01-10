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

package org.wso2.carbon.identity.entitlement.policy.finder.registry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.balana.AbstractPolicy;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.core.IdentityRegistryResources;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.dto.PolicyDTO;
import org.wso2.carbon.identity.entitlement.policy.PolicyMetaDataBuilder;
import org.wso2.carbon.identity.entitlement.policy.PolicyReader;
import org.wso2.carbon.registry.core.Collection;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Registry policy reader
 */
public class RegistryPolicyReader {

    /**
     * Governance registry instance of current tenant
     */
    private Registry registry;

    /**
     * policy store path of the registry
     */
    private String policyStorePath;
    
    /**
     * logger
     */
    private static Log log = LogFactory.getLog(RegistryPolicyReader.class);

    /**
     * constructor
     *
     * @param registry registry instance
     * @param policyStorePath  policy store path of the registry
     */
    public RegistryPolicyReader(Registry registry, String policyStorePath) {
        this.registry = registry;
        this.policyStorePath = policyStorePath;
    }

    /**
     * Reads given policy resource as PolicyDTO
     *
     * @param policyId policy id
     * @return PolicyDTO
     * @throws IdentityException throws, if fails
     */
    public PolicyDTO readPolicy(String policyId) throws IdentityException {

        Resource resource = null;
        
        resource = getPolicyResource(policyId);

        if (resource == null) {
            return new PolicyDTO();
        }

        return readPolicy(resource);
    }

    /**
     * Reads All policies as PolicyDTO
     * 
     * @return Array of PolicyDTO
     * @throws IdentityException throws, if fails
     */
    public PolicyDTO[] readAllPolicies() throws IdentityException {
        
        Resource[] resources = null;
        PolicyDTO[] policies = null;
        resources = getAllPolicyResource();

        if (resources == null) {
            return new PolicyDTO[0];
        }
        policies = new PolicyDTO[resources.length];

        List<PolicyDTO> policyDTOList = new ArrayList<PolicyDTO>();
        int[] policyOrder = new int[resources.length];

        for(int i = 0; i < resources.length; i++){
            PolicyDTO policyDTO = readPolicy(resources[i]);
            policyDTOList.add(policyDTO);
            policyOrder[i] = policyDTO.getPolicyOrder();
        }

        // sorting array            TODO  : with Comparator class
        int[] tempArray = new int[policyOrder.length];
        Arrays.sort(policyOrder);
        for (int i = 0; i < tempArray.length; i++) {
            int j = (policyOrder.length-1)-i;
            tempArray[j] = policyOrder[i];
        }
        policyOrder = tempArray;

        for (int i = 0; i < policyOrder.length; i++) {
            for(PolicyDTO policyDTO : policyDTOList){
                if(policyOrder[i] == policyDTO.getPolicyOrder()){
                    policies[i] = policyDTO;
                }
            }
        }
        
        return policies;
    }

    /**
     * This returns all the policy ids as String list. Here we assume registry resource name as
     * the policy id.
     * 
     * @return policy ids as String[]
     * @throws IdentityException throws if fails
     */
    public String[] getAllPolicyIds() throws IdentityException {
        
        String path = null;
        Collection collection = null;
        String[] children = null;
        List<String> resources = new ArrayList<String>();

        if (log.isDebugEnabled()) {
            log.debug("Retrieving all entitlement policies");
        }

        try {
            path = policyStorePath;

            if (!registry.resourceExists(path)) {
                if (log.isDebugEnabled()) {
                    log.debug("Trying to access an entitlement policy which does not exist");
                }
                return null;
            }
            collection = (Collection) registry.get(path);
            children = collection.getChildren();
            for (String child : children) {
                String[] resourcePath = child.split("/");
                if(resourcePath != null && resourcePath.length > 0 ){
                    resources.add(resourcePath[resourcePath.length -1]);
                }
            }

        } catch (RegistryException e) {
            log.error("Error while retrieving entitlement policy resources", e);
            throw new IdentityException("Error while retrieving entitlement policy resources", e);
        }

        return resources.toArray(new String[resources.size()]);
    }

    /**
     * Reads PolicyDTO for given registry resource
     * 
     * @param resource Registry resource
     * @return  PolicyDTO
     * @throws IdentityException throws, if fails
     */
    private PolicyDTO readPolicy(Resource resource) throws IdentityException {
        
        String policy = null;
        AbstractPolicy absPolicy = null;
        PolicyDTO dto = null;

        try {
            policy = new String((byte[]) resource.getContent());
            absPolicy = PolicyReader.getInstance(null).getPolicy(policy);
            dto = new PolicyDTO();
            dto.setPolicyId(absPolicy.getId().toASCIIString());
            dto.setPolicy(policy);

            String policyOrder = resource.getProperty("policyOrder");
            if(policyOrder != null){
                dto.setPolicyOrder(Integer.parseInt(policyOrder));
            } else {
                dto.setPolicyOrder(0);
            }   // TODO  policy refe IDs ???
            PolicyMetaDataBuilder policyMetaDataBuilder = new PolicyMetaDataBuilder();
            dto.setPolicyMetaData(policyMetaDataBuilder.
                                getPolicyMetaDataFromRegistryProperties(resource.getProperties()));            
            return dto;
        } catch (RegistryException e) {
            log.error("Error while loading entitlement policy", e);
            throw new IdentityException("Error while loading entitlement policy", e);
        }
    }

    /**
     * This reads the policy combining algorithm from registry resource property
     * @return policy combining algorithm as String
     * @throws IdentityException throws
     */
    public String readPolicyCombiningAlgorithm() throws IdentityException {
        try {
            Collection policyCollection = null;
            if(registry.resourceExists(policyStorePath)){
                policyCollection =  (Collection) registry.get(policyStorePath);
            }
            if(policyCollection != null){
                return policyCollection.getProperty("globalPolicyCombiningAlgorithm");
            }
            return null;
        } catch (RegistryException e) {
            log.error("Error while reading policy combining algorithm", e);
            throw new IdentityException("Error while reading policy combining algorithm", e);
        }
    }

    /**
     * This returns given policy as Registry resource
     * 
     * @param policyId policy id
     * @return policy as Registry resource
     * @throws IdentityException throws, if fails
     */
    private Resource getPolicyResource(String policyId) throws IdentityException {
        String path = null;

        if (log.isDebugEnabled()) {
            log.debug("Retrieving entitlement policy");
        }

        try {
            path = policyStorePath + policyId;

            if (!registry.resourceExists(path)) {
                if (log.isDebugEnabled()) {
                    log.debug("Trying to access an entitlement policy which does not exist");
                }
                return null;
            }
            return registry.get(path);
        } catch (RegistryException e) {
            log.error("Error while retrieving entitlement policy : " + policyId, e);
            throw new IdentityException("Error while retrieving entitlement policy : " + policyId, e);
        }
    }

    /**
     * This returns all the policies as Registry resources.
     *
     * @return policies as Resource[]
     * @throws org.wso2.carbon.identity.base.IdentityException throws if fails
     */
    private Resource[] getAllPolicyResource() throws IdentityException {

        String path = null;
        Collection collection = null;
        List<Resource> resources = new ArrayList<Resource>();
        String[] children = null;

        if (log.isDebugEnabled()) {
            log.debug("Retrieving all entitlement policies");
        }

        try {
            path = policyStorePath;

            if (!registry.resourceExists(path)) {
                if (log.isDebugEnabled()) {
                    log.debug("Trying to access an entitlement policy which does not exist");
                }
                return null;
            }
            collection = (Collection) registry.get(path);
            children = collection.getChildren();

            for (String aChildren : children) {
                resources.add(registry.get(aChildren));
            }

        } catch (RegistryException e) {
            log.error("Error while retrieving entitlement policy", e);
            throw new IdentityException("Error while retrieving entitlement policies", e);
        }

        return resources.toArray(new Resource[resources.size()]);
    }

}
