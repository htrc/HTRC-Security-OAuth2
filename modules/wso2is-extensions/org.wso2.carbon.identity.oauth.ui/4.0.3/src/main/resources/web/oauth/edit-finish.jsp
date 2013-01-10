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
<%@page import="org.wso2.carbon.ui.util.CharacterEncoder"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar"
    prefix="carbon"%>
<%@page import="org.wso2.carbon.utils.ServerConstants"%>
<%@page import="org.wso2.carbon.ui.CarbonUIUtil"%>
<%@page import="org.apache.axis2.context.ConfigurationContext"%>
<%@page import="org.wso2.carbon.CarbonConstants"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.lang.Exception"%>

<%@page import="org.wso2.carbon.user.core.UserCoreConstants"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="org.wso2.carbon.identity.oauth.ui.client.OAuthAdminClient"%>
<script type="text/javascript" src="extensions/js/vui.js"></script>
<script type="text/javascript" src="../extensions/core/js/vui.js"></script>
<script type="text/javascript" src="../admin/js/main.js"></script>

<jsp:include page="../dialog/display_messages.jsp" />
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage"%>
<%@ page import="org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO" %>

<%
	String consumerkey = CharacterEncoder.getSafeText(request.getParameter("consumerkey"));
    String callback = CharacterEncoder.getSafeText(request.getParameter("callback"));
    String applicationName = CharacterEncoder.getSafeText(request.getParameter("application"));
    String consumersecret = CharacterEncoder.getSafeText(request.getParameter("consumersecret"));
    String oauthVersion = CharacterEncoder.getSafeText(request.getParameter("oauthVersion"));
	String forwardTo = "index.jsp";
    String BUNDLE = "org.wso2.carbon.identity.oauth.ui.i18n.Resources";
	ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, request.getLocale());
	OAuthConsumerAppDTO app = new OAuthConsumerAppDTO();
	
    try {
         if(session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE) != null){
            String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
            String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
            ConfigurationContext configContext =
                    (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
            OAuthAdminClient client = new OAuthAdminClient(cookie, backendServerURL, configContext);
            app.setOauthConsumerKey(consumerkey);
            app.setOauthConsumerSecret(consumersecret);
            app.setCallbackUrl(callback);
            app.setApplicationName(applicationName);
            app.setOAuthVersion(oauthVersion);
            client.updateOAuthApplicationData(app);
            String message = resourceBundle.getString("app.updated.successfully");
            CarbonUIMessage.sendCarbonUIMessage(message,CarbonUIMessage.INFO, request);
         }else{
            forwardTo = "../admin/index.jsp";
            CarbonUIMessage.sendCarbonUIMessage("Your session has timed out. Please try again.", CarbonUIMessage.ERROR, request);
         }
    } catch (Exception e) {
    	String message = resourceBundle.getString("error.while.updating.app");
    	CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.ERROR, request,e);
        forwardTo ="../admin/error.jsp";
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