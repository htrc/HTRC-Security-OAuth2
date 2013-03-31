/*
*Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.carbon.identity.oauth2.model;

import org.wso2.carbon.caching.core.CacheEntry;

import java.sql.Timestamp;

public class AccessTokenDO extends CacheEntry {

    private static final long serialVersionUID = -8123522530178387354L;

    private String authzUser;

    private String userFullName;

    private  String userEmail;

    private String clientId;

    private String appName;

    private String[] scope;

    private String tokenState;

    private String refreshToken;

    private String accessToken;

    private Timestamp issuedTime;

    private long validityPeriod;

    public AccessTokenDO(String authzUser, String clientId, String appName, String[] scope, Timestamp issuedTime, long validityPeriod) {
        this.authzUser = authzUser;
        this.clientId = clientId;
        this.appName = appName;
        this.scope = scope;
        this.issuedTime = issuedTime;
        this.validityPeriod = validityPeriod;

    }

    public void setIssuedTime(Timestamp issuedTime) {
        this.issuedTime = issuedTime;
    }

    public void setValidityPeriod(long validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getAuthzUser() {
        return authzUser;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAppName() {
        return appName;
    }

    public String[] getScope() {
        return scope;
    }

    public void setScope(String[] scope) {
        this.scope = scope;
    }

    public Timestamp getIssuedTime() {
        return issuedTime;
    }

    public long getValidityPeriod() {
        return validityPeriod;
    }

    public String getUserFullName() {
        return userFullName;
    }


    public void setUserFullName(String userFullName){
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public String getTokenState() {
        return tokenState;
    }

    public void setTokenState(String tokenState) {
        this.tokenState = tokenState;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
