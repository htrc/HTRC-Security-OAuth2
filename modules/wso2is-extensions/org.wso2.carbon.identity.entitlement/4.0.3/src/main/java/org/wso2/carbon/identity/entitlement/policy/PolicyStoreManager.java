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

package org.wso2.carbon.identity.entitlement.policy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.dto.PolicyDTO;
import org.wso2.carbon.identity.entitlement.dto.PolicyStoreDTO;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.identity.entitlement.pap.store.PAPPolicyStore;
import org.wso2.carbon.identity.entitlement.pap.store.PAPPolicyStoreReader;
import org.wso2.carbon.identity.entitlement.pdp.EntitlementEngine;
import org.wso2.carbon.identity.entitlement.policy.store.CarbonPolicyStore;
import org.wso2.carbon.identity.entitlement.policy.store.CarbonRegistryPolicyStore;

import java.util.Map;
import java.util.Properties;

/**
 * This manages all policy persistent
 */
public class PolicyStoreManager {

    private CarbonPolicyStore policyStore =  null;

    private static Log log = LogFactory.getLog(PolicyStoreManager.class);

    public PolicyStoreManager() {
        // get policy collection
        Map<CarbonPolicyStore, Properties> policyCollections = EntitlementServiceComponent.
                                                getEntitlementConfig().getPolicyStore();
        if(policyCollections != null && policyCollections.size() > 0){
            policyStore =  policyCollections.entrySet().iterator().next().getKey();
        } else {
            policyStore = new CarbonRegistryPolicyStore();
        }
    }

    public boolean promotePolicy(String policyId) {

        PAPPolicyStoreReader reader = new PAPPolicyStoreReader(new PAPPolicyStore());
        PolicyDTO policy = null;
        try {
            policy = reader.readPolicyDTO(policyId);
        } catch (IdentityException e) {
            log.error("Error while reading policy " + policyId +" from PAP policy store : " + e.getMessage());
        }

        if(policy != null){
            PolicyStoreDTO dto = new PolicyStoreDTO();
            dto.setPolicyId(policy.getPolicyId());
            dto.setPolicy(policy.getPolicy());
            dto.setPolicyOrder(policy.getPolicyOrder());
            dto.setAttributeDTOs(policy.getPolicyMetaData());
            if(policyStore.addPolicy(dto)){
                return true;
            } else {
                log.error("Policy " + policyId  +" can not promote to policy store : " + policyStore.getClass());
            }
        } else {
            log.error("Policy with identifier : " + policyId +" is not exist in the PAP policy store" );
        }
        return false;
    }
    
    public boolean removePolicy(String policyId) {

        if(policyStore.deletePolicy(policyId)){
            return true;
        } else {
            log.error("Policy " + policyId  +" can not remove from policy store : " + policyStore.getClass());
        }
        return false;
    }

    public void setPolicyCombiningAlgorithm(){

        PAPPolicyStoreReader reader = new PAPPolicyStoreReader(new PAPPolicyStore());
        String policyCombingAlgorithm = null;
        try {
            policyCombingAlgorithm = reader.readPolicyCombiningAlgorithm();
        } catch (IdentityException e) {
            log.error("Error while reading policy combining algorithm from PAP policy store : "
                    + e.getMessage());
        }

        if(policyCombingAlgorithm != null){
            policyStore.setPolicyCombiningAlgorithm(policyCombingAlgorithm);
            EntitlementEngine.getInstance().getCarbonPolicyFinder().init();
        }
    }
}
