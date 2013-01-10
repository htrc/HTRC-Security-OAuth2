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

package org.wso2.carbon.identity.oauth.common;

public class OAuth2Constants {
    /*
     * <p>Defining the SAML2 Bearer grant type. Not sure whether this should come as part of Apache Amber. Apache Amber
     * defines all major grant types. The grant type defined here is from <a href="http://tools.ietf.org/html/draft-ietf-oauth-saml2-bearer-14"
     * >SAML 2.0 Bearer Assertion Profiles for OAuth 2.0</a>.</p>
     */
    public static final String SAML2_BEARER_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:saml2-bearer";
}
