<%@ page import="org.wso2.carbon.identity.oauth.ui.OAuthConstants" %>
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
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar" prefix="carbon" %>
<%
    String errorCode = (String) session.getAttribute(OAuthConstants.OAUTH_ERROR_CODE);
    String errorMsg = (String) session.getAttribute(OAuthConstants.OAUTH_ERROR_MESSAGE);
    // Server Error, invalidate the session.
    session.invalidate();
%>
<fmt:bundle
        basename="org.wso2.carbon.identity.oauth.ui.i18n.Resources">
    <div id="middle">
        <h2><fmt:message key='oauth.processing.error'/></h2>

        <div id="workArea">
            <table>
                <% if (errorCode != null && errorMsg != null) { %>
                <tr>
                    <td><b><%= errorCode%>
                    </b></td>
                </tr>
                <tr>
                    <td><%=errorMsg%>
                    </td>
                </tr>
                <%} else { %>
                <tr>
                    <td>
                        <fmt:message key='oauth.processing.error.msg'/>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>
    </div>
</fmt:bundle>