/*
*
* Copyright 2013 The Trustees of Indiana University
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/

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