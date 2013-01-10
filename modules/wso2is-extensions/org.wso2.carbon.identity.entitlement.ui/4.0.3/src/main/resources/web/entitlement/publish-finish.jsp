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
<%@ page import="org.apache.axis2.context.ConfigurationContext"%>
<%@ page import="org.wso2.carbon.CarbonConstants"%>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage"%>
<%@ page import="org.wso2.carbon.utils.ServerConstants"%>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="java.util.ResourceBundle" %>
<%@page import="java.lang.Exception"%>
<%@ page import="org.wso2.carbon.identity.entitlement.ui.client.EntitlementAdminServiceClient" %>
<%

    boolean publishToAllSubscribers = false;
    boolean publishAllPolicies = false;
    String[] selectedPolicies = null;
    String forwardTo;

    if(session.getAttribute("selectedPolicies") != null){
        selectedPolicies= (String[]) session.getAttribute("selectedPolicies"); 
    }
    if(session.getAttribute("publishAllPolicies") != null){
        publishAllPolicies = (Boolean) session.getAttribute("publishAllPolicies");
    }
    String[] selectedSubscribers = request.getParameterValues("subscribers");
    String allSubscribers = request.getParameter("publishToAllSubscribers");
    if(allSubscribers != null && "true".equals(allSubscribers.trim())){
        publishToAllSubscribers = true;
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
        if(publishAllPolicies && publishToAllSubscribers){
            client.publishAll(null, null);
        } else if(publishAllPolicies && selectedSubscribers != null && selectedSubscribers.length > 0){
            client.publishAll(null, selectedSubscribers);
        } else if(selectedPolicies != null && selectedPolicies.length > 0 && publishToAllSubscribers){
            client.publishAll(selectedPolicies, null);
        } else if(selectedPolicies != null && selectedPolicies.length > 0 && selectedSubscribers != null &&
                            selectedSubscribers.length > 0){
            client.publishAll(selectedPolicies, selectedSubscribers);
        }
        
        forwardTo = "policy-publish.jsp";

    } catch (Exception e) {
        e.printStackTrace();
    	String message = resourceBundle.getString("error.while.performing.advance.search");
        CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.ERROR, request);
        forwardTo = "../admin/error.jsp";
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


