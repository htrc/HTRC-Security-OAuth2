package org.wso2.carbon.identity.oauth.endpoint.userinfo;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


public class OAuth2UserInfoEndpointApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(OAuth2UserInfoEndpoint.class);
        return s;
    }
}
