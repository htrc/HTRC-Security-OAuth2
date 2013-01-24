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

package org.wso2.carbon.identity.oauth.ui.client;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.identity.oauth.ui.internal.OAuthUIServiceComponentHolder;
import org.wso2.carbon.identity.oauth2.OAuth2Service;
import org.wso2.carbon.identity.oauth2.ResponseHeader;
import org.wso2.carbon.identity.oauth2.dto.OAuth2UserInfoReqDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2UserInfoRespDTO;
import org.wso2.carbon.identity.oauth2.stub.OAuth2ServiceStub;
import org.wso2.carbon.identity.oauth2.stub.dto.*;

import java.rmi.RemoteException;

public class OAuth2ServiceClient {

    private OAuth2ServiceStub stub;
    private OAuth2Service oauth2Service;
    private boolean wsMode;


    public OAuth2ServiceClient(String backendServerURL, ConfigurationContext configCtx)
            throws AxisFault {

        OMElement wsModeOM;
        try {
            wsMode = Boolean.parseBoolean(IdentityUtil.getProperty("SeparateBackEnd"));
        } catch (Exception e) {
            //ignore exception this means that WSMode element is not present
        }

        if (!wsMode) {
            oauth2Service = OAuthUIServiceComponentHolder.getInstance().getOAuth2Service();
        }
        if (wsMode) {
            String serviceURL = backendServerURL + "OAuth2Service";
            stub = new OAuth2ServiceStub(configCtx, serviceURL);
        }

    }

    public OAuth2AuthorizeRespDTO authorize(OAuth2AuthorizeReqDTO authorizeReqDTO)
            throws RemoteException {
        if (wsMode) {
            return stub.authorize(authorizeReqDTO);
        }
        return _authorize(authorizeReqDTO);
    }


    private OAuth2AuthorizeRespDTO _authorize(OAuth2AuthorizeReqDTO authorizeReqDTO)
            throws RemoteException {
        org.wso2.carbon.identity.oauth2.dto.OAuth2AuthorizeReqDTO oauthDTO = new org.wso2.carbon.identity.oauth2.dto.OAuth2AuthorizeReqDTO();

        oauthDTO.setCallbackUrl(authorizeReqDTO.getCallbackUrl());
        oauthDTO.setConsumerKey(authorizeReqDTO.getConsumerKey());
        oauthDTO.setPassword(authorizeReqDTO.getPassword());
        oauthDTO.setResponseType(authorizeReqDTO.getResponseType());
        oauthDTO.setScopes(authorizeReqDTO.getScopes());
        oauthDTO.setUsername(authorizeReqDTO.getUsername());

        org.wso2.carbon.identity.oauth2.dto.OAuth2AuthorizeRespDTO resp = oauth2Service.authorize(oauthDTO);

        OAuth2AuthorizeRespDTO returnDTO = new OAuth2AuthorizeRespDTO();

        returnDTO.setAccessToken(resp.getAccessToken());
        returnDTO.setAuthenticated(resp.isAuthenticated());
        returnDTO.setAuthorizationCode(resp.getAuthorizationCode());
        returnDTO.setAuthorized(resp.isAuthorized());
        returnDTO.setCallbackURI(resp.getCallbackURI());
        returnDTO.setErrorCode(resp.getErrorCode());
        returnDTO.setErrorMsg(resp.getErrorMsg());
        returnDTO.setScope(resp.getScope());
        returnDTO.setValidityPeriod(resp.getValidityPeriod());

        return returnDTO;
    }

    public OAuth2ClientValidationResponseDTO validateClient(String clientId, String callbackURI)
            throws RemoteException {
        if (wsMode) {
            return stub.validateClientInfo(clientId, callbackURI);
        }
        return _validateClient(clientId, callbackURI);
    }


    private OAuth2ClientValidationResponseDTO _validateClient(String clientId, String callbackURI)
            throws RemoteException {
        org.wso2.carbon.identity.oauth2.dto.OAuth2ClientValidationResponseDTO validationRespDTO = oauth2Service.validateClientInfo(clientId, callbackURI);

        OAuth2ClientValidationResponseDTO responseDTO = new OAuth2ClientValidationResponseDTO();
        responseDTO.setApplicationName(validationRespDTO.getApplicationName());
        responseDTO.setCallbackURL(validationRespDTO.getCallbackURL());
        responseDTO.setErrorCode(validationRespDTO.getErrorCode());
        responseDTO.setErrorMsg(validationRespDTO.getErrorMsg());
        responseDTO.setValidClient(validationRespDTO.isValidClient());

        return responseDTO;
    }


    public OAuth2AccessTokenRespDTO issueAccessToken(OAuth2AccessTokenReqDTO tokenReqDTO)
            throws RemoteException {
        if (wsMode) {
            return stub.issueAccessToken(tokenReqDTO);
        }
        return _issueAccessToken(tokenReqDTO);

    }

    private OAuth2AccessTokenRespDTO _issueAccessToken(OAuth2AccessTokenReqDTO tokenReqDTO)
            throws RemoteException {
        org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenReqDTO areqDTO = new org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenReqDTO();

        areqDTO.setAuthorizationCode(tokenReqDTO.getAuthorizationCode());
        areqDTO.setCallbackURI(tokenReqDTO.getCallbackURI());
        areqDTO.setClientId(tokenReqDTO.getClientId());
        areqDTO.setClientSecret(tokenReqDTO.getClientSecret());
        areqDTO.setGrantType(tokenReqDTO.getGrantType());
        areqDTO.setRefreshToken(tokenReqDTO.getRefreshToken());
        areqDTO.setResourceOwnerPassword(tokenReqDTO.getResourceOwnerPassword());
        areqDTO.setResourceOwnerUsername(tokenReqDTO.getResourceOwnerUsername());
        areqDTO.setAuthorizationCode(tokenReqDTO.getAuthorizationCode());
        areqDTO.setScope(tokenReqDTO.getScope());
        areqDTO.setAssertion(tokenReqDTO.getAssertion());

        org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenRespDTO resp = oauth2Service.issueAccessToken(areqDTO);

        OAuth2AccessTokenRespDTO respDTO = new OAuth2AccessTokenRespDTO();

        respDTO.setAccessToken(resp.getAccessToken());
        respDTO.setCallbackURI(resp.getCallbackURI());
        respDTO.setErrorCode(resp.getErrorCode());
        respDTO.setErrorMsg(resp.getErrorMsg());
        respDTO.setExpiresIn(resp.getExpiresIn());
        respDTO.setRefreshToken(resp.getRefreshToken());
        respDTO.setError(resp.isError());
        respDTO.setTokenType(resp.getTokenType());

        org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader[] headers = new org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader[resp.getRespHeaders().length];
        respDTO.setRespHeaders(headers);

        for (int i = 0; i < resp.getRespHeaders().length; i++) {
            ResponseHeader[] rhr = resp.getRespHeaders();
            org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader h = new org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader();
            ResponseHeader rh = rhr[i];
            h.setKey(rh.getKey());
            h.setValue(rh.getValue());
            headers[i] = h;
        }

        respDTO.setRespHeaders(headers);
        return respDTO;
    }

    public org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoRespDTO getUserInfo(org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoReqDTO userInfoReqDTO)
            throws RemoteException {
        if (wsMode) {
            return stub.getUserInformation(userInfoReqDTO);
        }
        return _getUserInfo(userInfoReqDTO);

    }

    private org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoRespDTO _getUserInfo(org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoReqDTO userInfoReqDTO)
            throws RemoteException {
        OAuth2UserInfoReqDTO ureqDTO = new OAuth2UserInfoReqDTO();

        ureqDTO.setClientId(userInfoReqDTO.getClientId());
        ureqDTO.setClientSecret(userInfoReqDTO.getClientSecret());
        ureqDTO.setAccessToken(userInfoReqDTO.getAccessToken());

        OAuth2UserInfoRespDTO resp = oauth2Service.getUserInformation(ureqDTO);

        org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoRespDTO respDTO = new org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoRespDTO();

        respDTO.setAuthorizedUser(resp.getAuthorizedUser());
        respDTO.setErrorMsg(resp.getErrorMsg());
        respDTO.setError(resp.isError());
        respDTO.setErrorCode(resp.getErrorCode());

       return respDTO;
    }

}
