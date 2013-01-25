package htrc.security.oauth2.client;

import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.common.OAuth;

public class OAuthUserInfoRequest extends OAuthClientRequest {

    protected OAuthUserInfoRequest(String url) {
        super(url);
    }

    public static UserInfoRequestBuilder userInfoLocation(String url){
        return new UserInfoRequestBuilder(url);
    }


    public static class UserInfoRequestBuilder extends OAuthClientRequest.OAuthRequestBuilder{

        public UserInfoRequestBuilder(String url) {
            super(url);
        }


        public UserInfoRequestBuilder setClientId(String clientId){
            this.parameters.put(OAuth.OAUTH_CLIENT_ID, clientId);
            return this;
        }

        public UserInfoRequestBuilder setClientSecret(String clientSecret){
            this.parameters.put(OAuth.OAUTH_CLIENT_SECRET, clientSecret);
            return this;
        }

        public UserInfoRequestBuilder setAccessToken(String accessToken){
            this.parameters.put(OAuth.OAUTH_ACCESS_TOKEN, accessToken);
            return this;
        }
    }
}