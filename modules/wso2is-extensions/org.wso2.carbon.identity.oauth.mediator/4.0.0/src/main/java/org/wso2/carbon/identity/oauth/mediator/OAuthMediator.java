/*
 *  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.identity.oauth.mediator;

import java.util.Map;

import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.synapse.transport.nhttp.NhttpConstants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerDTO;
import org.wso2.carbon.identity.oauth.stub.types.Parameters;

public class OAuthMediator extends AbstractMediator {

	private static final Log log = LogFactory.getLog(OAuthMediator.class);

	private boolean remote = true;
	private String remoteServiceUrl;

	ConfigurationContext cfgCtx = null;
	private String clientRepository = null;
	private String axis2xml = null;
	public final static String DEFAULT_CLIENT_REPO = "./samples/axis2Client/client_repo";
	public final static String DEFAULT_AXIS2_XML = "./samples/axis2Client/client_repo/conf/axis2.xml";

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
			cfgCtx = ConfigurationContextFactory.createConfigurationContextFromFileSystem(
					clientRepository != null ? clientRepository : DEFAULT_CLIENT_REPO,
					axis2xml != null ? axis2xml : DEFAULT_AXIS2_XML);
		} catch (AxisFault e) {
			String msg = "Error initializing callout mediator : " + e.getMessage();
			log.error(msg, e);
			throw new SynapseException(msg, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean mediate(MessageContext synCtx) {

		OAuthServiceClient client = null;
		ConfigurationContext configContext = null;
		OAuthConsumerDTO consumer = null;
		boolean isValidConsumer = false;

		if (log.isDebugEnabled()) {
			log.debug("Mediation for OAuth started");
		}

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

	/*
	 * Populates the Parameters object from the OAuth authorization header or query string.
	 */
	private Parameters populateOauthConsumerData(MessageContext synCtx) {
		String authHeader = null;
		Parameters params = null;
		Map headersMap = null;
		String splitChar = ",";
		boolean noAuthorizationHeader = false;
		org.apache.axis2.context.MessageContext msgContext;
		Axis2MessageContext axis2Msgcontext = null;
		axis2Msgcontext = (Axis2MessageContext) synCtx;
		msgContext = axis2Msgcontext.getAxis2MessageContext();

		headersMap = (Map) msgContext
				.getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);
		authHeader = (String) headersMap.get("Authorization");
		params = new Parameters();
		String queryString;
		String operation = null;

		queryString = (String) msgContext.getProperty(NhttpConstants.REST_URL_POSTFIX);
		if (queryString.indexOf("?") > -1) {
			String temp = queryString;
			queryString = queryString.substring(queryString.indexOf("?") + 1);
			operation = temp.substring(0, temp.indexOf("?") + 1);
		}

		if (authHeader == null) {
			noAuthorizationHeader = true;
			// No Authorization header available.
			authHeader = queryString;
			splitChar = "&";
		}

		StringBuffer nonAuthParams = new StringBuffer();

		if (authHeader != null) {
			if (authHeader.startsWith("OAuth ")) {
				authHeader = authHeader.substring(authHeader.indexOf("o"));
			}
			String[] headers = authHeader.split(splitChar);
			if (headers != null && headers.length > 0) {
				for (int i = 0; i < headers.length; i++) {
					String[] elements = headers[i].split("=");
					if (elements != null && elements.length > 0) {
						if (OAuthConstants.OAUTH_CONSUMER_KEY.equals(elements[0].trim())) {
							params.setOauthConsumerKey(removeLeadingAndTrailingQuatation(elements[1]
									.trim()));
						} else if (OAuthConstants.OAUTH_NONCE.equals(elements[0].trim())) {
							params.setOauthNonce(removeLeadingAndTrailingQuatation(elements[1]
									.trim()));
						} else if (OAuthConstants.OAUTH_SIGNATURE.equals(elements[0].trim())) {
							params.setOauthSignature(removeLeadingAndTrailingQuatation(elements[1]
									.trim()));
						} else if (OAuthConstants.OAUTH_SIGNATURE_METHOD.equals(elements[0].trim())) {
							params.setOauthSignatureMethod(removeLeadingAndTrailingQuatation(elements[1]
									.trim()));
						} else if (OAuthConstants.OAUTH_TIMESTAMP.equals(elements[0].trim())) {
							params.setOauthTimeStamp(removeLeadingAndTrailingQuatation(elements[1]
									.trim()));
						} else if (OAuthConstants.OAUTH_CALLBACK.equals(elements[0].trim())) {
							params.setOauthCallback(removeLeadingAndTrailingQuatation(elements[1]
									.trim()));
						} else if (OAuthConstants.SCOPE.equals(elements[0].trim())) {
							params.setScope(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else if (OAuthConstants.OAUTH_DISPLAY_NAME.equals(elements[0].trim())) {
							params.setDisplayName(removeLeadingAndTrailingQuatation(elements[1]
									.trim()));
						} else if (OAuthConstants.OAUTH_TOKEN.equals(elements[0].trim())) {
							params.setOauthToken(removeLeadingAndTrailingQuatation(elements[1]
									.trim()));
						} else if (OAuthConstants.OAUTH_VERIFIER.equals(elements[0].trim())) {
							params.setOauthTokenVerifier(removeLeadingAndTrailingQuatation(elements[1]
									.trim()));
						} else if (OAuthConstants.OAUTH_TOKEN_SECRET.equals(elements[0].trim())) {
							params.setOauthTokenSecret(removeLeadingAndTrailingQuatation(elements[1]
									.trim()));
						} else if (OAuthConstants.OAUTH_VERSION.equals(elements[0].trim())) {
							params.setVersion(removeLeadingAndTrailingQuatation(elements[1].trim()));
						} else {
							nonAuthParams.append(elements[0].trim() + "="
									+ removeLeadingAndTrailingQuatation(elements[1].trim()) + "&");
						}
					}
				}
			}
		}

		String nonOauthParamStr = nonAuthParams.toString();

		if (!noAuthorizationHeader) {
			nonOauthParamStr = queryString + "&";
		}

		String scope = null;

		if (nonOauthParamStr.indexOf("scope=") > -1) {
			String postScope = nonOauthParamStr.substring(nonOauthParamStr.indexOf("scope="),
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
			params.setBaseString(prefix + operation
					+ nonOauthParamStr.substring(0, nonOauthParamStr.length() - 1));
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
}
