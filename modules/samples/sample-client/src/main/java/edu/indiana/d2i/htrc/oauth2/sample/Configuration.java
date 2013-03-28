package edu.indiana.d2i.htrc.oauth2.sample;

import org.apache.amber.oauth2.common.message.types.GrantType;

public class Configuration {
    private String accessTokenURL;

    private String userInfoURL;

    private String clientId;

    private String clientSecret;

    private String userName;

    private String userPassword;

    private GrantType grantType;

    private boolean isProduction;

    public String getAccessTokenURL() {
        return accessTokenURL;
    }

    public void setAccessTokenURL(String accessTokenURL) {
        this.accessTokenURL = accessTokenURL;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public GrantType getGrantType(){
        return grantType;
    }

    public void setGrantType(GrantType grantType){
        this.grantType = grantType;
    }

    public boolean isProduction() {
        return isProduction;
    }

    public void setProduction(boolean production) {
        isProduction = production;
    }

    public String getUserInfoURL() {
        return userInfoURL;
    }

    public void setUserInfoURL(String userInfoURL) {
        this.userInfoURL = userInfoURL;
    }
}
