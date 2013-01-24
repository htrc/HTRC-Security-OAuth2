/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/

package edu.indiana.d2i.htrc.oauth2.filter;

import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.wso2.carbon.identity.oauth.stub.OAuth2TokenValidationServiceStub;
import org.wso2.carbon.identity.oauth2.dto.xsd.OAuth2TokenValidationRequestDTO;
import org.wso2.carbon.identity.oauth2.dto.xsd.OAuth2TokenValidationResponseDTO;
import org.wso2.carbon.utils.CarbonUtils;

import javax.net.ssl.*;
import javax.servlet.ServletException;
import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class OAuth2ServiceClient {
    private OAuth2TokenValidationServiceStub stub;

    private static final int TIMEOUT_IN_MILLIS = 15 * 60 * 1000;
    public static final String OAUTH2_PROVIDER_URL = "https://localhost:9443/services/";
    public static final String OAUTH2_PROVIDER_USER_NAME = "admin";
    public static final String OAUTH2_PROVIDER_PASSWORD = "admin";

    public OAuth2ServiceClient(String providerUrl, String userName, String password) throws AxisFault {
        String serviceURL = providerUrl + "OAuth2TokenValidationService";
        stub = new OAuth2TokenValidationServiceStub(null, serviceURL);
        CarbonUtils.setBasicAccessSecurityHeaders(userName, password, true, stub._getServiceClient());
        ServiceClient client = stub._getServiceClient();
        Options options = client.getOptions();
        options.setTimeOutInMilliSeconds(TIMEOUT_IN_MILLIS);
        options.setProperty(HTTPConstants.SO_TIMEOUT, TIMEOUT_IN_MILLIS);
        options.setProperty(HTTPConstants.CONNECTION_TIMEOUT, TIMEOUT_IN_MILLIS);
        options.setCallTransportCleanup(true);
        options.setManageSession(true);
    }

    public void validateAuthenticationRequest(OAuth2TokenValidationRequestDTO params)
            throws OAuthProblemException, RemoteException {
        OAuth2TokenValidationResponseDTO resp = stub.validate(params);
        if(!resp.getValid()){
            throw  OAuthProblemException.error(OAuthError.ResourceResponse.INVALID_TOKEN);
        }
    }
}
