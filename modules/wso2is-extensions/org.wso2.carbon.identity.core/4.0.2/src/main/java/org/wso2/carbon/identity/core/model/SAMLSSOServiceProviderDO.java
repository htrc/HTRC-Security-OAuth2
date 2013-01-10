/*
 * Copyright 2005-2007 WSO2, Inc. (http://wso2.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.core.model;

import java.util.List;

import org.apache.axis2.databinding.utils.ConverterUtil;

public class SAMLSSOServiceProviderDO {

	private String issuer;
	private String assertionConsumerUrl;
	private String certAlias;
	private String logoutURL;
	private boolean useFullyQualifiedUsername;
	private boolean doSingleLogout;
	private String loginPageURL;
	private boolean doSignAssertions;
	private String attributeConsumingServiceIndex;
	private String[] requestedClaims;
	private List<String> requestedClaimsList;

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		if (issuer != null) {
			this.issuer = issuer.replaceAll("[\n\r]", "").trim();
		}
	}

	public String getAssertionConsumerUrl() {
		return assertionConsumerUrl;
	}

	public void setAssertionConsumerUrl(String assertionConsumerUrl) {
		if (assertionConsumerUrl != null) {
			this.assertionConsumerUrl = assertionConsumerUrl.replaceAll("[\n\r]", "").trim();
		}
	}

	public String getCertAlias() {
		return certAlias;
	}

	public void setCertAlias(String certAlias) {
		this.certAlias = certAlias;
	}

	public boolean isUseFullyQualifiedUsername() {
		return useFullyQualifiedUsername;
	}

	public void setUseFullyQualifiedUsername(boolean useFullyQualifiedUsername) {
		this.useFullyQualifiedUsername = useFullyQualifiedUsername;
	}

	public String getLogoutURL() {
		return logoutURL;
	}

	public void setLogoutURL(String logoutURL) {
		if (logoutURL != null) {
			this.logoutURL = logoutURL.replaceAll("[\n\r]", "").trim();
		}
	}

	public boolean isDoSingleLogout() {
		return doSingleLogout;
	}

	public void setDoSingleLogout(boolean doSingleLogout) {
		this.doSingleLogout = doSingleLogout;
	}

	public String getLoginPageURL() {
		return loginPageURL;
	}

	public void setLoginPageURL(String loginPageURL) {
		if (loginPageURL != null) {
			this.loginPageURL = loginPageURL.replaceAll("[\n\r]", "").trim();
		}
	}

	public boolean isDoSignAssertions() {
		return doSignAssertions;
	}

	public void setDoSignAssertions(boolean doSignAssertions) {
		this.doSignAssertions = doSignAssertions;
	}

	public String getAttributeConsumingServiceIndex() {
		return attributeConsumingServiceIndex;
	}

	public void setAttributeConsumingServiceIndex(String attributeConsumingServiceIndex) {
		this.attributeConsumingServiceIndex = attributeConsumingServiceIndex;
	}

	/**
	 * @return the requestedClaims
	 */
	public String[] getRequestedClaims() {
		return requestedClaims;
	}

	/**
	 * @return the requestedClaims
	 */
	public List<String> getRequestedClaimsList() {
		return requestedClaimsList;
	}

	/**
	 * @param requestedClaims
	 *            the requestedClaims to set
	 */
	public void setRequestedClaims(String[] requestedClaims) {
		this.requestedClaims = requestedClaims;
		this.requestedClaimsList = ConverterUtil.toList(requestedClaims);
	}

	/**
	 * @param requestedClaims
	 *            the requestedClaims to set
	 */
	public void setRequestedClaims(List<String> requestedClaims) {
		this.requestedClaimsList = requestedClaims;
		this.requestedClaims = requestedClaims.toArray(new String[requestedClaimsList.size()]);
	}
}
