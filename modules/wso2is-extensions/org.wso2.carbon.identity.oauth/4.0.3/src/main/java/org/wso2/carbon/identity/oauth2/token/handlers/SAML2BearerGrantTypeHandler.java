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

package org.wso2.carbon.identity.oauth2.token.handlers;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.opensaml.saml2.core.*;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.SignatureValidator;
import org.wso2.carbon.base.ServerConfiguration;
import org.wso2.carbon.identity.authenticator.saml2.sso.util.Util;
import org.wso2.carbon.identity.authenticator.saml2.sso.util.X509CredentialImpl;
import org.wso2.carbon.identity.oauth.IdentityOAuthAdminException;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenReqDTO;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.identity.oauth2.util.OAuth2Constants;
import org.wso2.carbon.identity.oauth2.util.OAuth2Util;
import org.wso2.carbon.utils.ServerConstants;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * This implements SAML 2.0 Bearer Assertion Profile for OAuth 2.0 -
 * http://tools.ietf.org/html/draft-ietf-oauth-saml2-bearer-14.
 */
public class SAML2BearerGrantTypeHandler extends AbstractAuthorizationGrantHandler {

    private static Log log = LogFactory.getLog(SAML2BearerGrantTypeHandler.class);

    public SAML2BearerGrantTypeHandler() throws IdentityOAuth2Exception {
        super();
    }

    /**
     * We have to override this method in order to set the resource owner username to OAuth token that we create. Token
     * is accessed through the token request message context.
     */
    @Override
    public boolean authenticateClient(OAuthTokenReqMessageContext tokReqMsgCtx) throws IdentityOAuth2Exception {
        OAuth2AccessTokenReqDTO oAuth2AccessTokenReqDTO = tokReqMsgCtx.getOauth2AccessTokenReqDTO();
        try {
            String authenticatedUser = OAuth2Util.getAuthenticatedUsername(
                    oAuth2AccessTokenReqDTO.getClientId(),
                    oAuth2AccessTokenReqDTO.getClientSecret());

            if (!authenticatedUser.equals("")) {
                // Set the username
                tokReqMsgCtx.getOauth2AccessTokenReqDTO().setResourceOwnerUsername(authenticatedUser);
                tokReqMsgCtx.setAuthorizedUser(authenticatedUser);
                return true;
            } else {
                // Authentication failure
                return false;
            }
        } catch (IdentityOAuthAdminException e) {
            throw new IdentityOAuth2Exception(e.getMessage(), e);
        }
        // Nothing to return at this point
    }

    /**
     * We're validating the SAML token that we receive from the request. Through the assertion parameter is the POST
     * request. A request format that we handle here looks like,
     * <p/>
     * POST /token.oauth2 HTTP/1.1
     * Host: as.example.com
     * Content-Type: application/x-www-form-urlencoded
     * <p/>
     * grant_type=urn%3Aietf%3Aparams%3Aoauth%3Agrant-type%3Asaml2-bearer&
     * assertion=PHNhbWxwOl...[omitted for brevity]...ZT4
     *
     * @param tokReqMsgCtx Token message request context
     * @return true if validation is successful, false otherwise
     * @throws IdentityOAuth2Exception
     */
    @Override
    public boolean validateGrant(OAuthTokenReqMessageContext tokReqMsgCtx) throws IdentityOAuth2Exception {
        boolean isValid = false;

        try {
            // Logging the SAML token
            if (log.isDebugEnabled()) {
                log.debug("Received SAML assertion : " +
                        new String(Base64.decodeBase64(tokReqMsgCtx.getOauth2AccessTokenReqDTO().getAssertion()))
                );
            }

            XMLObject samlObject = Util.unmarshall(
                    new String(Base64.decodeBase64(tokReqMsgCtx.getOauth2AccessTokenReqDTO().getAssertion()))
            );
            Assertion assertion = (Assertion) samlObject;
            // List<Assertion> assertions = assertion.getAssertions();

//            Assertion assertion = null;
//            if (assertions != null && assertions.size() > 0) {
//                assertion = assertions.get(0);
//            }

            if (assertion == null) {
                log.error("Assertion is null, cannot continue");
                // throw new SAML2SSOAuthenticatorException("SAMLResponse does not contain Assertions.");
                throw new Exception("Assertion is null, cannot continue");
            }

            /**
             * Validating SAML request according to criteria specified in "SAML 2.0 Bearer Assertion Profiles for
             * OAuth 2.0 - http://tools.ietf.org/html/draft-ietf-oauth-saml2-bearer-14
             */

            /**
             * The Assertion's <Issuer> element MUST contain a unique identifier for the entity that issued
             * the Assertion.
             */
            if ( (assertion.getIssuer() != null) && assertion.getIssuer().getValue().equals("")) {
                log.error("Issuer is empty in the SAML assertion");
                throw new Exception("Issuer is empty in the SAML assertion");
            }

            /**
             * The Assertion MUST contain <Conditions> element with an <AudienceRestriction> element with an <Audience>
             * element containing a URI reference that identifies the authorization server, or the service provider
             * SAML entity of its controlling domain, as an intended audience.  The token endpoint URL of the
             * authorization server MAY be used as an acceptable value for an <Audience> element.  The authorization
             * server MUST verify that it is an intended audience for the Assertion.
             */
            Conditions conditions = assertion.getConditions();
            if (conditions != null) {
                List<AudienceRestriction> restrictions = conditions.getAudienceRestrictions();
                if (restrictions != null && restrictions.size() > 0) {
                    AudienceRestriction ar = restrictions.get(0);

                    // Where to get what SPs are configured for this IdP?
                    for (Audience a : ar.getAudiences()) {
                        String audienceURI = a.getAudienceURI();
                        // TODO: figure out how to get the mapping there's an audience URI that matches IdP URI
                    }
                } else {
                    log.error("Cannot find any AudienceRestrictions in the Assertion");
                    throw new Exception("Cannot find any AudienceRestrictions in the Assertion");
                }
            } else {
                log.error("Cannot find any Conditions in the Assertion");
                throw new Exception("Cannot find any Conditions in the Assertion");
            }

            /**
             * The Assertion MUST contain a <Subject> element.  The subject MAY identify the resource owner for whom
             * the access token is being requested.  For client authentication, the Subject MUST be the "client_id"
             * of the OAuth client.  When using an Assertion as an authorization grant, the Subject SHOULD identify
             * an authorized accessor for whom the access token is being requested (typically the resource owner, or
             * an authorized delegate).  Additional information identifying the subject/principal of the transaction
             * MAY be included in an <AttributeStatement>.
             */
            if (assertion.getSubject() != null) {
                // Get user the client_id belongs to
                String token_user = OAuth2Util.getAuthenticatedUsername(
                        tokReqMsgCtx.getOauth2AccessTokenReqDTO().getClientId(),
                        tokReqMsgCtx.getOauth2AccessTokenReqDTO().getClientSecret()
                );
                // User of client id should match user in subject
                if (!assertion.getSubject().getNameID().getValue().equals(token_user)) {
                    log.error("NameID in Assertion doesn't match username the client id belongs to");
                    throw new Exception("NameID in Assertion doesn't match username the client id belongs to");
                }
            } else {
                log.error("Cannot find a Subject in the Assertion");
                throw new Exception("Cannot find a Subject in the Assertion");
            }

            /**
             * The Assertion MUST have an expiry that limits the time window during which it can be used.  The expiry
             * can be expressed either as the NotOnOrAfter attribute of the <Conditions> element or as the NotOnOrAfter
             * attribute of a suitable <SubjectConfirmationData> element.
             */
            boolean isNotOnOrAfterFound = false;
            DateTime notOnOrAfter = null;
            if (assertion.getSubject().getSubjectConfirmations() != null) {
                List<SubjectConfirmation> sc = assertion.getSubject().getSubjectConfirmations();
                for (SubjectConfirmation s : sc) {
                    notOnOrAfter = s.getSubjectConfirmationData().getNotOnOrAfter();
                    isNotOnOrAfterFound = true;
                }
            }
            if (!isNotOnOrAfterFound) {
                // We didn't find any NotOnOrAfter attributes in SubjectConfirmationData. Let's look at attributes in
                // the Conditions element
                if (assertion.getConditions() != null) {
                    notOnOrAfter = assertion.getConditions().getNotOnOrAfter();
                    isNotOnOrAfterFound = true;
                } else {
                    // At this point there can be no NotOnOrAfter attributes, according to the spec description above
                    // we can safely throw the error
                    log.error("Didn't find any NotOnOrAfter attribute, must have an expiry time");
                    throw new Exception("Didn't find any NotOnOrAfter attribute, must have an expiry time");
                }
            }

            /**
             * The <Subject> element MUST contain at least one <SubjectConfirmation> element that allows the
             * authorization server to confirm it as a Bearer Assertion.  Such a <SubjectConfirmation> element MUST
             * have a Method attribute with a value of "urn:oasis:names:tc:SAML:2.0:cm:bearer".  The
             * <SubjectConfirmation> element MUST contain a <SubjectConfirmationData> element, unless the Assertion
             * has a suitable NotOnOrAfter attribute on the <Conditions> element, in which case the
             * <SubjectConfirmationData> element MAY be omitted. When present, the <SubjectConfirmationData> element
             * MUST have a Recipient attribute with a value indicating the token endpoint URL of the authorization
             * server (or an acceptable alias).  The authorization server MUST verify that the value of the Recipient
             * attribute matches the token endpoint URL (or an acceptable alias) to which the Assertion was delivered.
             * The <SubjectConfirmationData> element MUST have a NotOnOrAfter attribute that limits the window during
             * which the Assertion can be confirmed.  The <SubjectConfirmationData> element MAY also contain an Address
             * attribute limiting the client address from which the Assertion can be delivered.  Verification of the
             * Address is at the discretion of the authorization server.
             */
            if ((assertion.getSubject().getSubjectConfirmations() != null) &&
                    (assertion.getSubject().getSubjectConfirmations().size() > 0)) {
                List<SubjectConfirmation> confirmations = assertion.getSubject().getSubjectConfirmations();
                boolean bearerFound = false;
                ArrayList<String> recipientURLS = new ArrayList<String>();
                for (SubjectConfirmation c : confirmations) {
                    if (c.getSubjectConfirmationData() != null) {
                        recipientURLS.add(c.getSubjectConfirmationData().getRecipient());
                    }
                    if (c.getMethod().equals(OAuth2Constants.OAUTH_SAML2_BEARER_METHOD)) {
                        bearerFound = true;
                    }
                }
                if (!bearerFound) {
                    log.error("Failed to find a SubjectConfirmation with a Method attribute having : " +
                            OAuth2Constants.OAUTH_SAML2_BEARER_METHOD);
                    throw new Exception("Failed to find a SubjectConfirmation with a Method attribute having : " +
                            OAuth2Constants.OAUTH_SAML2_BEARER_METHOD);
                }
                // TODO: Verify at least one recipientURLS matches token endpoint URL
            } else {
                log.error("No SubjectConfirmation exist in Assertion");
                throw new Exception("No SubjectConfirmation exist in Assertion");
            }

            /**
             * The authorization server MUST verify that the NotOnOrAfter instant has not passed, subject to allowable
             * clock skew between systems.  An invalid NotOnOrAfter instant on the <Conditions> element invalidates
             * the entire Assertion.  An invalid NotOnOrAfter instant on a <SubjectConfirmationData> element only
             * invalidates the individual <SubjectConfirmation>.  The authorization server MAY reject Assertions with
             * a NotOnOrAfter instant that is unreasonably far in the future.  The authorization server MAY ensure
             * that Bearer Assertions are not replayed, by maintaining the set of used ID values for the length of
             * time for which the Assertion would be considered valid based on the applicable NotOnOrAfter instant.
             */
            if (notOnOrAfter.compareTo(new DateTime()) != 1) {
                // notOnOrAfter is an expired timestamp
                log.error("NotOnOrAfter is having an expired timestamp");
                throw new Exception("NotOnOrAfter is having an expired timestamp");
            }

            /**
             * The Assertion MUST be digitally signed by the issuer and the authorization server MUST verify the
             * signature.
             */
            X509CredentialImpl credImpl;

            // Use primary keystore specified in carbon.xml
            ServerConfiguration sc = ServerConfiguration.getInstance();
            KeyStore ks = KeyStore.getInstance(sc.getFirstProperty("Security.KeyStore.Type"));
            FileInputStream ksFile = new FileInputStream(sc.getFirstProperty("Security.KeyStore.Location"));
            ks.load(
                    ksFile,
                    sc.getFirstProperty("Security.KeyStore.Password").toCharArray()
            );
            ksFile.close();

            String alias = sc.getFirstProperty("Security.KeyStore.KeyAlias");
            X509Certificate cert = null;
            if (alias != null) {
                cert = (X509Certificate) ks.getCertificate(alias);
                if (cert == null) {
                    log.error("Cannot find certificate with the alias - " + alias);
                }
            }
            credImpl = new X509CredentialImpl(cert);
            SignatureValidator validator = new SignatureValidator(credImpl);
            validator.validate(assertion.getSignature());

            /**
             * The authorization server MUST verify that the Assertion is valid in all other respects per
             * [OASIS.saml-core-2.0-os], such as (but not limited to) evaluating all content within the Conditions
             * element including the NotOnOrAfter and NotBefore attributes, rejecting unknown condition types, etc.
             *
             * [OASIS.saml-core-2.0-os] - http://docs.oasis-open.org/security/saml/v2.0/saml-core-2.0-os.pdf
             */
            // TODO: Throw the SAML request through the general SAML2 validation routines

            isValid = true;
        } catch (Exception e) {
            /**
             * Ideally we should handle a SAML2SSOAuthenticatorException here? Seems to be the right
             * way to go as there's no other exception class specified. Need a clear exception hierarchy here for
             * handling SAML messages.*/
            log.error(e.getMessage(), e);
        }
        return isValid;
    }

    @Override
    public boolean validateScope(OAuthTokenReqMessageContext tokReqMsgCtx) {
        if (tokReqMsgCtx.getScope() == null) {
            // Do we really need to do this?
            tokReqMsgCtx.setScope(new String[]{"SAML2 OAuth"});
        }
        return true;
    }

    @Override
    public boolean authorizeAccessDelegation(OAuthTokenReqMessageContext tokReqMsgCtx) {
        return true;
    }

}
