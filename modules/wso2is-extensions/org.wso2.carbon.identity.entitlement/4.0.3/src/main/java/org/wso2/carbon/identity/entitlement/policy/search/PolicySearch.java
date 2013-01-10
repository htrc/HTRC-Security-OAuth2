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

package org.wso2.carbon.identity.entitlement.policy.search;

import org.wso2.balana.ctx.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.EntitlementUtil;
import org.wso2.carbon.identity.entitlement.cache.PolicySearchCache;
import org.wso2.carbon.identity.entitlement.dto.*;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.identity.entitlement.pdp.EntitlementEngine;
import org.wso2.carbon.identity.entitlement.policy.finder.CarbonPolicyFinderModule;

import java.util.*;

/**
 *  This contains the searching methods for policies based on policy attribute values and how subjects
 * are entitled to resources
 */
public class PolicySearch {

	private static Log log = LogFactory.getLog(PolicySearch.class);


    private List<CarbonPolicyFinderModule>  finderModules = null;

    private EntitlementEngine engine;

    private int pdpDecisionCachingInterval;

    private PolicySearchCache policySearchCache = PolicySearchCache.getInstance();

    public PolicySearch(EntitlementEngine engine) {

        // get registered finder modules
		Map<CarbonPolicyFinderModule, Properties> finderModules = EntitlementServiceComponent.
                                                getEntitlementConfig().getPolicyFinderModules();

        if(finderModules != null){
            this.finderModules = new ArrayList<CarbonPolicyFinderModule>(finderModules.keySet());
        }

        this.engine = engine;
        this.pdpDecisionCachingInterval = engine.getPdpDecisionCachingInterval();
    }

    /**
	 * This returns resource name as the list of the entitled attributes for given
     * user or role and action, after evaluating the all the active policies in the PDP
	 *
     * @param subjectName  subject name 
     * @param resourceName resource name
     * @param subjectId subject attribute Id
     * @param action  Action Name
     * @param enableChildSearch   whether search is done for the child resources under the given  resource name
     * @return entitled resource id set
     * @throws IdentityException throws
	 */
	public EntitledResultSetDTO getEntitledAttributes(String subjectName, String resourceName,
                                    String subjectId, String action, boolean enableChildSearch)
                                                                        throws IdentityException {
        String cacheKey = "";

        if(pdpDecisionCachingInterval > 0){
            cacheKey = (subjectId != null  ? subjectId : "")  + (subjectName != null  ? subjectName : "") +
                                    (resourceName != null  ? resourceName : "") +
                                    (action != null ? action : "") + enableChildSearch;
            SearchResult searchResult  = policySearchCache.getFromCache(cacheKey);

            if (searchResult != null
                    && (searchResult.getCachedTime() + (long) pdpDecisionCachingInterval > Calendar
                            .getInstance().getTimeInMillis())) {
                if (log.isDebugEnabled()) {
                    int tenantId = CarbonContext.getCurrentContext().getTenantId();
                    log.debug("PDP Search Cache Hit for tenant " + tenantId);
                }
                return searchResult.getResultSetDTO();
            }
        }

        AttributeDTO subjectAttributeDTO;
        boolean hierarchicalResource = false;
        EntitledResultSetDTO resultSetDTO = new EntitledResultSetDTO();
        Set<EntitledAttributesDTO> resultSet = new HashSet<EntitledAttributesDTO>();

        if(subjectName != null && subjectName.trim().length() > 0){
            subjectAttributeDTO = new AttributeDTO();
            subjectAttributeDTO.setAttributeType(EntitlementConstants.SUBJECT_CATEGORY_URI);
            subjectAttributeDTO.setAttributeValue(subjectName);
            subjectAttributeDTO.setAttributeDataType(EntitlementConstants.STRING_DATA_TYPE);
            if(subjectId != null && subjectId.trim().length() > 0){
                subjectAttributeDTO.setAttributeId(subjectId);
            } else {
                subjectAttributeDTO.setAttributeId(EntitlementConstants.SUBJECT_ID_DEFAULT);
            }
        } else {
            throw new IdentityException("Error : subject value can not be null");
        }

        if(getResponse(Arrays.asList(subjectAttributeDTO))){
            EntitledAttributesDTO dto = new EntitledAttributesDTO();
            dto.setAllActions(true);
            dto.setAllResources(true);
            EntitledResultSetDTO setDTO = new EntitledResultSetDTO();
            setDTO.setEntitledAttributesDTOs(new EntitledAttributesDTO[]{dto});
            return setDTO;
        }

        for(CarbonPolicyFinderModule module : finderModules){
            if(module.isDefaultCategoriesSupported() &&
                    CarbonPolicyFinderModule.COMBINATIONS_BY_CATEGORY_AND_PARAMETER ==
                                                    module.getSupportedSearchAttributesScheme()){
                Map<String, Set<AttributeDTO>> requestMap = module.
                    getSearchAttributes(null, new HashSet<AttributeDTO>(Arrays.asList(subjectAttributeDTO)));

                for(Map.Entry<String, Set<AttributeDTO>> entry : requestMap.entrySet()){
                     Set<AttributeDTO> attributeDTOs = entry.getValue();
                    if (attributeDTOs!= null) {
                        Set <AttributeDTO> actions = new HashSet<AttributeDTO>();
                        Set <AttributeDTO> resources = new HashSet<AttributeDTO>();
                        Set <AttributeDTO> requestAttributes = new HashSet<AttributeDTO>();
                        if(resourceName != null && resourceName.trim().length() > 0){
                            AttributeDTO resourceAttribute = new AttributeDTO();
                            resourceAttribute.setAttributeValue(resourceName);
                            resourceAttribute.setAttributeDataType(EntitlementConstants.STRING_DATA_TYPE);
                            resourceAttribute.setAttributeId(EntitlementConstants.RESOURCE_ID_DEFAULT);
                            resourceAttribute.setAttributeType(EntitlementConstants.RESOURCE_CATEGORY_URI);
                            resources.add(resourceAttribute);
                            hierarchicalResource = true;
                        }

                        AttributeDTO resourceScopeAttribute = new AttributeDTO();
                        resourceScopeAttribute.setAttributeValue(EntitlementConstants.RESOURCE_DESCENDANTS);
                        resourceScopeAttribute.setAttributeDataType(EntitlementConstants.STRING_DATA_TYPE);
                        resourceScopeAttribute.setAttributeId(EntitlementConstants.RESOURCE_SCOPE_ID);
                        resourceScopeAttribute.setAttributeType(EntitlementConstants.RESOURCE_CATEGORY_URI);

                        for(AttributeDTO attributeDTO : attributeDTOs){
                            if (EntitlementConstants.ENVIRONMENT_CATEGORY_URI.equals(attributeDTO
                                                    .getAttributeType())) {
                                requestAttributes.add(attributeDTO);
                                attributeDTO.setAttributeId(EntitlementConstants.ENVIRONMENT_ID_DEFAULT);
                                requestAttributes.add(attributeDTO);
                            } else if (EntitlementConstants.ACTION_CATEGORY_URI.equals(attributeDTO
                                                    .getAttributeType())) {
                                if(action != null && action.trim().length() > 0){
                                    attributeDTO.setAttributeValue(action);
                                }
                                actions.add(attributeDTO);
                                attributeDTO.setAttributeId(EntitlementConstants.ACTION_ID_DEFAULT);
                                actions.add(attributeDTO);
                            } else if (EntitlementConstants.RESOURCE_CATEGORY_URI.equals(attributeDTO
                                                    .getAttributeType()) && !hierarchicalResource){
                                attributeDTO.setAttributeId(EntitlementConstants.RESOURCE_ID_DEFAULT);
                                resources.add(attributeDTO);
                            }
                        }

                        if(resultSetDTO.getMessage() == null){
                            List<String> entitledActions = new ArrayList<String>();
                            for(AttributeDTO actionDTO : actions){
                                List<AttributeDTO>  currentRequestAttributes =
                                                            new ArrayList<AttributeDTO>();
                                currentRequestAttributes.add(subjectAttributeDTO);
                                currentRequestAttributes.add(actionDTO);
                                if(getResponse(currentRequestAttributes)){
                                    EntitledAttributesDTO dto = new EntitledAttributesDTO();
                                    dto.setAllResources(true);
                                    dto.setAction(actionDTO.getAttributeValue());
                                    resultSet.add(dto);
                                    entitledActions.add(actionDTO.getAttributeValue());
                                }
                            }

                            for (AttributeDTO resource : resources) {
                                if (EntitlementConstants.RESOURCE_CATEGORY_URI.equals(resource.getAttributeType())){

                                    boolean allActionsAllowed = false;

                                    int noOfRequests = 1;
                                    if(enableChildSearch){
                                        noOfRequests = 0;
                                    }

                                    while(noOfRequests < 2){
                                        List<AttributeDTO>  currentRequestAttributes =
                                                                    new ArrayList<AttributeDTO>();
                                        for(AttributeDTO dto : requestAttributes){
                                            currentRequestAttributes.add(dto);
                                        }
                                        if(noOfRequests  < 1){
                                            currentRequestAttributes.add(resourceScopeAttribute);
                                        }
                                        currentRequestAttributes.add(subjectAttributeDTO);
                                        currentRequestAttributes.add(resource);

                                        if(getResponse(currentRequestAttributes)){
                                            EntitledAttributesDTO dto = new EntitledAttributesDTO();
                                            dto.setResourceName(resource.getAttributeValue());
                                            dto.setAllActions(true);
                                            resultSet.add(dto);
                                            allActionsAllowed = true;
                                        }
                                        noOfRequests ++;
                                    }

                                    if(allActionsAllowed){
                                        continue;
                                    }

                                    for(AttributeDTO actionAttributeDTO : actions){

                                        if(entitledActions.contains(actionAttributeDTO.getAttributeValue())){
                                            continue;
                                        }

                                        noOfRequests = 1;
                                        if(enableChildSearch){
                                            noOfRequests = 0;
                                        }
                                        while(noOfRequests < 2){
                                            List<AttributeDTO>  currentRequestAttributes =
                                                                        new ArrayList<AttributeDTO>();
                                            for(AttributeDTO dto : requestAttributes){
                                                currentRequestAttributes.add(dto);
                                            }
                                            if(noOfRequests  < 1){
                                                currentRequestAttributes.add(resourceScopeAttribute);
                                            }
                                            currentRequestAttributes.add(subjectAttributeDTO);
                                            currentRequestAttributes.add(resource);
                                            currentRequestAttributes.add(actionAttributeDTO);

                                            if(getResponse(currentRequestAttributes)){
                                                EntitledAttributesDTO dto = new EntitledAttributesDTO();
                                                dto.setResourceName(resource.getAttributeValue());
                                                dto.setAction(actionAttributeDTO.getAttributeValue());
                                                resultSet.add(dto);
                                            }
                                            noOfRequests ++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        resultSetDTO.setEntitledAttributesDTOs(resultSet.
                        toArray(new EntitledAttributesDTO[resultSet.size()]));

        if (pdpDecisionCachingInterval > 0) {
            SearchResult result = new SearchResult();
            result.setCachedTime(Calendar.getInstance().getTimeInMillis());
            result.setResultSetDTO(resultSetDTO);
            policySearchCache.addToCache(cacheKey, result);
            if (log.isDebugEnabled()) {
                int tenantId = CarbonContext.getCurrentContext().getTenantId();
                log.debug("PDP Decision Cache Updated for tenantId " + tenantId);
            }
        }
		return resultSetDTO;
	}

    /**
     * gets all entitled attributes for given set of attributes
     * this an universal method to do policy search and find entitlement attributes
     *
     * @param identifier identifier to separate out the attributes that is used for search
     *                   this is not required and can be null   
     * @param givenAttributes user provided attributes
     * @return all the attributes that is entitled
     */
    public EntitledResultSetDTO getEntitledAttributes(String identifier, AttributeDTO[] givenAttributes){

        String cacheKey = "";

        if(pdpDecisionCachingInterval > 0){
            int hashCode = 0;
            for(AttributeDTO dto : givenAttributes){
                hashCode = hashCode + (31 * dto.hashCode());
            }

            cacheKey = identifier + hashCode;

            SearchResult searchResult  = policySearchCache.getFromCache(cacheKey);

            if (searchResult != null
                    && (searchResult.getCachedTime() + (long) pdpDecisionCachingInterval > Calendar
                            .getInstance().getTimeInMillis())) {
                if (log.isDebugEnabled()) {
                    int tenantId = CarbonContext.getCurrentContext().getTenantId();
                    log.debug("PDP Search Cache Hit for tenant " + tenantId);
                }
                return searchResult.getResultSetDTO();
            }
        }

        EntitledResultSetDTO  result = new EntitledResultSetDTO();
        Set<EntitledAttributesDTO> resultAttributes = new HashSet<EntitledAttributesDTO>();
        Set<AttributeDTO> attributeDTOs = new HashSet<AttributeDTO>(Arrays.asList(givenAttributes));

        int[] moduleOrders = getPolicyModuleOrder();
        for (int moduleOrder : moduleOrders) {
            for(CarbonPolicyFinderModule finderModule : finderModules){
                if(finderModule.getModulePriority() == moduleOrder){
                    Map<String, Set<AttributeDTO>> attributesMap = finderModule.
                                                    getSearchAttributes(identifier, attributeDTOs);
                    int supportedSearchScheme = finderModule.getSupportedSearchAttributesScheme();
                    Set<List<AttributeDTO>> requestSet =  getPossibleRequests(attributesMap, supportedSearchScheme);
                    if(requestSet == null){
                        log.error("Invalid Search scheme in policy finder : " + finderModule.getModuleName());
                    } else {
                        for(List<AttributeDTO> attributeDTOList : requestSet){
                            if(getResponse(attributeDTOList)){
                                EntitledAttributesDTO dto = new EntitledAttributesDTO();
                                dto.setAttributeDTOs(attributeDTOs.
                                            toArray(new AttributeDTO[attributeDTOList.size()]));
                                resultAttributes.add(dto);
                            }
                        }
                    }
                }
            }
        }
        result.setAdvanceResult(true);
        result.setEntitledAttributesDTOs(resultAttributes.
                                    toArray(new EntitledAttributesDTO[resultAttributes.size()]));


        if (pdpDecisionCachingInterval > 0) {
            SearchResult searchResult = new SearchResult();
            searchResult.setCachedTime(Calendar.getInstance().getTimeInMillis());
            searchResult.setResultSetDTO(result);
            policySearchCache.addToCache(cacheKey, searchResult);
            if (log.isDebugEnabled()) {
                int tenantId = CarbonContext.getCurrentContext().getTenantId();
                log.debug("PDP Decision Cache Updated for tenantId " + tenantId);
            }
        }

        return result;
    }

    /**
     * Helper method to get possible XACML requests with attributes
     *
     * @param attributesMap
     * @param supportedSearchScheme
     * @return
     */
    private Set<List<AttributeDTO>> getPossibleRequests(Map<String, Set<AttributeDTO>> attributesMap,
                                                                        int supportedSearchScheme){

        if(CarbonPolicyFinderModule.ALL_COMBINATIONS == supportedSearchScheme){

            if(attributesMap.entrySet() != null){
                return getAllCombinations(attributesMap.entrySet().iterator().next().getValue());
            }

        } else if(CarbonPolicyFinderModule.COMBINATIONS_BY_CATEGORY == supportedSearchScheme){

            return getAllCombinationsWithCategory(attributesMap);

        } else if(CarbonPolicyFinderModule.COMBINATIONS_BY_PARAMETER == supportedSearchScheme){

            Set<List<AttributeDTO>> requestSet = new HashSet<List<AttributeDTO>>();
            for(Map.Entry<String, Set<AttributeDTO>> entry : attributesMap.entrySet()){
                requestSet.addAll(getAllCombinations(entry.getValue()));
            }
            return  requestSet;

        } else if(CarbonPolicyFinderModule.COMBINATIONS_BY_CATEGORY_AND_PARAMETER == supportedSearchScheme){

            Set<List<AttributeDTO>> requestSet = new HashSet<List<AttributeDTO>>();
            for(Map.Entry<String, Set<AttributeDTO>> entry : attributesMap.entrySet()){
                Map<String, Set<AttributeDTO>> map = new HashMap<String, Set<AttributeDTO>>();
                for(AttributeDTO dto : entry.getValue()){
                    if(!map.containsKey(dto.getAttributeType())){
                        Set<AttributeDTO> attributeDTOSet = new HashSet<AttributeDTO>();
                        attributeDTOSet.add(dto);
                        map.put(dto.getAttributeType(), attributeDTOSet);
                    }
                    map.get(dto.getAttributeType()).add(dto);
                }
                requestSet.addAll(getAllCombinationsWithCategory(map));
            }
            return  requestSet;
        } else if(CarbonPolicyFinderModule.NO_COMBINATIONS == supportedSearchScheme){
            Set<List<AttributeDTO>> requestSet = new HashSet<List<AttributeDTO>>();
            for(Map.Entry<String, Set<AttributeDTO>> entry : attributesMap.entrySet()){
                requestSet.add(new ArrayList<AttributeDTO>(entry.getValue()));    
            }
            return requestSet;
        }

        return null;
    }

    /**
     * Helper method to get all possible combination for given set of attributes
     *
     * @param allAttributes
     * @return
     */
    private Set<List<AttributeDTO>>  getAllCombinations(Set<AttributeDTO> allAttributes){

        Set<List<AttributeDTO>> requestSet = new HashSet<List<AttributeDTO>>();

        if(allAttributes.isEmpty()){
            requestSet.add(new ArrayList<AttributeDTO>());
            return requestSet;
        }

        List<AttributeDTO> list = new ArrayList<AttributeDTO>(allAttributes);

        AttributeDTO head = list.get(0);
        Set<AttributeDTO> rest = new HashSet<AttributeDTO>(list.subList(1, list.size()));

        for (List<AttributeDTO> set :  getAllCombinations(rest)) {
            List<AttributeDTO> newSet = new ArrayList<AttributeDTO>();
            newSet.add(head);
            newSet.addAll(set);
            requestSet.add(newSet);
            requestSet.add(set);
        }

        return requestSet;
    }

    /**
     * Helper method to get all possible combination for given set of attributes based on category
     *
     * @param attributesMap
     * @return
     */
    private Set<List<AttributeDTO>> getAllCombinationsWithCategory(Map<String, Set<AttributeDTO>> attributesMap){

        Set<List<AttributeDTO>> requestSet = new HashSet<List<AttributeDTO>>();
        List<String> categories = new ArrayList<String>(attributesMap.keySet());

        if(!categories.isEmpty()){
            String category = categories.get(0);
            Set<AttributeDTO> attributeDTOs = attributesMap.get(category);

            List<AttributeDTO> dtoList;
            for(AttributeDTO dto : attributeDTOs){
                dtoList = new ArrayList<AttributeDTO>();
                dtoList.add(dto);
                if(categories.get(1) != null){
                    processCombinations(1, categories, attributesMap, dtoList, requestSet);
                }
            }
        }

        return requestSet;
    }

    /**
     *  Helper method to get all possible combination for given set of attributes based on category
     * 
     * @param i
     * @param categories
     * @param attributesMap
     * @param dtoList
     * @param requestSet
     */
    private void  processCombinations(int i, List<String> categories, Map<String,
                                    Set<AttributeDTO>> attributesMap, List<AttributeDTO> dtoList,
                                    Set<List<AttributeDTO>> requestSet){
        if(categories.size() > i){
        String category = categories.get(i);
        i++;
        if(category != null){
            List<AttributeDTO> currentList = new ArrayList<AttributeDTO>(dtoList);
                Set<AttributeDTO> attributeDTOs = attributesMap.get(category);
                for(AttributeDTO dto : attributeDTOs){
                    dtoList.add(dto);
                    processCombinations(i, categories, attributesMap, dtoList, requestSet);
                    requestSet.add(dtoList);
                    dtoList =  new ArrayList<AttributeDTO>(currentList);
                }
            }
        }
    }

    /**
     * Helper method to get XACML decision
     *
     * @param requestAttributes XACML request attributes
     * @return whether permit or deny
     */
    private boolean getResponse(List<AttributeDTO>  requestAttributes) {

        ResponseCtx responseCtx;
        AbstractRequestCtx requestCtx = EntitlementUtil.createRequestContext(requestAttributes);

        responseCtx = engine.evaluateByContext(requestCtx);

        if (responseCtx != null) {
            Set<AbstractResult> results = responseCtx.getResults();
            for(AbstractResult result : results){
                if(result.getDecision() == AbstractResult.DECISION_PERMIT){
                    return true;
                }
            }
        }

        return false;
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
