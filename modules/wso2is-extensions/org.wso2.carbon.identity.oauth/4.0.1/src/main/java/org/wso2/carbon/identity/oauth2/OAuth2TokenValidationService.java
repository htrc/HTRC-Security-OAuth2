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

package org.wso2.carbon.identity.oauth2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.core.AbstractAdmin;
import org.wso2.carbon.identity.oauth2.dto.OAuth2TokenValidationRequestDTO;
import org.wso2.carbon.identity.oauth2.dto.OAuth2TokenValidationResponseDTO;
import org.wso2.carbon.identity.oauth2.validators.TokenValidationHandler;

/**
 * This is the SOAP version of the OAuth validation service which will be used by the
 * resource server.
 */
public class OAuth2TokenValidationService extends AbstractAdmin {

    private static Log log = LogFactory.getLog(OAuth2TokenValidationService.class);

    public OAuth2TokenValidationResponseDTO validate(
            OAuth2TokenValidationRequestDTO validationReqDTO) {

        if (log.isDebugEnabled()) {
            log.debug("Token validation request received for : " +
                    "Client Id : " + validationReqDTO.getClientId() +
                    "Token Type : " + validationReqDTO.getTokenType());
        }

        TokenValidationHandler validationHandler = TokenValidationHandler.getInstance();

        try {
            return validationHandler.validate(validationReqDTO);
        } catch (IdentityOAuth2Exception e) {
            log.error("Error when validating the token. ", e);
            OAuth2TokenValidationResponseDTO errRespDTO = new OAuth2TokenValidationResponseDTO();
            errRespDTO.setValid(false);
            errRespDTO.setErrorMsg("Server Error when validating the token.");
            return errRespDTO;
        }
    }
}
