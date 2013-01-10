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
package org.wso2.carbon.identity.sso.saml.ui;

import java.util.ArrayList;
import java.util.List;

public class SAMLSSOProviderConfigBean {

	private String issuer = "";
	private String assertionConsumerUrl = "";
	private String useFullyQualifiedUserName = "false";
	private String enableAssertionSigning = "false";
	private String enableSignatureValidation = "false";
	private String certificateAlias = "";
	private String enableSingleLogout = "false";
	private String singleLogoutUrl = "";
	private String enableClaims = "false";
	private List<String> selectedClaims = new ArrayList<String>();

	/**
	 * clears the values in bean
	 */
	public void clearBean() {
		issuer = "";
		assertionConsumerUrl = "";
		useFullyQualifiedUserName = "false";
		enableAssertionSigning = "false";
		enableSignatureValidation = "false";
		certificateAlias = "";
		enableSingleLogout = "false";
		singleLogoutUrl = "";
		enableClaims = "false";
		selectedClaims.clear();
	}

	/**
	 * @return the singleLogoutUrl
	 */
	public String getSingleLogoutUrl() {
		return singleLogoutUrl;
	}

	/**
	 * @param singleLogoutUrl
	 *            the singleLogoutUrl to set
	 */
	public void setSingleLogoutUrl(String singleLogoutUrl) {
		this.singleLogoutUrl = singleLogoutUrl;
	}

	/**
	 * @return the enableSingleLogout
	 */
	public String getEnableSingleLogout() {
		return enableSingleLogout;
	}

	/**
	 * @param enableSingleLogout
	 *            the enableSingleLogout to set
	 */
	public void setEnableSingleLogout(String enableSingleLogout) {
		this.enableSingleLogout = enableSingleLogout;
	}

	/**
	 * @return the certificateAlias
	 */
	public String getCertificateAlias() {
		return certificateAlias;
	}

	/**
	 * @param certificateAlias
	 *            the certificateAlias to set
	 */
	public void setCertificateAlias(String certificateAlias) {
		this.certificateAlias = certificateAlias;
	}

	/**
	 * @return the enableSignatureValidation
	 */
	public String getEnableSignatureValidation() {
		return enableSignatureValidation;
	}

	/**
	 * @param enableSignatureValidation
	 *            the enableSignatureValidation to set
	 */
	public void setEnableSignatureValidation(String enableSignatureValidation) {
		this.enableSignatureValidation = enableSignatureValidation;
	}

	/**
	 * @return the enableAssertionSigning
	 */
	public String getEnableAssertionSigning() {
		return enableAssertionSigning;
	}

	/**
	 * @param enableAssertionSigning
	 *            the enableAssertionSigning to set
	 */
	public void setEnableAssertionSigning(String enableAssertionSigning) {
		this.enableAssertionSigning = enableAssertionSigning;
	}

	/**
	 * @return the useFullyQualifiedUserName
	 */
	public String getUseFullyQualifiedUserName() {
		return useFullyQualifiedUserName;
	}

	/**
	 * @param useFullyQualifiedUserName
	 *            the useFullyQualifiedUserName to set
	 */
	public void setUseFullyQualifiedUserName(String useFullyQualifiedUserName) {
		this.useFullyQualifiedUserName = useFullyQualifiedUserName;
	}

	/**
	 * @return the assertionConsumerUrl
	 */
	public String getAssertionConsumerUrl() {
		return assertionConsumerUrl;
	}

	/**
	 * @param assertionConsumerUrl
	 *            the assertionConsumerUrl to set
	 */
	public void setAssertionConsumerUrl(String assertionConsumerUrl) {
		this.assertionConsumerUrl = assertionConsumerUrl;
	}

	/**
	 * @return the issuer
	 */
	public String getIssuer() {
		return issuer;
	}

	/**
	 * @param issuer
	 *            the issuer to set
	 */
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	/**
	 * @return the enableClaims
	 */
	public String getEnableClaims() {
		return enableClaims;
	}

	/**
	 * @param enableClaims
	 *            the enableClaims to set
	 */
	public void setEnableClaims(String enableClaims) {
		this.enableClaims = enableClaims;
	}

	/**
	 * @return the selectedClaims
	 */
	public List<String> getSelectedClaims() {
		return selectedClaims;
	}
	
	public String[] getSelectedClaimsAttay() {
		return selectedClaims.toArray(new String[selectedClaims.size()]);
	}

	/**
	 * @param selectedAttributes
	 *            the selectedAttributes to set
	 */
	public void setSelectedAttributes(List<String> selectedClaims) {
		this.selectedClaims = selectedClaims;
	}

	/**
	 * add an claim to the required cliams list
	 * 
	 * @param claim
	 * @return 
	 */
	public boolean addClaimToList(String claim) {
		if(selectedClaims.contains(claim)){
			return false;
		}
		selectedClaims.add(claim);
		return true;
	}

	/**
	 * remove a claim from the required claims list
	 * 
	 * @param claim
	 */
	public void removeClaimFromList(String claim) {
		selectedClaims.remove(claim);
	}
}
