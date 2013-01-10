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
<%@page import="org.wso2.carbon.utils.ServerConstants"%>
<%@page import="org.wso2.carbon.ui.CarbonUIUtil"%>
<%@page import="org.apache.axis2.context.ConfigurationContext"%>
<%@page import="org.wso2.carbon.CarbonConstants"%>
<%@page import="org.wso2.carbon.CarbonError"%>
<%@page import="java.lang.Exception"%>

<%@page import="org.wso2.carbon.user.core.UserCoreConstants"%>
<%@page import="java.util.ResourceBundle"%>
<script type="text/javascript" src="extensions/js/vui.js"></script>
<script type="text/javascript" src="../extensions/core/js/vui.js"></script>
<script type="text/javascript" src="../admin/js/main.js"></script>

<jsp:include page="../dialog/display_messages.jsp" />
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage"%>
<%@page import="org.wso2.carbon.user.core.UserCoreConstants"%>
<%@ page import="org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO" %>
<%@ page import="org.wso2.carbon.identity.oauth.ui.client.OAuthAdminClient" %>

<%
	OAuthConsumerAppDTO[] apps = null;
	String BUNDLE = "org.wso2.carbon.identity.oauth.ui.i18n.Resources";
	ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, request.getLocale());
	String forwardTo = null;
	String addAction = "add.jsp";
	
	try {		
		   String cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
	       String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
	       ConfigurationContext configContext =
	                (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
	       OAuthAdminClient client = new OAuthAdminClient(cookie, backendServerURL, configContext);
	       apps = client.getAllOAuthApplicationData();	
	} catch (Exception e) {
		String message = resourceBundle.getString("error.while.loading.user.application.data");
		CarbonUIMessage.sendCarbonUIMessage(message,CarbonUIMessage.ERROR, request);
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

<fmt:bundle basename="org.wso2.carbon.identity.oauth.ui.i18n.Resources">
    <carbon:breadcrumb
            label="identity.oauth"
            resourceBundle="org.wso2.carbon.identity.oauth.ui.i18n.Resources"
            topPage="true"
            request="<%=request%>"/>
            
            <script type="text/javascript" src="../carbon/admin/js/breadcrumbs.js"></script>
			<script type="text/javascript" src="../carbon/admin/js/cookies.js"></script>
			<script type="text/javascript" src="../carbon/admin/js/main.js"></script>
			
    <div id="middle">

        <h2><fmt:message key='oauth.management'/></h2>

        <div id="workArea">   
            <script type="text/javascript">
              function remove(consumerkey,appname) {
        	     CARBON.showConfirmationDialog("<fmt:message key='remove.message1'/>"+ appname +"<fmt:message key='remove.message2'/>",
                    function() {
              	       location.href ="remove-app.jsp?consumerkey="+consumerkey;
                     }, null);
                 }
            </script>

            <div style="height:30px;">
                <a href="javascript:document.location.href='<%=addAction%>'" class="icon-link"
                   style="background-image:url(../admin/images/add.gif);"><fmt:message key='add.new.application'/></a>
             </div>       

           	<table style="width: 100%" class="styledLeft">
			<thead>
				<tr>
					<th colspan="2"><fmt:message key='available.applications'/></th>
				</tr>
			</thead>
				<tbody>
           <%
           	if (apps != null && apps.length > 0 ) {
           			for (int i = 0; i < apps.length; i++) {
           %>		
			<tr>				
				<td width="50%"><a href="edit.jsp?consumerkey=<%=apps[i].getOauthConsumerKey()%>"><%=apps[i].getApplicationName()%></a></td>
				<td width="50%"><a title="<fmt:message key='remove.app'/>"
                                   onclick="remove('<%=apps[i].getOauthConsumerKey()%>','<%=apps[i].getApplicationName()%>');return false;"
                                   href="#" style="background-image: url(../oauth/images/delete.gif);" class="icon-link">
                                    <fmt:message key='delete'/></a></td>			
			</tr>
		  <%
		  	}
		  		} else {
		  %>
		    <tr>
				<td width="100%" colspan="2"><i><fmt:message key='no.apps'/></i></td>
			 </tr>
		  <%
		  	}
		  %>
		  </tbody>
		  </table>		
          </div>
    </div>
</fmt:bundle>
