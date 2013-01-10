package org.wso2.carbon.identity.oauth2.dto;


public class OAuth2UserInfoReqDTO {

    private String clientId;

    private String accessToken;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
