package org.wso2.carbon.identity.oauth.ui.endpoints.userinfo;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.apache.cxf.jaxrs.utils.ResourceUtils;
import org.wso2.carbon.identity.oauth.ui.endpoints.token.OAuth2EndpointApp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;


public class OAuth2UserInfoEndpointServlet extends CXFNonSpringJaxrsServlet {
    @Override
    protected void createServerFromApplication(String cName, ServletConfig servletConfig) throws ServletException {
        OAuth2UserInfoEndpointApp app = new OAuth2UserInfoEndpointApp();
        JAXRSServerFactoryBean bean = ResourceUtils.createApplication(app, true);
        bean.create();
    }
}
