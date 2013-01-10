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
package org.wso2.carbon.identity.sts.passive;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPFault;
import org.apache.axiom.soap.SOAPFaultCode;
import org.apache.axiom.soap.SOAPFaultDetail;
import org.apache.axiom.soap.SOAPFaultReason;
import org.apache.axiom.soap.SOAPFaultSubCode;
import org.apache.axiom.soap.SOAPFaultText;
import org.apache.axiom.soap.SOAPFaultValue;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rahas.TrustException;
import org.wso2.carbon.identity.sts.passive.internal.IdentityPassiveSTSServiceComponent;
import org.wso2.carbon.identity.sts.passive.internal.InMemoryTrustedServiceStore;
import org.wso2.carbon.identity.sts.passive.internal.RegistryBasedTrustedServiceStore;
import org.wso2.carbon.identity.sts.passive.processors.RequestProcessor;

import javax.xml.namespace.QName;

public class PassiveSTSService {
    private static final Log log = LogFactory.getLog(PassiveSTSService.class);

    public ResponseToken getResponse(RequestToken request) throws Exception {

        if (IdentityPassiveSTSServiceComponent.getRealm() == null) {
            throw new Exception("User realm not properly set");
        }

        if (request == null || request.getUserName() == null) {
            throw new Exception("Invalid request token. User credentials not provided");
        }

        RequestProcessor processor = null;
        ResponseToken responseToken = null;
        String soapfault = null;

        if (!IdentityPassiveSTSServiceComponent.getRealm().getUserStoreManager().isValidRememberMeToken(
                request.getUserName(), request.getPseudo())) {
            boolean isAuthenticated = IdentityPassiveSTSServiceComponent.getRealm().getUserStoreManager().
                    authenticate(request.getUserName(), request.getPassword());

            if (!isAuthenticated) {
                return new ResponseToken();
            } else {
                IdentityPassiveSTSServiceComponent.getRealm().getUserStoreManager().addRememberMe(
                        request.getUserName(), request.getPseudo());
            }
        }
        processor = RequestProcessorFactory.getInstance().getRequestProcessor(request.getAction());

        if (processor != null) {
            try {
                responseToken = processor.process(request);
            } catch (TrustException e) {
                soapfault = genFaultResponse(MessageContext.getCurrentMessageContext(), "Sender",
                                             "InvalidRequest", e.getMessage(), "none").toStringWithConsume();
            }
        } else {
            soapfault = genFaultResponse(MessageContext.getCurrentMessageContext(), "Sender",
                                         "InvalidRequest", "Invalid Request", "none").toStringWithConsume();
        }

        if (responseToken == null) {
            responseToken = new ResponseToken();
        }

        if (soapfault != null) {
            responseToken.setResults(soapfault);
        }

        responseToken.setAuthenticated(true);
        if (request.getReplyTo() != null) {
            responseToken.setReplyTo(request.getReplyTo());
        } else {
            responseToken.setReplyTo(request.getRealm());
        }

        if (responseToken.getReplyTo() == null) {
            throw new Exception("ReplyTo address not found");
        }

        responseToken.setContext(request.getContext());

        return responseToken;
    }

    private SOAPFault genFaultResponse(MessageContext messageCtx, String code, String subCode,
                                       String reason, String detail) {
        SOAPFactory soapFactory = null;
        if (messageCtx.isSOAP11()) {
            soapFactory = OMAbstractFactory.getSOAP11Factory();
            SOAPEnvelope message = soapFactory.getDefaultFaultEnvelope();
            SOAPFaultReason soapFaultReason = soapFactory.createSOAPFaultReason();
            soapFaultReason.setText(reason);
            message.getBody().getFault().setReason(soapFaultReason);
            SOAPFaultCode soapFaultCode = soapFactory.createSOAPFaultCode();
            QName qNameSubCode = new QName("http://wso2.org/passivests", subCode, "sts");
            soapFaultCode.setText(qNameSubCode);
            message.getBody().getFault().setCode(soapFaultCode);
            return message.getBody().getFault();
        } else {
            soapFactory = OMAbstractFactory.getSOAP12Factory();
            SOAPEnvelope message = soapFactory.getDefaultFaultEnvelope();
            SOAPFaultDetail soapFaultDetail = soapFactory.createSOAPFaultDetail();
            soapFaultDetail.setText(detail);
            message.getBody().getFault().setDetail(soapFaultDetail);
            SOAPFaultReason soapFaultReason = soapFactory.createSOAPFaultReason();
            SOAPFaultText soapFaultText = soapFactory.createSOAPFaultText();
            soapFaultText.setText(reason);
            soapFaultReason.addSOAPText(soapFaultText);
            message.getBody().getFault().setReason(soapFaultReason);
            SOAPFaultCode soapFaultCode = soapFactory.createSOAPFaultCode();
            SOAPFaultValue soapFaultValue = soapFactory.createSOAPFaultValue(soapFaultCode);
            soapFaultValue.setText(code);
            soapFaultCode.setValue(soapFaultValue);
            SOAPFaultSubCode soapFaultSubCode = soapFactory.createSOAPFaultSubCode(soapFaultCode);
            SOAPFaultValue soapFaultValueSub = soapFactory.createSOAPFaultValue(soapFaultSubCode);
            QName qNameSubCode = new QName("http://wso2.org/passivests", subCode, "sts");
            soapFaultValueSub.setText(qNameSubCode);
            soapFaultSubCode.setValue(soapFaultValueSub);
            soapFaultCode.setSubCode(soapFaultSubCode);
            message.getBody().getFault().setCode(soapFaultCode);
            return message.getBody().getFault();
        }
    }

    /**
     * Add a trusted service to which tokens are issued with given claims.
     *
     * @param realmName    - this uniquely represents the trusted service
     * @param claimDialect - claim dialects uris
     * @param claims       - these comma separated default claims are issued when a request is done from the given realm
     * @throws Exception - if fails to add trusted service
     */
    public void addTrustedService(String realmName, String claimDialect, String claims)
            throws Exception {
        InMemoryTrustedServiceStore inMemoryTrustedServiceStore = new InMemoryTrustedServiceStore();
        inMemoryTrustedServiceStore.addTrustedService(realmName, claimDialect, claims);

        RegistryBasedTrustedServiceStore registryBasedTrustedServiceStore = new RegistryBasedTrustedServiceStore();
        registryBasedTrustedServiceStore.addTrustedService(realmName, claimDialect, claims);

    }

    /**
     * Remove the given trusted service with realmName
     *
     * @param realmName - the realm of the service
     * @throws Exception
     */
    public void removeTrustedService(String realmName) throws Exception {
        InMemoryTrustedServiceStore inMemoryTrustedServiceStore = new InMemoryTrustedServiceStore();
        inMemoryTrustedServiceStore.removeTrustedService(realmName);

        RegistryBasedTrustedServiceStore registryBasedTrustedServiceStore = new RegistryBasedTrustedServiceStore();
        registryBasedTrustedServiceStore.removeTrustedService(realmName);
    }

    /**
     * Get all trusted services
     *
     * @return get default claims for all trusted services
     * @throws Exception
     */
    public ClaimDTO[] getAllTrustedServices() throws Exception {
        // load all trusted services from in-memory store
        InMemoryTrustedServiceStore inMemoryTrustedServiceStore = new InMemoryTrustedServiceStore();
        ClaimDTO[] claimDTOs = inMemoryTrustedServiceStore.getAllTrustedServices();
        if (claimDTOs != null && claimDTOs.length > 0) {
            return claimDTOs;
        }

        // if in-memory store is empty, load from registry
        RegistryBasedTrustedServiceStore registryBasedTrustedServiceStore = new RegistryBasedTrustedServiceStore();
        return registryBasedTrustedServiceStore.getAllTrustedServices();
    }

    /**
     * Get default claims for given trusted service
     *
     * @param realmName - trusted service realm name
     * @return - default claims for given trusted service
     * @throws Exception
     */
    public ClaimDTO getTrustedServiceClaims(String realmName) throws Exception {
        // check in in-memory store first
        InMemoryTrustedServiceStore inMemoryTrustedServiceStore = new InMemoryTrustedServiceStore();
        ClaimDTO inMemoryClaimDTO = inMemoryTrustedServiceStore.getTrustedServiceClaims(realmName);
        if (inMemoryClaimDTO != null) {
            return inMemoryClaimDTO;
        }

        // check in registry if not found in in-memory store
        RegistryBasedTrustedServiceStore registryBasedTrustedServiceStore = new RegistryBasedTrustedServiceStore();
        return registryBasedTrustedServiceStore.getTrustedServiceClaims(realmName);
    }
}
