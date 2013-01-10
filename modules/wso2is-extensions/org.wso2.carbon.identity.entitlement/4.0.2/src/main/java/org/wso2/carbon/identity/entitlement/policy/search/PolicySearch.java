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

import org.wso2.balana.*;
import org.wso2.balana.ctx.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.EntitlementUtil;
import org.wso2.carbon.identity.entitlement.dto.*;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.identity.entitlement.pdp.EntitlementEngine;
import org.wso2.carbon.identity.entitlement.policy.PolicyStore;
import org.wso2.carbon.identity.entitlement.policy.PolicyStoreReader;
import org.wso2.carbon.identity.entitlement.policy.finder.RegistryBasedPolicyFinder;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

import java.util.*;

/**
 *  This contains the searching methods for policies based on policy attribute values and how subjects
 * are entitled to resources
 */
public class PolicySearch {

	private static Log log = LogFactory.getLog(PolicySearch.class);

    private Map<String, PolicySearchAttributeFinderModule>  finderModules =
                                        new HashMap<String, PolicySearchAttributeFinderModule>();

    private Map<String, String> categoryMap = new HashMap<String, String>();

    private Map<String, Set<String>> defaultAttributeIdMap =
                                            new HashMap<String, Set<String>>();

    private Map<String, Set<String>> defaultDataTypeMap =
                                            new HashMap<String, Set<String>>();

    private Set<PolicyDTO> policyDTOSet;

    private EntitlementEngine engine;

    public PolicySearch(EntitlementEngine engine) {

        Set<PolicyEditorAttributeDTO> dtos = engine.getMetaDataFinder().getPolicyAttributeValues();

        for(PolicyEditorAttributeDTO dto : dtos){
            String[] categoryArray = dto.getSupportedCategories();
            if(categoryArray != null && categoryArray.length > 1){
                for(int i = 0; i < categoryArray.length -1; i = i+2){
                    categoryMap.put(categoryArray[i], categoryArray[i+1]);
                }
            }

            AttributeTreeNodeDTO[] nodeDTOs =  dto.getNodeDTOs();
            for(AttributeTreeNodeDTO nodeDTO : nodeDTOs){

                String category = nodeDTO.getCategoryId();
                String attributeId = nodeDTO.getDefaultAttributeId();
                String dataType = nodeDTO.getDefaultAttributeDataType();

                Set<String> dtoSet1 = this.defaultDataTypeMap.get(category);
                if(dtoSet1 != null){
                    dtoSet1.add(dataType);
                } else {
                    Set<String> newDtoSet = new HashSet<String>();
                    newDtoSet.add(dataType);
                    this.defaultDataTypeMap.put(category, newDtoSet);
                }

                Set<String> dtoSet2 = this.defaultAttributeIdMap.get(category);
                if(dtoSet2 != null){
                    dtoSet2.add(attributeId);
                } else {
                    Set<String> newDtoSet = new HashSet<String>();
                    newDtoSet.add(attributeId);
                    this.defaultAttributeIdMap.put(category, newDtoSet);
                }
            }
        }
        
        policyDTOSet = engine.getRegistryModule().getPolicyDTOSet();

        finderModules.put("default", new CarbonPolicySearchAttributeFinderModule());

        this.engine = engine;
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

        AttributeValueDTO subjectAttributeDTO;
        boolean hierarchicalResource = false;
        EntitledResultSetDTO resultSetDTO = new EntitledResultSetDTO();
        Set<EntitledAttributesDTO> resultSet = new HashSet<EntitledAttributesDTO>();

        if(policyDTOSet == null || policyDTOSet.size() < 1){
            return null;
        }

        if(subjectName != null && subjectName.trim().length() > 0){
            subjectAttributeDTO = new AttributeValueDTO();
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


        for (PolicyDTO policyDTO : policyDTOSet) {
            if(policyDTO.isActive()){
                List<AttributeValueDTO> attributeValueDTOs = Arrays.asList(policyDTO.getPolicyMetaData());
                String[] policyIdRef = policyDTO.getPolicyIdReferences();
                String[] policySetIdRef = policyDTO.getPolicySetIdReferences();

                if(policyIdRef != null && policyIdRef.length > 0 || policySetIdRef != null &&
                                                                        policySetIdRef.length > 0){
                    for(PolicyDTO dto : policyDTOSet){
                        for(String policyId : policyIdRef){
                            if(dto.getPolicyId().equals(policyId)){
                                attributeValueDTOs.addAll(Arrays.asList(dto.getPolicyMetaData()));
                            }
                        }
                        for(String policySetId : policySetIdRef){
                            if(dto.getPolicyId().equals(policySetId)){
                                attributeValueDTOs.addAll(Arrays.asList(dto.getPolicyMetaData()));
                            }
                        }
                    }
                }

                if (attributeValueDTOs != null) {
                    Set <AttributeValueDTO> actions = new HashSet<AttributeValueDTO>();
                    Set <AttributeValueDTO> resources = new HashSet<AttributeValueDTO>();
                    Set <AttributeValueDTO> requestAttributes = new HashSet<AttributeValueDTO>();
                    if(resourceName != null && resourceName.trim().length() > 0){
                        AttributeValueDTO resourceAttribute = new AttributeValueDTO();
                        resourceAttribute.setAttributeValue(resourceName);
                        resourceAttribute.setAttributeDataType(EntitlementConstants.STRING_DATA_TYPE);
                        resourceAttribute.setAttributeId(EntitlementConstants.RESOURCE_ID_DEFAULT);
                        resourceAttribute.setAttributeType(EntitlementConstants.RESOURCE_CATEGORY_URI);
                        resources.add(resourceAttribute);
                        hierarchicalResource = true;
                    }

                    AttributeValueDTO resourceScopeAttribute = new AttributeValueDTO();
                    resourceScopeAttribute.setAttributeValue(EntitlementConstants.RESOURCE_DESCENDANTS);
                    resourceScopeAttribute.setAttributeDataType(EntitlementConstants.STRING_DATA_TYPE);
                    resourceScopeAttribute.setAttributeId(EntitlementConstants.RESOURCE_SCOPE_ID);
                    resourceScopeAttribute.setAttributeType(EntitlementConstants.RESOURCE_CATEGORY_URI);

                    for(AttributeValueDTO attributeValueDTO : attributeValueDTOs){
                        if (EntitlementConstants.ENVIRONMENT_CATEGORY_URI.equals(attributeValueDTO
                                                .getAttributeType())) {
                            requestAttributes.add(attributeValueDTO);
                            attributeValueDTO.setAttributeId(EntitlementConstants.ENVIRONMENT_ID_DEFAULT);
                            requestAttributes.add(attributeValueDTO);
                        } else if (EntitlementConstants.ACTION_CATEGORY_URI.equals(attributeValueDTO
                                                .getAttributeType())) {
                            if(action != null && action.trim().length() > 0){
                                attributeValueDTO.setAttributeValue(action);
                            }
                            actions.add(attributeValueDTO);
                            attributeValueDTO.setAttributeId(EntitlementConstants.ACTION_ID_DEFAULT);
                            actions.add(attributeValueDTO);
                        } else if (EntitlementConstants.RESOURCE_CATEGORY_URI.equals(attributeValueDTO
                                                .getAttributeType()) && !hierarchicalResource){
                            attributeValueDTO.setAttributeId(EntitlementConstants.RESOURCE_ID_DEFAULT);
                            resources.add(attributeValueDTO);
                        } 
//                        else if(EntitlementConstants.UNKNOWN.equals(attributeValueDTO.getAttributeType())){
//                            // TODO finding other possible cases for invalid search scenarios and providing errors
//                            resultSetDTO.setMessage(attributeValueDTO.getAttribute() + " in policy : "
//                                + policyDTO.getPolicyId() + " " + EntitlementConstants.SEARCH_ERROR_MESSAGE);
//                            resultSetDTO.setMessageType(EntitlementConstants.SEARCH_ERROR);
//                            resultSetDTO.setEntitledAttributesDTOs(null);
//                            break;
//                        }
                    }

                    if(resultSetDTO.getMessage() == null){
                        List<String> entitledActions = new ArrayList<String>();
                        for(AttributeValueDTO actionDTO : actions){
                            List<AttributeValueDTO>  currentRequestAttributes =
                                                        new ArrayList<AttributeValueDTO>();
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

                        for (AttributeValueDTO resource : resources) {
                            if (EntitlementConstants.RESOURCE_CATEGORY_URI.equals(resource.getAttributeType())){

                                boolean allActionsAllowed = false;

                                int noOfRequests = 1;
                                if(enableChildSearch){
                                    noOfRequests = 0;
                                }

                                while(noOfRequests < 2){
                                    List<AttributeValueDTO>  currentRequestAttributes =
                                                                new ArrayList<AttributeValueDTO>();
                                    for(AttributeValueDTO dto : requestAttributes){
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

                                for(AttributeValueDTO actionAttributeDTO : actions){

                                    if(entitledActions.contains(actionAttributeDTO.getAttributeValue())){
                                        continue;
                                    }
                                    
                                    noOfRequests = 1;
                                    if(enableChildSearch){
                                        noOfRequests = 0;
                                    }
                                    while(noOfRequests < 2){
                                        List<AttributeValueDTO>  currentRequestAttributes =
                                                                    new ArrayList<AttributeValueDTO>();
                                        for(AttributeValueDTO dto : requestAttributes){
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
        
        resultSetDTO.setEntitledAttributesDTOs(resultSet.
                        toArray(new EntitledAttributesDTO[resultSet.size()]));

		return resultSetDTO;
	}


    /**
     * Helper method to get XACML decision
     *
     * @param requestAttributes XACML request attributes
     * @return whether permit or deny
     * @throws IdentityException if fails
     */
    private boolean getResponse(List<AttributeValueDTO>  requestAttributes)
                                                                        throws IdentityException {
        AbstractRequestCtx requestCtx =
            EntitlementUtil.createRequestContext(requestAttributes);
        ResponseCtx responseCtx = null;
        try {
            responseCtx = engine.evaluate(requestCtx);
        } catch (Exception e) {
           throw new IdentityException("Error while searching entitled resources");
        }

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
     * Gets matching policies for given request
     * @param subjectName  subject name
     * @param resourceName resource name
     * @param subjectId subject attribute Id
     * @param actionName action name
     * @return List of policy ids
     * @throws IdentityException throws, if fails
     */
    private List<String> getMatchPolicyIds(String subjectName, String resourceName, String subjectId,
                                          String actionName) throws IdentityException {

        EntitlementEngine entitlementEngine = null;
        RegistryBasedPolicyFinder policyFinder;
		PolicyStoreReader policyStoreReader;
        PolicyDTO[] policyDTOs;
        boolean oneAction = false;
        boolean oneResource = false;
        List<String> result = null;
        List<AttributeValueDTO> requestAttributes = new ArrayList<AttributeValueDTO>();
        List<AttributeValueDTO> attributeValueDTOs = new ArrayList<AttributeValueDTO>();
        try {
			policyStoreReader = new PolicyStoreReader(new PolicyStore(EntitlementServiceComponent
					.getRegistryService().getGovernanceSystemRegistry()));
		} catch (RegistryException e) {
			throw new IdentityException("Error while initializing policy store");
		}

        AttributeValueDTO subject = new AttributeValueDTO();
        subject.setAttributeId(subjectId);
        subject.setAttributeValue(subjectName);
        subject.setAttributeType(EntitlementConstants.SUBJECT_ELEMENT);
        subject.setAttributeDataType(EntitlementConstants.STRING_DATA_TYPE);
        requestAttributes.add(subject);

        AttributeValueDTO resource = new AttributeValueDTO();
        resource.setAttributeId(EntitlementConstants.RESOURCE_ID_DEFAULT);
        resource.setAttributeType(EntitlementConstants.RESOURCE_ELEMENT);
        resource.setAttributeDataType(EntitlementConstants.STRING_DATA_TYPE);
        if(resourceName != null && resourceName.trim().length() > 0){
            resource.setAttributeValue(resourceName);
        } else {
            resource.setAttributeValue("  ");         // TODO
        }
        requestAttributes.add(resource);

        if(actionName != null && actionName.trim().length() > 0){
            AttributeValueDTO action = new AttributeValueDTO();
            action.setAttributeId(EntitlementConstants.ACTION_ID_DEFAULT);
            action.setAttributeValue(actionName);
            action.setAttributeType(EntitlementConstants.ACTION_ELEMENT);
            action.setAttributeDataType(EntitlementConstants.STRING_DATA_TYPE);
            requestAttributes.add(action);
            oneAction = true;
        }

        policyDTOs = policyStoreReader.readAllLightPolicyDTOs();

        for (PolicyDTO policyDTO : policyDTOs) {
            if(policyDTO.isActive()){
                PolicyDTO metaDataPolicyDTO = policyStoreReader.readMetaDataPolicyDTO(policyDTO.getPolicyId());
                attributeValueDTOs.addAll(Arrays.asList(metaDataPolicyDTO.getPolicyMetaData()));
                String[] policyIdRef = policyDTO.getPolicyIdReferences();
                String[] policySetIdRef = policyDTO.getPolicySetIdReferences();
                for(String policyId : policyIdRef){
                    PolicyDTO dto =  policyStoreReader.readMetaDataPolicyDTO(policyId);
                    attributeValueDTOs.addAll(Arrays.asList(dto.getPolicyMetaData()));
                }
                for(String policySetId : policySetIdRef){
                    PolicyDTO dto =  policyStoreReader.readMetaDataPolicyDTO(policySetId);
                    attributeValueDTOs.addAll(Arrays.asList(dto.getPolicyMetaData()));
                }
            }
        }

        for(AttributeValueDTO dto : attributeValueDTOs){
            if(!oneAction){
                if(EntitlementConstants.ACTION_ELEMENT.equals(dto.getAttributeType())){
                    dto.setAttributeId(EntitlementConstants.ACTION_ID_DEFAULT);
                    dto.setAttributeDataType(EntitlementConstants.STRING_DATA_TYPE);
                    requestAttributes.add(dto);
                }
            }
            if(EntitlementConstants.ENVIRONMENT_ELEMENT.equals(dto.getAttributeType())){
                dto.setAttributeId(EntitlementConstants.ENVIRONMENT_ID_DEFAULT);
                dto.setAttributeDataType(EntitlementConstants.STRING_DATA_TYPE);
                requestAttributes.add(dto);
            }
        }

        try {
            entitlementEngine = EntitlementEngine.
                    getInstance(EntitlementServiceComponent.getRegistryService().
                            getGovernanceSystemRegistry(CarbonContext.getCurrentContext().getTenantId()),
                                CarbonContext.getCurrentContext().getTenantId());

        } catch (RegistryException e) {
            throw new IdentityException("Entitlement Engine can not be initialized", e);
        }

        policyFinder = entitlementEngine.getRegistryModule();

        if(policyFinder != null){
            Document doc = EntitlementUtil.createRequestElement(requestAttributes);
            if(doc != null){
                try {
                    AbstractRequestCtx requestCtx = RequestCtxFactory.getFactory().
                                                            getRequestCtx((doc.getDocumentElement()));
                    EvaluationCtx ctx = EvaluationCtxFactory.getFactory().
                            getEvaluationCtx(requestCtx, entitlementEngine.getPdpConfig());
                    result = policyFinder.getMatchingPolicies(ctx);
                } catch (ParsingException e) {
                    throw new IdentityException("Error while creating XACML Request context", e);
                }
            }
        }

        return result;
    }


    public AttributeValueDTO[] getSearchResults(String appId, AttributeValueDTO[] attributeValueDTOs){

        for(AttributeValueDTO valueDTO : attributeValueDTOs){
            String category = valueDTO.getAttributeType();
            if(category == null){
                continue;
            }
            if(valueDTO.getAttributeId() == null && valueDTO.getAttributeId().trim().length() < 1){
                valueDTO.setAttributeId(defaultAttributeIdMap.get(category).iterator().next());
            }
            if(valueDTO.getAttributeDataType() == null && valueDTO.getAttributeDataType().trim().length() < 1){
                valueDTO.setAttributeId(defaultDataTypeMap.get(category).iterator().next());
            }
        }

        PolicySearchAttributeFinderModule module = finderModules.get(appId);
        if(module == null){
            module = new CarbonPolicySearchAttributeFinderModule();
        }

        Map<String, Set<AttributeValueDTO>> processedAttributes = module.
                                                getPossibleRequestAttributes(attributeValueDTOs);
        if(module.isAllCombinationsSupported()){
            //TODO
        } else {
            //TODO
        }

        return null;
    }


    public String[] getAllApplicationIds(){
        return finderModules.keySet().toArray( new String[finderModules.keySet().size()]);
    }
}
