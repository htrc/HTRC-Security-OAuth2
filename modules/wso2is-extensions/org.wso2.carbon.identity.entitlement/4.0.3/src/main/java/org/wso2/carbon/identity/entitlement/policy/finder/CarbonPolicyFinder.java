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

package org.wso2.carbon.identity.entitlement.policy.finder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.balana.AbstractPolicy;
import org.wso2.balana.PolicyMetaData;
import org.wso2.balana.VersionConstraints;
import org.wso2.balana.combine.PolicyCombiningAlgorithm;
import org.wso2.balana.combine.xacml3.DenyOverridesPolicyAlg;
import org.wso2.balana.combine.xacml3.DenyOverridesRuleAlg;
import org.wso2.balana.ctx.EvaluationCtx;
import org.wso2.balana.finder.*;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.EntitlementException;
import org.wso2.carbon.identity.entitlement.EntitlementUtil;
import org.wso2.carbon.identity.entitlement.cache.EntitlementPolicyCache;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.identity.entitlement.pdp.EntitlementEngine;
import org.wso2.carbon.identity.entitlement.policy.collection.PolicyCollection;
import org.wso2.carbon.identity.entitlement.policy.PolicyReader;
import org.wso2.carbon.identity.entitlement.policy.collection.SimplePolicyCollection;

import java.net.URI;
import java.util.*;

/**
 * Policy finder of the WSO2 entitlement engine.  This an implementation of <code>PolicyFinderModule</code>
 * of Balana engine. Extensions can be plugged with this. 
 */
public class CarbonPolicyFinder extends PolicyFinderModule {

    private List<CarbonPolicyFinderModule> finderModules = null;

    private PolicyCollection policyCollection;

    private PolicyFinder finder;

	private EntitlementPolicyCache policyClearingCache = EntitlementPolicyCache.getInstance();

    private static Log log = LogFactory.getLog(CarbonPolicyFinder.class);

    @Override
    public void init(PolicyFinder finder) {

        PolicyCombiningAlgorithm policyCombiningAlgorithm = null;

        this.finder = finder;
        // get registered finder modules
		Map<CarbonPolicyFinderModule, Properties> finderModules = EntitlementServiceComponent.
                                                getEntitlementConfig().getPolicyFinderModules();

        if(finderModules != null){
            this.finderModules = new ArrayList<CarbonPolicyFinderModule>(finderModules.keySet());
        }

        // get policy collection
        Map<PolicyCollection, Properties> policyCollections = EntitlementServiceComponent.
                                                getEntitlementConfig().getPolicyCollections();
        if(policyCollections != null && policyCollections.size() > 0){
            policyCollection =  policyCollections.entrySet().iterator().next().getKey();
        } else {
            policyCollection = new SimplePolicyCollection();
        }

        // get policy reader
        PolicyReader policyReader = PolicyReader.getInstance(finder);

        // get order of the policy finder modules
        int[] moduleOrders = getPolicyModuleOrder();

        if(this.finderModules != null && this.finderModules.size() > 0){
            // find policy combining algorithm.
            // only check in highest order module
            for(CarbonPolicyFinderModule finderModule : this.finderModules){
                if(finderModule.getModulePriority() == moduleOrders[0]){
                    String algorithm = finderModule.getPolicyCombiningAlgorithm();
                    try {
                        policyCombiningAlgorithm = EntitlementUtil.getPolicyCombiningAlgorithm(algorithm);
                    } catch (IdentityException e) {
                        // ignore
                    }
                    break;
                }
            }

            // if null, set default one
            if(policyCombiningAlgorithm == null){
                policyCombiningAlgorithm  = new DenyOverridesPolicyAlg();
            }

            policyCollection.setPolicyCombiningAlgorithm(policyCombiningAlgorithm);

            for (int moduleOrder : moduleOrders) {
                for(CarbonPolicyFinderModule finderModule : this.finderModules){
                    if(finderModule.getModulePriority() == moduleOrder){
                        String[] policies = finderModule.getPolicies();
                        for(String policy : policies){
                            AbstractPolicy abstractPolicy = policyReader.getPolicy(policy);
                            if(abstractPolicy != null){
                                policyCollection.addPolicy(abstractPolicy);
                            }
                        }
                        //finderModule.invalidateCache(false);
                    }
                }
            }
        } else {
            log.warn("No Carbon policy finder modules are registered");

        }
    }


    @Override
    public String getIdentifier() {
        return super.getIdentifier();
    }
    
    @Override
    public boolean isRequestSupported() {
        return true;
    }

    @Override
    public boolean isIdReferenceSupported() {
        return true;
    }

    @Override
    public PolicyFinderResult findPolicy(EvaluationCtx context) {

        if(policyClearingCache.getFromCache() == 0){
            init(this.finder);
            policyClearingCache.addToCache(1);
            EntitlementEngine.getInstance().clearDecisionCache(false);
            if(log.isDebugEnabled()){
                int tenantId = CarbonContext.getCurrentContext().getTenantId();
                log.debug("Invalidation cache message is received. " +
                "Re-initialized policy finder module of current node and invalidate decision " +
                        "caching for tenantId : " + tenantId);
            }
        }

        try{
            AbstractPolicy policy = policyCollection.getEffectivePolicy(context);
            if (policy == null) {
                return new PolicyFinderResult();
            } else {
                return new PolicyFinderResult(policy);
            }
        } catch (EntitlementException e) {
            return new PolicyFinderResult(e.getStatus());
        }
    }

    @Override
    public PolicyFinderResult findPolicy(URI idReference, int type, VersionConstraints constraints,
                                                            PolicyMetaData parentMetaData) {

        if(policyClearingCache.getFromCache() == 0){
            init(this.finder);
            policyClearingCache.addToCache(1);
            EntitlementEngine.getInstance().clearDecisionCache(false);
            if(log.isDebugEnabled()){
                int tenantId = CarbonContext.getCurrentContext().getTenantId();
                log.debug("Invalidation cache message is received. " +
                "Re-initialized policy finder module of current node and invalidate decision " +
                        "caching for tenantId : " + tenantId);
            }
        }
        
		AbstractPolicy policy = policyCollection.getPolicy(idReference, type, constraints);

		if (policy == null) {
			return new PolicyFinderResult();
		} else {
			return new PolicyFinderResult(policy);
		}
    }



    /**
     * Helper method to order the module according to module number  //TODO : with Comparator class
     *
     * @return int array with ordered values
     */
    private int[] getPolicyModuleOrder(){

        int[] moduleOrder = new int[finderModules.size()];

        for(int i = 0; i < finderModules.size(); i++){
            CarbonPolicyFinderModule module = finderModules.get(i);
            moduleOrder[i] = module.getModulePriority();
        }

        int[] tempArray = new int[moduleOrder.length];
        Arrays.sort(moduleOrder);
        for (int i = 0; i < tempArray.length; i++) {
            int j = (moduleOrder.length-1)-i;
            tempArray[j] = moduleOrder[i];
        }        
        moduleOrder = tempArray;

        return moduleOrder;
    }
}
