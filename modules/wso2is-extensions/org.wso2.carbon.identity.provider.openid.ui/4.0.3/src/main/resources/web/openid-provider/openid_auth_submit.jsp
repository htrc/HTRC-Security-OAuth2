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

<%@page
	import="org.wso2.carbon.identity.provider.openid.ui.client.OpenIDAdminClient"%>
<%@page import="org.wso2.carbon.ui.CarbonUIUtil"%>
<%@page import="org.apache.axis2.context.ConfigurationContext"%>
<%@page import="org.wso2.carbon.CarbonConstants"%>
<%@page import="org.wso2.carbon.utils.ServerConstants"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="org.wso2.carbon.ui.CarbonUIMessage"%>
<%@page import="org.openid4java.message.ParameterList"%>
<%@page import="org.wso2.carbon.identity.base.IdentityConstants.OpenId"%>

<script type="text/javascript">

  function Set_Cookie( name, value, expires, path, domain, secure )
  {
  // set time, it's in milliseconds
  var today = new Date();
  today.setTime( today.getTime() );

  /*
  if the expires variable is set, make the correct
  expires time, the current script below will set
  it for x number of days, to make it for hours,
  delete * 24, for minutes, delete * 60 * 24
  */
  if ( expires )
  {
  expires = expires * 1000 * 60 * 60 * 24;
  }
  var expires_date = new Date( today.getTime() + (expires) );

  document.cookie = name + "=" +value+
  ( ( expires ) ? ";expires=" + expires_date.toGMTString() : "" ) +
  ( ( path ) ? ";path=" + path : "" ) +
  ( ( domain ) ? ";domain=" + domain : "" ) +
  ( ( secure ) ? ";secure" : "" );
  }

  function Get_Cookie( name ) {
	  var start = document.cookie.indexOf( name + "=" );
	  var len = start + name.length + 1;
	  if ( ( !start ) &&
	  ( name != document.cookie.substring( 0, name.length ) ) )
	  {
	  return null;
	  }
	  if ( start == -1 ) return null;
	  var end = document.cookie.indexOf( ";", len );
	  if ( end == -1 ) end = document.cookie.length;
	  return unescape( document.cookie.substring( len, end ) );
  }
	    

//this deletes the cookie when called
  function Delete_Cookie( name, path, domain ) {
  if ( Get_Cookie( name ) ) document.cookie = name + "=" +
  ( ( path ) ? ";path=" + path : "") +
  ( ( domain ) ? ";domain=" + domain : "" ) +
  ";expires=Thu, 01-Jan-1970 00:00:01 GMT";
  }
</script>

<%
	String serverURL = CarbonUIUtil.getServerURL(
			config.getServletContext(), session);
	ConfigurationContext configContext = (ConfigurationContext) config
			.getServletContext().getAttribute(
					CarbonConstants.CONFIGURATION_CONTEXT);
	String cookie = (String) session
			.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);

	String rememberMe = request.getParameter("remember");
	boolean isRemembered = false;
	String openid = (request.getParameter("openid") != null) ? request
			.getParameter("openid") : (String) session
			.getAttribute("openId");

	// Directed identity, request via openid_auth.jsp		
	String userName = request.getParameter("userName");
			   
	// Directed identity, request via OpenIDHandler.java
	if(openid.endsWith("/openid/") && (userName == null || "".equals(userName.trim()))){
	       userName = (String) session.getAttribute("userName");
	       Cookie[] cookies = request.getCookies();
	       // Directed identity, remember me enabled
		   if (userName == null && cookies != null) {
			 Cookie curCookie = null;
				for (Cookie cooki : cookies) {
					curCookie = cooki;
					if (curCookie.getName().equalsIgnoreCase("openidrememberme")) {
						userName = curCookie.getValue();
						break;
					}
				}
		   }
		   
	} else {
	       session.setAttribute("userName",  userName);        
	}
	
	if(userName != null && !"".equals(userName.trim())){
			       openid = openid + userName;
	}
			
	session.setAttribute("openId", openid);

	if ("true".equals(rememberMe)) {
		isRemembered = true;
	}

	OpenIDAdminClient client = new OpenIDAdminClient(configContext,
			serverURL, null);

	if (client.authenticateWithOpenID(openid,
			request.getParameter("password"), session, request,
			response, isRemembered) || (session.getAttribute("authenticatedOpenID") != null && (session.getAttribute("authenticatedOpenID").equals(openid)))) {

		session.setAttribute("isOpenIDAuthenticated", "true");
		session.setAttribute("authenticatedOpenID", openid);
		// user approval is always bypased based on the identity.xml config
		if (client.isOpenIDUserApprovalBypassEnabled()) {	
			session.setAttribute("selectedProfile", "default");
			session.setAttribute("_action", "complete");
			session.setAttribute("userApproved", "true");
			session.removeAttribute("isOpenIDAuthenticated");
%>
		<script type="text/javascript">
			Set_Cookie("openidtoken","<%=client.getNewCookieValue()%>",14,"/",null,true);
			Set_Cookie("openidrememberme","<%=userName%>",14,"/",null,true);
			location.href="../../openidserver";
		</script>
<%
	    }

		String[] rpInfo = client.getOpenIDUserRPInfo(openid,
				((ParameterList) session
						.getAttribute(OpenId.PARAM_LIST))
						.getParameterValue(OpenId.ATTR_RETURN_TO));

		// user approval is bypassed since approve always is enabled
		if (rpInfo[0].equals("true")) {
			session.setAttribute("_action", "complete");
			session.setAttribute("userApproved", "true");
			session.setAttribute("userApprovedAlways","true"); 
			session.setAttribute("selectedProfile", rpInfo[1]);
			session.removeAttribute("isOpenIDAuthenticated");
%>
		<script type="text/javascript">
			Set_Cookie("openidtoken","<%=client.getNewCookieValue()%>",14,"/",null,true);
			Set_Cookie("openidrememberme","<%=userName%>",14,"/",null,true);
			location.href="../../openidserver";
		</script>
<%
	// redirect to user approval page	
		} else {
%>
		<script type="text/javascript">
			Set_Cookie("openidtoken","<%=client.getNewCookieValue()%>", 14, "/", null,true);
			Set_Cookie("openidrememberme","<%=userName%>",14,"/",null,true);
			location.href = "openid_profile_view.jsp";
		</script>
<%
	}

	} else {
		session.removeAttribute("isOpenIDAuthenticated");
		String BUNDLE = "org.wso2.carbon.identity.provider.openid.ui.i18n.Resources";
		ResourceBundle resourceBundle = ResourceBundle.getBundle(
				BUNDLE, request.getLocale());

		String message = resourceBundle
				.getString("error.while.user.auth");
		session.removeAttribute("openId");
		CarbonUIMessage.sendCarbonUIMessage(message,
				CarbonUIMessage.ERROR, request);
%>
<script type="text/javascript">
	Delete_Cookie("openidtoken", "/", null);
	Delete_Cookie("openidrememberme", "/", null);
	location.href = "openid_auth.jsp";
</script>
<%
	}
%>
