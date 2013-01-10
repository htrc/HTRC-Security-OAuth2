package org.wso2.carbon.identity.oauth.mediator;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth2.stub.OAuth2TokenValidationServiceStub;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationRequestDTO;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationResponseDTO;
import org.wso2.carbon.utils.CarbonUtils;

public class OAuth2TokenValidationServiceClient {
	
	private OAuth2TokenValidationServiceStub stub = null;
	private static final Log log = LogFactory.getLog(OAuth2TokenValidationServiceClient.class);
	
	/**
	 * OAuth2TokenValidationService Admin Service Client
	 * @param backendServerURL
	 * @param username
	 * @param password
	 * @param configCtx
	 * @throws Exception
	 */
	public OAuth2TokenValidationServiceClient(String backendServerURL, String username, String password, ConfigurationContext configCtx) throws Exception{
		String serviceURL = backendServerURL + "OAuth2TokenValidationService";
		try {
	        stub = new OAuth2TokenValidationServiceStub(configCtx, serviceURL);
	        CarbonUtils.setBasicAccessSecurityHeaders(username, password, true, stub._getServiceClient());
        } catch (AxisFault e) {
        	log.error("Error initializing OAuth2 Client");
			throw new Exception("Error initializing OAuth Client", e);
        }
	}
	
	/**
	 * Validates the OAuth 2.0 request
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public boolean validateAuthenticationRequest(String accessToken) throws Exception{
		OAuth2TokenValidationRequestDTO  oauthReq = new OAuth2TokenValidationRequestDTO();    
	    oauthReq.setAccessToken(accessToken);
	    oauthReq.setTokenType(OAuthConstants.BEARER_TOKEN_TYPE);
		try {
	        OAuth2TokenValidationResponseDTO oauthResp = stub.validate(oauthReq);
	        return oauthResp.getValid();
        } catch (RemoteException e) {
        	log.error("Error while validating OAuth2 request");
			throw new Exception("Error while validating OAuth2 request", e);
        }
	}

}
