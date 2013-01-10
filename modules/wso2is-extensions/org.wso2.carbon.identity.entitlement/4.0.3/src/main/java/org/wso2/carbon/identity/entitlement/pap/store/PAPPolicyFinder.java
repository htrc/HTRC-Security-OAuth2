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
package org.wso2.carbon.identity.entitlement.pap.store;

import java.net.URI;
import java.util.*;

import org.wso2.balana.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.wso2.balana.combine.xacml3.DenyOverridesPolicyAlg;
import org.wso2.balana.ctx.EvaluationCtx;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.EntitlementException;
import org.wso2.carbon.identity.entitlement.EntitlementUtil;
import org.wso2.carbon.identity.entitlement.dto.PolicyDTO;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.identity.entitlement.policy.collection.DefaultPolicyCollection;
import org.wso2.balana.combine.PolicyCombiningAlgorithm;
import org.wso2.balana.finder.PolicyFinder;
import org.wso2.balana.finder.PolicyFinderModule;
import org.wso2.balana.finder.PolicyFinderResult;

public class PAPPolicyFinder extends PolicyFinderModule {

	// the list of policy URLs passed to the constructor
	private PAPPolicyStoreReader policyReader;

	// the map of policies
	private DefaultPolicyCollection policies;

    //keeps policy ids according to the order
    private List<String> policyIds;

    private PolicyFinder policyFinder;

    private int maxInMemoryPolicies;

	private String globalPolicyCombiningAlgorithm;
   
    public static final String POLICY_COMBINING_PREFIX_1 = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:";

    public static final String POLICY_COMBINING_PREFIX_3 = "urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:";
    
	// the logger we'll use for all messages
	private static Log log = LogFactory.getLog(PAPPolicyFinder.class);

	/**
	 * Creates a PAPPolicyFinder that provides access to the given collection of policies.
	 * Any policy that cannot be loaded will be noted in the log, but will not cause an error. The
	 * schema file used to validate policies is defined by the property
	 * PolicyRepository.POLICY_SCHEMA_PROPERTY. If the retrieved property is null, then no schema
	 * validation will occur.
	 * 
	 * @param policyReader Policy store repository for Registry
	 */
	public PAPPolicyFinder(PAPPolicyStoreReader policyReader) {
		this.policyReader = policyReader;
	}

	/**
	 * Always returns <code>true</code> since this module does support finding policies based on
	 * reference.
	 * 
	 * @return true
	 */
	public boolean isIdReferenceSupported() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wso2.balana.finder.CarbonPolicyFinderModule#isRequestSupported()
	 */
	public boolean isRequestSupported() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wso2.balana.finder.CarbonPolicyFinderModule#init(org.wso2.balana.finder.CarbonPolicyFinder)
	 */
	public void init(PolicyFinder finder) {

		PolicyCombiningAlgorithm algorithm;
        this.policyFinder = finder;
        
		try {
			globalPolicyCombiningAlgorithm = findPolicyCombiningAlgorithm();

			if (globalPolicyCombiningAlgorithm == null) {
				globalPolicyCombiningAlgorithm = DenyOverridesPolicyAlg.algId;
			}
			algorithm = EntitlementUtil.getPolicyCombiningAlgorithm(globalPolicyCombiningAlgorithm);

            policyIds = new ArrayList<String>();

            PolicyDTO[] policyDTOs = policyReader.readAllLightPolicyDTOs();
            for(PolicyDTO dto : policyDTOs){
                policyIds.add(dto.getPolicyId());
            }

            // this is only supporting for on demand loading
            maxInMemoryPolicies = EntitlementConstants.MAX_NO_OF_IN_MEMORY_POLICIES;
            String maxInMemoryPoliciesValue = EntitlementServiceComponent.getEntitlementConfig().
                    getEngineProperties().getProperty(EntitlementConstants.MAX_POLICY_ENTRIES);
            if (maxInMemoryPoliciesValue != null && !"".equals(maxInMemoryPoliciesValue)) {
                maxInMemoryPolicies = Integer.parseInt(maxInMemoryPoliciesValue);
            }
            
            this.policies = new DefaultPolicyCollection(algorithm, maxInMemoryPolicies);

			if (log.isDebugEnabled()) {
				log.debug("Global XACML policy combining algorithm used "
						+ globalPolicyCombiningAlgorithm);
			}
		} catch (IdentityException e) {
			log.error("Error while initializing PAPPolicyFinder", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wso2.balana.finder.CarbonPolicyFinderModule#findPolicy(java.net.URI, int,
	 * org.wso2.balana.VersionConstraints, org.wso2.balana.PolicyMetaData)
	 */
	public PolicyFinderResult findPolicy(URI idReference, int type, VersionConstraints constraints,
			PolicyMetaData parentMetaData) {

		AbstractPolicy policy = policies.getPolicy(idReference, type, constraints);

        if(policy == null){
            try {
                AbstractPolicy policyFromStore = policyReader.readActivePolicy(idReference.toString(),
                                                                                    this.policyFinder);

                if(policyFromStore != null){
                    if (type == PolicyReference.POLICY_REFERENCE) {
                        if (policyFromStore instanceof Policy){
                            policy = policyFromStore;
                            policies.addPolicy(policy);
                        }
                    } else {
                        if (policyFromStore instanceof PolicySet){
                            policy = policyFromStore;
                            policies.addPolicy(policy);
                        }
                    }
                }
            } catch (IdentityException e) {
                // ignore and just log the error.
                log.error(e);
            }
        }

		if (policy == null) {
			return new PolicyFinderResult();
		} else {
			return new PolicyFinderResult(policy);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wso2.balana.finder.CarbonPolicyFinderModule#findPolicy(org.wso2.balana.EvaluationCtx)
	 */
	public PolicyFinderResult findPolicy(EvaluationCtx context) {

        ArrayList<AbstractPolicy> list = new ArrayList<AbstractPolicy>();

        try {
            for (String policyId : policyIds) {

                if(list.size() == maxInMemoryPolicies){
                    break;
                }

                // for each identifier, get only the most recent policy
                AbstractPolicy policy = policies.getPolicy(policyId);

                if(policy == null){
                    try {
                        policy = policyReader.readActivePolicy(policyId, this.policyFinder);
                    } catch (IdentityException e) {
                        //log and ignore
                        log.error(e);
                    }
                    if(policy == null){
                        continue;
                    } else {
                        policies.addPolicy(policy);
                    }
                }
                // see if we match
                MatchResult match = policy.match(context);
                int result = match.getResult();

                // if there was an error, we stop right away
                if (result == MatchResult.INDETERMINATE) {
                    log.error("Error occurred while processing the XACML policy "
                            + policy.getId().toString());
                    throw new EntitlementException(match.getStatus()); 
                }

                // if we matched, we keep track of the matching policy...
                if (result == MatchResult.MATCH) {
                    if(log.isDebugEnabled()){
                        log.debug("Matching XACML policy found " + policy.getId().toString());
                    }
                    list.add(policy);
                }
            }

            AbstractPolicy policy = policies.getEffectivePolicy(list);
			if (policy == null) {
				return new PolicyFinderResult();
			} else {
				return new PolicyFinderResult(policy);
			}
        } catch (EntitlementException e) {
            return new PolicyFinderResult(e.getStatus());
        }
	}


	/**
	 * Finds PolicyCombiningAlgorithm in registry
	 * 
	 * @return PolicyCombiningAlgorithm as String
	 */
	public String findPolicyCombiningAlgorithm() {
		try {                        
			String name = policyReader.readPolicyCombiningAlgorithm();
            if(name == null || name.trim().length() == 0){
                return null;
            }
            if("first-applicable".equals(name) || "only-one-applicable".equals(name)){
                return POLICY_COMBINING_PREFIX_1 + name;
            } else {
                return POLICY_COMBINING_PREFIX_3 + name;
            }
		} catch (IdentityException e) {
			log.warn("Error occurs while finding policy combining algorithm");
		}
		return null;
	}

	/**
	 * Returns PolicyCombiningAlgorithm
	 * 
	 * @return PolicyCombiningAlgorithm as String
	 */
	public String getGlobalPolicyCombiningAlgorithm() {
		return globalPolicyCombiningAlgorithm;
	}

    /**
     * Sets polices ids that is evaluated
     *
     * @param policyIds
     */
    public void setPolicyIds(List<String> policyIds) {
        this.policyIds = policyIds;
    }
}
