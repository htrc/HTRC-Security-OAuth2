<!--
 ~ Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 ~
 ~ WSO2 Inc. licenses this file to you under the Apache License,
 ~ Version 2.0 (the "License"); you may not use this file except
 ~ in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~    http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing,
 ~ software distributed under the License is distributed on an
 ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 ~ KIND, either express or implied.  See the License for the
 ~ specific language governing permissions and limitations
 ~ under the License.
 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar"
	prefix="carbon"%>
<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="org.wso2.carbon.identity.entitlement.stub.dto.ModuleDataHolder" %>
<%@ page import="org.wso2.carbon.identity.entitlement.stub.dto.ModulePropertyDTO" %>
<%@ page import="org.wso2.carbon.identity.entitlement.stub.dto.ModuleStatusHolder" %>
<%@ page import="org.wso2.carbon.identity.entitlement.ui.PropertyDTOComparator" %>
<%@ page import="org.wso2.carbon.identity.entitlement.ui.client.EntitlementAdminServiceClient" %>

<%
    String subscriberId;
    ModuleDataHolder subscriber = null;
    ModuleDataHolder[] dataHolders;
    ModulePropertyDTO[] propertyDTOs = null;
    String selectedModule = null;
    String forwardTo = null;
    boolean fromIndexPage = false;
    boolean view = false;


    selectedModule = request.getParameter("selectedModule");
    String viewString = request.getParameter("view");
    subscriberId = request.getParameter("subscriberId");
    String fromIndex = request.getParameter("fromIndexPage");
    dataHolders = (ModuleDataHolder[]) session.getAttribute("publisherModuleHolders");

    if((viewString != null)){
        view = Boolean.parseBoolean(viewString);
    }
    
    if(fromIndex != null){
        fromIndexPage = Boolean.parseBoolean(fromIndex);
    }

    String serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
    ConfigurationContext configContext =
            (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.
                    CONFIGURATION_CONTEXT);
    String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
    String BUNDLE = "org.wso2.carbon.identity.entitlement.ui.i18n.Resources";
	ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, request.getLocale());
    try {
        EntitlementAdminServiceClient client = new EntitlementAdminServiceClient(cookie,
                serverURL, configContext);
        if(subscriberId != null){
            subscriber = client.getSubscriber(subscriberId);
            if(subscriber != null){
                propertyDTOs = subscriber.getPropertyDTOs();
                selectedModule = subscriber.getModuleName();
                dataHolders = new ModuleDataHolder[]{subscriber};
            }
        } else {
            if(dataHolders == null){
                dataHolders = client.getPublisherModuleProperties();
            }
            if(dataHolders != null){
                session.setAttribute("publisherModuleHolders", dataHolders);
                if(selectedModule != null){
                    for(ModuleDataHolder holder : dataHolders){
                        if(selectedModule.equals(holder.getModuleName())){
                            propertyDTOs = holder.getPropertyDTOs();
                            break;
                        }
                    }
                }
            }
        }
        if(propertyDTOs != null){
            session.setAttribute("propertyDTO", propertyDTOs);
            java.util.Arrays.sort(propertyDTOs , new PropertyDTOComparator());
        }
    } catch (Exception e) {
        e.printStackTrace();
    	String message = resourceBundle.getString("error.while.performing.advance.search");
        CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.ERROR, request);
        forwardTo = "../admin/error.jsp";
%>
<script type="text/javascript">
    function forward() {
        location.href = "<%=forwardTo%>";
    }
</script>

<script type="text/javascript">
    forward();
</script>
<%
    }
%>


<fmt:bundle basename="org.wso2.carbon.identity.entitlement.ui.i18n.Resources">
<carbon:breadcrumb
		label="add.new.subscriber"
		resourceBundle="org.wso2.carbon.identity.entitlement.ui.i18n.Resources"
		topPage="false"
		request="<%=request%>" />

    <script type="text/javascript" src="../carbon/admin/js/breadcrumbs.js"></script>
    <script type="text/javascript" src="../carbon/admin/js/cookies.js"></script>
    <script type="text/javascript" src="resources/js/main.js"></script>
    <!--Yahoo includes for dom event handling-->
    <script src="../yui/build/yahoo-dom-event/yahoo-dom-event.js" type="text/javascript"></script>
    <script src="../entitlement/js/create-basic-policy.js" type="text/javascript"></script>
    <link href="../entitlement/css/entitlement.css" rel="stylesheet" type="text/css" media="all"/>

<script type="text/javascript">

    function doAdd(){
        document.requestForm.action = "policy-publish.jsp";
        var update = document.createElement("input");
        update.setAttribute("type", "hidden");
        update.setAttribute("name", "update");
        update.setAttribute("value", "false");
        document.requestForm.appendChild(update);
        document.requestForm.submit();
    }

    function doUpdate(){
        document.requestForm.action = "policy-publish.jsp";
        var update = document.createElement("input");
        update.setAttribute("type", "hidden");
        update.setAttribute("name", "update");
        update.setAttribute("value", "true");
        document.requestForm.appendChild(update);
        document.requestForm.submit();
    }

    function doCancel(){
        location.href = 'policy-publish.jsp?fromIndexPage=' + '<%=fromIndexPage%>';
    }

    function getSelectedSubjectType() {
        document.requestForm.submit();
    }

</script>

<div id="middle">
    <h2><fmt:message key="add.subscriber"/></h2>
    <div id="workArea">
        <form id="requestForm" name="requestForm" method="post" action="add-subscriber.jsp">
        <table class="styledLeft noBorders">
        <%
            if(view){
        %>
                <tr>
                    <td>
                        <table  class="styledLeft"  style="width: 100%;margin-top:10px;">
                        <%
                            if(propertyDTOs != null){
                                for(ModulePropertyDTO dto : propertyDTOs){
                                    if(dto.getDisplayName() != null && dto.getValue() != null){
                        %>
                            <tr>
                                <td><%=dto.getDisplayName()%></td>
                                <td><%=dto.getValue()%></td>
                            </tr>
                        <%
                                    }
                                }
                            }
                        %>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table  class="styledLeft"  style="width: 100%;margin-top:10px;">
                        <%
                            if(subscriber != null && subscriber.getStatusHolders() != null){
                                for(ModuleStatusHolder dto : subscriber.getStatusHolders()){
                                    if(dto != null && dto.getTimeInstance() != null &&
                                                                            dto.getKey() != null){
                        %>
                                <tr>
                                    <td><%=dto.getTimeInstance()%></td>
                                    <td><%=dto.getKey()%></td>
                                    <td><%=dto.getSuccess()%></td>
                                    <td>
                                        <%
                                          if(dto.getMessage() != null){
                                        %>
                                        <%=dto.getMessage()%>
                                        <%
                                            }
                                        %>
                                    </td>
                                </tr>
                        <%
                                    }
                                }
                            }
                        %>
                        </table>
                    </td>
                </tr>
        <%
            } else {
        %>
                <tr>
                    <td class="leftCol-small"><fmt:message key='select.module'/><span class="required">*</span></td>
                    <td colspan="2">
                    <select  onchange="getSelectedSubjectType();" id="selectedModule" name="selectedModule">
                            <option value="Selected" selected="selected">---Select----</option>
                            <%
                                if(dataHolders != null){
                                    for (ModuleDataHolder module : dataHolders) {
                                        if(module.getModuleName().equals(selectedModule)) {
                            %>
                                <option value="<%=selectedModule%>" selected="selected"><%=selectedModule%></option>
                            <%
                                        } else {
                            %>
                                <option value="<%=module.getModuleName()%>"><%=module.getModuleName()%></option>
                            <%
                                        }
                                    }
                                }
                            %>
                    </select>
                    </td>
                </tr>

                <%
                    if(propertyDTOs != null){
                        for (ModulePropertyDTO dto : propertyDTOs) {
                            String inputType = "text";
                            if ("subscriberPassword".equals(dto.getId())) {
                                inputType = "password";
                            }
                %>
                <tr>
                    <td class="leftCol-small"><%=dto.getDisplayName()%>
                    <%
                        if(dto.getRequired()){
                    %>
                        <span class="required">*</span>
                    <%
                        }
                    %>
                    </td>
                    <td>
                        <% if(dto.getValue() != null) {%>
                            <input type="<%=inputType%>" name="<%=dto.getId()%>" id="<%=dto.getId()%>"
                                                                        value="<%=dto.getValue()%>" <% if("subscriberId".equals(dto.getId())){ %> readonly='readonly' <% } %> />
                        <%
                            } else {
                        %>
                            <input type="<%=inputType%>" name="<%=dto.getId()%>" id="<%=dto.getId()%>"/>
                        <%
                            }
                        %>
                    </td>
                </tr>
            <%
                    }
                }
            }
            %>
                <tr>
                    <td colspan="2" class="buttonRow">
                        <%
                            if(!view){   
                        %>
                        <input class="button" type="button"
                            <%if(subscriber != null){%> value="<fmt:message key='update'/>" onclick="doUpdate();" <%} else { %>
                            value="<fmt:message key='add'/>" onclick="doAdd();" <% } %> />
                        <%
                            }
                        %>
                        <input class="button" type="button" value="<fmt:message key='cancel'/>"  onclick="doCancel();"/>
                    </td>
                </tr>
        </table>
        </form>
    </div>
</div>
</fmt:bundle>
