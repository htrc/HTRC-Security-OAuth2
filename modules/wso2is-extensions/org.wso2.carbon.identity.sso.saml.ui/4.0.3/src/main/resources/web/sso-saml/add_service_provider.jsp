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
<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.identity.sso.saml.ui.client.SAMLSSOConfigServiceClient" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIMessage" %>
<%@page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@page import="org.wso2.carbon.utils.ServerConstants" %>
<%@page import="java.util.ArrayList" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderDTO" %>

<jsp:useBean id="samlSsoServuceProviderConfigBean"
             type="org.wso2.carbon.identity.sso.saml.ui.SAMLSSOProviderConfigBean"
             class="org.wso2.carbon.identity.sso.saml.ui.SAMLSSOProviderConfigBean"
             scope="session"/>
<jsp:setProperty name="samlSsoServuceProviderConfigBean" property="*"/>

<script type="text/javascript" src="global-params.js"></script>
<script type="text/javascript" src="../carbon/admin/js/breadcrumbs.js"></script>
<script type="text/javascript" src="../carbon/admin/js/cookies.js"></script>
<script type="text/javascript" src="../carbon/admin/js/main.js"></script>


<%
    String backendServerURL;
    ConfigurationContext configContext;
    String cookie;
    String user = null;
    SAMLSSOConfigServiceClient client;

    backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
    configContext = (ConfigurationContext) config.getServletContext().getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
    cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
    String BUNDLE = "org.wso2.carbon.identity.sso.saml.ui.i18n.Resources";
    ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE, request.getLocale());

    try {
        client = new SAMLSSOConfigServiceClient(cookie, backendServerURL, configContext);

        SAMLSSOServiceProviderDTO serviceProviderDTO = new SAMLSSOServiceProviderDTO();
        boolean isEditingSP = false;
        if ("editServiceProvider".equals(request.getParameter("SPAction"))) {
            isEditingSP = true;
            serviceProviderDTO.setIssuer(request.getParameter("hiddenIssuer"));
        } else {
            serviceProviderDTO.setIssuer(request.getParameter("issuer"));
        }

        serviceProviderDTO.setAssertionConsumerUrl(request.getParameter("assrtConsumerURL"));
        serviceProviderDTO.setCertAlias(request.getParameter("alias"));

        if ("true".equals(request.getParameter("useFullQualifiedUsername"))) {
            serviceProviderDTO.setUseFullyQualifiedUsername(true);
        }
        if ("true".equals(request.getParameter("enableSingleLogout"))) {
            serviceProviderDTO.setDoSingleLogout(true);
        }
        if ("true".equals(request.getParameter("enableSignature"))) {
            serviceProviderDTO.setDoSignAssertions(true);
        }

        if ("true".equals(request.getParameter("enableAttributeProfile"))) {
            serviceProviderDTO.setRequestedClaims(samlSsoServuceProviderConfigBean.getSelectedClaimsAttay());
        }
        serviceProviderDTO.setLogoutURL(request.getParameter("logoutURL"));

        String claimsCountParameter = request.getParameter("claimPropertyCounter");
        if (claimsCountParameter != null && !"".equals(claimsCountParameter)) {
            try {
                int claimsCount = Integer.parseInt(claimsCountParameter);
                for (int i = 0; i < claimsCount; i++) {
                    String claim = request.getParameter("claimPropertyName" + i);
                    if (claim != null && !"".equals(claim) && !"null".equals(claim)) {
                        String[] currentClaims = serviceProviderDTO.getRequestedClaims();
                        boolean isClaimAlreadyAdded = false;
                        for (String currentClaim : currentClaims) {
                            if (claim.equals(currentClaim)) {
                                isClaimAlreadyAdded = true;
                                break;
                            }
                        }

                        if (!isClaimAlreadyAdded) {
                            serviceProviderDTO.addRequestedClaims(claim);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid number", e);
            }
        }


        if (isEditingSP) {
            client.removeServiceProvier(serviceProviderDTO.getIssuer());
        }
        boolean status = client.addServiceProvider(serviceProviderDTO);

        samlSsoServuceProviderConfigBean.clearBean();

        String message;
        if (status) {
            if (isEditingSP) {
                message = resourceBundle.getString("sp.updated.successfully");
            } else {
                message = resourceBundle.getString("sp.added.successfully");
            }
        } else {
            message = resourceBundle.getString("error.adding.sp");
        }

        if (status) {
            CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.INFO, request);
        } else {
            CarbonUIMessage.sendCarbonUIMessage(message, CarbonUIMessage.ERROR, request);
        }
%>
<script>
    location.href = 'manage_service_providers.jsp?region=region1&item=manage_saml_sso';
</script>
<%

} catch (Exception e) {
    CarbonUIMessage.sendCarbonUIMessage(e.getMessage(), CarbonUIMessage.ERROR, request, e);
%>
<script type="text/javascript">
    location.href = "../admin/error.jsp";
</script>
<%
        return;
    }
%>