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

<%@ page import="org.wso2.carbon.identity.entitlement.ui.dto.BasicPolicyEditorDTO" %>
<%@ page import="org.wso2.carbon.identity.entitlement.ui.PolicyEditorConstants" %>
<%@ page import="org.wso2.carbon.identity.entitlement.ui.dto.BasicPolicyEditorElementDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar" prefix="carbon" %>
<jsp:useBean id="entitlementPolicyBean"
             type="org.wso2.carbon.identity.entitlement.ui.EntitlementPolicyBean"
             class="org.wso2.carbon.identity.entitlement.ui.EntitlementPolicyBean" scope="session"/>
<jsp:setProperty name="entitlementPolicyBean" property="*"/>

<%
    BasicPolicyEditorDTO policyEditorDTO = entitlementPolicyBean.getBasicPolicyEditorDTO();
    List<BasicPolicyEditorElementDTO> elementDTOList = null;
    String selectedPolicyApplied = request.getParameter("policyApplied");
    String policyId = request.getParameter("policyId");
    String policyDescription = request.getParameter("policyDescription");

    String[] policyApplies  = new String[]{PolicyEditorConstants.SOA_CATEGORY_RESOURCE ,
            PolicyEditorConstants.SOA_CATEGORY_USER, PolicyEditorConstants.SOA_CATEGORY_ACTION};
    
    Set<String> userAttributeIds = entitlementPolicyBean.getAttributeIdMap().keySet();
    // adding entry if attrbutes ids are null or empty. we want to show something in UI
    if(userAttributeIds != null){
        userAttributeIds = new HashSet<String>(userAttributeIds);
        userAttributeIds.add("User Name");
    } else {
        userAttributeIds = new HashSet<String>();
        userAttributeIds.add("User Name");
    }
    //String[] environmentIds = new String[]{"between", "from ip-address", "on date"};
    String[] operationTypes = new String[]{PolicyEditorConstants.PreFunctions.CAN_DO,
                                                PolicyEditorConstants.PreFunctions.CAN_NOT_DO};
    String[] functions = new String[]{PolicyEditorConstants.BasicEditorFunctions.EQUAL , 
                                        PolicyEditorConstants.BasicEditorFunctions.REGEXP_EQUAL};



    String selectedRuleUserAttributeId = null;
    String selectedRuleUserAttributeValue = null;
    String selectedRuleActionValue = null;
    String selectedRuleResourceValue = null;
    String selectedRuleEnvironmentValue= null;
    String selectedRuleEnvironmentId= null;
    String selectedRuleOperationType= null;
    String selectedRuleResourceFunction = null;
    String selectedRuleUserFunction = null;
    String selectedRuleActionFunction = null;



    String selectedUserAttributeId = null;
    String selectedUserAttributeValue = null;
    String selectedActionValue = null;
    String selectedResourceValue = null;
    String selectedEnvironmentValue= null;
    String selectedEnvironmentId= null;

    String selectedFunction = null;

    if(policyEditorDTO != null){
        policyId = policyEditorDTO.getPolicyId();
        policyDescription = policyEditorDTO.getDescription();
        selectedFunction = policyEditorDTO.getFunction();
        selectedUserAttributeId = policyEditorDTO.getUserAttributeId();
        selectedUserAttributeValue = policyEditorDTO.getUserAttributeValue();
        selectedActionValue= policyEditorDTO.getActionValue();
        selectedEnvironmentValue= policyEditorDTO.getEnvironmentValue();
        selectedResourceValue = policyEditorDTO.getResourceValue();
        if(selectedPolicyApplied == null || selectedPolicyApplied.trim().length() == 0){
            selectedPolicyApplied = policyEditorDTO.getAppliedCategory();
        }

        elementDTOList = policyEditorDTO.getBasicPolicyEditorElementDTOs();

        if(elementDTOList != null && elementDTOList.size() > 0){
            BasicPolicyEditorElementDTO elementDTO = elementDTOList.get(0);
            if(elementDTO != null){
                selectedRuleActionValue = elementDTO.getActionValue();
                selectedRuleUserAttributeId = elementDTO.getUserAttributeId();
                selectedRuleUserAttributeValue = elementDTO.getUserAttributeValue();
                selectedRuleResourceValue = elementDTO.getResourceValue();
                selectedRuleEnvironmentValue= elementDTO.getEnvironmentValue();
                selectedRuleEnvironmentId= elementDTO.getEnvironmentId();
                selectedRuleOperationType= elementDTO.getOperationType();
                selectedRuleResourceFunction = elementDTO.getFunctionOnResources();
                selectedRuleUserFunction = elementDTO.getFunctionOnUsers();
            }
        }

    }

%>


<fmt:bundle basename="org.wso2.carbon.identity.entitlement.ui.i18n.Resources">
<carbon:breadcrumb
        label="create.basic.policy"
        resourceBundle="org.wso2.carbon.identity.entitlement.ui.i18n.Resources"
        topPage="false"
        request="<%=request%>"/>
<script type="text/javascript" src="../carbon/admin/js/breadcrumbs.js"></script>
<script type="text/javascript" src="../carbon/admin/js/cookies.js"></script>
<script type="text/javascript" src="resources/js/main.js"></script>
<!--Yahoo includes for dom event handling-->
<script src="../yui/build/yahoo-dom-event/yahoo-dom-event.js" type="text/javascript"></script>
<script src="../entitlement/js/policy-editor.js" type="text/javascript"></script>
<link href="../entitlement/css/entitlement.css" rel="stylesheet" type="text/css" media="all"/>




<script type="text/javascript">
    jQuery(document).ready(function(){
        jQuery('#policyTypeTitle').html("Select a <strong>"+jQuery('#policyApplied').val() + "</strong> to apply policy");
        jQuery('#policyTypeTitleSub').html("Add rules to <strong>"+jQuery('#policyApplied').val()+"</strong>");
    });

    function doValidationPolicyNameOnly() {

        var value = document.getElementsByName("policyId")[0].value;
        if (value == '') {
            CARBON.showWarningDialog('<fmt:message key="policy.name.is.required"/>');
            return false;
        }

        return true;
    }

    function doSubmit(){
        if(doValidationPolicyNameOnly()){
            preSubmit();
            document.dataForm.action = "basic-policy-finish.jsp";
            document.dataForm.submit();
        }
    }

    function preSubmit(){

        var userRuleTable = "";
        var actionRuleTable = "";
        var resourceRuleTable = "";

        if(document.getElementById('userRuleTable') != null){
            userRuleTable =  jQuery(document.getElementById('userRuleTable').rows[document.
                        getElementById('userRuleTable').rows.length-1]).attr('data-value');
        }

        if(document.getElementById('resourceRuleTable') != null){
            resourceRuleTable = jQuery(document.getElementById('resourceRuleTable').rows[document.
                    getElementById('resourceRuleTable').rows.length-1]).attr('data-value');
        }

        if(document.getElementById('actionRuleTable') != null){
            actionRuleTable = jQuery(document.getElementById('actionRuleTable').rows[document.
                    getElementById('actionRuleTable').rows.length-1]).attr('data-value');
        }

        jQuery('#mainTable > tbody:last').append('<tr><td><input type="hidden" name="maxUserRow" id="maxUserRow" value="' + userRuleTable +'"/></td></tr>') ;
        jQuery('#mainTable > tbody:last').append('<tr><td><input type="hidden" name="maxResourceRow" id="maxResourceRow" value="' + resourceRuleTable +'"/></td></tr>') ;
        jQuery('#mainTable > tbody:last').append('<tr><td><input type="hidden" name="maxActionRow" id="maxActionRow" value="' + actionRuleTable +'"/></td></tr>') ;
    }

    function doCancel(){
        location.href = "index.jsp";
    }

    function removeRow(link){
        link.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.
                removeChild(link.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode);
    }
    
    function createNewResourceRuleRow() {
        var rowIndex =  jQuery(document.getElementById('resourceRuleTable').rows[document.
                            getElementById('resourceRuleTable').rows.length-1]).attr('data-value');
        var index = parseInt(rowIndex, 10) + 1;
        jQuery('#resourceRuleTable > tbody:last').append('<tr data-value="'+ index +'"><td><table class="oneline-listing"><tr><td style="white-space:nowrap;">Resource  which is</td><td></td><td></td><td></td><td>User whose</td><td></td><td></td><td></td><td></td><td>and perform</td></tr>' +
            '<tr><td><select id="resourceRuleFunction_'  + index + '" name="resourceRuleFunction_'  + index + '" ><%for (String function : functions) {if (selectedRuleResourceFunction != null && selectedRuleResourceFunction.equals(function)) {%><option value="<%=function%>"  selected="selected"><%=function%></option><%} else {%><option value="<%=function%>"><%=function%></option><%}}%></select></td>' +
            '<td>to</td>' +
            '<td><%if (selectedRuleResourceValue != null && selectedRuleResourceValue.trim().length() > 0) {%><input type="text" name="resourceRuleValue_'  + index + '"  id="resourceRuleValue_'  + index + '" value="<%=selectedRuleResourceValue%>"/><%} else {%><input type="text" name="resourceRuleValue_'  + index + '"  id="resourceRuleValue_'  + index + '"  /><% }%> </td>' +
            '<td><select id="operationRuleType_'  + index + '"  name="operationRuleType_'  + index + '" ><%for (String type  : operationTypes) {if (selectedRuleOperationType != null && type.equals(selectedRuleOperationType)) {%><option value="<%=type%>"  selected="selected"><%=type%></option><%} else {%><option value="<%=type%>"><%=type%></option><%}}%></select></td>' +
            '<td><select id="userRuleAttributeId_'  + index + '"  name="userRuleAttributeId_'  + index + '"  ><%for (String userAttribute : userAttributeIds) {if (selectedRuleUserAttributeId != null && userAttribute.equals(selectedRuleUserAttributeId)) {%><option value="<%=userAttribute%>" selected="selected"><%=userAttribute%></option><%} else {%><option value="<%=userAttribute%>"><%=userAttribute%></option><%}}%></select></td>' +
            '<td>is</td>' +
            '<td> <select id="userRuleFunction_'  + index + '"  name="userRuleFunction_'  + index + '"  ><%for (String function : functions) {if (selectedRuleUserFunction != null && selectedRuleUserFunction.equals(function)) {%><option value="<%=function%>"  selected="selected"><%=function%></option><%} else {%><option value="<%=function%>"><%=function%></option><%}}%></select></td>' +
            '<td>to</td>' +
            '<td><%if (selectedRuleUserAttributeValue != null && selectedRuleUserAttributeValue.trim().length() > 0) {%><input type="text" name="userRuleAttributeValue_'  + index + '"  id="userRuleAttributeValue_'  + index + '"  value="<%=selectedRuleUserAttributeValue%>"/><%} else {%><input type="text" name="userRuleAttributeValue_'  + index + '"  id="userRuleAttributeValue_'  + index + '"  /><%}%></td>' +
            '<td><%if (selectedRuleActionValue != null && selectedRuleActionValue.trim().length() > 0) {%><input type="text" name="actionRuleValue_'  + index + '"  id="actionRuleValue_'  + index + '"  value="<%=selectedRuleActionValue%>"/><%} else {%><input type="text" name="actionRuleValue_'  + index + '"  id="actionRuleValue_'  + index + '"  /><%}%></td>' +
            '<td><a onclick="removeRow(this)" style="background-image:url(images/delete.gif);" type="button" class="icon-link"></a></td>' +
            '</tr><tr><td></td><td></td><td></td><td>be accessed by</td><td></td><td></td><td></td><td></td><td></td><td>action</td></tr></table></td></tr>');
    }
    

    function createNewUserRuleRow() {
        var rowIndex =  jQuery(document.getElementById('userRuleTable').rows[document.
                            getElementById('userRuleTable').rows.length-1]).attr('data-value');
        var index = parseInt(rowIndex, 10) + 1;
        jQuery('#userRuleTable > tbody:last').append('<tr data-value="'+ index +'"><td><table class="oneline-listing"><tr></tr><tr><td> User </td>' +
            '<td><select id="operationRuleType_'  + index + '" name="operationRuleType_'  + index + '" ><%for (String type  : operationTypes) {if (selectedRuleOperationType != null && type.equals(selectedRuleOperationType)) {%><option value="<%=type%>"  selected="selected"><%=type%></option><%} else {%><option value="<%=type%>"><%=type%></option><%}} %></select></td>' +
            '<td> perform </td>' +
            '<td><%if (selectedRuleActionValue != null && selectedRuleActionValue.trim().length() > 0) {%><input type="text" name="actionRuleValue_'  + index + '" id="actionRuleValue_'  + index + '" value="<%=selectedRuleActionValue%>"/><%} else {%><input type="text" name="actionRuleValue_'  + index + '" id="actionRuleValue_'  + index + '" /><%}%></td>' +
            '<td> action on resource which is </td>' +
            '<td><select id="resourceRuleFunction_'  + index + '" name="resourceRuleFunction_'  + index + '" ><%for (String function : functions) {if (selectedRuleResourceFunction != null && selectedRuleResourceFunction.equals(function)) {%><option value="<%=function%>"  selected="selected"><%=function%></option><%} else {%><option value="<%=function%>"><%=function%></option><%}}%></select></td>' +
            '<td> to </td>' +
            '<td><%if (selectedRuleResourceValue != null && selectedRuleResourceValue.trim().length() > 0) {%><input type="text" name="resourceRuleValue_'  + index + '" id="resourceRuleValue_'  + index + '" value="<%=selectedRuleResourceValue%>"/><%} else {%><input type="text" name="resourceRuleValue_'  + index + '" id="resourceRuleValue_'  + index + '" /><%}%></td>' +
            '<td><a onclick="removeRow(this)" style="background-image:url(images/delete.gif);" type="button" class="icon-link"></a></td>' +
            '</tr><tr></tr></table></td></tr>');
    }

    function createNewActionRuleRow() {
        var rowIndex =  jQuery(document.getElementById('actionRuleTable').rows[document.
                            getElementById('actionRuleTable').rows.length-1]).attr('data-value');
        var index = parseInt(rowIndex, 10) + 1;
        jQuery('#actionRuleTable > tbody:last').append('<tr data-value="'+ index +'"><td><table class="oneline-listing"><tr></tr><tr><td>This action</td>' +
                '<td><select id="operationRuleType_'  + index + '" name="operationRuleType_'  + index + '"  ><%for (String type  : operationTypes) {if (selectedRuleOperationType != null && type.equals(selectedRuleOperationType)) { %><option value="<%=type%>"  selected="selected"><%=type%></option><%} else {%><option value="<%=type%>"><%=type%></option><%}}%></select></td>' +
                '<td> be performed on resource which is </td>' +
                '<td><select id="resourceRuleFunction_'  + index + '" name="resourceRuleFunction_'  + index + '"><%for (String function : functions) {if (selectedRuleResourceFunction != null && selectedRuleResourceFunction.equals(function)) { %><option value="<%=function%>"  selected="selected"><%=function%></option><%} else {%><option value="<%=function%>"><%=function%></option><%}}%></select></td><td>to</td><td><%if (selectedRuleResourceValue != null && selectedRuleResourceValue.trim().length() > 0) {%><input type="text" name="resourceRuleValue_'  + index + '" id="resourceRuleValue_'  + index + '" value="<%=selectedRuleResourceValue%>"/><%} else {%><input type="text" name="resourceRuleValue_'  + index + '" id="resourceRuleValue_'  + index + '" /><%}%></td>' +
                '<td>by user whose</td>' +
                '<td><select id="userRuleAttributeId_'  + index + '" name="userRuleAttributeId_'  + index + '" ><%for (String userAttribute : userAttributeIds) {if (selectedRuleUserAttributeId != null && userAttribute.equals(selectedRuleUserAttributeId)) {%><option value="<%=userAttribute%>"  selected="selected"><%=userAttribute%></option><%} else {%><option value="<%=userAttribute%>"><%=userAttribute%></option><%}}%></select></td>' +
                '<td> is </td>' +
                '<td><select id="userRuleFunction_'  + index + '" name="userRuleFunction_'  + index + '"  class="leftCol-small"><%for (String function : functions) {if (selectedRuleUserFunction != null && selectedRuleUserFunction.equals(function)) {%><option value="<%=function%>"  selected="selected"><%=function%></option><%} else {%><option value="<%=function%>"><%=function%></option><%}}%></select></td>' +
                '<td> to </td>' +
                '<td><%if (selectedRuleUserAttributeValue != null && selectedRuleUserAttributeValue.trim().length() > 0) {%><input type="text" name="userRuleAttributeValue_'  + index + '" id="userRuleAttributeValue_'  + index + '" value="<%=selectedRuleUserAttributeValue%>"/><% } else {%><input type="text" name="userRuleAttributeValue_'  + index + '" id="userRuleAttributeValue_'  + index + '" /><%}%></td>' +
                '<td><a onclick="removeRow(this)" style="background-image:url(images/delete.gif);" type="button" class="icon-link"></a></td>' +
                '</tr><tr></tr></table></td></tr>');
    }

    function getCategoryType() {
        document.dataForm.submit();
    }


</script>

<div id="middle">
<h2><fmt:message key="create.entitlement.policy"/></h2>
<div id="workArea">
<div class="goToAdvance">
    <a class='icon-link' href="../entitlement/policy-editor.jsp"
       style='background-image:url(images/advanceview.png);float:none'><fmt:message
            key="use.advance.view"/></a>
</div>
<div class="goToAdvance">
    <a class='icon-link' href="../entitlement/add-policy.jsp"
       style='background-image:url(images/advanceview.png);float:none'><fmt:message
            key="use.xml.view"/></a>
</div>
    <form id="dataForm" name="dataForm" method="post" action="">
        <div class="sectionSeperator">Basic Information</div>
        <div class="sectionSub">
            <table id="mainTable">
                <tr>
                    <td class="leftCol-med"><fmt:message key="policy.name"/><span class="required">*</span></td>
                    <%
                        if (policyId != null && policyId.trim().length() > 0) {
                    %>
                        <td><input type="text" name="policyId" id="policyId" value="<%=policyId%>"/></td>
                    <%
                        } else {
                    %>
                        <td><input type="text" name="policyId" id="policyId" /></td>
                    <%
                        }
                    %>
                </tr>
                <tr>
                    <td><fmt:message key="policy.description"/></td>
                    <%
                        if (policyDescription != null && policyDescription.trim().length() > 0) {
                    %>
                        <td><input type="text" name="policyDescription" id="policyDescription" value="<%=policyDescription%>"/></td>
                    <%
                        } else {
                    %>
                        <td><input type="text" name="policyDescription" id="policyDescription" /></td>
                    <%
                        }
                    %>
                </tr>
                <tr>
                    <td class="leftCol-small">
                        This policy is based on
                    </td>
                    <td>
                        <select id="policyApplied" name="policyApplied" onchange="getCategoryType();">
                            <%
                                for (String  policyApply : policyApplies) {
                                    if(selectedPolicyApplied != null && policyApply.equals(selectedPolicyApplied)){
                            %>
                                <option value="<%=policyApply%>" selected="selected" ><%=policyApply%></option>
                            <%
                                    } else {
                            %>
                                <option value="<%=policyApply%>" ><%=policyApply%></option>
                            <%
                                    }
                                }
                            %>
                        </select>




                         <div class="sectionHelp">
                            Depending on the selection, you will be given different options to defince rules.
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <%--END Basic information section --%>



                <%--**********************--%>
                <%--**********************--%>
                <%--START user policy type--%>
                <%--**********************--%>
                <%--**********************--%>

                <%
                    if(PolicyEditorConstants.SOA_CATEGORY_USER.equals(selectedPolicyApplied)) {
                %>
                <div class="sectionSeperator" id="policyTypeTitle"></div>
                <div class="sectionSub sectionSubShifter">
                    <table class="oneline-listing">
                        <tr>
                            <td>User Whose</td>
                            <td>
                                <select id="userAttributeId" name="userAttributeId" >
                                    <%
                                        for (String userAttribute : userAttributeIds) {
                                            if (selectedUserAttributeId != null && userAttribute.equals(selectedUserAttributeId)) {
                                    %>
                                        <option value="<%=userAttribute%>"  selected="selected"><%=userAttribute%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=userAttribute%>"><%=userAttribute%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td> is </td>
                            <td>
                                <select id="function" name="function"  class="leftCol-small">
                                    <%
                                        for (String function : functions) {
                                            if (selectedFunction != null && selectedFunction.equals(function)) {
                                    %>
                                        <option value="<%=function%>"  selected="selected"><%=function%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=function%>"><%=function%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td> to </td>
                            <td>
                                <%
                                    if (selectedUserAttributeValue != null && selectedUserAttributeValue.trim().length() > 0) {
                                %>
                                    <input type="text" name="userAttributeValue" id="userAttributeValue" value="<%=selectedUserAttributeValue%>"/>
                                <%
                                    } else {
                                %>
                                    <input type="text" name="userAttributeValue" id="userAttributeValue" />
                                <%
                                    }
                                %>
                            </td>
                        </tr>
                    </table>
                        <div class="sectionSeperator" id="policyTypeTitleSub"></div>
                        <div class="sectionSub sectionSubShifter">
                                <table  id="userRuleTable" >
                                    <tr data-value="0">
                                    <td>
                                    <table class="oneline-listing">
                                    <tr></tr>
                                    <tr>
                                        <td> User </td>
                                        <td>
                                            <select id="operationRuleType_0" name="operationRuleType_0"  >
                                                <%
                                                    for (String type  : operationTypes) {
                                                        if (selectedRuleOperationType != null && type.equals(selectedRuleOperationType)) {
                                                %>
                                                    <option value="<%=type%>"  selected="selected"><%=type%></option>
                                                <%
                                                        } else {
                                                %>
                                                    <option value="<%=type%>"><%=type%></option>
                                                <%
                                                        }
                                                    }
                                                %>
                                            </select>
                                        </td>
                                        <td> perform </td>
                                        <td>
                                        <%
                                            if (selectedRuleActionValue != null && selectedRuleActionValue.trim().length() > 0) {
                                        %>
                                            <input type="text" name="actionRuleValue_0" id="actionRuleValue_0" value="<%=selectedRuleActionValue%>"/>
                                        <%
                                            } else {
                                        %>
                                            <input type="text" name="actionRuleValue_0" id="actionRuleValue_0" />
                                        <%
                                            }
                                        %>
                                        </td>
                                        <td> action on resource which is </td>
                                        <td><select id="resourceRuleFunction_0" name="resourceRuleFunction_0">
                                                <%
                                                    for (String function : functions) {
                                                        if (selectedRuleResourceFunction != null && selectedRuleResourceFunction.equals(function)) {
                                                %>
                                                    <option value="<%=function%>"  selected="selected"><%=function%></option>
                                                <%
                                                        } else {
                                                %>
                                                    <option value="<%=function%>"><%=function%></option>
                                                <%
                                                        }
                                                    }
                                                %>
                                            </select></td>
                                        <td>to</td>
                                        <td>
                                        <%
                                            if (selectedRuleResourceValue != null && selectedRuleResourceValue.trim().length() > 0) {
                                        %>
                                            <input type="text" name="resourceRuleValue_0" id="resourceRuleValue_0" value="<%=selectedRuleResourceValue%>"/>
                                        <%
                                            } else {
                                        %>
                                            <input type="text" name="resourceRuleValue_0" id="resourceRuleValue_0" />
                                        <%
                                            }
                                        %>
                                        </td>

                                        <td>
                                            <a onclick="createNewUserRuleRow();" style="background-image:url(images/add.gif);float:none" type="button"
                                               class="icon-link"></a>
                                        </td>
                                    </tr>
                                    <tr></tr>
                                    </table>
                                    </td>
                                    </tr>
                    <%
                        if(elementDTOList != null && elementDTOList.size() > 0){
                            elementDTOList.remove(0);
                            for(BasicPolicyEditorElementDTO elementDTO : elementDTOList){
                                selectedRuleActionValue = elementDTO.getActionValue();
                                selectedRuleUserAttributeId = elementDTO.getUserAttributeId();
                                selectedRuleUserAttributeValue = elementDTO.getUserAttributeValue();
                                selectedRuleResourceValue = elementDTO.getResourceValue();
                                selectedRuleEnvironmentValue= elementDTO.getEnvironmentValue();
                                selectedRuleEnvironmentId= elementDTO.getEnvironmentId();
                                selectedRuleOperationType= elementDTO.getOperationType();
                                selectedRuleResourceFunction = elementDTO.getFunctionOnResources();
                                selectedRuleUserFunction = elementDTO.getFunctionOnUsers();
                    %>
                            <script type="text/javascript">
                                function createUserRuleRow() {
                                    var rowIndex =  jQuery(document.getElementById('userRuleTable').rows[document.
                                                getElementById('userRuleTable').rows.length-1]).attr('data-value');
                                    var index = parseInt(rowIndex, 10) + 1;
                                    jQuery('#userRuleTable > tbody:last').append('<tr data-value="'+ index +'"><td><table class="oneline-listing"><tr></tr><tr><td> User </td>' +
                                            '<td><select id="operationRuleType_'  + index + '" name="operationRuleType_'  + index + '" ><%for (String type  : operationTypes) {if (selectedRuleOperationType != null && type.equals(selectedRuleOperationType)) {%><option value="<%=type%>"  selected="selected"><%=type%></option><%} else {%><option value="<%=type%>"><%=type%></option><%}} %></select></td>' +
                                            '<td> perform </td>' +
                                            '<td><%if (selectedRuleActionValue != null && selectedRuleActionValue.trim().length() > 0) {%><input type="text" name="actionRuleValue_'  + index + '" id="actionRuleValue_'  + index + '" value="<%=selectedRuleActionValue%>"/><%} else {%><input type="text" name="actionRuleValue_'  + index + '" id="actionRuleValue_'  + index + '" /><%}%></td>' +
                                            '<td> action on resource which is </td>' +
                                            '<td><select id="resourceRuleFunction_'  + index + '" name="resourceRuleFunction_'  + index + '" ><%for (String function : functions) {if (selectedRuleResourceFunction != null && selectedRuleResourceFunction.equals(function)) {%><option value="<%=function%>"  selected="selected"><%=function%></option><%} else {%><option value="<%=function%>"><%=function%></option><%}}%></select></td>' +
                                            '<td> to </td>' +
                                            '<td><%if (selectedRuleResourceValue != null && selectedRuleResourceValue.trim().length() > 0) {%><input type="text" name="resourceRuleValue_'  + index + '" id="resourceRuleValue_'  + index + '" value="<%=selectedRuleResourceValue%>"/><%} else {%><input type="text" name="resourceRuleValue_'  + index + '" id="resourceRuleValue_'  + index + '" /><%}%></td>' +
                                            '<td><a onclick="removeRow(this)" style="background-image:url(images/delete.gif);" type="button" class="icon-link"></a></td>' +
                                            '</tr><tr></tr></table></td></tr>');
                                }
                                createUserRuleRow();
                            </script>
                                <%
                                    }
                                }
                                %>
                    </table>
                    </div>






        </div>
        <%--********************--%>
        <%--********************--%>
        <%--END user policy type--%>
        <%--********************--%>
        <%--********************--%>
        <%--********************--%>


        <%--************************--%>
        <%--************************--%>
        <%--START action policy type--%>
        <%--************************--%>
        <%--************************--%>
        <%--************************--%>

        <%
            } else if(PolicyEditorConstants.SOA_CATEGORY_ACTION.equals(selectedPolicyApplied)){
        %>



        <div class="sectionSub sectionSubShifter">
                    <div class="sectionSeperator" id="policyTypeTitle"></div>
                    <table class="oneline-listing-alt">
                        <tr>
                            <td>Action which is</td>
                            <td>
                                <select id="function" name="function"  class="leftCol-small">
                                    <%
                                        for (String function : functions) {
                                            if (selectedFunction != null && selectedFunction.equals(function)) {
                                    %>
                                        <option value="<%=function%>"  selected="selected"><%=function%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=function%>"><%=function%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td> to </td>
                            <%
                                if (selectedActionValue != null && selectedActionValue.trim().length() > 0) {
                            %>
                                <td><input type="text" name="actionValue" id="actionValue" value="<%=selectedActionValue%>"/></td>
                            <%
                                } else {
                            %>
                                <td><input type="text" name="actionValue" id="actionValue" /></td>
                            <%
                                }
                            %>
                        </tr>
                    </table>


                    <div class="sectionSeperator" id="policyTypeTitleSub"></div>
                    <div class="sectionSub sectionSubShifter">
                    <table  id="actionRuleTable" >
                        <tr data-value="0">
                        <td>
                        <table class="oneline-listing">
                        <tr></tr>
                        <tr>
                            <td>This action</td>
                            <td>
                                <select id="operationRuleType_0" name="operationRuleType_0"  >
                                    <%
                                        for (String type  : operationTypes) {
                                            if (selectedRuleOperationType != null && type.equals(selectedRuleOperationType)) {
                                    %>
                                        <option value="<%=type%>"  selected="selected"><%=type%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=type%>"><%=type%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td> be performed on resource which is </td>
                            <td><select id="resourceRuleFunction_0" name="resourceRuleFunction_0">
                                    <%
                                        for (String function : functions) {
                                            if (selectedRuleResourceFunction != null && selectedRuleResourceFunction.equals(function)) {
                                    %>
                                        <option value="<%=function%>"  selected="selected"><%=function%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=function%>"><%=function%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select></td>
                            <td>to</td>
                            <td>
                            <%
                                if (selectedRuleResourceValue != null && selectedRuleResourceValue.trim().length() > 0) {
                            %>
                                <input type="text" name="resourceRuleValue_0" id="resourceRuleValue_0" value="<%=selectedRuleResourceValue%>"/>
                            <%
                                } else {
                            %>
                                <input type="text" name="resourceRuleValue_0" id="resourceRuleValue_0" />
                            <%
                                }
                            %>
                            </td>
                            <td>by user whose</td>
                            <td>
                                <select id="userRuleAttributeId_0" name="userRuleAttributeId_0" >
                                    <%
                                        for (String userAttribute : userAttributeIds) {
                                            if (selectedRuleUserAttributeId != null && userAttribute.equals(selectedRuleUserAttributeId)) {
                                    %>
                                        <option value="<%=userAttribute%>"  selected="selected"><%=userAttribute%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=userAttribute%>"><%=userAttribute%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td> is </td>
                            <td>
                                <select id="userRuleFunction_0" name="userRuleFunction_0"  class="leftCol-small">
                                    <%
                                        for (String function : functions) {
                                            if (selectedRuleUserFunction != null && selectedRuleUserFunction.equals(function)) {
                                    %>
                                        <option value="<%=function%>"  selected="selected"><%=function%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=function%>"><%=function%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td> to </td>
                            <td>
                                <%
                                    if (selectedRuleUserAttributeValue != null && selectedRuleUserAttributeValue.trim().length() > 0) {
                                %>
                                    <input type="text" name="userRuleAttributeValue_0" id="userRuleAttributeValue_0" value="<%=selectedRuleUserAttributeValue%>"/>
                                <%
                                    } else {
                                %>
                                    <input type="text" name="userRuleAttributeValue_0" id="userRuleAttributeValue_0" />
                                <%
                                    }
                                %>
                            </td>
                            <td>
                                <a onclick="createNewActionRuleRow();" style="background-image:url(images/add.gif);float:none" type="button"
                                   class="icon-link"></a>
                            </td>
                        </tr>
                        <tr></tr>
                        </table>
                        </td>
                        </tr>
        <%
            if(elementDTOList != null && elementDTOList.size() > 0){
                elementDTOList.remove(0);
                for(BasicPolicyEditorElementDTO elementDTO : elementDTOList){
                    selectedRuleActionValue = elementDTO.getActionValue();
                    selectedRuleUserAttributeId = elementDTO.getUserAttributeId();
                    selectedRuleUserAttributeValue = elementDTO.getUserAttributeValue();
                    selectedRuleResourceValue = elementDTO.getResourceValue();
                    selectedRuleEnvironmentValue= elementDTO.getEnvironmentValue();
                    selectedRuleEnvironmentId= elementDTO.getEnvironmentId();
                    selectedRuleOperationType= elementDTO.getOperationType();
                    selectedRuleResourceFunction = elementDTO.getFunctionOnResources();
                    selectedRuleUserFunction = elementDTO.getFunctionOnUsers();
        %>
                <script type="text/javascript">
                    function createActionRuleRow() {
                        var rowIndex =  jQuery(document.getElementById('actionRuleTable').rows[document.
                                    getElementById('actionRuleTable').rows.length-1]).attr('data-value');
                        var index = parseInt(rowIndex, 10) + 1;
                        jQuery('#actionRuleTable > tbody:last').append('<tr data-value="'+ index +'"><td><table class="oneline-listing"><tr></tr><tr><td>This action</td>' +
                                '<td><select id="operationRuleType_'  + index + '" name="operationRuleType_'  + index + '"  ><%for (String type  : operationTypes) {if (selectedRuleOperationType != null && type.equals(selectedRuleOperationType)) { %><option value="<%=type%>"  selected="selected"><%=type%></option><%} else {%><option value="<%=type%>"><%=type%></option><%}}%></select></td>' +
                                '<td> be performed on resource which is </td>' +
                                '<td><select id="resourceRuleFunction_'  + index + '" name="resourceRuleFunction_'  + index + '"><%for (String function : functions) {if (selectedRuleResourceFunction != null && selectedRuleResourceFunction.equals(function)) { %><option value="<%=function%>"  selected="selected"><%=function%></option><%} else {%><option value="<%=function%>"><%=function%></option><%}}%></select></td><td>to</td><td><%if (selectedRuleResourceValue != null && selectedRuleResourceValue.trim().length() > 0) {%><input type="text" name="resourceRuleValue_'  + index + '" id="resourceRuleValue_'  + index + '" value="<%=selectedRuleResourceValue%>"/><%} else {%><input type="text" name="resourceRuleValue_'  + index + '" id="resourceRuleValue_'  + index + '" /><%}%></td>' +
                                '<td>by user whose</td>' +
                                '<td><select id="userRuleAttributeId_'  + index + '" name="userRuleAttributeId_'  + index + '" ><%for (String userAttribute : userAttributeIds) {if (selectedRuleUserAttributeId != null && userAttribute.equals(selectedRuleUserAttributeId)) {%><option value="<%=userAttribute%>"  selected="selected"><%=userAttribute%></option><%} else {%><option value="<%=userAttribute%>"><%=userAttribute%></option><%}}%></select></td>' +
                                '<td> is </td>' +
                                '<td><select id="userRuleFunction_'  + index + '" name="userRuleFunction_'  + index + '"  class="leftCol-small"><%for (String function : functions) {if (selectedRuleUserFunction != null && selectedRuleUserFunction.equals(function)) {%><option value="<%=function%>"  selected="selected"><%=function%></option><%} else {%><option value="<%=function%>"><%=function%></option><%}}%></select></td>' +
                                '<td> to </td>' +
                                '<td><%if (selectedRuleUserAttributeValue != null && selectedRuleUserAttributeValue.trim().length() > 0) {%><input type="text" name="userRuleAttributeValue_'  + index + '" id="userRuleAttributeValue_'  + index + '" value="<%=selectedRuleUserAttributeValue%>"/><% } else {%><input type="text" name="userRuleAttributeValue_'  + index + '" id="userRuleAttributeValue_'  + index + '" /><%}%></td>' +
                                '<td><a onclick="removeRow(this)" style="background-image:url(images/delete.gif);" type="button" class="icon-link"></a></td>' +
                                '</tr><tr></tr></table></td></tr>');
                    }
                    createActionRuleRow();
                </script>
        <%
                }
            }
        %>
                    </table>
        </div>
        </div>

        <%--********************--%>
        <%--********************--%>
        <%--END action policy type--%>
        <%--********************--%>
        <%--********************--%>
        <%--********************--%>


        <%--************************--%>
        <%--************************--%>
        <%--START environment policy type--%>
        <%--************************--%>
        <%--************************--%>
        <%--************************--%>

        <%
            } else if(PolicyEditorConstants.SOA_CATEGORY_ENVIRONMENT.equals(selectedPolicyApplied)){
        %>

          <div class="sectionSub sectionSubShifter">
                    <div class="sectionSeperator" id="policyTypeTitle"></div>
                    <table class="oneline-listing-alt">




                        <tr>
                            <td>Environment which is</td>
                            <td>
                                <select id="function" name="function"  class="leftCol-small">
                                    <%
                                        for (String function : functions) {
                                            if (selectedFunction != null && selectedFunction.equals(function)) {
                                    %>
                                        <option value="<%=function%>"  selected="selected"><%=function%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=function%>"><%=function%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td> to </td>
                            <%
                                if (selectedEnvironmentValue != null && selectedEnvironmentValue.trim().length() > 0) {
                            %>
                                <td><input type="text" name="environmentValue" id="environmentValue" value="<%=selectedEnvironmentValue%>"/></td>
                            <%
                                } else {
                            %>
                                <td><input type="text" name="environmentValue" id="environmentValue" /></td>
                            <%
                                }
                            %>
                        </tr>
                        </table>
                </div>
                <%--********************--%>
                <%--********************--%>
                <%--END environment policy type--%>
                <%--********************--%>
                <%--********************--%>
                <%--********************--%>


                <%--************************--%>
                <%--************************--%>
                <%--START Resource policy type--%>
                <%--************************--%>
                <%--************************--%>
                <%--************************--%>
                <%
                    } else {
                %>
                 <div class="sectionSub sectionSubShifter">
                    <div class="sectionSeperator" id="policyTypeTitle"></div>
                    <table class="oneline-listing-alt">
                        <tr>
                            <td>Resource which is</td>
                            <td>
                                <select id="function" name="function"  class="leftCol-small">
                                    <%
                                        for (String function : functions) {
                                            if (selectedFunction != null && selectedFunction.equals(function)) {
                                    %>
                                        <option value="<%=function%>"  selected="selected"><%=function%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=function%>"><%=function%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td> to </td>
                            <%
                                if (selectedResourceValue != null && selectedResourceValue.trim().length() > 0) {
                            %>
                                <td><input type="text" name="resourceValue" id="resourceValue" value="<%=selectedResourceValue%>"/></td>
                            <%
                                } else {
                            %>
                                <td><input type="text" name="resourceValue" id="resourceValue" /></td>
                            <%
                                }
                            %>
                        </tr>
                    </table>

                    <div class="sectionSeperator" id="policyTypeTitleSub"></div>
                    <div class="sectionSub sectionSubShifter">
                    <table  id="resourceRuleTable" >
                        <tr data-value="0">
                        <td>
                        <table class="oneline-listing">
                        <tr>
                            <td style="white-space:nowrap;">Child resource which is</td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>User whose</td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>and perform</td>
                        </tr>
                        <tr>
                            <td><select id="resourceRuleFunction_0" name="resourceRuleFunction_0">
                                    <%
                                        for (String function : functions) {
                                            if (selectedRuleResourceFunction != null && selectedRuleResourceFunction.equals(function)) {
                                    %>
                                        <option value="<%=function%>"  selected="selected"><%=function%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=function%>"><%=function%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select></td>
                            <td>to</td>
                            <td>
                            <%
                                if (selectedRuleResourceValue != null && selectedRuleResourceValue.trim().length() > 0) {
                            %>
                                <input type="text" name="resourceRuleValue_0" id="resourceRuleValue_0" value="<%=selectedRuleResourceValue%>"/>
                            <%
                                } else {
                            %>
                                <input type="text" name="resourceRuleValue_0" id="resourceRuleValue_0" />
                            <%
                                }
                            %>
                            </td>
                            <td>
                                <select id="operationRuleType_0" name="operationRuleType_0"  >
                                    <%
                                        for (String type  : operationTypes) {
                                            if (selectedRuleOperationType != null && type.equals(selectedRuleOperationType)) {
                                    %>
                                        <option value="<%=type%>"  selected="selected"><%=type%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=type%>"><%=type%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td>
                                <select id="userRuleAttributeId_0" name="userRuleAttributeId_0"  >
                                    <%
                                        for (String userAttribute : userAttributeIds) {
                                            if (selectedRuleUserAttributeId != null && userAttribute.equals(selectedRuleUserAttributeId)) {
                                    %>
                                        <option value="<%=userAttribute%>"  selected="selected"><%=userAttribute%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=userAttribute%>"><%=userAttribute%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                            <td>is</td>
                            <td> <select id="userRuleFunction_0" name="userRuleFunction_0" >
                                    <%
                                        for (String function : functions) {
                                            if (selectedRuleUserFunction != null && selectedRuleUserFunction.equals(function)) {
                                    %>
                                        <option value="<%=function%>"  selected="selected"><%=function%></option>
                                    <%
                                            } else {
                                    %>
                                        <option value="<%=function%>"><%=function%></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select></td>
                            <td>to</td>
                            <td> <%
                                if (selectedRuleUserAttributeValue != null && selectedRuleUserAttributeValue.trim().length() > 0) {
                            %>
                                <input type="text" name="userRuleAttributeValue_0" id="userRuleAttributeValue_0" value="<%=selectedRuleUserAttributeValue%>"/>
                            <%
                                } else {
                            %>
                                <input type="text" name="userRuleAttributeValue_0" id="userRuleAttributeValue_0" />
                            <%
                                }
                            %>
                            </td>
                            <td>  <%
                                if (selectedRuleActionValue != null && selectedRuleActionValue.trim().length() > 0) {
                            %>
                                <input type="text" name="actionRuleValue_0" id="actionRuleValue_0" value="<%=selectedRuleActionValue%>"/>
                            <%
                                } else {
                            %>
                                <input type="text" name="actionRuleValue_0" id="actionRuleValue_0" />
                            <%
                                }
                            %></td>
                            <td>
                                <a onclick="createNewResourceRuleRow();" style="background-image:url(images/add.gif);float:none" type="button"
                                   class="icon-link"></a>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>be accessed by</td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td>action</td>
                        </tr>
                        </table>
                        </td>
                        </tr>
        <%
            if(elementDTOList != null && elementDTOList.size() > 0){
                elementDTOList.remove(0);
                for(BasicPolicyEditorElementDTO elementDTO : elementDTOList){
                    selectedRuleActionValue = elementDTO.getActionValue();
                    selectedRuleUserAttributeId = elementDTO.getUserAttributeId();
                    selectedRuleUserAttributeValue = elementDTO.getUserAttributeValue();
                    selectedRuleResourceValue = elementDTO.getResourceValue();
                    selectedRuleEnvironmentValue= elementDTO.getEnvironmentValue();
                    selectedRuleEnvironmentId= elementDTO.getEnvironmentId();
                    selectedRuleOperationType= elementDTO.getOperationType();
                    selectedRuleResourceFunction = elementDTO.getFunctionOnResources();
                    selectedRuleUserFunction = elementDTO.getFunctionOnUsers();
        %>
                <script type="text/javascript">
                    function createResourceRuleRow() {
                        var rowIndex =  jQuery(document.getElementById('resourceRuleTable').rows[document.
                                    getElementById('resourceRuleTable').rows.length-1]).attr('data-value');
                        var index = parseInt(rowIndex, 10) + 1;
                        jQuery('#resourceRuleTable > tbody:last').append('<tr data-value="'+ index +'"><td><table class="oneline-listing"><tr><td style="white-space:nowrap;">Resource  which is</td><td></td><td></td><td></td><td>User whose</td><td></td><td></td><td></td><td></td><td>and perform</td></tr>' +
                            '<tr><td><select id="resourceRuleFunction_'  + index + '" name="resourceRuleFunction_'  + index + '" ><%for (String function : functions) {if (selectedRuleResourceFunction != null && selectedRuleResourceFunction.equals(function)) {%><option value="<%=function%>"  selected="selected"><%=function%></option><%} else {%><option value="<%=function%>"><%=function%></option><%}}%></select></td>' +
                            '<td>to</td>' +
                            '<td><%if (selectedRuleResourceValue != null && selectedRuleResourceValue.trim().length() > 0) {%><input type="text" name="resourceRuleValue_'  + index + '"  id="resourceRuleValue_'  + index + '" value="<%=selectedRuleResourceValue%>"/><%} else {%><input type="text" name="resourceRuleValue_'  + index + '"  id="resourceRuleValue_'  + index + '"  /><% }%> </td>' +
                            '<td><select id="operationRuleType_'  + index + '"  name="operationRuleType_'  + index + '" ><%for (String type  : operationTypes) {if (selectedRuleOperationType != null && type.equals(selectedRuleOperationType)) {%><option value="<%=type%>"  selected="selected"><%=type%></option><%} else {%><option value="<%=type%>"><%=type%></option><%}}%></select></td>' +
                            '<td><select id="userRuleAttributeId_'  + index + '"  name="userRuleAttributeId_'  + index + '"  ><%for (String userAttribute : userAttributeIds) {if (selectedRuleUserAttributeId != null && userAttribute.equals(selectedRuleUserAttributeId)) {%><option value="<%=userAttribute%>" selected="selected"><%=userAttribute%></option><%} else {%><option value="<%=userAttribute%>"><%=userAttribute%></option><%}}%></select></td>' +
                            '<td>is</td>' +
                            '<td> <select id="userRuleFunction_'  + index + '"  name="userRuleFunction_'  + index + '"  ><%for (String function : functions) {if (selectedRuleUserFunction != null && selectedRuleUserFunction.equals(function)) {%><option value="<%=function%>"  selected="selected"><%=function%></option><%} else {%><option value="<%=function%>"><%=function%></option><%}}%></select></td>' +
                            '<td>to</td>' +
                            '<td><%if (selectedRuleUserAttributeValue != null && selectedRuleUserAttributeValue.trim().length() > 0) {%><input type="text" name="userRuleAttributeValue_'  + index + '"  id="userRuleAttributeValue_'  + index + '"  value="<%=selectedRuleUserAttributeValue%>"/><%} else {%><input type="text" name="userRuleAttributeValue_'  + index + '"  id="userRuleAttributeValue_'  + index + '"  /><%}%></td>' +
                            '<td><%if (selectedRuleActionValue != null && selectedRuleActionValue.trim().length() > 0) {%><input type="text" name="actionRuleValue_'  + index + '"  id="actionRuleValue_'  + index + '"  value="<%=selectedRuleActionValue%>"/><%} else {%><input type="text" name="actionRuleValue_'  + index + '"  id="actionRuleValue_'  + index + '"  /><%}%></td>' +
                            '<td><a onclick="removeRow(this)" style="background-image:url(images/delete.gif);" type="button" class="icon-link"></a></td>' +
                            '</tr><tr><td></td><td></td><td></td><td>be accessed by</td><td></td><td></td><td></td><td></td><td></td><td>action</td></tr></table></td></tr>');
                    }
                    createResourceRuleRow();
                </script>
        <%
                }
            }
        %>
                    </table>
                        </div>
               </div><!-- Section Sub -->
                <%
                    }
                %>

        <div class="buttonRow">
            <input type="button" onclick="doSubmit();" value="<fmt:message key="finish"/>"
                   class="button"/>
            <input type="button" onclick="doCancel();" value="<fmt:message key="cancel" />"
                   class="button"/>
        </div>

    </form>
</div>
</div>
</fmt:bundle>
