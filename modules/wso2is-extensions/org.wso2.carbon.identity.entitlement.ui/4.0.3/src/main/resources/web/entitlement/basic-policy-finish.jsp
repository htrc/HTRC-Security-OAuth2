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
<%@ page import="org.wso2.carbon.identity.entitlement.ui.PolicyEditorConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="org.wso2.carbon.identity.entitlement.ui.EntitlementPolicyCreator" %>
<%@ page
        import="org.wso2.carbon.identity.entitlement.ui.client.EntitlementPolicyAdminServiceClient" %>
<%@ page import="org.wso2.carbon.identity.entitlement.stub.dto.PolicyDTO" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.wso2.carbon.identity.entitlement.ui.EntitlementPolicyCreationException" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.wso2.carbon.identity.entitlement.ui.util.PolicyEditorUtil" %>
<jsp:useBean id="entitlementPolicyBean"
             type="org.wso2.carbon.identity.entitlement.ui.EntitlementPolicyBean"
             class="org.wso2.carbon.identity.entitlement.ui.EntitlementPolicyBean" scope="session"/>
<jsp:setProperty name="entitlementPolicyBean" property="*"/>

<%

    String serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
    ConfigurationContext configContext =
            (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.
                    CONFIGURATION_CONTEXT);
    String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
    String BUNDLE = "org.wso2.carbon.identity.entitlement.ui.i18n.Resources";
    ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, request.getLocale());

    int maxUserRow = 0;
    int maxResourceRow = 0;
    int maxActionRow = 0;
    int maxRows = 0;
    String dynamicCategory = CharacterEncoder.getSafeText(request.getParameter("policyApplied"));
    String policyId = CharacterEncoder.getSafeText(request.getParameter("policyId"));
    String policyDescription = CharacterEncoder.getSafeText(request.getParameter("policyDescription"));
    String maxUserRowString = CharacterEncoder.getSafeText(request.getParameter("maxUserRow"));
    String maxResourceRowString = CharacterEncoder.getSafeText(request.getParameter("maxResourceRow"));
    String maxActionRowString = CharacterEncoder.getSafeText(request.getParameter("maxActionRow"));
    if(maxResourceRowString != null){
        try{
            maxResourceRow = Integer.parseInt(maxResourceRowString);
        } catch(Exception e ){
            //ignore
        }
    }

    if(maxUserRowString != null){
        try{
            maxUserRow = Integer.parseInt(maxUserRowString);
        } catch(Exception e ){
            //ignore
        }
    }

    if(maxActionRowString != null){
        try{
            maxActionRow = Integer.parseInt(maxActionRowString);
        } catch(Exception e ){
            //ignore
        }
    }

    BasicPolicyEditorDTO editorDTO = null;
    String forwardTo = null;

    if(policyId != null && policyId.trim().length() > 0){

        editorDTO = new BasicPolicyEditorDTO();
        editorDTO.setAppliedCategory(dynamicCategory);
        editorDTO.setPolicyId(policyId);
        editorDTO.setDescription(policyDescription);

        List<BasicPolicyEditorElementDTO>  elementDTOs = new ArrayList<BasicPolicyEditorElementDTO>();

        if(PolicyEditorConstants.SOA_CATEGORY_RESOURCE.equals(dynamicCategory)){
            String resourceValue = CharacterEncoder.getSafeText(request.getParameter("resourceValue"));
            String function = CharacterEncoder.getSafeText(request.getParameter("function"));
            editorDTO.setResourceValue(resourceValue);
            editorDTO.setFunction(function);
            maxRows = maxResourceRow;
        } else if(PolicyEditorConstants.SOA_CATEGORY_USER.equals(dynamicCategory)){
            String userAttributeValue = CharacterEncoder.getSafeText(request.getParameter("userAttributeValue"));
            String userAttributeId = CharacterEncoder.getSafeText(request.getParameter("userAttributeId"));
            String function = CharacterEncoder.getSafeText(request.getParameter("function"));
            editorDTO.setUserAttributeId(userAttributeId);
            editorDTO.setUserAttributeValue(userAttributeValue);
            editorDTO.setFunction(function);
            maxRows = maxUserRow;
        } else if(PolicyEditorConstants.SOA_CATEGORY_ACTION.equals(dynamicCategory)){
            String actionValue = CharacterEncoder.getSafeText(request.getParameter("actionValue"));
            String function = CharacterEncoder.getSafeText(request.getParameter("function"));
            editorDTO.setActionValue(actionValue);
            editorDTO.setFunction(function);
            maxRows = maxActionRow;
        }

        for(int rowNumber = 0; rowNumber < maxRows + 1; rowNumber++){

            BasicPolicyEditorElementDTO elementDTO = new BasicPolicyEditorElementDTO();

            String userAttributeId = CharacterEncoder.getSafeText(request.
                    getParameter("userRuleAttributeId_" + rowNumber));
            if(userAttributeId != null && userAttributeId.trim().length() > 0){
                elementDTO.setUserAttributeId(userAttributeId);
            }

            String userAttributeValue = CharacterEncoder.getSafeText(request.
                    getParameter("userRuleAttributeValue_" + rowNumber));
            if(userAttributeValue != null && userAttributeValue.trim().length() > 0){
                elementDTO.setUserAttributeValue(userAttributeValue);
            } else {
                if(PolicyEditorConstants.SOA_CATEGORY_RESOURCE.equals(dynamicCategory)
                            || PolicyEditorConstants.SOA_CATEGORY_ACTION.equals(dynamicCategory)){
                    continue;    
                }
            }
            
            String actionValue = CharacterEncoder.getSafeText(request.
                    getParameter("actionRuleValue_" + rowNumber));
            if(actionValue != null && actionValue.trim().length() > 0){
                elementDTO.setActionValue(actionValue);
            }

            String resourceValue = CharacterEncoder.getSafeText(request.
                    getParameter("resourceRuleValue_" + rowNumber));
            if(resourceValue != null && resourceValue.trim().length() > 0){
                elementDTO.setResourceValue(resourceValue);
            } else {
                if(PolicyEditorConstants.SOA_CATEGORY_USER.equals(dynamicCategory)){
                    continue;
                }
            }

            String environmentId = CharacterEncoder.getSafeText(request.
                    getParameter("environmentRuleId_" + rowNumber));
            if(environmentId != null && environmentId.trim().length() > 0){
                elementDTO.setEnvironmentId(environmentId);
            }

            String environmentValue = CharacterEncoder.getSafeText(request.
                    getParameter("environmentRuleValue_" + rowNumber));
            if(environmentValue != null && environmentValue.trim().length() > 0){
                elementDTO.setEnvironmentValue(environmentValue);
            }

            String operationType = CharacterEncoder.getSafeText(request.
                    getParameter("operationRuleType_" + rowNumber));
            if(operationType != null && operationType.trim().length() > 0){
                elementDTO.setOperationType(operationType);
            }

            String resourceFunction = CharacterEncoder.getSafeText(request.
                    getParameter("resourceRuleFunction_" + rowNumber));
            if(resourceFunction != null && resourceFunction.trim().length() > 0){
                elementDTO.setFunctionOnResources(resourceFunction);
            }

            String userFunction = CharacterEncoder.getSafeText(request.
                    getParameter("userRuleFunction_" + rowNumber));
            if(userFunction != null && userFunction.trim().length() > 0){
                elementDTO.setFunctionOnUsers(userFunction);
            }

            String actionFunction = CharacterEncoder.getSafeText(request.
                    getParameter("actionRuleFunction_" + rowNumber));
            if(actionFunction != null && actionFunction.trim().length() > 0){
                elementDTO.setFunctionOnActions(actionFunction);
            }
            
            elementDTOs.add(elementDTO);
        }
        editorDTO.setBasicPolicyEditorElementDTOs(elementDTOs);
    }

    try {
        EntitlementPolicyCreator creator = new EntitlementPolicyCreator();
        PolicyDTO policyDTO = null;
        if(editorDTO != null){
            String[] policyData = PolicyEditorUtil.createBasicPolicyData(editorDTO,
                                                                            entitlementPolicyBean);
            String policy = creator.createBasicPolicy(editorDTO);
            if(policy != null){
                policyDTO = new PolicyDTO();
                policyDTO.setPolicyId(policyId);
                policyDTO.setPolicy(policy);
                policyDTO.setBasicPolicyEditorMetaData(policyData);
                policyDTO.setPolicyEditor(PolicyEditorConstants.SOA_POLICY_EDITOR);
            }
        }


        EntitlementPolicyAdminServiceClient client = new EntitlementPolicyAdminServiceClient(cookie,
                serverURL, configContext);

        if(entitlementPolicyBean.isEditPolicy()){
            client.updatePolicy(policyDTO);
        } else {
            client.addPolicy(policyDTO);
        }

        String message = resourceBundle.getString("ent.policy.added.successfully");
        CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.INFO, request);
        forwardTo = "index.jsp?";
    } catch (EntitlementPolicyCreationException e) {
        String message = resourceBundle.getString("error.while.creating.policy");
        CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.ERROR, request);
        forwardTo = "index.jsp?";
    } catch (Exception e) {
        String message = resourceBundle.getString("error.while.loading.policy");
        CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.ERROR, request);
        forwardTo = "index.jsp?";
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