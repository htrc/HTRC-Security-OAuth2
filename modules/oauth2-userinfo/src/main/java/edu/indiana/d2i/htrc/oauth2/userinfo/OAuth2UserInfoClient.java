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

import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth.ui.OAuthClientException;
import org.wso2.carbon.identity.oauth.ui.client.OAuth2ServiceClient;
import org.wso2.carbon.identity.oauth.ui.internal.OAuthUIServiceComponentHolder;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoReqDTO;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoRespDTO;
import org.wso2.carbon.ui.CarbonUIUtil;


public class OAuth2UserInfoClient {
    private static Log log = LogFactory.getLog(OAuth2UserInfoClient.class);

    private String backendServerURL;
    private ConfigurationContext configContext;

    public OAuth2UserInfoClient() {
        OAuthUIServiceComponentHolder serviceComponentHolder = OAuthUIServiceComponentHolder.
                getInstance();
        backendServerURL = CarbonUIUtil.getServerURL(
                serviceComponentHolder.getServerConfigurationService());
        configContext = serviceComponentHolder.
                getConfigurationContextService().getServerConfigContext();
    }

    public OAuth2UserInfoRespDTO getUserInfo(OAuthUserInfoRequest oauthRequest)
            throws OAuthClientException {
        OAuth2UserInfoReqDTO userInfoReqDTO = new OAuth2UserInfoReqDTO();

        userInfoReqDTO.setClientId(oauthRequest.getClientId());
        userInfoReqDTO.setClientSecret(oauthRequest.getClientSecret());
        userInfoReqDTO.setAccessToken(oauthRequest.getAccessToken());

        try {
            OAuth2ServiceClient oauth2ServiceClient = new OAuth2ServiceClient(backendServerURL,
                    configContext);
            return oauth2ServiceClient.getUserInfo(userInfoReqDTO);
        } catch (Exception e){
            String errorMsg = "Error when invoking the OAuth2Service to get an access token.";
            log.error(errorMsg, e);
            throw new OAuthClientException(errorMsg, e);
        }
    }
}

