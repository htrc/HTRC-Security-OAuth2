/*
*  Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.identity.entitlement.ui.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wso2.carbon.identity.entitlement.ui.*;
import org.wso2.carbon.identity.entitlement.ui.dto.*;

import java.util.*;

/**
 * Util class that helps to create the XACML policy which is defined by the XACML basic policy editor
 */
public class PolicyEditorUtil {

    /**
     * map of apply element w.r.t identifier
     */
    private static Map<String, ApplyElementDTO> applyElementMap = new HashMap<String, ApplyElementDTO>();

    /**
     * Create XACML policy with the simplest input attributes
     *
     * @param policyEditorDTO
     * @param document
     * @return
     * @throws EntitlementPolicyCreationException
     */
    public static String createBasicPolicy(BasicPolicyEditorDTO policyEditorDTO, Document document)
                                                        throws EntitlementPolicyCreationException {

        PolicyElementDTO policyElementDTO = new PolicyElementDTO();
        BasicTargetElementDTO basicTargetElementDTO = null;
        List<BasicRuleElementDTO> ruleElementDTOs = new ArrayList<BasicRuleElementDTO>();

        //create policy element
        policyElementDTO.setPolicyName(policyEditorDTO.getPolicyId());
        // setting rule combining algorithm
        policyElementDTO.setRuleCombiningAlgorithms(PolicyEditorConstants.
                                                    CombiningAlog.RULE_COMBINING_FIRST_APPLICABLE);
        policyElementDTO.setPolicyDescription(policyEditorDTO.getDescription());


        if(PolicyEditorConstants.SOA_CATEGORY_USER.equals(policyEditorDTO.getAppliedCategory())){

            if(policyEditorDTO.getUserAttributeValue() != null &&
                    !PolicyEditorConstants.ANY.equals(policyEditorDTO.getUserAttributeValue().trim())){
                basicTargetElementDTO = new BasicTargetElementDTO();
                basicTargetElementDTO.setSubjectId(policyEditorDTO.getUserAttributeId());
                basicTargetElementDTO.setSubjectDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                basicTargetElementDTO.setSubjectList(policyEditorDTO.getUserAttributeValue());
                basicTargetElementDTO.
                        setFunctionOnSubjects(getBasicPolicyEditorFunction(policyEditorDTO.getFunction()));
            }

            List<BasicPolicyEditorElementDTO> elementDTOs = policyEditorDTO.getBasicPolicyEditorElementDTOs();

            if(elementDTOs != null){
                int ruleNo = 1;
                for(BasicPolicyEditorElementDTO dto : elementDTOs){
                    BasicRuleElementDTO ruleElementDTO = new BasicRuleElementDTO();

                    if(dto.getResourceValue() != null && dto.getResourceValue().trim().length() > 0 &&
                        !PolicyEditorConstants.ANY.equals(dto.getResourceValue().trim())){
                        ruleElementDTO.setResourceDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        ruleElementDTO.setResourceId(PolicyEditorConstants.RESOURCE_ID_DEFAULT);
                        ruleElementDTO.setResourceList(dto.getResourceValue());
                        ruleElementDTO.setFunctionOnResources(getBasicPolicyEditorFunction(dto.
                                                                        getFunctionOnResources()));
                    }

                    if(dto.getActionValue() != null && dto.getActionValue().trim().length() > 0 &&
                        !PolicyEditorConstants.ANY.equals(dto.getActionValue().trim())){
                        ruleElementDTO.setActionDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        ruleElementDTO.setActionList(dto.getActionValue());
                        ruleElementDTO.setActionId(PolicyEditorConstants.ACTION_ID_DEFAULT);
                        ruleElementDTO.setFunctionOnActions(getBasicPolicyEditorFunction(dto.
                                                                        getFunctionOnActions()));
                    }

                    if(dto.getEnvironmentValue() != null && dto.getEnvironmentValue().trim().length() > 0 &&
                        !PolicyEditorConstants.ANY.equals(dto.getEnvironmentValue().trim())){
                        ruleElementDTO.setEnvironmentId(dto.getEnvironmentId());
                        ruleElementDTO.setEnvironmentDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        ruleElementDTO.setEnvironmentList(dto.getEnvironmentValue());
                        ruleElementDTO.setFunctionOnEnvironment(getBasicPolicyEditorFunction(dto.
                                                                    getFunctionOnEnvironments()));
                    }

                    if(dto.getOperationType() != null && PolicyEditorConstants.PreFunctions.
                                                    CAN_DO.equals(dto.getOperationType().trim())){
                        ruleElementDTO.setRuleEffect(PolicyEditorConstants.RULE_EFFECT_PERMIT);
                    } else {
                        ruleElementDTO.setRuleEffect(PolicyEditorConstants.RULE_EFFECT_DENY);
                    }
                    ruleElementDTO.setRuleId("Rule-" + ruleNo);
                    ruleElementDTOs.add(ruleElementDTO);
                    ruleNo ++;
                }
            }
        } else if(PolicyEditorConstants.SOA_CATEGORY_RESOURCE.equals(policyEditorDTO.getAppliedCategory())){

            if(policyEditorDTO.getResourceValue() != null &&
                    !PolicyEditorConstants.ANY.equals(policyEditorDTO.getResourceValue().trim())){
                basicTargetElementDTO = new BasicTargetElementDTO();
                basicTargetElementDTO.setResourceId(PolicyEditorConstants.RESOURCE_ID_DEFAULT);
                basicTargetElementDTO.setResourceDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                basicTargetElementDTO.setResourceList(policyEditorDTO.getResourceValue());
                basicTargetElementDTO.setFunctionOnResources(getBasicPolicyEditorFunction(policyEditorDTO.
                                                                                        getFunction()));
            }
            List<BasicPolicyEditorElementDTO> elementDTOs = policyEditorDTO.getBasicPolicyEditorElementDTOs();

            if(elementDTOs != null){
                int ruleNo = 1;
                for(BasicPolicyEditorElementDTO dto : elementDTOs){
                    BasicRuleElementDTO ruleElementDTO = new BasicRuleElementDTO();

                    if(dto.getResourceValue() != null && dto.getResourceValue().trim().length() > 0 &&
                        !PolicyEditorConstants.ANY.equals(dto.getResourceValue().trim())){
                        ruleElementDTO.setResourceDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        ruleElementDTO.setResourceId(PolicyEditorConstants.RESOURCE_ID_DEFAULT);
                        ruleElementDTO.setResourceList(dto.getResourceValue());
                        ruleElementDTO.setFunctionOnResources(getBasicPolicyEditorFunction(dto.
                                                                        getFunctionOnResources()));
                    }

                    if(dto.getUserAttributeValue() != null && dto.getUserAttributeValue().trim().length() > 0 &&
                        !PolicyEditorConstants.ANY.equals(dto.getUserAttributeValue().trim())){
                        ruleElementDTO.setSubjectDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        ruleElementDTO.setSubjectId(dto.getUserAttributeId());
                        ruleElementDTO.setSubjectList(dto.getUserAttributeValue());
                        ruleElementDTO.setFunctionOnSubjects(getBasicPolicyEditorFunction(dto.
                                                                            getFunctionOnUsers()));
                    }

                    if(dto.getActionValue() != null && dto.getActionValue().trim().length() > 0 &&
                        !PolicyEditorConstants.ANY.equals(dto.getActionValue().trim())){
                        ruleElementDTO.setActionDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        ruleElementDTO.setActionList(dto.getActionValue());
                        ruleElementDTO.setActionId(PolicyEditorConstants.ACTION_ID_DEFAULT);
                        ruleElementDTO.setFunctionOnActions(getBasicPolicyEditorFunction(dto.
                                                                        getFunctionOnActions()));
                    }

                    if(dto.getEnvironmentValue() != null && dto.getEnvironmentValue().trim().length() > 0 &&
                        !PolicyEditorConstants.ANY.equals(dto.getEnvironmentValue().trim())){
                        ruleElementDTO.setEnvironmentId(dto.getEnvironmentId());
                        ruleElementDTO.setEnvironmentList(dto.getEnvironmentValue());
                        ruleElementDTO.setEnvironmentDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        ruleElementDTO.setFunctionOnEnvironment(getBasicPolicyEditorFunction(dto.
                                                                    getFunctionOnEnvironments()));
                    }

                    if(dto.getOperationType() != null && PolicyEditorConstants.PreFunctions.CAN_DO.
                                                            equals(dto.getOperationType().trim())){
                        ruleElementDTO.setRuleEffect(PolicyEditorConstants.RULE_EFFECT_PERMIT);
                    } else {
                        ruleElementDTO.setRuleEffect(PolicyEditorConstants.RULE_EFFECT_DENY);
                    }
                    ruleElementDTO.setRuleId("Rule-" + ruleNo);
                    ruleElementDTOs.add(ruleElementDTO);
                    ruleNo ++;
                }
            }
        } else if(PolicyEditorConstants.SOA_CATEGORY_ACTION.equals(policyEditorDTO.getAppliedCategory())){

            if(policyEditorDTO.getActionValue() != null &&
                    !PolicyEditorConstants.ANY.equals(policyEditorDTO.getActionValue().trim())){
                basicTargetElementDTO = new BasicTargetElementDTO();
                basicTargetElementDTO.setActionId(PolicyEditorConstants.ACTION_ID_DEFAULT);
                basicTargetElementDTO.setActionDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                basicTargetElementDTO.setActionList(policyEditorDTO.getActionValue());
                basicTargetElementDTO.setFunctionOnActions(getBasicPolicyEditorFunction(policyEditorDTO.
                                                                                        getFunction()));
            }
            List<BasicPolicyEditorElementDTO> elementDTOs = policyEditorDTO.getBasicPolicyEditorElementDTOs();

            if(elementDTOs != null){
                int ruleNo = 1;
                for(BasicPolicyEditorElementDTO dto : elementDTOs){
                    BasicRuleElementDTO ruleElementDTO = new BasicRuleElementDTO();

                    if(dto.getResourceValue() != null && dto.getResourceValue().trim().length() > 0 &&
                        !PolicyEditorConstants.ANY.equals(dto.getResourceValue().trim())){
                        ruleElementDTO.setResourceDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        ruleElementDTO.setResourceId(PolicyEditorConstants.RESOURCE_ID_DEFAULT);
                        ruleElementDTO.setResourceList(dto.getResourceValue());
                        ruleElementDTO.setFunctionOnResources(getBasicPolicyEditorFunction(dto.
                                                                        getFunctionOnResources()));
                    }

                    if(dto.getUserAttributeValue() != null && dto.getUserAttributeValue().trim().length() > 0 &&
                        !PolicyEditorConstants.ANY.equals(dto.getUserAttributeValue().trim())){
                        ruleElementDTO.setSubjectDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        ruleElementDTO.setSubjectId(dto.getUserAttributeId());
                        ruleElementDTO.setSubjectList(dto.getUserAttributeValue());
                        ruleElementDTO.setFunctionOnSubjects(getBasicPolicyEditorFunction(dto.
                                                                            getFunctionOnUsers()));
                    }

                    if(dto.getEnvironmentValue() != null && dto.getEnvironmentValue().trim().length() > 0 &&
                        !PolicyEditorConstants.ANY.equals(dto.getEnvironmentValue().trim())){
                        ruleElementDTO.setEnvironmentId(dto.getEnvironmentId());
                        ruleElementDTO.setEnvironmentList(dto.getEnvironmentValue());
                        ruleElementDTO.setEnvironmentDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        ruleElementDTO.setFunctionOnEnvironment(getBasicPolicyEditorFunction(dto.
                                                                    getFunctionOnEnvironments()));
                    }

                    if(dto.getOperationType() != null && PolicyEditorConstants.PreFunctions.CAN_DO.
                                                            equals(dto.getOperationType().trim())){
                        ruleElementDTO.setRuleEffect(PolicyEditorConstants.RULE_EFFECT_PERMIT);
                    } else {
                        ruleElementDTO.setRuleEffect(PolicyEditorConstants.RULE_EFFECT_DENY);
                    }
                    ruleElementDTO.setRuleId("Rule-" + ruleNo);
                    ruleElementDTOs.add(ruleElementDTO);
                    ruleNo ++;
                }
            }            
        }

        Element policyElement = PolicyCreatorUtil.createPolicyElement(policyElementDTO, document);
        document.appendChild(policyElement);

        if(basicTargetElementDTO != null){
            Element target = PolicyCreatorUtil.createBasicTargetElementDTO(basicTargetElementDTO, document);
            policyElement.appendChild(target);
        } else {
            policyElement.appendChild(document.createElement(EntitlementPolicyConstants.TARGET_ELEMENT));    
        }

        if(ruleElementDTOs.size() > 0){
            for(BasicRuleElementDTO dto : ruleElementDTOs){
                Element rule = PolicyCreatorUtil.createBasicRuleElementDTO(dto, document);
                policyElement.appendChild(rule);
            }
        }

        return PolicyCreatorUtil.getStringFromDocument(document);
    }

    /**
     * helper function
     * 
     * @param value
     * @return
     */
    private static String getBasicPolicyEditorFunction(String value){

        if(PolicyEditorConstants.BasicEditorFunctions.REGEXP_EQUAL.equals(value)){
            return EntitlementPolicyConstants.REGEXP_MATCH;
        } else {
            return EntitlementPolicyConstants.EQUAL_TO;
        }
    }

    /**
     * Creates DOM representation of the XACML rule element.
     *
     * @param ruleDTO
     * @param doc
     * @return
     * @throws PolicyEditorException
     */
    public static Element createRule(RuleDTO ruleDTO, Document doc) throws PolicyEditorException {

        RuleElementDTO ruleElementDTO = new RuleElementDTO();

        ruleElementDTO.setRuleId(ruleDTO.getRuleId());
        ruleElementDTO.setRuleEffect(ruleDTO.getRuleEffect());
        BasicTargetDTO targetDTO = ruleDTO.getTargetDTO();
        List<ExtendAttributeDTO> dynamicAttributeDTOs = ruleDTO.getAttributeDTOs();
        List<ObligationDTO> obligationDTOs = ruleDTO.getObligationDTOs();

        if(dynamicAttributeDTOs != null && dynamicAttributeDTOs.size() > 0){
            Map<String, ExtendAttributeDTO> dtoMap = new HashMap<String, ExtendAttributeDTO>();
            //1st creating map of dynamic attribute elements
            for(ExtendAttributeDTO dto : dynamicAttributeDTOs){
                dtoMap.put("${" + dto.getId().trim() + "}", dto);
            }
            //creating map of apply element with identifier
            for(ExtendAttributeDTO dto : dynamicAttributeDTOs){
                ApplyElementDTO applyElementDTO = createApplyElement(dto, dtoMap);
                if(applyElementDTO == null){
                    continue;
                }
                applyElementMap.put("${" + dto.getId().trim() + "}", applyElementDTO);
            }
        }

        if(targetDTO != null && targetDTO.getRowDTOList() != null && targetDTO.getRowDTOList().size() > 0){
            NewTargetElementDTO targetElementDTO = createTargetDTO(ruleDTO.getTargetDTO());
            if(targetElementDTO != null){
                ruleElementDTO.setNewTargetElementDTO(targetElementDTO);
            }
        }

        if(ruleDTO.getRowDTOList() != null && ruleDTO.getRowDTOList().size() > 0){
            ConditionElementDT0 conditionElementDT0 = createConditionDTO(ruleDTO.getRowDTOList());
            if(conditionElementDT0 != null){
                ruleElementDTO.setConditionElementDT0(conditionElementDT0);
            }
        }

        if(obligationDTOs != null && obligationDTOs.size() > 0){
            for(ObligationDTO obligationDTO : obligationDTOs){
                ObligationElementDTO elementDTO = createObligationElement(obligationDTO);
                if(elementDTO != null){
                    ruleElementDTO.addObligationElementDTO(elementDTO);
                }
            }
        }        

        return PolicyCreatorUtil.createRuleElement(ruleElementDTO, doc);
    }

    /**
     * creates DOM representation of the XACML obligation/advice element.
     * 
     * @param obligationDTOs
     * @param doc
     * @return
     * @throws PolicyEditorException
     */
    public static List<Element> createObligation(List<ObligationDTO> obligationDTOs, Document doc)
                                                                    throws PolicyEditorException {

        List<ObligationElementDTO> obligationElementDTOs = new ArrayList<ObligationElementDTO>();
        List<Element> returnList = new ArrayList<Element>();

        if(obligationDTOs != null){
            for(ObligationDTO obligationDTO : obligationDTOs){
                ObligationElementDTO elementDTO = createObligationElement(obligationDTO);
                if(elementDTO != null){
                    obligationElementDTOs.add(elementDTO);
                }
            }
        }

        if(obligationElementDTOs.size() > 0){
            List<ObligationElementDTO> obligations = new ArrayList<ObligationElementDTO>();
            List<ObligationElementDTO> advices = new ArrayList<ObligationElementDTO>();
            for(ObligationElementDTO obligationElementDTO : obligationElementDTOs){
                if(obligationElementDTO.getType() == ObligationElementDTO.ADVICE){
                    advices.add(obligationElementDTO);
                } else {
                    obligations.add(obligationElementDTO);
                }
            }
            Element obligation = PolicyCreatorUtil.createObligationsElement(obligations, doc);
            Element advice = PolicyCreatorUtil.createObligationsElement(advices, doc);
            if(obligation != null){
                returnList.add(obligation);
            }
            if(advice != null){
                returnList.add(advice);
            }
        }

        return returnList;
    }


    /**
     * Creates DOM representation of the XACML target element.
     *
     * @param basicTargetDTO
     * @param doc
     * @return
     * @throws PolicyEditorException
     */
    public static Element createTarget(BasicTargetDTO basicTargetDTO, Document doc) throws PolicyEditorException {

        Element targetElement = null;

        NewTargetElementDTO targetElementDTO = createTargetDTO(basicTargetDTO);

        if(targetElementDTO != null){
            targetElement = createTargetElement(targetElementDTO, doc);
        }

        return targetElement;
    }

    /**
     *
     * @param dynamicAttributeDTO
     * @param map
     * @return
     */
    private static ApplyElementDTO createApplyElement(ExtendAttributeDTO dynamicAttributeDTO,
                                                       Map<String, ExtendAttributeDTO> map){

        if(PolicyEditorConstants.DYNAMIC_SELECTOR_CATEGORY.equals(dynamicAttributeDTO.getSelector())){

            String category = dynamicAttributeDTO.getCategory();
            String attributeId  = dynamicAttributeDTO.getAttributeId();
            String attributeDataType = dynamicAttributeDTO.getDataType();

            if(category != null && category.trim().length() > 0 && attributeDataType != null &&
                    attributeDataType.trim().length() > 0) {
                AttributeDesignatorDTO designatorDTO = new AttributeDesignatorDTO();
                designatorDTO.setCategory(category);
                designatorDTO.setAttributeId(attributeId);
                designatorDTO.setDataType(attributeDataType);
                designatorDTO.setMustBePresent("true");

                ApplyElementDTO applyElementDTO = new ApplyElementDTO();
                applyElementDTO.setAttributeDesignators(designatorDTO);
                applyElementDTO.setFunctionId(processFunction("bag", attributeDataType));
                return applyElementDTO;
            }

        } else {

            String function  = dynamicAttributeDTO.getFunction();
            String attributeValue = dynamicAttributeDTO.getAttributeValue();
            String attributeId  = dynamicAttributeDTO.getAttributeId();
            String attributeDataType = dynamicAttributeDTO.getDataType();

            if(attributeValue != null && function != null){
                String[] values = attributeValue.split(",");

                if(values != null && values.length > 0){

                    if(function.contains("concatenate")){
                        ApplyElementDTO applyElementDTO = new ApplyElementDTO();
                        applyElementDTO.setFunctionId(processFunction(function, attributeDataType, "2.0"));
                        // there can be any number of inputs
                        for(String value : values){
                            if(map.containsKey(value)){
                                applyElementDTO.setApplyElement(createApplyElement(map.get(value), map));
                            } else {
                                AttributeValueElementDTO valueElementDTO = new AttributeValueElementDTO();
                                valueElementDTO.setAttributeDataType(attributeDataType);
                                valueElementDTO.setAttributeValue(value);
                                applyElementDTO.setAttributeValueElementDTO(valueElementDTO);
                            }
                        }

                        return applyElementDTO;
                    }
                }
            }
        }

        return null;
    }


    private static ObligationElementDTO createObligationElement(ObligationDTO obligationDTO){

        String id = obligationDTO.getObligationId();
        String effect = obligationDTO.getEffect();
        String type = obligationDTO.getType();

        if(id != null && id.trim().length() > 0 && effect != null){

            ObligationElementDTO elementDTO = new ObligationElementDTO();
            elementDTO.setId(id);
            elementDTO.setEffect(effect);
            if("Advice".equals(type)){
                elementDTO.setType(ObligationElementDTO.ADVICE);
            } else {
                elementDTO.setType(ObligationElementDTO.OBLIGATION);
            }

            String attributeValue = obligationDTO.getAttributeValue();
            String attributeDataType = obligationDTO.getAttributeValueDataType();
            String resultingAttributeId = obligationDTO.getResultAttributeId();

            if(attributeValue != null && attributeValue.trim().length() > 0 &&
                    resultingAttributeId != null && resultingAttributeId.trim().length() > 0){
                
                AttributeAssignmentElementDTO assignmentElementDTO = new
                                                                    AttributeAssignmentElementDTO();
                assignmentElementDTO.setAttributeId(resultingAttributeId);
                if(attributeValue.contains(",")){
                    String[] values = attributeValue.split(",");
                    ApplyElementDTO applyElementDTO = new ApplyElementDTO();
                    applyElementDTO.setFunctionId(processFunction("bag", attributeDataType));
                    for(String value : values){
                        if(applyElementMap.containsKey(value)){
                            applyElementDTO.setApplyElement(applyElementMap.get(value));
                        } else {
                            AttributeValueElementDTO valueElementDTO = new AttributeValueElementDTO();
                            valueElementDTO.setAttributeDataType(attributeDataType);
                            valueElementDTO.setAttributeValue(value);
                            applyElementDTO.setAttributeValueElementDTO(valueElementDTO);
                        }
                    }
                    assignmentElementDTO.setApplyElementDTO(applyElementDTO);
                } else {
                    if(applyElementMap.containsKey(attributeValue)){
                        assignmentElementDTO.setApplyElementDTO(applyElementMap.get(attributeValue));
                    } else {
                        AttributeValueElementDTO valueElementDTO = new AttributeValueElementDTO();
                        valueElementDTO.setAttributeDataType(attributeDataType);
                        valueElementDTO.setAttributeValue(attributeValue);
                        assignmentElementDTO.setValueElementDTO(valueElementDTO);
                    }
                }

                elementDTO.addAssignmentElementDTO(assignmentElementDTO);
            }

            return elementDTO;
        }

        return null;
    }

    /**
     * Creates <code>ConditionElementDT0</code> Object that represents the XACML Condition element
     *
     * @param rowDTOs
     * @return
     * @throws PolicyEditorException
     */
    public static ConditionElementDT0 createConditionDTO(List<RowDTO> rowDTOs) throws PolicyEditorException {

        ConditionElementDT0 rootApplyDTO = new ConditionElementDT0();

        ArrayList<RowDTO> temp = new ArrayList<RowDTO>();
        Set<ArrayList<RowDTO>> listSet = new HashSet <ArrayList<RowDTO>>();

        for(int i = 0; i < rowDTOs.size(); i ++){

            if(i == 0){
                temp.add(rowDTOs.get(0));
                continue;
            }

            String combineFunction = rowDTOs.get(i-1).getCombineFunction();

            if(PolicyEditorConstants.COMBINE_FUNCTION_AND.equals(combineFunction)){
                temp.add(rowDTOs.get(i));
            }

            if(PolicyEditorConstants.COMBINE_FUNCTION_OR.equals(combineFunction)){
                listSet.add(temp);
                temp = new ArrayList<RowDTO>();
                temp.add(rowDTOs.get(i)) ;
            }
        }

        listSet.add(temp);

        if(listSet.size() > 1){
            ApplyElementDTO orApplyDTO = new ApplyElementDTO();
            orApplyDTO.setFunctionId(processFunction("or"));
            for(ArrayList<RowDTO> rowDTOArrayList : listSet){
                if(rowDTOArrayList.size() > 1){
                     ApplyElementDTO andApplyDTO = new ApplyElementDTO();
                     andApplyDTO.setFunctionId(processFunction("and"));
                    for(RowDTO rowDTO : rowDTOArrayList){
                        ApplyElementDTO applyElementDTO = createApplyElement(rowDTO);
                        andApplyDTO.setApplyElement(applyElementDTO);
                    }
                    orApplyDTO.setApplyElement(andApplyDTO);

                } else if (rowDTOArrayList.size() == 1) {
                    RowDTO rowDTO = rowDTOArrayList.get(0);
                    ApplyElementDTO andApplyDTO = createApplyElement(rowDTO);
                    orApplyDTO.setApplyElement(andApplyDTO);
                }
            }
            rootApplyDTO.setApplyElement(orApplyDTO);
        } else if(listSet.size() == 1) {
            ArrayList<RowDTO> rowDTOArrayList = listSet.iterator().next();
            if(rowDTOArrayList.size() > 1){
                 ApplyElementDTO andApplyDTO = new ApplyElementDTO();
                 andApplyDTO.setFunctionId(processFunction("and"));
                for(RowDTO rowDTO : rowDTOArrayList){
                    ApplyElementDTO applyElementDTO = createApplyElement(rowDTO);
                    andApplyDTO.setApplyElement(applyElementDTO);
                }
                rootApplyDTO.setApplyElement(andApplyDTO);
            } else if (rowDTOArrayList.size() == 1) {
                RowDTO rowDTO = rowDTOArrayList.get(0);
                ApplyElementDTO andApplyDTO = createApplyElement(rowDTO);
                rootApplyDTO.setApplyElement(andApplyDTO);
            }
        }

        return rootApplyDTO;
    }

    /**
     * Creates <code>ApplyElementDTO</code> Object that represents the XACML Apply element
     *
     * @param rowDTO
     * @return
     * @throws PolicyEditorException
     */
    public static ApplyElementDTO createApplyElement(RowDTO rowDTO) throws PolicyEditorException {

        String function =  rowDTO.getFunction();
        String dataType = rowDTO.getAttributeDataType();
        String attributeValue = rowDTO.getAttributeValue();
        
        if(function == null || function.trim().length() < 1){
            throw new PolicyEditorException("Can not create Apply element:" +
                    "Missing required function Id");
        }

        if(attributeValue == null || attributeValue.trim().length() < 1){
            throw new PolicyEditorException("Can not create Apply element:" +
                    "Missing required attribute value");
        }

        ApplyElementDTO applyElementDTO = null;

        AttributeDesignatorDTO designatorDTO = new AttributeDesignatorDTO();
        designatorDTO.setCategory(rowDTO.getCategory());
        designatorDTO.setAttributeId(rowDTO.getAttributeId());
        designatorDTO.setDataType(dataType);
        designatorDTO.setMustBePresent("true");


        if(rowDTO.getFunction().contains("less" ) || rowDTO.getFunction().contains("greater" )){
            applyElementDTO = processGreaterLessThanFunctions(function, dataType, attributeValue,
                                                                                designatorDTO);
        } else if(PolicyEditorConstants.RuleFunctions.FUNCTION_EQUAL.equals(rowDTO.getFunction())){
            applyElementDTO = processEqualFunctions(function, dataType, attributeValue, designatorDTO);
        } else {
            applyElementDTO = processBagFunction(function, dataType, attributeValue, designatorDTO);
        }

        return applyElementDTO;
    }

    /**
     * Creates <code>NewTargetElementDTO</code> Object that represents the XACML Target element
     *
     * @param targetDTO
     * @return
     */
    public static NewTargetElementDTO createTargetDTO(BasicTargetDTO targetDTO) {

        AllOfElementDTO allOfElementDTO = new AllOfElementDTO();
        AnyOfElementDTO anyOfElementDTO = new AnyOfElementDTO();
        NewTargetElementDTO targetElementDTO = new NewTargetElementDTO();

        List<RowDTO> rowDTOs = targetDTO.getRowDTOList();
        ArrayList<RowDTO> tempRowDTOs = new ArrayList<RowDTO>();

        // pre function processing
        for(RowDTO rowDTO : rowDTOs){
            if(PolicyEditorConstants.PreFunctions.PRE_FUNCTION_ARE.equals(rowDTO.getPreFunction())){
                String[] attributeValues = rowDTO.getAttributeValue().split(",");
                allOfElementDTO =  new AllOfElementDTO();
                for(int j = 0; j < attributeValues.length; j ++){
                    RowDTO newDto = new RowDTO(rowDTO);
                    newDto.setAttributeValue(attributeValues[j]);
                    if(j != attributeValues.length - 1){
                        newDto.setCombineFunction(PolicyEditorConstants.COMBINE_FUNCTION_AND);
                    }
                    tempRowDTOs.add(newDto);
                }
            } else {
                tempRowDTOs.add(rowDTO);
            }
        }

        for(int i = 0; i < tempRowDTOs.size(); i ++){
            if(i == 0){
                MatchElementDTO matchElementDTO = createTargetMatch(tempRowDTOs.get(0));
                if(matchElementDTO != null){
                    allOfElementDTO.addMatchElementDTO(matchElementDTO);
                }
                continue;
            }

            String combineFunction = tempRowDTOs.get(i-1).getCombineFunction();

            if(PolicyEditorConstants.COMBINE_FUNCTION_AND.equals(combineFunction)){
                MatchElementDTO matchElementDTO = createTargetMatch(tempRowDTOs.get(i));
                if(matchElementDTO != null){
                    allOfElementDTO.addMatchElementDTO(matchElementDTO);
                }

            }

            if(PolicyEditorConstants.COMBINE_FUNCTION_OR.equals(combineFunction)){
                anyOfElementDTO.addAllOfElementDTO(allOfElementDTO);
                allOfElementDTO =  new AllOfElementDTO();
                MatchElementDTO matchElementDTO = createTargetMatch(tempRowDTOs.get(i));
                if(matchElementDTO != null){
                    allOfElementDTO.addMatchElementDTO(matchElementDTO);
                }
            }
        }
        anyOfElementDTO.addAllOfElementDTO(allOfElementDTO);
        targetElementDTO.addAnyOfElementDTO(anyOfElementDTO);

        return targetElementDTO;
    }

    /**
     *  Creates DOM representation of the XACML target element.
     * 
     * @param targetDTO
     * @param doc
     * @return
     */
    public static Element createTargetElement(NewTargetElementDTO targetDTO, Document doc) {

        Element targetElement = doc.createElement(PolicyEditorConstants.TARGET_ELEMENT);
        List<AnyOfElementDTO> anyOfElementDTOs = targetDTO.getAnyOfElementDTOs();

        for(AnyOfElementDTO anyOfElementDTO : anyOfElementDTOs){
            Element anyOfElement = doc.createElement(PolicyEditorConstants.ANY_OF_ELEMENT);
            List<AllOfElementDTO> allOfElementDTOs = anyOfElementDTO.getAllOfElementDTOs();

            for(AllOfElementDTO allOfElementDTO : allOfElementDTOs){
                Element allOfElement = doc.createElement(PolicyEditorConstants.ALL_OF_ELEMENT);
                List<MatchElementDTO> matchElementDTOs =  allOfElementDTO.getMatchElementDTOs();

                for(MatchElementDTO matchElementDTO : matchElementDTOs){
                    Element matchElement = PolicyCreatorUtil.createMatchElement(matchElementDTO, doc);

                    allOfElement.appendChild(matchElement);
                }

                anyOfElement.appendChild(allOfElement);
            }

            targetElement.appendChild(anyOfElement);
        }

        return targetElement;

    }

    /**
     * process Bag functions
     *
     * @param function
     * @param dataType
     * @param attributeValue
     * @param designatorDTO
     * @return
     */
    public static ApplyElementDTO processBagFunction(String function, String dataType,
                                    String attributeValue, AttributeDesignatorDTO designatorDTO){

        if(PolicyEditorConstants.RuleFunctions.FUNCTION_IS_IN.equals(function)){
            ApplyElementDTO applyElementDTO = new ApplyElementDTO();
            applyElementDTO.setFunctionId(processFunction("is-in", dataType));
            if(applyElementMap.containsKey(attributeValue)){
                applyElementDTO.setApplyElement(applyElementMap.get(attributeValue));
            } else {
                AttributeValueElementDTO valueElementDTO = new AttributeValueElementDTO();
                valueElementDTO.setAttributeDataType(dataType);
                valueElementDTO.setAttributeValue(attributeValue);
                applyElementDTO.setAttributeValueElementDTO(valueElementDTO);
            }

            applyElementDTO.setAttributeDesignators(designatorDTO);
            return applyElementDTO;

        } else if(PolicyEditorConstants.RuleFunctions.FUNCTION_AT_LEAST_ONE.equals(function) ||
                PolicyEditorConstants.RuleFunctions.FUNCTION_SET_EQUALS.equals(function)){

            ApplyElementDTO applyElementDTO = new ApplyElementDTO();
            if(PolicyEditorConstants.RuleFunctions.FUNCTION_AT_LEAST_ONE.equals(function)){
                applyElementDTO.setFunctionId(processFunction("at-least-one-member-of", dataType));
            } else {
                applyElementDTO.setFunctionId(processFunction("set-equals", dataType));    
            }

            String[] values = attributeValue.split(PolicyEditorConstants.ATTRIBUTE_SEPARATOR);

            ApplyElementDTO applyBagElementDTO = new ApplyElementDTO();
            applyBagElementDTO.setFunctionId(processFunction("bag", dataType));
            for(String value : values){                
                if(applyElementMap.containsKey(value)){
                    applyBagElementDTO.setApplyElement(applyElementMap.get(value));
                } else {
                    AttributeValueElementDTO valueElementDTO = new AttributeValueElementDTO();
                    valueElementDTO.setAttributeDataType(dataType);
                    valueElementDTO.setAttributeValue(value);
                    applyBagElementDTO.setAttributeValueElementDTO(valueElementDTO);
                }
            }

            applyElementDTO.setAttributeDesignators(designatorDTO);
            applyElementDTO.setApplyElement(applyBagElementDTO);

            return applyElementDTO;
        }

        return null;
    }

    /**
     * Process  equal function
     *
     * @param function
     * @param dataType
     * @param attributeValue
     * @param designatorDTO
     * @return
     */
    public static ApplyElementDTO processEqualFunctions(String function, String dataType,
                                    String attributeValue, AttributeDesignatorDTO designatorDTO) {

        if(PolicyEditorConstants.RuleFunctions.FUNCTION_EQUAL.equals(function)){

            ApplyElementDTO applyElementDTO = new ApplyElementDTO();
            if(PolicyEditorConstants.DAY_TIME_DURATION.equals(dataType) ||
                    PolicyEditorConstants.YEAR_MONTH_DURATION.equals(dataType)){
                applyElementDTO.setFunctionId(processFunction("equal", dataType, "3.0"));                
            } else {
                applyElementDTO.setFunctionId(processFunction("equal", dataType));
            }

            ApplyElementDTO oneAndOnlyApplyElement = new ApplyElementDTO();
            oneAndOnlyApplyElement.setFunctionId(processFunction("one-and-only", dataType));
            oneAndOnlyApplyElement.setAttributeDesignators(designatorDTO);

            if(applyElementMap.containsKey(attributeValue)){
                applyElementDTO.setApplyElement(applyElementMap.get(attributeValue));
            } else {
                AttributeValueElementDTO valueElementDTO = new AttributeValueElementDTO();
                valueElementDTO.setAttributeDataType(dataType);
                valueElementDTO.setAttributeValue(attributeValue);
                applyElementDTO.setAttributeValueElementDTO(valueElementDTO);
            }

            applyElementDTO.setApplyElement(oneAndOnlyApplyElement);

            return applyElementDTO;
        }

        return null;
    }

    /**
     * Process less than and greater than functions
     *
     * @param function
     * @param dataType
     * @param attributeValue
     * @param designatorDTO
     * @return
     * @throws PolicyEditorException
     */
    public static ApplyElementDTO processGreaterLessThanFunctions(String function, String dataType,
                                    String attributeValue, AttributeDesignatorDTO designatorDTO)
                                                                    throws PolicyEditorException {

        String[] values = attributeValue.split(PolicyEditorConstants.ATTRIBUTE_SEPARATOR);


        if(PolicyEditorConstants.RuleFunctions.FUNCTION_GREATER_EQUAL_AND_LESS_EQUAL.equals(function) ||
            PolicyEditorConstants.RuleFunctions.FUNCTION_GREATER_AND_LESS_EQUAL.equals(function) ||
            PolicyEditorConstants.RuleFunctions.FUNCTION_GREATER_EQUAL_AND_LESS.equals(function) ||
            PolicyEditorConstants.RuleFunctions.FUNCTION_GREATER_AND_LESS.equals(function)) {

            String leftValue;
            String rightValue;

            if(values.length == 2){
                leftValue = values[0];
                rightValue = values[1];
            } else {
                throw new PolicyEditorException("Can not create Apply element:" +
                        "Missing required attribute values for function : " + function);
            }

            ApplyElementDTO andApplyElement = new ApplyElementDTO();

            andApplyElement.setFunctionId(processFunction("and"));

            ApplyElementDTO greaterThanApplyElement = new ApplyElementDTO();
            if(PolicyEditorConstants.RuleFunctions.FUNCTION_GREATER_AND_LESS.equals(function) ||
                    PolicyEditorConstants.RuleFunctions.FUNCTION_GREATER_AND_LESS_EQUAL.equals(function)){
                greaterThanApplyElement.setFunctionId(processFunction("greater-than", dataType));
            } else {
                greaterThanApplyElement.setFunctionId(processFunction("greater-than-or-equal", dataType));                     
            }


            ApplyElementDTO lessThanApplyElement = new ApplyElementDTO();
            if(PolicyEditorConstants.RuleFunctions.FUNCTION_GREATER_AND_LESS.equals(function) ||
                    PolicyEditorConstants.RuleFunctions.FUNCTION_GREATER_EQUAL_AND_LESS.equals(function)){
                lessThanApplyElement.setFunctionId(processFunction("less-than", dataType));
            } else {
                lessThanApplyElement.setFunctionId(processFunction("less-than-or-equal", dataType));
            }

            ApplyElementDTO oneAndOnlyApplyElement = new ApplyElementDTO();
            oneAndOnlyApplyElement.setFunctionId(processFunction("one-and-only", dataType));
            oneAndOnlyApplyElement.setAttributeDesignators(designatorDTO);

            AttributeValueElementDTO leftValueElementDTO = new AttributeValueElementDTO();
            leftValueElementDTO.setAttributeDataType(dataType);
            leftValueElementDTO.setAttributeValue(leftValue);

            AttributeValueElementDTO rightValueElementDTO = new AttributeValueElementDTO();
            rightValueElementDTO.setAttributeDataType(dataType);
            rightValueElementDTO.setAttributeValue(rightValue);

            greaterThanApplyElement.setApplyElement(oneAndOnlyApplyElement);
            greaterThanApplyElement.setAttributeValueElementDTO(leftValueElementDTO);

            lessThanApplyElement.setApplyElement(oneAndOnlyApplyElement);
            lessThanApplyElement.setAttributeValueElementDTO(rightValueElementDTO);

            andApplyElement.setApplyElement(greaterThanApplyElement);
            andApplyElement.setApplyElement(lessThanApplyElement);

            return andApplyElement;

        } else {

            ApplyElementDTO applyElementDTO = new ApplyElementDTO();
            
            if(PolicyEditorConstants.RuleFunctions.FUNCTION_GREATER.equals(function)){
                applyElementDTO.setFunctionId(processFunction("greater-than", dataType));
            } else if(PolicyEditorConstants.RuleFunctions.FUNCTION_GREATER_EQUAL.equals(function)){
                applyElementDTO.setFunctionId(processFunction("greater-than-or-equal", dataType));
            } else if(PolicyEditorConstants.RuleFunctions.FUNCTION_LESS.equals(function)){
                applyElementDTO.setFunctionId(processFunction("less-than", dataType));
            } else if(PolicyEditorConstants.RuleFunctions.FUNCTION_LESS_EQUAL.equals(function)){
                applyElementDTO.setFunctionId(processFunction("less-than-or-equal", dataType));
            } else {
                throw new PolicyEditorException("Can not create Apply element:" +
                        "Invalid function : " + function);
            }

            ApplyElementDTO oneAndOnlyApplyElement = new ApplyElementDTO();
            oneAndOnlyApplyElement.setFunctionId(processFunction("one-and-only", dataType));
            oneAndOnlyApplyElement.setAttributeDesignators(designatorDTO);

            AttributeValueElementDTO valueElementDTO = new AttributeValueElementDTO();
            valueElementDTO.setAttributeDataType(dataType);
            valueElementDTO.setAttributeValue(values[0]);

            applyElementDTO.setApplyElement(oneAndOnlyApplyElement);
            applyElementDTO.setAttributeValueElementDTO(valueElementDTO);

            return  applyElementDTO;

        }
    }

    /**
     * Helper method to create full XACML function URI
     *
     * @param function
     * @param type
     * @param version
     * @return
     */
    private static String processFunction(String function, String type, String version){
        return  "urn:oasis:names:tc:xacml:" + version + ":function:" + getDataTypePrefix(type) +
                                                                                "-" + function;
    }

    /**
     * Helper method to create full XACML function URI
     *
     * @param function
     * @return
     */
    private static String processFunction(String function){
        return "urn:oasis:names:tc:xacml:1.0:function:" + function;
    }

    /**
     * Helper method to create full XACML function URI
     *
     * @param function
     * @param type
     * @return
     */
    private static String processFunction(String function, String type){
        return  "urn:oasis:names:tc:xacml:1.0:function:" + getDataTypePrefix(type) + "-" + function;
    }
//
//    /**
//     * Helper method to check whether attribute value is pre-defined one
//     *
//     * @param value
//     * @return
//     */
//    private static boolean isPreDefinedValue(String value){
//
//        if(value != null && applyElementMap != null && applyElementMap.size() > 0){
//            value = value.trim();
//            if(value.startsWith("${") && value.endsWith("}")){
//                value = value.substring(value.indexOf("{") + 1, value.indexOf("}"));
//                return applyElementMap.containsKey(value);
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * Helper method to check whether attribute value is pre-defined one
//     *
//     * @param value
//     * @param map
//     * @return
//     */
//    private static boolean isPreDefinedValue(String value, Map<String, ExtendAttributeDTO> map){
//
//        if(value != null && map != null && map.size() > 0){
//            value = value.trim();
//            if(value.startsWith("${") && value.endsWith("}")){
//                value = value.substring(value.indexOf("{") + 1, value.indexOf("}"));
//                return map.containsKey(value);
//            }
//        }
//
//        return false;
//    }

    /**
     * Helper method to create full XACML function URI
     *
     * @param dataTypeUri
     * @return
     */
    private static String getDataTypePrefix(String dataTypeUri){

        if(dataTypeUri != null){
            if(dataTypeUri.contains("#")){
                return dataTypeUri.substring(dataTypeUri.indexOf("#") + 1);
            } else if(dataTypeUri.contains(":")){
                String[] stringArray = dataTypeUri.split(":");
                if(stringArray != null && stringArray.length > 0){
                    return stringArray[stringArray.length - 1];
                }
            }
        }
        return dataTypeUri;
    }

    /**
     * Creates match element
     * 
     * @param rowDTO
     * @return
     */
    public static MatchElementDTO createTargetMatch(RowDTO rowDTO) {


        String category = rowDTO.getCategory();
        String functionId = rowDTO.getFunction();
        String attributeValue = rowDTO.getAttributeValue();
        String attributeId = rowDTO.getAttributeId();
        String dataType = rowDTO.getAttributeDataType();
        MatchElementDTO matchElementDTO;

        if (functionId != null && functionId.trim().length() > 0 && attributeValue != null &&
                attributeValue.trim().length() > 0 && category != null &&
                category.trim().length() > 0 && attributeId != null &&
                attributeId.trim().length() > 0 && dataType != null &&
                dataType.trim().length() > 0) {

            functionId = processFunction(functionId, dataType);

            matchElementDTO = new MatchElementDTO();

            AttributeValueElementDTO attributeValueElementDTO = new AttributeValueElementDTO();
            attributeValueElementDTO.setAttributeDataType(dataType);
            attributeValueElementDTO.setAttributeValue(attributeValue.trim());

            AttributeDesignatorDTO attributeDesignatorDTO = new AttributeDesignatorDTO();
            attributeDesignatorDTO.setDataType(dataType);
            attributeDesignatorDTO.setAttributeId(attributeId);
            attributeDesignatorDTO.setCategory(category);
            
            matchElementDTO.setMatchId(functionId);
            matchElementDTO.setAttributeValueElementDTO(attributeValueElementDTO);
            matchElementDTO.setAttributeDesignatorDTO(attributeDesignatorDTO);
        } else {
            return null; // TODO
        }

        return matchElementDTO;
    }


    /**
     * This method creates a match element (such as subject,action,resource or environment) of the XACML policy
     * @param matchElementDTO match element data object
     * @param doc XML document
     * @return match Element
     * @throws PolicyEditorException if any error occurs
     */
    public static Element createMatchElement(MatchElementDTO matchElementDTO, Document doc)
                                                                    throws PolicyEditorException {

        Element matchElement;

        if(matchElementDTO.getMatchId() != null && matchElementDTO.getMatchId().trim().length() > 0) {

            matchElement = doc.createElement(PolicyEditorConstants.MATCH_ELEMENT);

            matchElement.setAttribute(PolicyEditorConstants.MATCH_ID,
                    matchElementDTO.getMatchId());

            if(matchElementDTO.getAttributeValueElementDTO() != null) {
                Element attributeValueElement = createAttributeValueElement(matchElementDTO.
                        getAttributeValueElementDTO(), doc);
                matchElement.appendChild(attributeValueElement);
            }

            if(matchElementDTO.getAttributeDesignatorDTO() != null ) {
                Element attributeDesignatorElement = createAttributeDesignatorElement(matchElementDTO.
                        getAttributeDesignatorDTO(), doc);
                matchElement.appendChild(attributeDesignatorElement);
            } else if(matchElementDTO.getAttributeSelectorDTO() != null ) {
                Element attributeSelectorElement = createAttributeSelectorElement(matchElementDTO.
                        getAttributeSelectorDTO(), doc);
                matchElement.appendChild(attributeSelectorElement);
            }
        } else {
            throw new PolicyEditorException("Can not create Match element:" +
                    " Required Attributes are missing");
        }
        return matchElement;
    }

    /**
     * This method creates attribute value DOM element
     * @param attributeValueElementDTO attribute value element data object
     * @param doc XML document
     * @return attribute value element as DOM
     */
    public static Element createAttributeValueElement(AttributeValueElementDTO
            attributeValueElementDTO, Document doc) {

        Element attributeValueElement = doc.createElement(EntitlementPolicyConstants.ATTRIBUTE_VALUE);

        if(attributeValueElementDTO.getAttributeValue() != null && attributeValueElementDTO.
                getAttributeValue().trim().length() > 0) {

            attributeValueElement.setTextContent(attributeValueElementDTO.getAttributeValue().trim());

            if(attributeValueElementDTO.getAttributeDataType()!= null && attributeValueElementDTO.
                    getAttributeDataType().trim().length() > 0){
                attributeValueElement.setAttribute(EntitlementPolicyConstants.DATA_TYPE,
                                attributeValueElementDTO.getAttributeDataType());
            } else {
                attributeValueElement.setAttribute(EntitlementPolicyConstants.DATA_TYPE,
                                EntitlementPolicyConstants.STRING_DATA_TYPE);
            }

        }

        return attributeValueElement;
    }

    /**
     * This method creates attribute designator DOM element
     * @param attributeDesignatorDTO  attribute designator data object
     * @param doc  XML document
     * @return attribute designator element as DOM
     * @throws PolicyEditorException throws if missing required data
     */
    public static Element createAttributeDesignatorElement(AttributeDesignatorDTO
            attributeDesignatorDTO, Document doc) throws PolicyEditorException {

        Element attributeDesignatorElement;

        if(attributeDesignatorDTO != null && doc != null){

            String category = attributeDesignatorDTO.getCategory();
            String attributeId = attributeDesignatorDTO.getAttributeId();
            String dataType = attributeDesignatorDTO.getDataType();
            String mustBe = attributeDesignatorDTO.getMustBePresent();

            if(category != null && category.trim().length() > 0 && attributeId != null &&
                    attributeId.trim().length() > 0 && dataType != null && dataType.trim().length() > 0 &&
                    mustBe != null && mustBe.trim().length() > 0){

                attributeDesignatorElement = doc.
                        createElement(PolicyEditorConstants.ATTRIBUTE_DESIGNATOR);

                attributeDesignatorElement.setAttribute(PolicyEditorConstants.ATTRIBUTE_ID,
                        attributeId);

                attributeDesignatorElement.setAttribute(PolicyEditorConstants.CATEGORY, category);

                attributeDesignatorElement.setAttribute(PolicyEditorConstants.DATA_TYPE, dataType);

                attributeDesignatorElement.setAttribute(PolicyEditorConstants.MUST_BE_PRESENT, mustBe);

                if(attributeDesignatorDTO.getIssuer() != null && attributeDesignatorDTO.getIssuer().
                        trim().length() > 0) {
                    attributeDesignatorElement.setAttribute(EntitlementPolicyConstants.ISSUER,
                            attributeDesignatorDTO.getIssuer());
                }
            } else {
                throw new PolicyEditorException("Can not create AttributeDesignator element:" +
                        " Required Attributes are missing");
            }
        } else {
            throw new PolicyEditorException("Can not create AttributeDesignator element:" +
                    " A Null object is received");
        }
        return attributeDesignatorElement;
    }

    /**
     * This method creates attribute selector DOM element
     * @param attributeSelectorDTO  attribute selector data object
     * @param doc xML document
     * @return attribute selector element as DOM
     */
    public static Element createAttributeSelectorElement(AttributeSelectorDTO attributeSelectorDTO,
                                                         Document doc)  {

        Element attributeSelectorElement = doc.createElement(EntitlementPolicyConstants.
                ATTRIBUTE_SELECTOR);

        if(attributeSelectorDTO.getAttributeSelectorRequestContextPath() != null &&
                attributeSelectorDTO.getAttributeSelectorRequestContextPath().trim().length() > 0) {

            attributeSelectorElement.setAttribute(EntitlementPolicyConstants.REQUEST_CONTEXT_PATH,
                    EntitlementPolicyConstants.ATTRIBUTE_NAMESPACE + attributeSelectorDTO.
                            getAttributeSelectorRequestContextPath());

            if(attributeSelectorDTO.getAttributeSelectorDataType() != null &&
                    attributeSelectorDTO.getAttributeSelectorDataType().trim().length() > 0) {
                attributeSelectorElement.setAttribute(EntitlementPolicyConstants.DATA_TYPE,
                        attributeSelectorDTO.getAttributeSelectorDataType());
            } else {
                attributeSelectorElement.setAttribute(EntitlementPolicyConstants.DATA_TYPE,
                        EntitlementPolicyConstants.STRING_DATA_TYPE);
            }

            if(attributeSelectorDTO.getAttributeSelectorMustBePresent() != null &&
                    attributeSelectorDTO.getAttributeSelectorMustBePresent().trim().length() > 0) {
                attributeSelectorElement.setAttribute(EntitlementPolicyConstants.MUST_BE_PRESENT,
                        attributeSelectorDTO.getAttributeSelectorMustBePresent());
            }

        }

        return attributeSelectorElement;
    }

    /**
     * Modifies the user data that are got from policy editor. If there are null values for required
     * things, replace them with default values
     *
     * @param targetDTO
     * @param ruleDTOs
     * @param ruleElementOrder
     * @param policyBean
     */
    public static String[] processPolicyData(BasicTargetDTO targetDTO,  List<RuleDTO> ruleDTOs,
              List<ObligationDTO>  obligationDTOs, String ruleElementOrder, EntitlementPolicyBean policyBean){

        Map<String, Set<String>> defaultDataTypeMap = policyBean.getDefaultDataTypeMap();
        Map<String, Set<String>> defaultAttributeIdMap = policyBean.getDefaultAttributeIdMap();
	    Map<String, String> targetFunctionMap = policyBean.getTargetFunctionMap();
	    Map<String, String> ruleFunctionMap = policyBean.getRuleFunctionMap();
	    Map<String, String> categoryMap = policyBean.getCategoryMap();

        List<String> policyMetaDataList = new ArrayList<String>();

        List<RuleDTO> arrangedRules = new ArrayList<RuleDTO>();

        if(ruleElementOrder != null && ruleElementOrder.trim().length() > 0){
            String[] ruleIds = ruleElementOrder.
                    split(EntitlementPolicyConstants.ATTRIBUTE_SEPARATOR);
            for(String ruleId : ruleIds){
                for(RuleDTO ruleDTO : ruleDTOs) {
                    if(ruleId.equals(ruleDTO.getRuleId())){
                        arrangedRules.add(ruleDTO);
                    }
                }
            }
            ruleDTOs = arrangedRules;
        }

        if(targetDTO != null && targetDTO.getRowDTOList() != null){
            List<RowDTO> newRowDTOs = new ArrayList<RowDTO>();
            for (RowDTO rowDTO : targetDTO.getRowDTOList()){

                String category = rowDTO.getCategory();

                if(category == null){
                    continue;
                }

                String attributeValue = rowDTO.getAttributeValue();
                if(attributeValue == null || attributeValue.trim().length() < 1){
                    continue;
                }

                if(categoryMap.get(category) != null){
                    rowDTO.setCategory(categoryMap.get(category));
                }

                if(rowDTO.getAttributeDataType() == null ||
                        rowDTO.getAttributeDataType().trim().length() < 1 ||
                        rowDTO.getAttributeDataType().trim().equals("null")) {

                    if(defaultDataTypeMap.get(category) != null){
                        rowDTO.setAttributeDataType((defaultDataTypeMap.
                                            get(category).iterator().next()));
                    } else {
                        rowDTO.setAttributeDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                    }
                }

                if(rowDTO.getAttributeId() == null ||
                        rowDTO.getAttributeId().trim().length() < 1 ||
                        rowDTO.getAttributeId().trim().equals("null")) {
                    if(defaultAttributeIdMap.get(category) != null){
                        rowDTO.setAttributeId((defaultAttributeIdMap.
                                            get(category).iterator().next()));
                    }
                }

                String function = rowDTO.getFunction();
                if(function != null){
                    function = function.replace("&gt;", ">");
                    function = function.replace("&lt;", "<");
                    
                    if(targetFunctionMap.get(function) != null){
                        rowDTO.setFunction(targetFunctionMap.get(function));
                    }
                }

                RowDTO odlRowDTO = new RowDTO(rowDTO);
                odlRowDTO.setCategory(category);
                odlRowDTO.setFunction(function);
                createMetaDataFromRowDTO("target", odlRowDTO, policyMetaDataList);
                newRowDTOs.add(rowDTO);
            }
            targetDTO.setRowDTOList(newRowDTOs);
        }

        if(ruleDTOs != null){
            for(RuleDTO ruleDTO : ruleDTOs){
                List<RowDTO> newRowDTOs = new ArrayList<RowDTO>();
                for(RowDTO rowDTO : ruleDTO.getRowDTOList()){

                    String category = rowDTO.getCategory();

                    if(category == null){
                        continue;
                    }

                    String attributeValue = rowDTO.getAttributeValue();
                    if(attributeValue == null || attributeValue.trim().length() < 1){
                        continue;
                    }

                    if(categoryMap.get(category) != null){
                        rowDTO.setCategory(categoryMap.get(category));
                    }

                    if(rowDTO.getAttributeDataType() == null ||
                            rowDTO.getAttributeDataType().trim().length() < 1 ||
                            rowDTO.getAttributeDataType().trim().equals("null")) {

                        if(defaultDataTypeMap.get(category) != null){
                            rowDTO.setAttributeDataType((defaultDataTypeMap.
                                                get(category).iterator().next()));
                        } else {
                            rowDTO.setAttributeDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        }
                    }

                    if(rowDTO.getAttributeId() == null ||
                            rowDTO.getAttributeId().trim().length() < 1 ||
                            rowDTO.getAttributeId().trim().equals("null")) {
                        if(defaultAttributeIdMap.get(category) != null){
                            rowDTO.setAttributeId((defaultAttributeIdMap.
                                                get(category).iterator().next()));
                        }
                    }

                    String function = rowDTO.getFunction();
                    if(function != null){
                        function = function.replace("&gt;", ">");
                        function = function.replace("&lt;", "<");
                        if(ruleFunctionMap.get(function) != null){
                            rowDTO.setFunction(ruleFunctionMap.get(function));
                        }
                    }

                    RowDTO odlRowDTO = new RowDTO(rowDTO);
                    odlRowDTO.setCategory(category);
                    odlRowDTO.setFunction(function);
                    createMetaDataFromRowDTO("rule" + ruleDTO.getRuleId(), odlRowDTO, policyMetaDataList);
                    newRowDTOs.add(rowDTO);
                }

                ruleDTO.setRowDTOList(newRowDTOs);

                BasicTargetDTO ruleTargetDTO = ruleDTO.getTargetDTO();

                if(ruleTargetDTO == null){
                    continue;
                }

                List<RowDTO> newTargetRowDTOs = new ArrayList<RowDTO>();

                for(RowDTO rowDTO : ruleTargetDTO.getRowDTOList()){
                    String category = rowDTO.getCategory();

                    if(category == null){
                        continue;
                    }

                    String attributeValue = rowDTO.getAttributeValue();
                    if(attributeValue == null || attributeValue.trim().length() < 1){
                        continue;
                    }
                    
                    if(categoryMap.get(category) != null){
                        rowDTO.setCategory(categoryMap.get(category));
                    }

                    if(rowDTO.getAttributeDataType() == null ||
                            rowDTO.getAttributeDataType().trim().length() < 1 ||
                            rowDTO.getAttributeDataType().trim().equals("null")) {

                        if(defaultDataTypeMap.get(category) != null){
                            rowDTO.setAttributeDataType((defaultDataTypeMap.
                                                get(category).iterator().next()));
                        } else {
                            rowDTO.setAttributeDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        }
                    }

                    if(rowDTO.getAttributeId() == null ||
                            rowDTO.getAttributeId().trim().length() < 1 ||
                            rowDTO.getAttributeId().trim().equals("null")) {
                        if(defaultAttributeIdMap.get(category) != null){
                            rowDTO.setAttributeId((defaultAttributeIdMap.
                                                get(category).iterator().next()));
                        }
                    }

                    String function = rowDTO.getFunction();
                    if(function != null){
                        function = function.replace("&gt;", ">");
                        function = function.replace("&lt;", "<");

                        if(targetFunctionMap.get(function) != null){
                            rowDTO.setFunction(targetFunctionMap.get(function));
                        }
                    }

                    RowDTO odlRowDTO = new RowDTO(rowDTO);
                    odlRowDTO.setCategory(category);
                    odlRowDTO.setFunction(function);
                    createMetaDataFromRowDTO("ruleTarget" + ruleDTO.getRuleId(), odlRowDTO, policyMetaDataList);
                    newTargetRowDTOs.add(rowDTO);
                }

                ruleTargetDTO.setRowDTOList(newTargetRowDTOs);

                List<ObligationDTO> ruleObligationDTOs = ruleDTO.getObligationDTOs();

                if(ruleObligationDTOs != null){
                    for(ObligationDTO dto : ruleObligationDTOs){

                        if(dto.getAttributeValueDataType() == null ||
                                dto.getAttributeValueDataType().trim().length() < 1 ||
                                dto.getAttributeValueDataType().trim().equals("null")) {
                            dto.setAttributeValueDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                        }

                        if(dto.getObligationId() != null){
                            createMetaDataFromObligation("ruleObligation" + ruleDTO.getRuleId(),
                                                                            dto, policyMetaDataList);
                        }
                    }
                }

                ruleDTO.setTargetDTO(ruleTargetDTO);
            }
        }

        if(obligationDTOs != null){
            for(ObligationDTO dto : obligationDTOs){
                if(dto.getAttributeValueDataType() == null ||
                        dto.getAttributeValueDataType().trim().length() < 1 ||
                        dto.getAttributeValueDataType().trim().equals("null")) {
                    dto.setAttributeValueDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                }
                if(dto.getObligationId() != null){
                    createMetaDataFromObligation("obligation" ,dto, policyMetaDataList);
                }
            }
        }

//        for(ExtendAttributeDTO attributeDTO : ruleDTO.getAttributeDTOs()){
//
//            String id = attributeDTO.getId();
//            String selector = attributeDTO.getSelector();
//            String category = null;
//            String function = null;
//
//            if(id == null){
//                continue;
//            }
//
//            if(PolicyEditorConstants.DYNAMIC_SELECTOR_FUNCTION.equals(selector)){
//
//                String attributeValue = attributeDTO.getAttributeValue();
//                if(attributeValue == null || attributeValue.trim().length() < 1){
//                    continue;
//                }
//                function = attributeDTO.getFunction();
//                if(function != null){
//                    function = function.replace("&gt;", ">");
//                    function = function.replace("&lt;", "<");
//
//                    if(ruleFunctionMap.get(function) != null){// TODO
//                        attributeDTO.setFunction(ruleFunctionMap.get(function));
//                    }
//                }
//
//                if(attributeDTO.getDataType() == null ||
//                        attributeDTO.getDataType().trim().length() < 1 ||
//                        attributeDTO.getDataType().trim().equals("null")) {
//
//                    if(category != null && defaultDataTypeMap.get(category) != null){
//                        attributeDTO.setDataType((defaultDataTypeMap.
//                                            get(category).iterator().next()));
//                    } else {
//                        attributeDTO.setDataType(PolicyEditorConstants.STRING_DATA_TYPE);
//                    }
//                }
//
//            } else {
//
//                category = attributeDTO.getCategory();
//
//                if(category == null || category.trim().length() < 1){
//                    continue;
//                }
//
//                if(categoryMap.get(category) != null){
//                    attributeDTO.setCategory(categoryMap.get(category));
//                }
//
//                if(attributeDTO.getDataType() == null ||
//                        attributeDTO.getDataType().trim().length() < 1 ||
//                        attributeDTO.getDataType().trim().equals("null")) {
//
//                    if(defaultDataTypeMap.get(category) != null){
//                        attributeDTO.setDataType((defaultDataTypeMap.
//                                            get(category).iterator().next()));
//                    } else {
//                        attributeDTO.setDataType(PolicyEditorConstants.STRING_DATA_TYPE);
//                    }
//                }
//
//                if(attributeDTO.getAttributeId() == null ||
//                        attributeDTO.getAttributeId().trim().length() < 1 ||
//                        attributeDTO.getAttributeId().trim().equals("null")) {
//                    if(defaultAttributeIdMap.get(category) != null){
//                        attributeDTO.setAttributeId((defaultAttributeIdMap.
//                                            get(category).iterator().next()));
//                    }
//                }
//            }
//
//
//            ExtendAttributeDTO odlRowDTO = new ExtendAttributeDTO(attributeDTO);
//            odlRowDTO.setCategory(category);
//            odlRowDTO.setFunction(function);
//            createMetaDataFromDynamicAttribute("targetRule" + odlRowDTO.getId(), odlRowDTO,
//                                                policyMetaDataList);
//            //newDynamicAttributeDTOs.add(attributeDTO);
//        }




        return policyMetaDataList.toArray(new String[policyMetaDataList.size()]);
    }

    private static void createMetaDataFromRowDTO(String prefix, RowDTO rowDTO, List<String> metaDataList){

        if(metaDataList != null){
            metaDataList.add(prefix + "|" + rowDTO.getCategory());
            metaDataList.add(prefix + "|" + rowDTO.getPreFunction());
            metaDataList.add(prefix + "|" + rowDTO.getFunction());
            metaDataList.add(prefix + "|" + rowDTO.getAttributeValue());
            metaDataList.add(prefix + "|" + rowDTO.getAttributeId());
            metaDataList.add(prefix + "|" + rowDTO.getAttributeDataType());
            metaDataList.add(prefix + "|" + rowDTO.getCombineFunction());
        }
    }

    private static void createMetaDataFromDynamicAttribute(String prefix, ExtendAttributeDTO dto,
                                                                        List<String> metaDataList){

        if(metaDataList != null){
            metaDataList.add(prefix + "|" + dto.getCategory());
            metaDataList.add(prefix + "|" + dto.getSelector());
            metaDataList.add(prefix + "|" + dto.getFunction());
            metaDataList.add(prefix + "|" + dto.getAttributeValue());
            metaDataList.add(prefix + "|" + dto.getAttributeId());
            metaDataList.add(prefix + "|" + dto.getDataType());
            metaDataList.add(prefix + "|" + dto.getId());
        }
    }

    private static void createMetaDataFromObligation(String prefix, ObligationDTO dto,
                                                                        List<String> metaDataList){

        if(metaDataList != null){
            metaDataList.add(prefix + "|" + dto.getType());
            metaDataList.add(prefix + "|" + dto.getObligationId());
            metaDataList.add(prefix + "|" + dto.getEffect());
            metaDataList.add(prefix + "|" + dto.getAttributeValue());
            metaDataList.add(prefix + "|" + dto.getResultAttributeId());
            metaDataList.add(prefix + "|" + dto.getAttributeValueDataType());
        }
    }

    public static String[] createBasicPolicyData(BasicPolicyEditorDTO policyEditorDTO,
                                                                        EntitlementPolicyBean bean){

        List<String> metaDataList = new ArrayList<String>();
        Map<String, String> attributeMap = bean.getAttributeIdMap();

        metaDataList.add("policyId|" + policyEditorDTO.getPolicyId());
        metaDataList.add("category|" + policyEditorDTO.getAppliedCategory());
        metaDataList.add("policyDescription|" + policyEditorDTO.getDescription());
        metaDataList.add("userAttributeId|" + policyEditorDTO.getUserAttributeId());
        metaDataList.add("userAttributeValue|" + policyEditorDTO.getUserAttributeValue());
        metaDataList.add("function|" + policyEditorDTO.getFunction());
        metaDataList.add("actionValue|" + policyEditorDTO.getActionValue());
        metaDataList.add("resourceValue|" + policyEditorDTO.getResourceValue());
        metaDataList.add("category|" + policyEditorDTO.getAppliedCategory());
        metaDataList.add("environmentValue|" + policyEditorDTO.getEnvironmentValue());
        metaDataList.add("environmentId|" + policyEditorDTO.getEnvironmentId());
        if(attributeMap.get(policyEditorDTO.getUserAttributeId()) != null){
             policyEditorDTO.setUserAttributeId(attributeMap.get(policyEditorDTO.getUserAttributeId()));
        } else {
            policyEditorDTO.setUserAttributeId(PolicyEditorConstants.SUBJECT_ID_DEFAULT);
        }


        List<BasicPolicyEditorElementDTO>  elementDTOs = policyEditorDTO.getBasicPolicyEditorElementDTOs();

        if(elementDTOs != null && elementDTOs.size() > 0){
            for(int i = 0; i < elementDTOs.size(); i ++){
                BasicPolicyEditorElementDTO dto = elementDTOs.get(i);
                if(dto.getResourceValue() != null){
                    metaDataList.add("resourceValue" + i + "|" + dto.getResourceValue());
                } else {
                    metaDataList.add("resourceValue" + i);
                }
                if(dto.getEnvironmentValue() != null){
                    metaDataList.add("environmentValue" + i + "|" + dto.getEnvironmentValue());
                } else {
                    metaDataList.add("environmentValue" + i);
                }
                if(dto.getActionValue() != null){
                    metaDataList.add("actionValue" + i + "|" + dto.getActionValue());
                } else {
                    metaDataList.add("actionValue" + i);    
                }
                if(dto.getOperationType() != null){
                    metaDataList.add("operationValue" + i + "|" + dto.getOperationType());
                } else {
                    metaDataList.add("operationValue" + i);
                }
                if(dto.getUserAttributeId() != null){
                    metaDataList.add("userAttributeId" + i + "|" + dto.getUserAttributeId());
                } else {
                    metaDataList.add("userAttributeId" + i);
                }
                if(dto.getUserAttributeValue() != null){
                    metaDataList.add("userAttributeValue" + i + "|" + dto.getUserAttributeValue());
                } else {
                    metaDataList.add("userAttributeValue" + i);
                }
                if(dto.getEnvironmentId() != null){
                    metaDataList.add("environmentId" + i + "|" + dto.getEnvironmentId());
                } else {
                    metaDataList.add("environmentId" + i);
                }
                if(dto.getFunctionOnResources() != null){
                    metaDataList.add("functionOnResources" + i + "|" + dto.getFunctionOnResources());
                } else {
                    metaDataList.add("functionOnResources" + i);
                }
                if(dto.getFunctionOnActions() != null){
                    metaDataList.add("functionOnActions" + i + "|" + dto.getFunctionOnActions());
                } else {
                    metaDataList.add("functionOnActions" + i);
                }
                if(dto.getFunctionOnUsers() != null){
                    metaDataList.add("functionOnUsers" + i + "|" + dto.getFunctionOnUsers());
                } else {
                    metaDataList.add("functionOnUsers" + i);
                }
                if(dto.getFunctionOnEnvironments() != null){
                    metaDataList.add("functionOnEnvironments" + i + "|" + dto.getFunctionOnEnvironments());
                } else {
                    metaDataList.add("functionOnEnvironments" + i);
                }
                
                if(attributeMap.get(dto.getUserAttributeId()) != null){
                     dto.setUserAttributeId(attributeMap.get(dto.getUserAttributeId()));
                } else {
                    dto.setUserAttributeId(PolicyEditorConstants.SUBJECT_ID_DEFAULT);
                }
            }
        }
        return metaDataList.toArray(new String[metaDataList.size()]);
    }

    public static BasicPolicyEditorDTO createBasicPolicyEditorDTO(String[] policyEditorData){

        Map<String, String> metaDataMap = new HashMap<String, String>();
        List<BasicPolicyEditorElementDTO>  basicPolicyEditorElementDTOs = new ArrayList<BasicPolicyEditorElementDTO>();

        int i = 0;

        if(policyEditorData != null){
            for(String data : policyEditorData){
                if(data.contains("|")){
                    String identifier = data.substring(0, data.indexOf("|"));
                    String value = data.substring(data.indexOf("|") + 1);
                    metaDataMap.put(identifier, value);
                }
                i++;
            }
        }

        BasicPolicyEditorDTO policyEditorDTO = new BasicPolicyEditorDTO();
        policyEditorDTO.setPolicyId(metaDataMap.get("policyId"));
        policyEditorDTO.setAppliedCategory(metaDataMap.get("policyId"));
        policyEditorDTO.setFunction(metaDataMap.get("function"));
        policyEditorDTO.setActionValue(metaDataMap.get("actionValue"));
        policyEditorDTO.setDescription(metaDataMap.get("policyDescription"));
        policyEditorDTO.setUserAttributeId(metaDataMap.get("userAttributeId"));
        policyEditorDTO.setUserAttributeValue(metaDataMap.get("userAttributeValue"));
        policyEditorDTO.setResourceValue(metaDataMap.get("resourceValue"));
        policyEditorDTO.setEnvironmentValue(metaDataMap.get("environmentValue"));
        policyEditorDTO.setEnvironmentId(metaDataMap.get("environmentId"));
        policyEditorDTO.setAppliedCategory(metaDataMap.get("category"));

        i = (i-11)/11;

        for(int j = 0; j < i; j++){

            BasicPolicyEditorElementDTO elementDTO = new BasicPolicyEditorElementDTO();

            elementDTO.setResourceValue(metaDataMap.get("resourceValue" + j));
            elementDTO.setEnvironmentValue(metaDataMap.get("environmentValue" + j));
            if(metaDataMap.get("actionValue" + j) != null){
                elementDTO.setActionValue(metaDataMap.get("actionValue" + j));
            }
            elementDTO.setOperationType(metaDataMap.get("operationValue" + j));
            elementDTO.setUserAttributeId(metaDataMap.get("userAttributeId" + j));
            elementDTO.setUserAttributeValue(metaDataMap.get("userAttributeValue" + j));
            elementDTO.setEnvironmentId(metaDataMap.get("environmentId" + j));
            elementDTO.setFunctionOnResources(metaDataMap.get("functionOnResources" + j));
            elementDTO.setFunctionOnActions(metaDataMap.get("functionOnActions" + j));
            elementDTO.setFunctionOnUsers(metaDataMap.get("functionOnUsers" + j));
            elementDTO.setFunctionOnEnvironments(metaDataMap.get("functionOnEnvironments" + j));

            basicPolicyEditorElementDTOs.add(elementDTO);
        }

        if(basicPolicyEditorElementDTOs.size() > 0){
            policyEditorDTO.setBasicPolicyEditorElementDTOs(basicPolicyEditorElementDTOs);
        }

        return policyEditorDTO;
    }
}
