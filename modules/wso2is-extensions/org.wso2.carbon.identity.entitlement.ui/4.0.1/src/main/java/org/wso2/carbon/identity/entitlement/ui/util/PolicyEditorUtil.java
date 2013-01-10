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
import org.wso2.carbon.identity.entitlement.ui.EntitlementPolicyBean;
import org.wso2.carbon.identity.entitlement.ui.EntitlementPolicyConstants;
import org.wso2.carbon.identity.entitlement.ui.PolicyEditorConstants;
import org.wso2.carbon.identity.entitlement.ui.PolicyEditorException;
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
            if("AdviceExpression".equals(type)){
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
            for(ArrayList<RowDTO> rowDTOArrayList : listSet){
                ApplyElementDTO orApplyDTO = new ApplyElementDTO();
                orApplyDTO.setFunctionId(processFunction("or"));

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
        } else if(listSet.size() == 1) {
            ArrayList<RowDTO> rowDTOArrayList = listSet.iterator().next();
            if(rowDTOArrayList.size() > 1){
                 ApplyElementDTO andApplyDTO = new ApplyElementDTO();
                 andApplyDTO.setFunctionId(processFunction("and"));
                for(RowDTO rowDTO : rowDTOArrayList){
                    ApplyElementDTO applyElementDTO = createApplyElement(rowDTO);
                    andApplyDTO.setApplyElement(applyElementDTO);
                }
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
        } else if(PolicyEditorConstants.FUNCTION_EQUAL.equals(rowDTO.getFunction())){
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
            if(PolicyEditorConstants.PRE_FUNCTION_ARE.equals(rowDTO.getPreFunction())){
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

        if(PolicyEditorConstants.FUNCTION_IS_IN.equals(function)){
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

        } else if(PolicyEditorConstants.FUNCTION_AT_LEAST_ONE.equals(function) ||
                PolicyEditorConstants.FUNCTION_SET_EQUALS.equals(function)){

            ApplyElementDTO applyElementDTO = new ApplyElementDTO();
            if(PolicyEditorConstants.FUNCTION_AT_LEAST_ONE.equals(function)){
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

        if(PolicyEditorConstants.FUNCTION_EQUAL.equals(function)){

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


        if(PolicyEditorConstants.FUNCTION_GREATER_EQUAL_AND_LESS_EQUAL.equals(function) ||
            PolicyEditorConstants.FUNCTION_GREATER_AND_LESS_EQUAL.equals(function) ||
            PolicyEditorConstants.FUNCTION_GREATER_EQUAL_AND_LESS.equals(function) ||
            PolicyEditorConstants.FUNCTION_GREATER_AND_LESS.equals(function)) {

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
            if(PolicyEditorConstants.FUNCTION_GREATER_AND_LESS.equals(function) ||
                    PolicyEditorConstants.FUNCTION_GREATER_AND_LESS_EQUAL.equals(function)){
                greaterThanApplyElement.setFunctionId(processFunction("greater-than", dataType));
            } else {
                greaterThanApplyElement.setFunctionId(processFunction("greater-than-or-equal", dataType));                     
            }


            ApplyElementDTO lessThanApplyElement = new ApplyElementDTO();
            if(PolicyEditorConstants.FUNCTION_GREATER_AND_LESS.equals(function) ||
                    PolicyEditorConstants.FUNCTION_GREATER_EQUAL_AND_LESS.equals(function)){
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
            
            if(PolicyEditorConstants.FUNCTION_GREATER.equals(function)){
                applyElementDTO.setFunctionId(processFunction("greater-than", dataType));
            } else if(PolicyEditorConstants.FUNCTION_GREATER_EQUAL.equals(function)){
                applyElementDTO.setFunctionId(processFunction("greater-than-or-equal", dataType));
            } else if(PolicyEditorConstants.FUNCTION_LESS.equals(function)){
                applyElementDTO.setFunctionId(processFunction("less-than", dataType));
            } else if(PolicyEditorConstants.FUNCTION_LESS_EQUAL.equals(function)){
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
                                        String ruleElementOrder, EntitlementPolicyBean policyBean){

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
                    createMetaDataFromRowDTO("ruleTarget", odlRowDTO, policyMetaDataList);
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
                    createMetaDataFromRowDTO("rule", odlRowDTO, policyMetaDataList);
                    newTargetRowDTOs.add(rowDTO);
                }

                ruleTargetDTO.setRowDTOList(newTargetRowDTOs);

                List<ExtendAttributeDTO> newDynamicAttributeDTOs = new ArrayList<ExtendAttributeDTO>();

                for(ExtendAttributeDTO attributeDTO : ruleDTO.getAttributeDTOs()){

                    String id = attributeDTO.getId();
                    String selector = attributeDTO.getSelector();
                    String category = null;
                    String function = null;

                    if(id == null){
                        continue;
                    }

                    if(PolicyEditorConstants.DYNAMIC_SELECTOR_FUNCTION.equals(selector)){
                        
                        String attributeValue = attributeDTO.getAttributeValue();
                        if(attributeValue == null || attributeValue.trim().length() < 1){
                            continue;
                        }
                        function = attributeDTO.getFunction();
                        if(function != null){
                            function = function.replace("&gt;", ">");
                            function = function.replace("&lt;", "<");

                            if(ruleFunctionMap.get(function) != null){// TODO
                                attributeDTO.setFunction(ruleFunctionMap.get(function));
                            }
                        }

                        if(attributeDTO.getDataType() == null ||
                                attributeDTO.getDataType().trim().length() < 1 ||
                                attributeDTO.getDataType().trim().equals("null")) {

                            if(category != null && defaultDataTypeMap.get(category) != null){
                                attributeDTO.setDataType((defaultDataTypeMap.
                                                    get(category).iterator().next()));
                            } else {
                                attributeDTO.setDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                            }
                        }
                        
                    } else {

                        category = attributeDTO.getCategory();

                        if(category == null || category.trim().length() < 1){
                            continue;
                        }

                        if(categoryMap.get(category) != null){
                            attributeDTO.setCategory(categoryMap.get(category));
                        }

                        if(attributeDTO.getDataType() == null ||
                                attributeDTO.getDataType().trim().length() < 1 ||
                                attributeDTO.getDataType().trim().equals("null")) {

                            if(defaultDataTypeMap.get(category) != null){
                                attributeDTO.setDataType((defaultDataTypeMap.
                                                    get(category).iterator().next()));
                            } else {
                                attributeDTO.setDataType(PolicyEditorConstants.STRING_DATA_TYPE);
                            }
                        }

                        if(attributeDTO.getAttributeId() == null ||
                                attributeDTO.getAttributeId().trim().length() < 1 ||
                                attributeDTO.getAttributeId().trim().equals("null")) {
                            if(defaultAttributeIdMap.get(category) != null){
                                attributeDTO.setAttributeId((defaultAttributeIdMap.
                                                    get(category).iterator().next()));
                            }
                        }
                    }


                    ExtendAttributeDTO odlRowDTO = new ExtendAttributeDTO(attributeDTO);
                    odlRowDTO.setCategory(category);
                    odlRowDTO.setFunction(function);
                    createMetaDataFromDynamicAttribute("rule", odlRowDTO, policyMetaDataList);
                    newDynamicAttributeDTOs.add(attributeDTO);
                }

                ruleDTO.setAttributeDTOs(newDynamicAttributeDTOs);
                ruleDTO.setTargetDTO(ruleTargetDTO);
            }
        }
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
}
