<!--
/*
* Copyright (c) 2008, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
-->
<%@ page import="org.wso2.carbon.ui.util.CharacterEncoder" %>
<%@ page import="org.wso2.carbon.identity.entitlement.ui.dto.*" %>
<jsp:useBean id="entitlementPolicyBean" type="org.wso2.carbon.identity.entitlement.ui.EntitlementPolicyBean"
             class="org.wso2.carbon.identity.entitlement.ui.EntitlementPolicyBean" scope="session"/>
<jsp:setProperty name="entitlementPolicyBean" property="*" />

<%
    int rowNumber = 0;
    int targetRowIndex = -1;
    int ruleRowIndex = -1;
    int targetRuleRowIndex = -1;
    int dynamicRowIndex = -1;
    int obligationRowIndex = -1;
    String categoryType = null;
    RuleDTO ruleDTO = new RuleDTO();
    BasicTargetDTO targetDTO = new BasicTargetDTO();
    entitlementPolicyBean.setRuleElementOrder(null);
    
    String targetRowIndexString = CharacterEncoder.getSafeText(request.getParameter("targetRowIndex"));
    String ruleRowIndexString = CharacterEncoder.getSafeText(request.getParameter("ruleRowIndex"));
    String targetRuleRowIndexString = CharacterEncoder.getSafeText(request.getParameter("targetRuleRowIndex"));
    String dynamicRowIndexString = CharacterEncoder.getSafeText(request.getParameter("dynamicRowIndex"));
    String obligationRowIndexString = CharacterEncoder.getSafeText(request.getParameter("obligationRowIndex"));
    if(targetRowIndexString != null && targetRowIndexString.trim().length() > 0){
        targetRowIndex = Integer.parseInt(targetRowIndexString);
    }
    if(ruleRowIndexString != null && ruleRowIndexString.trim().length() > 0){
        ruleRowIndex = Integer.parseInt(ruleRowIndexString);
    }
    if(targetRuleRowIndexString != null && targetRuleRowIndexString.trim().length() > 0){
        targetRuleRowIndex = Integer.parseInt(targetRuleRowIndexString);
    }
    if(dynamicRowIndexString != null && dynamicRowIndexString.trim().length() > 0){
        dynamicRowIndex = Integer.parseInt(dynamicRowIndexString);
    }
    if(obligationRowIndexString != null && obligationRowIndexString.trim().length() > 0){
        obligationRowIndex = Integer.parseInt(obligationRowIndexString);
    }

    String ruleElementOrder = request.getParameter("ruleElementOrder");
    String updateRule = request.getParameter("updateRule");
    String nextPage = request.getParameter("nextPage");
    String ruleId = request.getParameter("ruleId");
    String ruleEffect = request.getParameter("ruleEffect");
    String ruleDescription = request.getParameter("ruleDescription");
    String completedRule = request.getParameter("completedRule");
    String editRule = request.getParameter("editRule");

    while(true){
        RowDTO  rowDTO = new RowDTO();
        String targetCategory = CharacterEncoder.getSafeText(request.
                getParameter("targetCategory_" + rowNumber));
        if(targetCategory != null){
            rowDTO.setCategory(targetCategory);
        } else {
            break;
        }

        String targetPreFunction = CharacterEncoder.getSafeText(request.
                getParameter("targetPreFunction_" + rowNumber));
        if(targetPreFunction != null){
            rowDTO.setPreFunction(targetPreFunction);
        }

        String targetFunction = CharacterEncoder.getSafeText(request.
                getParameter("targetFunction_" + rowNumber));
        if(targetFunction != null){
            rowDTO.setFunction(targetFunction);
        }

        String targetAttributeValue = CharacterEncoder.getSafeText(request.
                getParameter("targetAttributeValue_" + rowNumber));
        if(targetAttributeValue != null && targetAttributeValue.trim().length() > 0){
            rowDTO.setAttributeValue(targetAttributeValue);
        }

        String targetAttributeId = CharacterEncoder.getSafeText(request.
                getParameter("targetAttributeId_" + rowNumber));
        if(targetAttributeId != null){
            rowDTO.setAttributeId(targetAttributeId);
        }

        String targetAttributeType = CharacterEncoder.getSafeText(request.
                getParameter("targetAttributeTypes_" + rowNumber));
        if(targetAttributeType != null){
            rowDTO.setAttributeDataType(targetAttributeType);
        }

        String targetCombineFunction = CharacterEncoder.getSafeText(request.
                getParameter("targetCombineFunctions_" + rowNumber));
        if(targetCombineFunction != null){
            rowDTO.setCombineFunction(targetCombineFunction);
        }

        if(targetRowIndex == rowNumber){
            categoryType = targetCategory;
            rowDTO.setNotCompleted(true);
        }

        targetDTO.addRowDTO(rowDTO);
        rowNumber ++;
    }

    // set target element to entitlement bean
    entitlementPolicyBean.setTargetDTO(targetDTO);

    if(ruleId != null && !ruleId.trim().equals("") && !ruleId.trim().equals("null") && editRule == null ) {

        ruleDTO.setRuleId(ruleId);
        ruleDTO.setRuleEffect(ruleEffect);
        if(ruleDescription != null && ruleDescription.trim().length() > 0 ){
            ruleDTO.setRuleDescription(ruleDescription);
        }
        if(completedRule != null && completedRule.equals("true")){
            ruleDTO.setCompletedRule(true);
        }

        BasicTargetDTO ruleTargetDTO = new BasicTargetDTO();
        rowNumber = 0;

        while(true){
            RowDTO  rowDTO = new RowDTO();
            String targetCategory = CharacterEncoder.getSafeText(request.
                    getParameter("ruleTargetCategory_" + rowNumber));
            if(targetCategory != null){
                rowDTO.setCategory(targetCategory);
            } else {
                break;
            }

            String targetPreFunction = CharacterEncoder.getSafeText(request.
                    getParameter("ruleTargetPreFunction_" + rowNumber));
            if(targetPreFunction != null){
                rowDTO.setPreFunction(targetPreFunction);
            }

            String targetFunction = CharacterEncoder.getSafeText(request.
                    getParameter("ruleTargetFunction_" + rowNumber));
            if(targetFunction != null){
                rowDTO.setFunction(targetFunction);
            }

            String targetAttributeValue = CharacterEncoder.getSafeText(request.
                    getParameter("ruleTargetAttributeValue_" + rowNumber));
            if(targetAttributeValue != null && targetAttributeValue.trim ().length() > 0){
                rowDTO.setAttributeValue(targetAttributeValue);
            }

            String targetAttributeId = CharacterEncoder.getSafeText(request.
                    getParameter("ruleTargetAttributeId_" + rowNumber));
            if(targetAttributeId != null){
                rowDTO.setAttributeId(targetAttributeId);
            }

            String targetAttributeType = CharacterEncoder.getSafeText(request.
                    getParameter("ruleTargetAttributeTypes_" + rowNumber));
            if(targetAttributeType != null){
                rowDTO.setAttributeDataType(targetAttributeType);
            }

            String targetCombineFunction = CharacterEncoder.getSafeText(request.
                    getParameter("ruleTargetCombineFunctions_" + rowNumber));
            if(targetCombineFunction != null){
                rowDTO.setCombineFunction(targetCombineFunction);
            }

            if(targetRuleRowIndex == rowNumber){
                categoryType = targetCategory;
                rowDTO.setNotCompleted(true);
            }

            ruleTargetDTO.addRowDTO(rowDTO);
            rowNumber ++;
        }

        // rule's target
        ruleDTO.setTargetDTO(ruleTargetDTO);

        rowNumber = 0;

        while(true){
            RowDTO rowDTO = new RowDTO();
            String ruleCategory = CharacterEncoder.getSafeText(request.
                    getParameter("ruleCategory_" + rowNumber));
            if(ruleCategory != null){
                rowDTO.setCategory(ruleCategory);
            } else {
                break;
            }

            String rulePreFunction = CharacterEncoder.getSafeText(request.
                    getParameter("rulePreFunction_" + rowNumber));
            if(rulePreFunction != null){
                rowDTO.setPreFunction(rulePreFunction);
            }

            String ruleFunction = CharacterEncoder.getSafeText(request.
                    getParameter("ruleFunction_" + rowNumber));
            if(ruleFunction != null){
                rowDTO.setFunction(ruleFunction);
            }

            String ruleAttributeValue = CharacterEncoder.getSafeText(request.
                    getParameter("ruleAttributeValue_" + rowNumber));
            if(ruleAttributeValue != null  && ruleAttributeValue.trim().length() > 0){
                rowDTO.setAttributeValue(ruleAttributeValue);
            }

            String ruleAttributeId = CharacterEncoder.getSafeText(request.
                    getParameter("ruleAttributeId_" + rowNumber));
            if(ruleAttributeId != null){
                rowDTO.setAttributeId(ruleAttributeId);
            }

            String ruleAttributeType = CharacterEncoder.getSafeText(request.
                    getParameter("ruleAttributeTypes_" + rowNumber));
            if(ruleAttributeType != null){
                rowDTO.setAttributeDataType(ruleAttributeType);
            }

            String ruleCombineFunction = CharacterEncoder.getSafeText(request.
                    getParameter("ruleCombineFunctions_" + rowNumber));
            if(ruleCombineFunction != null){
                rowDTO.setCombineFunction(ruleCombineFunction);
            }

            if(ruleRowIndex == rowNumber){
                categoryType = ruleCategory;
                rowDTO.setNotCompleted(true);
            }

            ruleDTO.addRowDTO(rowDTO);
            rowNumber ++;
        }

        rowNumber = 0;

        while(true){
            ObligationDTO dto = new ObligationDTO();
            String obligationType = CharacterEncoder.getSafeText(request.
                    getParameter("obligationRuleType_" + rowNumber));
            if(obligationType != null){
                dto.setType(obligationType);
            } else {
                break;
            }

            String obligationId = CharacterEncoder.getSafeText(request.
                    getParameter("obligationRuleId_" + rowNumber));
            if(obligationId != null){
                dto.setObligationId(obligationId);
            }

            String obligationAttributeValue = CharacterEncoder.getSafeText(request.
                    getParameter("obligationRuleAttributeValue_" + rowNumber));
            if(obligationAttributeValue != null){
                dto.setAttributeValue(obligationAttributeValue);
            }

            String obligationAttributeId = CharacterEncoder.getSafeText(request.
                    getParameter("obligationRuleAttributeId_" + rowNumber));
            if(obligationAttributeId != null){
                dto.setResultAttributeId(obligationAttributeId);
            }

            dto.setEffect(ruleEffect);

            if(obligationRowIndex == rowNumber){
                categoryType = null;          // TODO
                dto.setNotCompleted(true);
            }

            // Set rule's obligation
            ruleDTO.addObligationDTO(dto);
            rowNumber ++;
        }

        // Set rule
        entitlementPolicyBean.setRuleDTO(ruleDTO);
    }

    rowNumber = 0;

    while(true){

        ObligationDTO dto = new ObligationDTO();
        String obligationType = CharacterEncoder.getSafeText(request.
                getParameter("obligationType_" + rowNumber));
        if(obligationType != null){
            dto.setType(obligationType);
        } else {
            break;
        }

        String obligationId = CharacterEncoder.getSafeText(request.
                getParameter("obligationId_" + rowNumber));
        if(obligationId != null){
            dto.setObligationId(obligationId);
        }

        String obligationAttributeValue = CharacterEncoder.getSafeText(request.
                getParameter("obligationAttributeValue_" + rowNumber));
        if(obligationAttributeValue != null){
            dto.setAttributeValue(obligationAttributeValue);
        }

        String obligationAttributeId = CharacterEncoder.getSafeText(request.
                getParameter("obligationAttributeId_" + rowNumber));
        if(obligationAttributeId != null){
            dto.setResultAttributeId(obligationAttributeId);
        }

        dto.setEffect(ruleEffect);

        if(obligationRowIndex == rowNumber){
            categoryType = null;          // TODO
            dto.setNotCompleted(true);
        }

        // Set obligations
        entitlementPolicyBean.addObligationDTO(dto);
        rowNumber ++;
    }

    rowNumber = 0;
    while(true){

        ExtendAttributeDTO dto = new ExtendAttributeDTO();
        String dynamicId = CharacterEncoder.getSafeText(request.
                getParameter("dynamicId_" + rowNumber));
        if(dynamicId != null){
            dto.setId(dynamicId);
        } else {
            break;
        }

        String dynamicSelector = CharacterEncoder.getSafeText(request.
                getParameter("dynamicSelector_" + rowNumber));
        if(dynamicSelector != null){
            dto.setSelector(dynamicSelector);
        }

        String dynamicFunction = CharacterEncoder.getSafeText(request.
                getParameter("dynamicFunction_" + rowNumber));
        if(dynamicFunction != null){
            dto.setFunction(dynamicFunction);
        }

        String dynamicCategory = CharacterEncoder.getSafeText(request.
                getParameter("dynamicCategory_" + rowNumber));
        if(dynamicCategory != null){
            dto.setCategory(dynamicCategory);
        }

        String dynamicAttributeValue = CharacterEncoder.getSafeText(request.
                getParameter("dynamicAttributeValue_" + rowNumber));
        if(dynamicAttributeValue != null  && dynamicAttributeValue.trim().length() > 0){
            dto.setAttributeValue(dynamicAttributeValue);
        }

        String dynamicAttributeId = CharacterEncoder.getSafeText(request.
                getParameter("dynamicAttributeId_" + rowNumber));
        if(dynamicAttributeId != null){
            dto.setAttributeId(dynamicAttributeId);
        }

        String dynamicAttributeTypes = CharacterEncoder.getSafeText(request.
                getParameter("dynamicAttributeTypes_0" + rowNumber));
        if(dynamicAttributeTypes != null){
            dto.setDataType(dynamicAttributeTypes);
        }

        if(dynamicRowIndex == rowNumber){
            categoryType = null;          // TODO
            dto.setNotCompleted(true);
        }

        // Set extend attributes
        entitlementPolicyBean.addExtendAttributeDTO(dto);
        rowNumber ++;
    }

    String forwardTo;

    if(ruleElementOrder != null && ruleElementOrder.trim().length() > 0){
        if(ruleDTO.isCompletedRule() && !"true".equals(updateRule)){
            entitlementPolicyBean.setRuleElementOrder(ruleElementOrder.trim() + ", " +
                                                      ruleDTO.getRuleId());
        } else{
            entitlementPolicyBean.setRuleElementOrder(ruleElementOrder.trim());
        }
    }

    if(completedRule != null && completedRule.equals("true")){
        forwardTo = nextPage + ".jsp?";
    } else {
        forwardTo = nextPage + ".jsp?ruleId=" + ruleId;
        if(categoryType != null && categoryType.trim().length() > 0){
            forwardTo = forwardTo + "&attributeType=" + categoryType +"&";
        }
    }

%>

<script type="text/javascript">
    function forward() {
        location.href = "<%=forwardTo%>";
    }
</script>

<script type="text/javascript">
    forward();
</script>