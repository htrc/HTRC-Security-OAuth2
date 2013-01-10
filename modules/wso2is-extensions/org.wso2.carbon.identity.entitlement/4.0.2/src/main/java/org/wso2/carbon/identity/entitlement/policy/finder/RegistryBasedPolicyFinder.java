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
package org.wso2.carbon.identity.entitlement.policy.finder;

import java.net.URI;
import java.util.*;

import org.wso2.balana.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.wso2.balana.combine.xacml2.FirstApplicablePolicyAlg;
import org.wso2.balana.combine.xacml2.OnlyOneApplicablePolicyAlg;
import org.wso2.balana.combine.xacml3.*;
import org.wso2.balana.ctx.EvaluationCtx;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.EntitlementException;
import org.wso2.carbon.identity.entitlement.cache.EntitlementPolicyCache;
import org.wso2.carbon.identity.entitlement.dto.PolicyDTO;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.identity.entitlement.pdp.EntitlementEngine;
import org.wso2.carbon.identity.entitlement.policy.PolicyCollection;
import org.wso2.carbon.identity.entitlement.policy.PolicyStoreReader;
import org.wso2.balana.combine.PolicyCombiningAlgorithm;
import org.wso2.balana.finder.PolicyFinder;
import org.wso2.balana.finder.PolicyFinderModule;
import org.wso2.balana.finder.PolicyFinderResult;
import org.wso2.carbon.identity.entitlement.policy.PolicyTarget;

public class RegistryBasedPolicyFinder extends PolicyFinderModule {

	private static final String POLICY_COMBINING_PREFIX_1 = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:";

    private static final String POLICY_COMBINING_PREFIX_3 = "urn:oasis:names:tc:xacml:3.0:policy-combining-algorithm:";

	// the list of policy URLs passed to the constructor
	private PolicyStoreReader policyReader;

	// the map of policies
	private PolicyCollection policies;

	private int tenantId;

	private PolicyTarget[] policyTargets;

	private int cacheValue;

    private int maxInMemoryPolicies;

    private Properties cachingProperties;

	private static EntitlementPolicyCache entitlementPolicyCache = EntitlementPolicyCache.getInstance();

	private String globalPolicyCombiningAlgorithm;

    private Set<PolicyDTO> policyDTOSet;      

	// the logger we'll use for all messages
	private static Log log = LogFactory.getLog(RegistryBasedPolicyFinder.class);

	/**
	 * Creates a RegistryBasedPolicyFinder that provides access to the given collection of policies.
	 * Any policy that cannot be loaded will be noted in the log, but will not cause an error. The
	 * schema file used to validate policies is defined by the property
	 * PolicyRepository.POLICY_SCHEMA_PROPERTY. If the retrieved property is null, then no schema
	 * validation will occur.
	 * 
	 * @param policyReader Policy store repository for Registry
	 * @param tenantId tenantId to init the cache key value
	 */
	public RegistryBasedPolicyFinder(PolicyStoreReader policyReader, int tenantId) {
		this.policyReader = policyReader;
		this.tenantId = tenantId;
		String schemaName = System.getProperty(PolicyStoreReader.POLICY_SCHEMA_PROPERTY);
	}

	/**
	 * Creates a RegistryBasedPolicyFinder that provides access to the given collection of
	 * policyList based on the Registry.
	 * 
	 * @param policyReader Policy store repository for Registry
	 * @param tenantId tenantId to init the cache key value
	 * @param schemaName schema file name which is used to validate policies
	 */
	public RegistryBasedPolicyFinder(PolicyStoreReader policyReader, int tenantId, String schemaName) {
		this.policyReader = policyReader;
		this.tenantId = tenantId;
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
	 * @see org.wso2.balana.finder.PolicyFinderModule#isRequestSupported()
	 */
	public boolean isRequestSupported() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wso2.balana.finder.PolicyFinderModule#init(org.wso2.balana.finder.PolicyFinder)
	 */
	public void init(PolicyFinder finder) {

		AbstractPolicy[] policies;
		PolicyCombiningAlgorithm algorithm;
        cachingProperties = EntitlementServiceComponent.getEntitlementConfig().getCachingProperties();

		try {
			globalPolicyCombiningAlgorithm = findPolicyCombiningAlgorithm();

			if (globalPolicyCombiningAlgorithm == null) {
				globalPolicyCombiningAlgorithm = "deny-overrides";
			}
			algorithm = getPolicyCombiningAlgorithm(globalPolicyCombiningAlgorithm);

			if ("true".equals(cachingProperties.getProperty(EntitlementConstants.ON_DEMAND_POLICY_LOADING))) {
				policyTargets = policyReader.readTargets();
				maxInMemoryPolicies = EntitlementConstants.MAX_NO_OF_IN_MEMORY_POLICIES;
				String maxInMemoryPoliciesValue = cachingProperties.
                        getProperty(EntitlementConstants.MAX_POLICY_ENTRIES);
				if (maxInMemoryPoliciesValue != null && !"".equals(maxInMemoryPoliciesValue)) {
					maxInMemoryPolicies = Integer.parseInt(maxInMemoryPoliciesValue);
				}
				this.policies = new PolicyCollection(algorithm, maxInMemoryPolicies);
			} else {
				policies = policyReader.readPolicies();
				this.policies = new PolicyCollection(algorithm);
				for (AbstractPolicy policy : policies) {
					if (policy != null) {
						if (!this.policies.addPolicy(policy)) {
							if (log.isWarnEnabled()) {
								log.warn(" Trying to load the same policy multiple times: "
										+ policy.getId());
							}
						}
					}
				}
			}

            entitlementPolicyCache.addToCache(tenantId, 1);

			if (log.isDebugEnabled()) {
				log.debug("Global XACML policy combining algorithm used "
						+ globalPolicyCombiningAlgorithm);
			}

            policyDTOSet = policyReader.readAllMetaDataPolicyDTOs();
            
		} catch (IdentityException e) {
			log.error("Error while initializing RegistryBasedPolicyFinder", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wso2.balana.finder.PolicyFinderModule#findPolicy(java.net.URI, int,
	 * org.wso2.balana.VersionConstraints, org.wso2.balana.PolicyMetaData)
	 */
	public PolicyFinderResult findPolicy(URI idReference, int type, VersionConstraints constraints,
			PolicyMetaData parentMetaData) {

		int cacheEntryValue = entitlementPolicyCache.getFromCache(tenantId);
        
        if (cacheEntryValue == 0) {
            init(new PolicyFinder());
            if (log.isDebugEnabled()) {
                log.debug("Entitlement Policy cache is updated for tenant " + tenantId);
            }
            try {
                EntitlementEngine.getInstance(null, tenantId).clearDecisionCache(false);
            } catch (IdentityException e) {
                log.error("Decision Cache can not be cleared when Entitlement Policy cache is updated");
            }
            entitlementPolicyCache.invalidateCache(tenantId);
        }

		AbstractPolicy policy = policies.getPolicy(idReference.toString(), type, constraints);

		if (policy == null) {
			return new PolicyFinderResult();
		} else {
			return new PolicyFinderResult(policy);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wso2.balana.finder.PolicyFinderModule#findPolicy(org.wso2.balana.EvaluationCtx)
	 */
	public PolicyFinderResult findPolicy(EvaluationCtx context) {

		AbstractPolicy policy;

		try {
            int cacheEntryValue = entitlementPolicyCache.getFromCache(tenantId);
            if (cacheEntryValue != cacheValue) {
                init(new PolicyFinder());
                if (log.isDebugEnabled()) {
                    log.debug("Entitlement Policy cache is updated for tenant " + tenantId);
                }
                try {
                    EntitlementEngine.getInstance(null, tenantId).clearDecisionCache(false);
                } catch (IdentityException e) {
                    log.error("Decision Cache can not be cleared when Entitlement Policy cache is updated");
                }
                cacheValue = cacheEntryValue;
            }

			if ("true".equals(cachingProperties.
                    getProperty(EntitlementConstants.ON_DEMAND_POLICY_LOADING))) {
				policy = findPolicyUsingTarget(context);
			} else {
				policy = policies.getPolicy(context);
			}

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
	 * Find the matching Policy or Policy Set for given request based on the target
	 * 
	 * @param context request as EvaluationCtx object
	 * @return matching Policy of Policy Set
	 * @throws EntitlementException throws
	 */
	private AbstractPolicy findPolicyUsingTarget(EvaluationCtx context) throws EntitlementException {

		ArrayList<AbstractPolicy> list = new ArrayList<AbstractPolicy>(maxInMemoryPolicies);

		for (PolicyTarget policyTarget : policyTargets) {
			if (policyTarget != null) {
                if(list.size() >= maxInMemoryPolicies){
                    break;
                }
				AbstractTarget target = policyTarget.getTarget();
				MatchResult matchResult = target.match(context);
				int result = matchResult.getResult();

				if (result == MatchResult.INDETERMINATE) {
					log.error("Error occurred while processing the XACML policy "
							+ policyTarget.getPolicyId());
					throw new EntitlementException(matchResult.getStatus());
				}

				if (result == MatchResult.MATCH) {
					AbstractPolicy policy = policies.getPolicy(policyTarget.getPolicyId());
					if (policy != null) {
						list.add(policy);
					} else {
						try {
							policy = policyReader.readPolicy(policyTarget.getPolicyId());
						} catch (IdentityException e) {
							log.error("Error occurred while reading XACML Policy "
									+ policyTarget.getPolicyId());
						}
						if (policy != null) {
							policies.addPolicy(policy);
							list.add(policy);
						}
					}
					if (log.isDebugEnabled()) {
						log.debug("Matching XACML policy found " + policyTarget.getPolicyId());
					}
				}
			}
		}

		return policies.getPolicy(list);
	}

    /**
     * Gets applicable policies for given request
     * @param ctx  XACML request as EvaluationCtx object
     * @return list of matching policy ids
     */
    public List<String> getMatchingPolicies(EvaluationCtx ctx){

        List<String> policyIds = new ArrayList<String>();
        List<PolicyTarget> policyTargets =  new ArrayList<PolicyTarget>();

        if(this.policyTargets != null && this.policyTargets.length > 0){
            policyTargets.addAll(Arrays.asList(this.policyTargets));
        } else {
            LinkedHashMap<String, TreeSet<AbstractPolicy>> policies = this.policies.getPolicies();
            if(policies != null && policies.size() > 0){
                Set<Map.Entry<String, TreeSet<AbstractPolicy>>> entrySet = policies.entrySet();
                for(Map.Entry<String, TreeSet<AbstractPolicy>> entry : entrySet){
                    AbstractPolicy policy = entry.getValue().first();
                    PolicyTarget policyTarget = new PolicyTarget();
                    policyTarget.setPolicyId(policy.getId().toString());
                    policyTarget.setTarget(policy.getTarget());
                    policyTargets.add(policyTarget);
                }
            }
        }

        for(PolicyTarget target : policyTargets){
            MatchResult matchResult = target.getTarget().match(ctx);
            int result = matchResult.getResult();
            if (result == MatchResult.MATCH) {
                policyIds.add(target.getPolicyId());
            }
        }

        return policyIds;
    }

	/**
	 * Creates PolicyCombiningAlgorithm object based on policy combining url
	 * 
	 * @param uri policy combining url as String
	 * @return PolicyCombiningAlgorithm object
	 * @throws IdentityException throws if unsupported algorithm
	 */
	private PolicyCombiningAlgorithm getPolicyCombiningAlgorithm(String uri)
			throws IdentityException {

		if (FirstApplicablePolicyAlg.algId.equals(POLICY_COMBINING_PREFIX_1 + uri)) {
			return new FirstApplicablePolicyAlg();
		} else if (DenyOverridesPolicyAlg.algId.equals(POLICY_COMBINING_PREFIX_3 + uri)) {
			return new DenyOverridesPolicyAlg();
		} else if (PermitOverridesPolicyAlg.algId.equals(POLICY_COMBINING_PREFIX_3 + uri)) {
			return new PermitOverridesPolicyAlg();
		} else if (OnlyOneApplicablePolicyAlg.algId.equals(POLICY_COMBINING_PREFIX_1 + uri)) {
			return new OnlyOneApplicablePolicyAlg();
		} else if (OrderedDenyOverridesPolicyAlg.algId.equals(POLICY_COMBINING_PREFIX_3 + uri)) {
			return new OrderedDenyOverridesPolicyAlg();
		} else if (OrderedPermitOverridesPolicyAlg.algId.equals(POLICY_COMBINING_PREFIX_3 + uri)) {
			return new OrderedPermitOverridesPolicyAlg();
		} else if (DenyUnlessPermitPolicyAlg.algId.equals(POLICY_COMBINING_PREFIX_3 + uri)){
            return new DenyUnlessPermitPolicyAlg();
        } else if(PermitUnlessDenyPolicyAlg.algId.equals(POLICY_COMBINING_PREFIX_3 + uri)){
            return new PermitUnlessDenyPolicyAlg();    
        }

		throw new IdentityException("Unsupported policy algorithm " + uri);
	}

	/**
	 * Finds PolicyCombiningAlgorithm in registry
	 * 
	 * @return PolicyCombiningAlgorithm as String
	 */
	public String findPolicyCombiningAlgorithm() {
		try {
			return policyReader.readPolicyCombiningAlgorithm();
		} catch (IdentityException e) {
			log.warn("Error occurs while finding policy combining algorithm");
		}
		return null;
	}

	/**
	 * Return PolicyCombiningAlgorithm
	 * 
	 * @return PolicyCombiningAlgorithm as String
	 */
	public String getGlobalPolicyCombiningAlgorithm() {
		return globalPolicyCombiningAlgorithm;
	}

    /**
     * Return policy collection
     *
     * @return policies as PolicyCollection
     */
    public PolicyCollection getPolicies() {
        return policies;
    }

    /**
     * Return all policies with meta data 
     *
     * @return
     */
    public Set<PolicyDTO> getPolicyDTOSet() {
        return policyDTOSet;
    }

}
