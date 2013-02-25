package edu.indiana.d2i.htrc.oauth2.userinfo;


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

        public OAuthUserInfoResponseBuilder setUserFullName(java.lang.String userFullName){
            this.parameters.put("user_fullname" , userFullName);
            return this;
        }

        public OAuthUserInfoResponseBuilder setUserEmail(java.lang.String userEmail){
            this.parameters.put("user_email" , userEmail);
            return this;
        }

    }
}

