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


import org.apache.amber.oauth2.client.HttpClient;
import org.apache.amber.oauth2.client.OAuthClient;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;

import java.util.HashMap;
import java.util.Map;

public class OAuth2Client extends OAuthClient {
    public OAuth2Client(HttpClient oauthClient) {
        super(oauthClient);
    }

    /**
     * Does the reverse lookup for OAuth tokens. You can get the user's information by giving access token.
     * @param request
     * @return
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    public OAuthUserInfoResponse userInfo(OAuthClientRequest request) throws OAuthProblemException, OAuthSystemException {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(OAuth.HeaderType.CONTENT_TYPE, OAuth.ContentType.URL_ENCODED);

        return httpClient.execute(request, headers, OAuth.HttpMethod.POST, OAuthUserInfoResponse.class);
    }
}