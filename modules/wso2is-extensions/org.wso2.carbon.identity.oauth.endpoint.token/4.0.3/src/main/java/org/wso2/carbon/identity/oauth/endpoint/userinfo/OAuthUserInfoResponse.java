package org.wso2.carbon.identity.oauth.endpoint.userinfo;


import org.apache.amber.oauth2.common.message.OAuthResponse;

public class OAuthUserInfoResponse extends OAuthResponse {
    protected OAuthUserInfoResponse(String uri, int responseStatus) {
        super(uri, responseStatus);
    }

    public static OAuthUserInfoResponse.OAuthUserInfoResponseBuilder userInfoResponse(int code) {
        return new OAuthUserInfoResponseBuilder(code);
    }

    public static class OAuthUserInfoResponseBuilder extends OAuthResponseBuilder {
        public OAuthUserInfoResponseBuilder(int responseCode) {
            super(responseCode);
        }

        public OAuthUserInfoResponseBuilder setAuthroizedUser(java.lang.String authorizedUser) {
            this.parameters.put("authorized_user", authorizedUser);
            return this;
        }
    }
}

