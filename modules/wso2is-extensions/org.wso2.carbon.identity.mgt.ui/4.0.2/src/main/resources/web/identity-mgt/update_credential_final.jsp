<!--
~ Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
<%@ page import="org.wso2.carbon.identity.mgt.ui.IdentityManagementClient" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.captcha.mgt.beans.xsd.CaptchaInfoBean" %>
<%@ page import="org.wso2.carbon.identity.mgt.stub.beans.UserMgtBean" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://wso2.org/projects/carbon/taglibs/carbontags.jar" prefix="carbon" %>
<carbon:jsi18n
        resourceBundle="org.wso2.carbon.identity.mgt.ui.i18n.Resources"
        request="<%=request%>"/>
<%
    try {
        String backendServerURL = CarbonUIUtil.getServerURL(config.getServletContext(),
                session);
        ConfigurationContext configContext = (ConfigurationContext) config
                .getServletContext()
                .getAttribute(CarbonConstants.CONFIGURATION_CONTEXT);
        IdentityManagementClient client =
                new IdentityManagementClient(backendServerURL, configContext);
        CaptchaInfoBean captchaInfoBean = new CaptchaInfoBean();
        UserMgtBean userMgtBean = new UserMgtBean();
        
        captchaInfoBean.setSecretKey(request.getParameter("captcha-secret-key"));
        captchaInfoBean.setUserAnswer(request.getParameter("captcha-user-answer"));
        userMgtBean.setUserId(request.getParameter("userName"));
        userMgtBean.setSecretKey(request.getParameter("secretKey"));
        userMgtBean.setUserPassword(request.getParameter("userPassword"));
        boolean  isCredentialsUpdated = client.updateCredential(userMgtBean, captchaInfoBean);

        if (isCredentialsUpdated) {
            session.setAttribute("update-credentials-success", "true");

            response.sendRedirect("../identity-mgt/success_update.jsp");
        } else {
            session.setAttribute("update-credentials-failed", "true");
            response.sendRedirect("../identity-mgt/update_credential.jsp");
        }

    } catch (Exception e) {
        response.sendRedirect("../identity-mgt/update_credential.jsp");
        return;
    }
%>