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
<%@ page import="org.apache.axis2.context.ConfigurationContext"%>
<%@ page import="org.wso2.carbon.CarbonConstants"%>
<%@ page import="org.wso2.carbon.CarbonError"%>
<%@ page
        import="org.wso2.carbon.identity.sso.saml.ui.client.SAMLSSOConfigServiceClient"%>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil"%>
<%@ page import="org.wso2.carbon.utils.ServerConstants"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.apache.axis2.AxisFault"%>
<%@ page
        import="org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderDTO"%>
<%@ page
        import="org.wso2.carbon.identity.sso.saml.stub.types.SAMLSSOServiceProviderInfoDTO"%>
<%@ page import="java.util.Collections" %>
<%@ page import="org.wso2.carbon.identity.sso.saml.ui.SAMLSSOProviderConfigBean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar"
           prefix="carbon"%>
<jsp:useBean id="samlSsoServuceProviderConfigBean"
             type="org.wso2.carbon.identity.sso.saml.ui.SAMLSSOProviderConfigBean"
             class="org.wso2.carbon.identity.sso.saml.ui.SAMLSSOProviderConfigBean"
             scope="session" />
<jsp:setProperty name="samlSsoServuceProviderConfigBean" property="*" />
<jsp:include page="../dialog/display_messages.jsp" />

<fmt:bundle
        basename="org.wso2.carbon.identity.sso.saml.ui.i18n.Resources">
<carbon:breadcrumb label="sso.configuration"
                   resourceBundle="org.wso2.carbon.identity.sso.saml.ui.i18n.Resources"
                   topPage="true" request="<%=request%>" />

<script type="text/javascript" src="global-params.js"></script>
<script type="text/javascript" src="../carbon/admin/js/breadcrumbs.js"></script>
<script type="text/javascript" src="../carbon/admin/js/cookies.js"></script>
<script type="text/javascript" src="../carbon/admin/js/main.js"></script>

<script type="text/javascript">
    function doValidation() {
        var fld = document.getElementsByName("assrtConsumerURL")[0];
        var value = fld.value;
        if (value.length == 0) {
            CARBON.showWarningDialog(
                    "<fmt:message key='sp.enter.valid.endpoint.address'/>",
                    null, null);
            return false;
        }

        value = value.replace(/^\s+/, "");
        if (value.length == 0) {
            CARBON.showWarningDialog(
                    "<fmt:message key='sp.enter.valid.endpoint.address'/>",
                    null, null);
            return false;
        }

        var fld = document.getElementsByName("issuer")[0];
        var value = fld.value;
        if (value.length == 0) {
            CARBON.showWarningDialog(
                    "<fmt:message key='sp.enter.valid.issuer'/>", null,
                    null);
            return false;
        }

        if (document.getElementsByName("subjectType")[1].checked) {
            var claimVal = document.getElementsByName("claimID")[0].value;
            if (claimVal.length == 0) {
                CARBON.showWarningDialog(
                        "<fmt:message key='sp.enter.valid.claimID'/>",
                        null, null);
                return false;
            }
        }
        return true;
    }

    function edit(issuer) {
        location.href = "manage_service_providers.jsp?region=region1&item=manage_saml_sso&SPAction=editServiceProvider&issuer=" + issuer;
    }

    function remove(issuer) {
        CARBON.showConfirmationDialog(
                "<fmt:message key='remove.message1'/>" + " " + issuer
                        + "<fmt:message key='remove.message2'/>",
                function() {
                    location.href = "remove_service_providers.jsp?issuer="
                            + issuer;
                }, null);
    }
    function showHideTxtBox(radioBtn) {
        var claimIdRow = document.getElementById('claimIdRow');
        var nameIdRow = document.getElementById('nameIdRow');
        if (radioBtn.checked && radioBtn.value == "useClaimId") {
            claimIdRow.style.display = "";
            nameIdRow.style.display = "";
        } else {
            claimIdRow.style.display = "none";
            nameIdRow.style.display = "none";
        }
    }
    function disableCertAlias(chkbx) {
        document.addServiceProvider.alias.disabled = (chkbx.checked) ? false
                : true;
    }
    function disableLogoutUrl(chkbx) {
        document.addServiceProvider.logoutURL.disabled = (chkbx.checked) ? false
                : true;
    }
    function disableSignature(chkbx) {
        document.addServiceProvider.enableSignature.value = (chkbx.checked) ? true
                : false;
    }
    function disableAttributeProfile(chkbx) {
        document.addServiceProvider.claim.disabled = (chkbx.checked) ? false
                : true;
        document.addServiceProvider.addClaims.disabled = (chkbx.checked) ? false
                : true;
    }
    function addClaim() {
        var propertyCount = document.getElementById("claimPropertyCounter");

        var i = propertyCount.value;
        var currentCount = parseInt(i);

        currentCount = currentCount + 1;
        propertyCount.value = currentCount;

        document.getElementById('claimTableId').style.display = '';
        var claimTableTBody = document.getElementById('claimTableTbody');

        var claimRow = document.createElement('tr');
        claimRow.setAttribute('id', 'claimRow' + i);

        var claim = document.getElementById('claim').value;
        var claimPropertyTD = document.createElement('td');
        claimPropertyTD.setAttribute('style', 'padding-left: 40px ! important; color: rgb(119, 119, 119); font-style: italic;');
        claimPropertyTD.innerHTML = "" + claim + "<input type='hidden' name='claimPropertyName" + i + "' id='claimPropertyName" + i + "'  value='" + claim +"'/> ";

        var claimRemoveTD = document.createElement('td');
        claimRemoveTD.innerHTML  = "<a href='#' class='icon-link' style='background-image: url(../admin/images/delete.gif)' onclick='removeClaim(" + i + ");return false;'>" + "Delete" + "</a>";

        claimRow.appendChild(claimPropertyTD);
        claimRow.appendChild(claimRemoveTD);

        claimTableTBody.appendChild(claimRow);
    }


    function removeClaim(i) {
        var propRow = document.getElementById("claimRow" + i);
        if (propRow != undefined && propRow != null) {
            var parentTBody = propRow.parentNode;
            if (parentTBody != undefined && parentTBody != null) {
                parentTBody.removeChild(propRow);
                if (!isContainRaw(parentTBody)) {
                    var propertyTable = document.getElementById("claimTableId");
                    propertyTable.style.display = "none";

                }
            }
        }
    }

    function isContainRaw(tbody) {
        if (tbody.childNodes == null || tbody.childNodes.length == 0) {
            return false;
        } else {
            for (var i = 0; i < tbody.childNodes.length; i++) {
                var child = tbody.childNodes[i];
                if (child != undefined && child != null) {
                    if (child.nodeName == "tr" || child.nodeName == "TR") {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    function clearAll() {
        document.addServiceProvider.action = "update_claims.jsp?action=clear";
        document.addServiceProvider.submit();
    }
</script>

<%
    String cookie;
    String serverURL;
    ConfigurationContext configContext;
    SAMLSSOConfigServiceClient spConfigClient;
    ArrayList<String> aliasSet = null;
    String[] claimUris = null;
    SAMLSSOServiceProviderInfoDTO serviceProviderInfoDTO = null;
    ArrayList<SAMLSSOServiceProviderDTO> providers =
            new ArrayList<SAMLSSOServiceProviderDTO>();

    try {
        serverURL = CarbonUIUtil.getServerURL(config.getServletContext(), session);
        configContext =
                (ConfigurationContext) config.getServletContext()
                        .getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
        cookie = (String) session.getAttribute(ServerConstants.ADMIN_SERVICE_COOKIE);
        spConfigClient =
                new SAMLSSOConfigServiceClient(cookie, serverURL,
                        configContext);
        serviceProviderInfoDTO = spConfigClient.getRegisteredServiceProviders();
        if (serviceProviderInfoDTO.getServiceProviders() != null) {
            Collections.addAll(providers, serviceProviderInfoDTO.getServiceProviders());
        }
        aliasSet = spConfigClient.getCertAlias();
        claimUris = spConfigClient.getClaimURIs();
    } catch (AxisFault e) {
        CarbonError error = new CarbonError();
        error.addError(e.getMessage());
        request.getSession().setAttribute(CarbonError.ID, error);
%>
<script type="text/javascript">
    location.href = '../admin/error.jsp';
</script>
<%
    }
%>

<div id="middle">
<h2>
    <fmt:message key="saml.sso" />
</h2>

<div id="workArea">

<form>
    <table class="styledLeft" width="100%" id="ServiceProviders">
        <thead>
        <tr>
            <th><fmt:message key="sp.issuer" /></th>
            <th><fmt:message key="sp.assertionConsumerURL" /></th>
            <th colspan="1"><fmt:message key="sp.certAlias" /></th>
            <th colspan="1"><fmt:message key="sp.consumerIndex" /></th>
            <th colspan="2"><fmt:message key="sp.action"/></th>
        </tr>
        </thead>
        <tbody>
        <%
            if (providers != null && providers.size() > 0) {
                for (SAMLSSOServiceProviderDTO sp : providers) {
        %>
        <tr>
            <td width="30%"><%=sp.getIssuer()%></td>
            <td width="35%"><%=sp.getAssertionConsumerUrl()%></td>
            <td width="10%"><%=sp.getCertAlias() == null ? "" : sp.getCertAlias()%></td>
            <td width="15%"><%=sp.getAttributeConsumingServiceIndex() == null
                    ? ""
                    : sp.getAttributeConsumingServiceIndex()%></td>
            <td width="10%"><a title="Edit Service Providers"
                               onclick="edit('<%=sp.getIssuer()%>');return false;" href="#"
                               class="icon-link"
                               style="background-image: url(../admin/images/edit.gif)">Edit</a>

            </td>
            <td width="10%"><a title="Remove Service Providers"
                               onclick="remove('<%=sp.getIssuer()%>');return false;" href="#"
                               class="icon-link"
                               style="background-image: url(../admin/images/delete.gif)">Delete
            </a></td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="4"><i><fmt:message
                    key="saml.sso.service.providers.not.found" /></i></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <script type="text/javascript">

        alternateTableRows('ServiceProviders', 'tableEvenRow',
                'tableOddRow');
    </script>
</form>

<br />
<%
    SAMLSSOServiceProviderDTO provider = null;
    String spAction = request.getParameter("SPAction");
    String claimTableStyle = "display:none";
    String issuer = request.getParameter("issuer");
    boolean isEditSP = false;
    if (spAction != null && "editServiceProvider".equals(spAction)) {
        if (providers.size() > 0) {
            for (SAMLSSOServiceProviderDTO sp : providers) {
                if (issuer.equals(sp.getIssuer())) {
                    isEditSP = true;
                    provider = sp;
                    claimTableStyle = provider.getRequestedClaims().length > 0 ? "" :"display:none";
                }
            }
        }
    }
%>

<form method="POST" action="add_service_provider.jsp?SPAction=<%=spAction%>"
      id="addServiceProvider" name="addServiceProvider" target="_self"
      onsubmit="return doValidation();">
<table class="styledLeft" width="100%">
<thead>
<tr>
    <%
        if (isEditSP) {
    %>
    <th><fmt:message key="saml.sso.edit.service.provider"/><%=provider.getIssuer()%>)</th>
    <%
    } else {
    %>
    <th><fmt:message key="saml.sso.add.service.provider"/></th>
    <%
        }
    %>
</tr>
</thead>
<tbody>
<tr>
<td class="formRow">
<table class="normal" cellspacing="0" style="width: 100%;">
<tr>
    <td style="width: 300px;">
        <fmt:message key="sp.issuer" />
        <font color="red">*</font>
    </td>
    <td><input type="text" id="issuer" name="issuer"
               class="text-box-big"
               value="<%=isEditSP? provider.getIssuer():""%>" <%=isEditSP ? "disabled=\"disabled\"" : ""%>/>
        <input type="hidden" id="hiddenIssuer" name="hiddenIssuer" value="<%=isEditSP? provider.getIssuer():""%>"/>
    </td>
</tr>
<tr>
    <td>
        <fmt:message key="sp.assertionConsumerURL" />
        <font color="red">*</font>
    </td>
    <td>
        <input type="text" id="assrtConsumerURL"
               name="assrtConsumerURL" class="text-box-big"
               value="<%=isEditSP?provider.getAssertionConsumerUrl():""%>" />
    </td>
</tr>

<!-- UseFullQualifiedUsername -->

<tr>
    <td colspan="2">
        <input type="checkbox" name="useFullQualifiedUsername" value="true"
                <%=(isEditSP && provider.getUseFullyQualifiedUsername()?"checked=\"checked\"":"")%> />
        <fmt:message key="use.fullqualified.username" />
    </td>
</tr>


<!-- EnableSignatureTrigger, FIXME it seems this config is not considered -->
<tr>
    <td colspan="2">
        <input type="checkbox" name="enableSignature" value="true"
               onclick="disableSignature(this);"
                <%=(isEditSP && provider.getDoSignAssertions() ? "checked=\"checked\"":"")%> />
            <%--<input type="hidden" name="enableSignature" value="true"/>--%>
        <fmt:message key="do.signature"/>
    </td>
</tr>

<!-- enableSigValidation -->
<% if(isEditSP && provider.isCertAliasSpecified()) {
%>
<tr>
    <td colspan="2">
        <input type="checkbox"
               name="enableSigValidation" value="true" checked="checked"
               onclick="disableCertAlias(this);" />
        <fmt:message
                key='validate.signature' />
    </td>
</tr>
<tr>
    <td style="padding-left: 40px ! important; color: rgb(119, 119, 119); font-style: italic;">
        <fmt:message key="sp.certAlias" />
    </td>
    <td><select id="alias" name="alias">
        <%
            if (aliasSet != null) {
                for (String alias : aliasSet) {
                    if (alias != null) {
                        if (alias.equals(provider.getCertAlias())) {
        %>
        <option selected="selected" value="<%=alias%>"><%=alias%>
        </option>
        <%
        } else {
        %>
        <option value="<%=alias%>"><%=alias%>
        </option>
        <%
                        }
                    }
                }
            }
        %>
    </select></td>
</tr>
<% } else {%>
<tr>
    <td colspan="2">
        <input type="checkbox"
               name="enableSigValidation" value="true"
               onclick="disableCertAlias(this);" />
        <fmt:message
                key='validate.signature' />
    </td>
</tr>
<tr>
    <td style="padding-left: 40px ! important; color: rgb(119, 119, 119); font-style: italic;">
        <fmt:message key="sp.certAlias" />
    </td>
    <td><select id="alias" name="alias" disabled="disabled">
        <%
            if (aliasSet != null) {
                for (String alias : aliasSet) {
                    if (alias != null) {
                        if (alias.equals(samlSsoServuceProviderConfigBean.getCertificateAlias())) {
        %>
        <option selected="selected" value="<%=alias%>"><%=alias%>
        </option>
        <%
        } else {
        %>
        <option value="<%=alias%>"><%=alias%>
        </option>
        <%
                        }
                    }
                }
            }
        %>
    </select></td>
</tr>
<%}%>

<!-- EnableSingleLogout -->
<%
    if(isEditSP && provider.getDoSingleLogout()){
%>
<tr>
    <td colspan="2"><input type="checkbox"
                           name="enableSingleLogout" value="true"
                           onclick="disableLogoutUrl(this);" checked="checked" /> <fmt:message
            key="enable.single.logout" /></td>
</tr>
<tr>
    <td
            style="padding-left: 40px ! important; color: rgb(119, 119, 119); font-style: italic;">
        <fmt:message key="logout.url" />
    </td>
    <td><input type="text" id="logoutURL" name="logoutURL"
               value="<%=provider.getLogoutURL()%>"
               class="text-box-big"></td>
</tr>
<% } else {%>
<tr>
    <td colspan="2"><input type="checkbox"
                           name="enableSingleLogout" value="true"
                           onclick="disableLogoutUrl(this);" /> <fmt:message
            key="enable.single.logout" /></td>
</tr>
<tr>
    <td
            style="padding-left: 40px ! important; color: rgb(119, 119, 119); font-style: italic;">
        <fmt:message key="logout.url" />
    </td>
    <td><input type="text" id="logoutURL" name="logoutURL"
               value="<%=samlSsoServuceProviderConfigBean.getSingleLogoutUrl()%>"
               class="text-box-big" disabled="disabled"></td>
</tr>
<% } %>

<!-- EnableAttributeProfile -->
<% if(isEditSP && provider.getRequestedClaims().length > 0 && provider.getRequestedClaims()[0] != null){
%>
<tr>
    <td colspan="2"><input type="checkbox"
                           name="enableAttributeProfile" id="enableAttributeProfile" value="true" checked="checked"
                           onclick="disableAttributeProfile(this);" /> <fmt:message
            key="enable.attribute.profile" /></td>
</tr>
<tr>
    <td
            style="padding-left: 40px ! important; color: rgb(119, 119, 119); font-style: italic;">
        <fmt:message key="sp.claim" />
    </td>
    <td>
        <select id="claim" name="claim">
            <%
                if (claimUris != null) {
                    for (String claimUri : claimUris) {
                        if (claimUri != null) {
            %>
            <option value="<%=claimUri%>"><%=claimUri%></option>
            <%
                        }
                    }
                }
            %>
        </select> <input id="addClaims" name="addClaims" type="button"
                         value="<fmt:message key="saml.sso.add.claim"/>"
                         onclick="addClaim()" />
    </td>
</tr>
<% } else {%>
<tr>
    <td colspan="2">
        <input type="checkbox"
               name="enableAttributeProfile" id="enableAttributeProfile" value="true"
               onclick="disableAttributeProfile(this);" />
        <fmt:message key="enable.attribute.profile" />
    </td>
</tr>
<tr>
    <td
            style="padding-left: 40px ! important; color: rgb(119, 119, 119); font-style: italic;">
        <fmt:message key="sp.claim" />
    </td>
    <td>
        <select id="claim" name="claim" disabled="disabled">
            <%
                if (claimUris != null) {
                    for (String claimUri : claimUris) {
                        if (claimUri != null) {
            %>
            <option value="<%=claimUri%>"><%=claimUri%></option>
            <%
                        }
                    }
                }
            %>
        </select> <input id="addClaims" name="addClaims" type="button"
                         disabled="disabled" value="<fmt:message key="saml.sso.add.claim"/>"
                         onclick="addClaim()" />
    </td>
</tr>
<%} %>
<tr>
    <td>
        <table id="claimTableId" style="<%=claimTableStyle%>" class="styledInner">
            <tbody id="claimTableTbody">
            <%
                int i = 0;
                if (isEditSP && provider.getRequestedClaims().length > 0) {
            %>
            <%
                for (String claim : provider.getRequestedClaims()) {
                    if(claim != null && !"null".equals(claim)){
            %>
            <tr id="claimRow<%=i%>">
                <td style="padding-left: 40px ! important; color: rgb(119, 119, 119); font-style: italic;">
                    <input type="hidden" name="claimPropertyName<%=i%>" id="claimPropertyName<%=i%>" value="<%=claim%>"/><%=claim%></td>
                <td>
                    <a onclick="removeClaim('<%=i%>');return false;"
                       href="#" class="icon-link" style="background-image: url(../admin/images/delete.gif)">Delete
                    </a>
                </td>
            </tr>
            <%
                        i++;
                    }
                }
            %>
            <%
                }
            %>
            <input type="hidden" name="claimPropertyCounter" id="claimPropertyCounter" value="<%=i%>"/>
            </tbody>
        </table>
    </td>
</tr>
</table>
</td>
</tr>
<tr>
    <td class="buttonRow">
        <%
            if (isEditSP) {
        %>
        <input class="button" type="submit" value="<fmt:message key="saml.sso.edit"/>"/>
        <%
        } else {
        %>
        <input class="button" type="submit" value="<fmt:message key="saml.sso.add"/>"/>
        <%
            }
        %>
        <input class="button" type="button" onclick="clearAll()"
               value="<fmt:message key="saml.sso.clear"/>"/>
    </td>
</tr>
</tbody>
</table>
</form>
</div>
</div>
</fmt:bundle>