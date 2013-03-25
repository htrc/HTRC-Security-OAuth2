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

import org.apache.amber.oauth2.client.response.OAuthClientResponse;
import org.apache.amber.oauth2.client.validator.OAuthClientValidator;
import org.apache.amber.oauth2.client.validator.TokenValidator;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.utils.JSONUtils;
import org.codehaus.jettison.json.JSONException;

public class OAuthUserInfoResponse extends OAuthClientResponse{
    @Override
    protected void setBody(String s) throws OAuthProblemException {
        this.body = s;
        System.out.println(s);
        try {
            this.parameters = JSONUtils.parseJSON(s);
        } catch (JSONException e) {
            System.out.println(s);
            throw OAuthProblemException.error(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE,
                    "Invalid response! Response body is not " + OAuth.ContentType.JSON + " encoded");
        }
    }

    @Override
    protected void setContentType(String s) {
        this.contentType = s;
    }

    @Override
    protected void setResponseCode(int i) {
        this.responseCode = i;
    }

    public String getUser(){
        return (String)parameters.get("registered_user");
    }

    @Override
    protected void init(String body, String contentType, int responseCode) throws OAuthProblemException {
        validator = new UserInfoValidator();
        super.init(body, contentType, responseCode);
    }

    public class UserInfoValidator extends OAuthClientValidator{
        public UserInfoValidator(){
        }
    }
}
