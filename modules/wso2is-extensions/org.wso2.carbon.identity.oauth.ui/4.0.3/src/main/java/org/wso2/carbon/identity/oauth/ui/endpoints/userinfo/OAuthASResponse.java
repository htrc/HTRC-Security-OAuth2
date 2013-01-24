package org.wso2.carbon.identity.oauth.ui.endpoints.userinfo;


import javax.ws.rs.core.Response;

public class OAuthASResponse extends OAuthResponse  {
    protected OAuthASResponse(String uri, int responseStatus) {
        super(uri, responseStatus);
    }

    public static OAuthASResponse.OAuthUserInfoResponseBuilder userInfoResponse(int code) {

        return null;
    }

    public static OAuthUserInfoResponseBuilder userInfoResponse() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static class OAuthUserInfoResponseBuilder extends OAuthResponseBuilder {
        public OAuthUserInfoResponseBuilder(int responseCode) {
            super(responseCode);
        }

        public OAuthUserInfoResponseBuilder setUserInfo(java.lang.String authorizedUser) {
            return null;
        }



    }
}

