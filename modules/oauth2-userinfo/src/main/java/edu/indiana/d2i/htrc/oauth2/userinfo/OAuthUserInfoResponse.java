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

