<!--
~ Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
~
~ WSO2 Inc. licenses this file to you under the Apache License,
~ Version 2.0 (the "License"); you may not use this file except
~ in compliance with the License.
~ You may obtain a copy of the License at
~
~ http://www.apache.org/licenses/LICENSE-2.0
~
~ Unless required by applicable law or agreed to in writing,
~ software distributed under the License is distributed on an
~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
~ KIND, either express or implied. See the License for the
~ specific language governing permissions and limitations
~ under the License.
-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar"
           prefix="carbon" %>
<%@page import="org.apache.axis2.context.ConfigurationContext" %>
<%@page import="org.wso2.carbon.CarbonConstants" %>
<%@page import="org.wso2.carbon.identity.scim.common.stub.config.SCIMProviderDTO" %>
<%@page import="org.wso2.carbon.identity.scim.ui.client.SCIMConfigAdminClient" %>
<%@page import="org.wso2.carbon.identity.scim.ui.utils.SCIMUIUtils" %>

<%@page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<script type="text/javascript" src="extensions/js/vui.js"></script>
<script type="text/javascript" src="../extensions/core/js/vui.js"></script>
<script type="text/javascript" src="../admin/js/main.js"></script>

<jsp:include page="../dialog/display_messages.jsp"/>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="java.util.ResourceBundle" %>

<%
    SCIMProviderDTO[] scimProviders = null;
    String BUNDLE = "org.wso2.carbon.identity.scim.ui.i18n.Resources";
    ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, request.getLocale());
    String forwardTo = null;
    String addAction = "my-scim-add.jsp";

    try {
        if (session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE) != null) {
            String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
            String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
            ConfigurationContext configContext =
                    (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
            SCIMConfigAdminClient client = new SCIMConfigAdminClient(cookie, backendServerURL, configContext);
            String userName = (String) session.getAttribute(ServerConstants.USER_LOGGED_IN);
            scimProviders = client.getAllUserProviders(SCIMUIUtils.getUserConsumerId(userName));
        } else {
            CarbonUIMessage.sendCarbonUIMessage("Your session has timed out. Please try again.", CarbonUIMessage.ERROR, request);
            forwardTo = "../admin/index.jsp";
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
} catch (Exception e) {
    String message = resourceBundle.getString("error.while.loading.scim.provider.data");
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

<fmt:bundle basename="org.wso2.carbon.identity.scim.ui.i18n.Resources">
    <carbon:breadcrumb
            label="identity.scim"
            resourceBundle="org.wso2.carbon.identity.scim.ui.i18n.Resources"
            topPage="true"
            request="<%=request%>"/>

    <script type="text/javascript" src="../carbon/admin/js/breadcrumbs.js"></script>
    <script type="text/javascript" src="../carbon/admin/js/cookies.js"></script>
    <script type="text/javascript" src="../carbon/admin/js/main.js"></script>

    <div id="middle">

        <h2><fmt:message key='scim.management'/></h2>

        <div id="workArea">
            <script type="text/javascript">
                function remove(providerId) {
                    CARBON.showConfirmationDialog("<fmt:message key='remove.message1'/>" + providerId + "<fmt:message key='remove.message2'/>",
                                                  function() {
                                                      location.href = "my-scim-remove-provider.jsp?providerId=" + providerId;
                                                  }, null);
                }
            </script>
            <div style="height:30px;">
                <a href="javascript:document.location.href='<%=addAction%>'" class="icon-link"
                   style="background-image:url(../admin/images/add.gif);"><fmt:message
                        key='add.new.provider'/></a>
            </div>

            <table style="width: 100%" class="styledLeft">
                <thead>
                <tr>
                    <th colspan="2"><fmt:message key='available.providers'/></th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (scimProviders != null && scimProviders.length > 0) {
                        for (int i = 0; i < scimProviders.length; i++) {
                %>
                <tr>
                    <td width="50%"><a
                            href="my-scim-edit.jsp?providerId=<%=scimProviders[i].getProviderId()%>"><%=scimProviders[i].getProviderId()%>
                    </a></td>
                    <td width="50%"><a title="<fmt:message key='remove.provider'/>"
                                       onclick="remove('<%=scimProviders[i].getProviderId()%>');return false;"
                                       href="#"
                                       style="background-image: url(../scim/images/delete.gif);"
                                       class="icon-link">
                        <fmt:message key='delete'/></a></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td width="100%" colspan="2"><i><fmt:message key='no.providers'/></i></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</fmt:bundle>
