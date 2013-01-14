package org.wso2.carbon.identity.oauth.ui.endpoints.userinfo;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth.ui.OAuthClientException;
import org.wso2.carbon.identity.oauth.ui.client.OAuth2ServiceClient;
import org.wso2.carbon.identity.oauth.ui.internal.OAuthUIServiceComponentHolder;
import org.wso2.carbon.identity.oauth2.dto.OAuth2UserInfoRequestDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2UserInfoResponseDTO;
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

        userInfoReqDTO.setClientId(oauthRequest.getClientID());
        userInfoReqDTO.setClientSecret(oauthRequest.getClientSecret());
        userInfoReqDTO.setAccessToken(oauthRequest.getAccessToken());

        try {
            OAuth2ServiceClient oauth2ServiceClient = new OAuth2ServiceClient(backendServerURL,
                    configContext);
            return oauth2ServiceClient.issueUserInfo(userInfoReqDTO);
        } catch (Exception e){
            String errorMsg = "Error when invoking the OAuth2Service to get an access token.";
            log.error(errorMsg, e);
            throw new OAuthClientException(errorMsg, e);
        }
    }
}

