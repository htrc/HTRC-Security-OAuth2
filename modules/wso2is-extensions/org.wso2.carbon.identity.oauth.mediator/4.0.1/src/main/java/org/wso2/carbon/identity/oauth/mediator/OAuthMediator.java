/*
 * Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.identity.oauth.mediator;

import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.synapse.transport.nhttp.NhttpConstants;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerDTO;
import org.wso2.carbon.identity.oauth.stub.types.Parameters;

public class OAuthMediator extends AbstractMediator {

	private static final Log log = LogFactory.getLog(OAuthMediator.class);

	private boolean remote = true;
	private String remoteServiceUrl;
	private String username;
	private String password;

	ConfigurationContext cfgCtx = null;
	private String clientRepository = null;
	private String axis2xml = null;
	public final static String DEFAULT_CLIENT_REPO = "./samples/axis2Client/client_repo";
	public final static String DEFAULT_AXIS2_XML =
	                                               "./samples/axis2Client/client_repo/conf/axis2.xml";

	private org.apache.axis2.context.MessageContext msgContext;
	private String authHeader;
	private String quarryString;
	private boolean isOauth2;
	private String accessToken;

	public boolean isRemote() {
		return remote;
	}

	public void setRemote(boolean remote) {
		this.remote = remote;
	}

	public String getRemoteServiceUrl() {
		if (remoteServiceUrl != null) {
			if (!remoteServiceUrl.endsWith("/")) {
				remoteServiceUrl += "/";
			}
		}
		return remoteServiceUrl;
	}

	public void setRemoteServiceUrl(String remoteServiceUrl) {
		this.remoteServiceUrl = remoteServiceUrl;
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(SynapseEnvironment synEnv) {
		try {
			cfgCtx =
			         ConfigurationContextFactory.createConfigurationContextFromFileSystem(clientRepository != null
			                                                                                                      ? clientRepository
			                                                                                                      : DEFAULT_CLIENT_REPO,
			                                                                              axis2xml != null
			                                                                                              ? axis2xml
			                                                                                              : DEFAULT_AXIS2_XML);
		} catch (AxisFault e) {
			String msg = "Error initializing callout mediator : " + e.getMessage();
			log.error(msg, e);
			throw new SynapseException(msg, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean mediate(MessageContext synCtx) {

		// checks if the message carries OAuth params
		validateRequest(synCtx);

		if (isOauth2) {
			return handleOAuth2(synCtx);
		} else {
			return handleOAuth1a(synCtx);
		}
	}

	/**
	 * Checks if the message contains Authorization header or quarry strings
	 * 
	 * @param synCtx
	 * @return
	 */
	private void validateRequest(MessageContext synCtx) {

		Axis2MessageContext axis2Msgcontext = (Axis2MessageContext) synCtx;
		msgContext = axis2Msgcontext.getAxis2MessageContext();
		Map headersMap =
		                 (Map) msgContext.getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
		authHeader = (String) headersMap.get("Authorization");
		quarryString = (String) msgContext.getProperty(NhttpConstants.REST_URL_POSTFIX);

		// if we can't find the auth header and querry string, prompt error
		if (authHeader == null && quarryString == null && !"".equals(quarryString)) {
			throw new SynapseException("Not a valid OAuth Request");
		}

		// checking for OAuth 2.0 params
		if (authHeader != null && authHeader.startsWith(OAuthConstants.BEARER)) {
			isOauth2 = true;
			accessToken = authHeader.substring(7).trim();
		} else if (quarryString != null && quarryString.contains(OAuthConstants.ACCESS_TOKEN)) {
			isOauth2 = true;
			String[] params = quarryString.substring(quarryString.indexOf("?") + 1).split("&");
			for (String param : params) {
				if (param.contains(OAuthConstants.ACCESS_TOKEN)) {
					accessToken = param.trim().substring(12).trim();
				}
			}
		}
		
		// not a valid oauth 2.0 request
		if (isOauth2 == true && accessToken == null) {
			throw new SynapseException("OAuth 2.0 access_token could not found in the request");
		}
	}

	/**
	 * Try to authenticate using OAuth
	 * 2.0queryString.contains(OAuthConstants.ACCESS_TOKEN)
	 * 
	 * @param synCtx
	 * @return
	 */
	private boolean handleOAuth2(MessageContext synCtx) {
		log.debug("Validating the OAuth 2.0 Request");
		boolean isAuthenticated;

		try {
			OAuth2TokenValidationServiceClient oauth2Client =
			                                                  new OAuth2TokenValidationServiceClient(
			                                                                                         getRemoteServiceUrl(),
			                                                                                         getUsername(),
			                                                                                         getPassword(),
			                                                                                         cfgCtx);
			isAuthenticated = oauth2Client.validateAuthenticationRequest(accessToken);
		} catch (Exception e) {
			log.error("Error occured while validating oauth access token", e);
			throw new SynapseException("Error occured while validating oauth 2.0 access token");
		}
		
		if(!isAuthenticated){
			throw new SynapseException("OAuth 2.0 authentication failed");
		}
		
		return true;
	}

	/**
	 * Try to authenticate using OAuth 1.0a.
	 * 
	 * @param synCtx
	 * @return
	 */
	private boolean handleOAuth1a(MessageContext synCtx) {
		log.debug("Validating the OAuth 1.0a Request");

		OAuthServiceClient client = null;
		ConfigurationContext configContext = null;
		OAuthConsumerDTO consumer = null;
		boolean isValidConsumer = false;

		try {

			Parameters params = populateOauthConsumerData(synCtx);
			client = new OAuthServiceClient(getRemoteServiceUrl(), configContext);

			if (params != null && params.getOauthToken() == null) {
				consumer = new OAuthConsumerDTO();
				consumer.setBaseString(params.getBaseString());
				consumer.setHttpMethod(params.getHttpMethod());
				consumer.setOauthConsumerKey(params.getOauthConsumerKey());
				consumer.setOauthNonce(params.getOauthNonce());
				consumer.setOauthSignature(params.getOauthSignature());
				consumer.setOauthSignatureMethod(params.getOauthSignatureMethod());
				consumer.setOauthTimeStamp(params.getOauthTimeStamp());
				isValidConsumer = client.isOAuthConsumerValid(consumer);
			} else {
				isValidConsumer = client.validateAuthenticationRequest(params);

			}

			if (!isValidConsumer) {
				throw new SynapseException("OAuth authentication failed");
			} else {
				return true;
			}

		} catch (Exception e) {
			log.error("Error occured while validating oauth consumer", e);
			throw new SynapseException("Error occured while validating oauth consumer");
		}

	}

	/**
	 * Populates the Parameters object from the OAuth authorization header or
	 * query string.
	 * 
	 * @param synCtx
	 * @return
	 */
	private Parameters populateOauthConsumerData(MessageContext synCtx) {

		Parameters params = null;
		String splitChar = ",";
		boolean noAuthorizationHeader = false;

		params = new Parameters();
		String operation = null;

		if (quarryString.indexOf("?") > -1) {
			String temp = quarryString;
			quarryString = quarryString.substring(quarryString.indexOf("?") + 1);
			operation = temp.substring(0, temp.indexOf("?") + 1);
		}

		if (authHeader == null) {
			noAuthorizationHeader = true;
			// No Authorization header available.
			authHeader = quarryString;
			splitChar = "&";
		}

		StringBuffer nonAuthParams = new StringBuffer();

		if (authHeader != null) {
			if (authHeader.startsWith("OAuth ")) {
				authHeader = authHeader.substring(authHeader.indexOf("o"));
			}
			String[] headers = authHeader.split(splitChar);
			if (headers != null && headers.length > 0) {
				for (String header : headers) {
					String[] elements = header.split("=");
					if (elements != null && elements.length > 0) {
						if (OAuthConstants.OAUTH_CONSUMER_KEY.equals(elements[0].trim())) {
							params.setOauthConsumerKey(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.OAUTH_NONCE.equals(elements[0].trim())) {
							params.setOauthNonce(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.OAUTH_SIGNATURE.equals(elements[0].trim())) {
							params.setOauthSignature(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.OAUTH_SIGNATURE_METHOD.equals(elements[0].trim())) {
							params.setOauthSignatureMethod(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.OAUTH_TIMESTAMP.equals(elements[0].trim())) {
							params.setOauthTimeStamp(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.OAUTH_CALLBACK.equals(elements[0].trim())) {
							params.setOauthCallback(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.SCOPE.equals(elements[0].trim())) {
							params.setScope(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.OAUTH_DISPLAY_NAME.equals(elements[0].trim())) {
							params.setDisplayName(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.OAUTH_TOKEN.equals(elements[0].trim())) {
							params.setOauthToken(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.OAUTH_VERIFIER.equals(elements[0].trim())) {
							params.setOauthTokenVerifier(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.OAUTH_TOKEN_SECRET.equals(elements[0].trim())) {
							params.setOauthTokenSecret(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.OAUTH_VERSION.equals(elements[0].trim())) {
							params.setVersion(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else {
							nonAuthParams.append(elements[0].trim() +
							                     "=" +
							                     removeLeadingAndTrailingQuatation(elements[1].trim()) +
							                     "&");
						}
					}
				}
			}
		}

		String nonOauthParamStr = nonAuthParams.toString();

		if (!noAuthorizationHeader) {
			nonOauthParamStr = quarryString + "&";
		}

		String scope = null;

		if (nonOauthParamStr.indexOf("scope=") > -1) {
			String postScope =
			                   nonOauthParamStr.substring(nonOauthParamStr.indexOf("scope="),
			                                              nonOauthParamStr.length());
			if (postScope.indexOf("&") > -1) {
				scope = postScope.substring(6, postScope.indexOf("&"));
			} else {
				scope = postScope.substring(6, postScope.length());

			}
		}

		if (scope != null) {
			params.setScope(scope);
		}

		params.setHttpMethod((String) msgContext.getProperty("HTTP_METHOD"));

		String prefix = (String) msgContext.getProperty(NhttpConstants.SERVICE_PREFIX);

		if (nonOauthParamStr.length() > 1) {
			params.setBaseString(prefix + operation +
			                     nonOauthParamStr.substring(0, nonOauthParamStr.length() - 1));
		} else {
			params.setBaseString(prefix);
		}

		return params;
	}

	private String removeLeadingAndTrailingQuatation(String base) {
		String result = base;

		if (base.startsWith("\"") || base.endsWith("\"")) {
			result = base.replace("\"", "");
		}
		return result.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
