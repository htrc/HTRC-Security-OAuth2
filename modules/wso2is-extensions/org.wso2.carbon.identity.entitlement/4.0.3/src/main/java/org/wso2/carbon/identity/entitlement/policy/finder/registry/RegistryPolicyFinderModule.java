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
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.dto.AttributeDTO;
import org.wso2.carbon.identity.entitlement.dto.PolicyDTO;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.identity.entitlement.policy.finder.AbstractPolicyFinderModule;
import org.wso2.carbon.identity.entitlement.policy.finder.CarbonPolicyFinderModule;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import java.util.*;

/**
 * WSO2 specific policy finder module, That reads policies from registry
 */
public class RegistryPolicyFinderModule extends AbstractPolicyFinderModule {

    private static final String MODULE_NAME = "Registry Policy Finder Module";

    private String policyStorePath;

    public static final String POLICY_COMBINING_PREFIX_1 = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:";

    public static final String POLICY_COMBINING_PREFIX_3 = "urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:";

	private static Log log = LogFactory.getLog(RegistryPolicyFinderModule.class);

    @Override
    public void init(Properties properties) throws Exception {
        policyStorePath = properties.getProperty("policyStorePath");
        if(policyStorePath == null){
            policyStorePath = "/repository/identity/Entitlement/actualStore/";
        }
    }

       
    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }

    @Override
    public int getModulePriority() {
        return 0;
    }

    @Override
    public String getPolicy(String policyId) {
        PolicyDTO dto;
        try {
            dto = getPolicyReader().readPolicy(policyId);
            return  dto.getPolicy();
        } catch (Exception e) {
            log.error("Policy with identifier " + policyId + " can not be retrieved " +
                    "from registry policy finder module" , e);
        }
        return null;
    }

    @Override
    public String[] getPolicies() {

        List<String> policies = new ArrayList<String>();

        try {
            PolicyDTO[] policyDTOs =  getPolicyReader().readAllPolicies();
            for(PolicyDTO dto : policyDTOs){
                if(dto.getPolicy() != null){
                    policies.add(dto.getPolicy());
                }
            }
        } catch (Exception e) {
            log.error("Policies can not be retrieved from registry policy finder module" , e); 
        }
        return  policies.toArray(new String[policies.size()]);
    }

    @Override
    public String[] getPolicyIdentifiers() {
        String[] policyIds = null;
        try {
            policyIds =  getPolicyReader().getAllPolicyIds();
        } catch (Exception e) {
            log.error("Policy identifiers can not be retrieved from registry policy finder module" , e);
        }
        return  policyIds;
    }

    @Override
    public Map<String, Set<AttributeDTO>> getSearchAttributes(String identifier, Set<AttributeDTO> givenAttribute) {

        PolicyDTO[] policyDTOs = null;
        Map<String, Set<AttributeDTO>> attributeMap = null;
        try {
            policyDTOs =  getPolicyReader().readAllPolicies();
        } catch (Exception e) {
            log.error("Policies can not be retrieved from registry policy finder module" , e);         
        }

        if(policyDTOs != null){
            attributeMap = new  HashMap<String, Set<AttributeDTO>>();
            for (PolicyDTO policyDTO : policyDTOs) {
                Set<AttributeDTO> attributeDTOs =
                        new HashSet<AttributeDTO>(Arrays.asList(policyDTO.getPolicyMetaData()));
                String[] policyIdRef = policyDTO.getPolicyIdReferences();
                String[] policySetIdRef = policyDTO.getPolicySetIdReferences();

                if(policyIdRef != null && policyIdRef.length > 0 || policySetIdRef != null &&
                                                                        policySetIdRef.length > 0){
                    for(PolicyDTO dto : policyDTOs){
                        if(policyIdRef != null){
                            for(String policyId : policyIdRef){
                                if(dto.getPolicyId().equals(policyId)){
                                    attributeDTOs.addAll(Arrays.asList(dto.getPolicyMetaData()));
                                }
                            }
                        }
                        for(String policySetId : policySetIdRef){
                            if(dto.getPolicyId().equals(policySetId)){
                                attributeDTOs.addAll(Arrays.asList(dto.getPolicyMetaData()));
                            }
                        }
                    }
                }
                attributeMap.put(policyDTO.getPolicyId(), attributeDTOs);
            }
        }

        return attributeMap;
    }

    @Override
    public String getPolicyCombiningAlgorithm() {
        String algorithm = null;
        try {
            algorithm =  getPolicyReader().readPolicyCombiningAlgorithm();
            return getPolicyCombiningAlgorithmUri(algorithm);
        } catch (Exception e) {
            log.error("Policy combining algorithm can not be retrieved from registry policy finder module" , e);
        }
        return algorithm;
    }    

    @Override
    public int getSupportedSearchAttributesScheme() {
        return CarbonPolicyFinderModule.COMBINATIONS_BY_CATEGORY_AND_PARAMETER;
    }

    @Override
    public boolean isDefaultCategoriesSupported() {
        return true;
    }

    @Override
    public boolean isPolicyOrderingSupport() {
        return true;
    }

    /**
     *
     */
    public void clearCache(){
        invalidateCache();    
    }


    /**
     * creates policy reader instance
     * @return
     */
    private RegistryPolicyReader getPolicyReader() {

        Registry registry = null;
        int tenantId = CarbonContext.getCurrentContext().getTenantId();
        try {
            registry = EntitlementServiceComponent.getRegistryService().
                                                            getGovernanceSystemRegistry(tenantId);
        } catch (RegistryException e) {
            log.error("Error while obtaining registry for tenant :  " + tenantId, e);
        }
        return new RegistryPolicyReader(registry, policyStorePath);
    }

	/**
	 * Creates PolicyCombiningAlgorithm object based on policy combining url
	 *
	 * @param name policy combining name as String
	 * @return PolicyCombiningAlgorithm object
	 * @throws IdentityException throws if unsupported algorithm
	 */
	private String getPolicyCombiningAlgorithmUri(String name)
			throws IdentityException {

        if(name == null){
            return null;
        }

        if("first-applicable".equals(name) || "only-one-applicable".equals(name)){
            return POLICY_COMBINING_PREFIX_1 + name;
        } else {
            return POLICY_COMBINING_PREFIX_3 + name;
        }                                             
	}
}
