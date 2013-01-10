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
package org.wso2.carbon.identity.entitlement.pdp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.wso2.balana.Balana;
import org.wso2.balana.ctx.RequestCtxFactory;
import org.wso2.balana.ctx.AbstractRequestCtx;
import org.wso2.balana.ctx.xacml2.RequestCtx;
import org.wso2.balana.finder.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.EntitlementUtil;
import org.wso2.carbon.identity.entitlement.cache.DecisionCache;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.identity.entitlement.pap.PolicyEditorDataFinder;
import org.wso2.carbon.identity.entitlement.pap.store.PAPPolicyFinder;
import org.wso2.carbon.identity.entitlement.pap.store.PAPPolicyStore;
import org.wso2.carbon.identity.entitlement.pap.store.PAPPolicyStoreReader;
import org.wso2.carbon.identity.entitlement.pip.CarbonAttributeFinder;
import org.wso2.carbon.identity.entitlement.pip.PIPExtension;
import org.wso2.carbon.identity.entitlement.policy.*;
import org.wso2.carbon.identity.entitlement.policy.finder.CarbonPolicyFinder;

import org.wso2.balana.PDP;
import org.wso2.balana.PDPConfig;
import org.wso2.balana.ParsingException;
import org.wso2.balana.ctx.ResponseCtx;
import org.wso2.balana.finder.impl.CurrentEnvModule;
import org.wso2.balana.finder.impl.SelectorModule;
import org.wso2.carbon.identity.entitlement.pip.CarbonResourceFinder;
import org.wso2.carbon.identity.entitlement.policy.publisher.PolicyPublisher;
import org.wso2.carbon.utils.CarbonUtils;

public class EntitlementEngine {

	private PolicyFinder papPolicyFinder;
	private CarbonAttributeFinder carbonAttributeFinder;
    private CarbonResourceFinder carbonResourceFinder;
    private PolicyEditorDataFinder metaDataFinder;
    private PolicyPublisher policyPublisher;
    private PolicyFinder carbonPolicyFinder;
    private PolicyStoreManager policyStoreManager;
	private PDP pdp;
    private PDP pdpTest;
    private Balana balana;
	private int tenantId;
	private static volatile EntitlementEngine engine;
	private static final Object lock = new Object();
	private int pdpDecisionCachingInterval = 60000;
	private static ConcurrentHashMap<String, EntitlementEngine> entitlementEngines = new ConcurrentHashMap<String, EntitlementEngine>();
	private Map<String, PolicyDecision> decisionCache = new ConcurrentHashMap<String, PolicyDecision>();
	private Map<String, PolicyDecision> simpleDecisionCache = new ConcurrentHashMap<String, PolicyDecision>();

	private DecisionCache decisionClearingCache = DecisionCache.getInstance();

	private static Log log = LogFactory.getLog(EntitlementEngine.class);

	/**
	 * Get a EntitlementEngine instance for that tenant. This method will return an
	 * EntitlementEngine instance if exists, or creates a new one
	 *
	 * @return EntitlementEngine instance for that tenant
	 */
	public static EntitlementEngine getInstance() {

        int tenantId = CarbonContext.getCurrentContext().getTenantId();
        if (!entitlementEngines.containsKey(Integer.toString(tenantId))) {
            entitlementEngines.put(Integer.toString(tenantId), new EntitlementEngine(tenantId));
        }
        return entitlementEngines.get(Integer.toString(tenantId));
	}

	private EntitlementEngine(int tenantId) {

        boolean isPDP = Boolean.parseBoolean((String)EntitlementServiceComponent.getEntitlementConfig().
                        getEngineProperties().get(EntitlementConstants.PDP_ENABLE));
        boolean isPAP = Boolean.parseBoolean((String)EntitlementServiceComponent.getEntitlementConfig().
                        getEngineProperties().get(EntitlementConstants.PAP_ENABLE));

        if(!isPAP && !isPDP){
            isPAP = true;
        }

        boolean balanaConfig = Boolean.parseBoolean((String)EntitlementServiceComponent.getEntitlementConfig().
                                getEngineProperties().get(EntitlementConstants.BALANA_CONFIG_ENABLE));


        if(balanaConfig){
            System.setProperty("org.wso2.balana.PDPConfigFile", CarbonUtils.getCarbonConfigDirPath()
                                + File.separator + "security" + File.separator + "balana-config.xml");
        }

        // if PDP config file is not configured, then balana instance is created from default configurations
        balana = Balana.getInstance();

        setUpAttributeFinders();
        setUpResourceFinders();

		this.tenantId = tenantId;

        metaDataFinder = new PolicyEditorDataFinder(tenantId);
        metaDataFinder.init();

        policyPublisher = new PolicyPublisher(EntitlementServiceComponent.getGovernanceRegistry(tenantId));
        policyPublisher.init();

        this.policyStoreManager = new PolicyStoreManager();

        Properties properties = EntitlementServiceComponent.getEntitlementConfig().getEngineProperties();
		String cacheEnable = properties.getProperty(EntitlementConstants.DECISION_CACHING);
		if (cacheEnable != null && "true".equals(cacheEnable.trim())) {
			String cacheInterval = properties.getProperty(EntitlementConstants.DECISION_CACHING_INTERVAL);
			if (cacheInterval != null) {
				pdpDecisionCachingInterval = Integer.parseInt(cacheInterval.trim());
			}
		} else {
			pdpDecisionCachingInterval = -1;
		}
        // adding init value to cache
        decisionClearingCache.addToCache(1);

        // Finally, initialize
        if(isPAP){
            // Test PDP with all finders but policy finder is different
            PolicyFinder policyFinder = new PolicyFinder();
            Set<PolicyFinderModule> policyModules = new HashSet<PolicyFinderModule>();
            PAPPolicyFinder papPolicyFinder = new PAPPolicyFinder(new PAPPolicyStoreReader(new PAPPolicyStore()));
            policyModules.add(papPolicyFinder);
            policyFinder.setModules(policyModules);
            this.papPolicyFinder = policyFinder;
            PDPConfig pdpConfig = new PDPConfig(balana.getPdpConfig().getAttributeFinder(),
                                policyFinder, balana.getPdpConfig().getResourceFinder(), true);
            pdpTest = new PDP(pdpConfig);
        }

        if(isPDP){
             // Actual PDP with all finders but policy finder is different
            Set<PolicyFinderModule> policyModules = new HashSet<PolicyFinderModule>();
            CarbonPolicyFinder policyFinder = new CarbonPolicyFinder();
            policyModules.add(policyFinder);
            balana.getPdpConfig().getPolicyFinder().setModules(policyModules);
            carbonPolicyFinder = balana.getPdpConfig().getPolicyFinder();
            pdp = new PDP(balana.getPdpConfig());
        }
    }


    /**
     * Test request for PDP
     * 
     * @param xacmlRequest  XACML request as String
     * @return   response as String
     */
    public String test(String xacmlRequest) {

        if(log.isDebugEnabled()){
            log.debug("XACML Request : " + xacmlRequest);
        }

        String xacmlResponse = pdpTest.evaluate(xacmlRequest);

        if(log.isDebugEnabled()){
            log.debug("XACML Response : " + xacmlResponse);
        }
        
        return xacmlResponse;
    }

	/**
	 * Evaluates the given XACML request and returns the Response that the EntitlementEngine will
	 * hand back to the PEP. PEP needs construct the XACML request before sending it to the
	 * EntitlementEngine
	 *
     * @param xacmlRequest  XACML request as String
     * @return XACML response as String
     * @throws org.wso2.balana.ParsingException throws
     * @throws org.wso2.carbon.identity.base.IdentityException throws
     */

	public String evaluate(String xacmlRequest) throws IdentityException, ParsingException {

        if(log.isDebugEnabled()){
            log.debug("XACML Request : " + xacmlRequest);
        }

        String xacmlResponse;

        if ((xacmlResponse = getFromCache(xacmlRequest, false)) != null) {
            return xacmlResponse;
		}

        Map<PIPExtension, Properties> extensions = EntitlementServiceComponent.getEntitlementConfig()
                .getExtensions();

        if(extensions != null && !extensions.isEmpty()){
            PolicyRequestBuilder policyRequestBuilder = new PolicyRequestBuilder();
            PolicyResponseBuilder policyResponseBuilder = new PolicyResponseBuilder();
            Element xacmlRequestElement = policyRequestBuilder.getXacmlRequest(xacmlRequest);
            AbstractRequestCtx requestCtx = RequestCtxFactory.getFactory().
                                                            getRequestCtx(xacmlRequestElement);
            Set<PIPExtension> pipExtensions = extensions.keySet();
            for (PIPExtension pipExtension : pipExtensions) {
                pipExtension.update(requestCtx);
            }
            ResponseCtx responseCtx; responseCtx = pdp.evaluate(requestCtx);
            xacmlResponse = policyResponseBuilder.getXacmlResponse(responseCtx);
        } else {
            xacmlResponse = pdp.evaluate(xacmlRequest);
        }

        addToCache(xacmlRequest, xacmlResponse, false);

        if(log.isDebugEnabled()){
            log.debug("XACML Response : " + xacmlResponse);
        }

        return xacmlResponse;

	}

    /**
     * Evaluates XACML request directly. This is used by advance search module.
     * Therefore caching and logging has not be implemented for this
     * 
     * @param requestCtx  Balana Object model for request
     * @return ResponseCtx  Balana Object model for response
     */
    public ResponseCtx evaluateByContext(AbstractRequestCtx requestCtx){
        return pdp.evaluate(requestCtx);
    }

    /**
	 * Evaluates the given XACML request and returns the Response that the EntitlementEngine will
	 * hand back to the PEP. Here PEP does not need construct the XACML request before sending it to the
	 * EntitlementEngine. Just can send the single attribute value. But here default attribute ids and data types
     * are used
     * @param subject subject
     * @param resource resource
     * @param action  action
     * @param environment  environment
     * @return XACML request as String object
     * @throws Exception throws, if fails
     */
    public String evaluate(String subject, String resource, String action, String environment)
            throws Exception {

        String response;
        String request =  (subject != null  ? subject : "")  + (resource != null  ? resource : "") +
                            (action != null  ? action : "") + (environment != null  ? environment : "");

        if ((response = getFromCache(request, true)) != null) {
            return response;
		}

        String requestAsString = EntitlementUtil.createSimpleXACMLRequest(subject, resource, action);

        if(log.isDebugEnabled()){
            log.debug("XACML Request : " + requestAsString);
        }
        
        response = pdp.evaluate(requestAsString);
        
        addToCache(request, response, true);

        if(log.isDebugEnabled()){
            log.debug("XACML Response : " + response);
        }

        return response;
    }


	/**
	 * This method is returns the registry based policy finder for current tenant
	 * 
	 * @return RegistryBasedPolicyFinder
	 */
	public PolicyFinder getPapPolicyFinder() {
		return papPolicyFinder;
	}


	/**
	 * This method returns the carbon based attribute finder for the current tenant
	 * 
	 * @return   CarbonAttributeFinder
	 */
	public CarbonAttributeFinder getCarbonAttributeFinder() {
		return carbonAttributeFinder;
	}

    /**
     * This method returns the policy meta data finder for the current tenant
     *
     * @return  PolicyMetaDataFinder
     */
    public PolicyEditorDataFinder getMetaDataFinder() {
        return metaDataFinder;
    }

    /**
     *  This method returns the carbon based resource finder for the current tenant
     * 
     * @return  CarbonResourceFinder
     */
    public CarbonResourceFinder getCarbonResourceFinder() {
        return carbonResourceFinder;
    }

    /**
     *  This method returns the carbon based policy finder for the current tenant
     *
     * @return  CarbonPolicyFinder
     */
    public PolicyFinder getCarbonPolicyFinder() {
        return carbonPolicyFinder;
    }

    /**
     * This method returns policy publisher
     * 
     * @return PolicyPublisher
     */
    public PolicyPublisher getPolicyPublisher() {
        return policyPublisher;
    }

    /**
     * This returns policy store manager
     * 
     * @return
     */
    public PolicyStoreManager getPolicyStoreManager() {
        return policyStoreManager;
    }

    /**
     * get entry from decision caching
     * @param request XACML request as String
     * @param simpleCache whether using simple cache or not
     * @return XACML response as String
     */
    private String getFromCache(String  request, boolean simpleCache) {

		if (pdpDecisionCachingInterval > 0) {

            PolicyDecision decision;

			int hashCode = decisionClearingCache.getFromCache();

            if (hashCode == 0) {
                clearDecisionCache(false);
                if (log.isDebugEnabled()) {
                    log.debug("Invalidation cache message is received. " +
                                            "Decision Cache is cleared for tenant " + tenantId);
                }
                return null;
            }

            if(simpleCache){
			    decision = simpleDecisionCache.get(request);
            } else {
                decision = decisionCache.get(request);
            }

			if (decision != null
					&& (decision.getCachedTime() + (long) pdpDecisionCachingInterval > Calendar
							.getInstance().getTimeInMillis())) {
				if (log.isDebugEnabled()) {
					log.debug("PDP Decision Cache Hit for tenant " + tenantId);
				}
				return decision.getResponse();
			} else {
				if (log.isDebugEnabled()) {
					log.debug("PDP Decision Cache Miss for tenant " + tenantId);
				}
                if(simpleCache){
                    simpleDecisionCache.remove(request);
                } else {
				    decisionCache.remove(request);                    
                }

				return null;
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("PDP Decision Caching Disabled");
		}
		return null;
	}

    /**
     * put entry in to cache
     * @param request  XACML request as String
     * @param response XACML response as String
     * @param simpleCache whether using simple cache or not
     */
	private void addToCache(String request, String response, boolean simpleCache) {
		if (pdpDecisionCachingInterval > 0) {
			PolicyDecision decision = new PolicyDecision();
			decision.setCachedTime(Calendar.getInstance().getTimeInMillis());
			decision.setResponse(response);
            if(simpleCache){
                simpleDecisionCache.put(request, decision);    
            } else {
			    decisionCache.put(request, decision);
            }

			if (log.isDebugEnabled()) {
				log.debug("PDP Decision Cache Updated for tenantId " + tenantId);
			}
		} else {
			if (log.isDebugEnabled()) {
				log.debug("PDP Decision Caching Disabled");
			}
		}
	}

	/**
	 * Clears the decision cache.
     * @param updateAllNodes whether to propagate the clearing of the cache to other nodes
     */
	public void clearDecisionCache(boolean updateAllNodes) {

		decisionCache.clear();
        simpleDecisionCache.clear();
        decisionClearingCache.addToCache(1);

        if(updateAllNodes){
            decisionClearingCache.invalidateCache();
            if (log.isDebugEnabled()) {
                log.debug("Invalidation decision cache of is called for tenant " + tenantId);
            }
        }
	}

    /**
     * Helper method to init engine
     */
    private void setUpAttributeFinders(){

        List<AttributeFinderModule> attributeModules = new ArrayList<AttributeFinderModule>();

        // Creates carbon attribute finder instance  and init it
        carbonAttributeFinder = new CarbonAttributeFinder(tenantId);
        carbonAttributeFinder.init();

		// Now setup attribute finder modules for the current date/time and
		// AttributeSelectors (selectors are optional, but this project does
		// support a basic implementation)
		CurrentEnvModule envAttributeModule = new CurrentEnvModule();
		SelectorModule selectorAttributeModule = new SelectorModule();

        attributeModules.add(carbonAttributeFinder);
        attributeModules.add(envAttributeModule);
        attributeModules.add(selectorAttributeModule);
        balana.getPdpConfig().getAttributeFinder().setModules(attributeModules);
    }

    /**
     * Helper method to init engine
     */
    private void setUpResourceFinders(){

        List<ResourceFinderModule> resourceModuleList = new ArrayList<ResourceFinderModule>();
        carbonResourceFinder = new CarbonResourceFinder(tenantId);
        carbonResourceFinder.init();
        resourceModuleList.add(carbonResourceFinder);
        balana.getPdpConfig().getResourceFinder().setModules(resourceModuleList);
    }

    public int getPdpDecisionCachingInterval() {
        return pdpDecisionCachingInterval;
    }
}