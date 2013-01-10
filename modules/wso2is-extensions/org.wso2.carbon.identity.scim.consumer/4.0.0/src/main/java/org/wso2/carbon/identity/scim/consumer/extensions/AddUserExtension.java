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
package org.wso2.carbon.identity.scim.consumer.extensions;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.scim.consumer.utils.BasicAuthUtil;
import org.wso2.carbon.identity.scim.consumer.utils.IdentitySCIMException;
import org.wso2.carbon.identity.scim.consumer.utils.SCIMConsumerConstants;
import org.wso2.carbon.server.admin.privilegedaction.PrivilegedActionException;
import org.wso2.charon.core.client.SCIMClient;
import org.wso2.charon.core.exceptions.BadRequestException;
import org.wso2.charon.core.exceptions.CharonException;
import org.wso2.charon.core.objects.User;
import org.wso2.charon.core.schema.SCIMConstants;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AddUserExtension extends AbstractExtension {

    /**
     * for the moment, have some hard coded urls and credentials..
     */
    public static String USER_ENDPOINT = "http://localhost:9763/wso2/scim/Users";
    public static String USER_NAME = "admin";
    public static String PASSWORD = "admin";

    private static Log logger = LogFactory.getLog(AddUserExtension.class.getName());
    private final String EXTENSION_NAME = "addUserExtension";
    private final String SOAP_ACTION = "addUser";
    private final int PRIORITY = 1;

    public void execute(MessageContext inMessageContext, MessageContext outMessageContext)
            throws PrivilegedActionException {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("AddUser SCIM Extension was invoked...");
            }
            //get the tenant domain from message context
            String tenantDomain = (String) inMessageContext.getProperty("tenantDomain");
            //check if scim consumer is enabled for the tenant
            if (this.isSCIMConsumerEnabled(tenantDomain)) {
                //extract info from SOAP envelope
                OMElement bodyElement = inMessageContext.getEnvelope().getBody().getFirstElement();
                //extract user name
                OMElement userNameElement = bodyElement.getFirstChildWithName(
                        new QName(SCIMConsumerConstants.USER_MGT_NS,
                                  SCIMConsumerConstants.USER_NAME_ELEMENT_NAME));
                String userName = userNameElement.getText();
                //extract password element
                OMElement passwordElement = bodyElement.getFirstChildWithName(
                        new QName(SCIMConsumerConstants.USER_MGT_NS,
                                  SCIMConsumerConstants.PASSWORD_ELEMENT_NAME));
                String password = passwordElement.getText();

                //create SCIM user
                SCIMClient scimClient = new SCIMClient();
                User user = scimClient.createUser();
                user.setUserName(userName);
                user.setPassword(password);

                //encode SCIM User
                String encodedUser = scimClient.encodeSCIMObject(user, SCIMConstants.JSON);

                //create client to consume SCIM REST endpoint
                PostMethod postMethod = new PostMethod(
                        this.getSCIMEndpoint(tenantDomain, SCIMConsumerConstants.USER_RESOURCE_ENDPOINT));

                //add basic auth header
                postMethod.addRequestHeader(SCIMConstants.AUTHORIZATION_HEADER,
                                            BasicAuthUtil.getBase64EncodedBasicAuthHeader(
                                                    this.getUserName(tenantDomain),
                                                    this.getPassword(tenantDomain)));

                RequestEntity requestEntity = new StringRequestEntity(encodedUser,
                                                                      SCIMConstants.APPLICATION_JSON, null);
                postMethod.setRequestEntity(requestEntity);

                HttpClient client = new HttpClient();
                int responseStatus = client.executeMethod(postMethod);
                logger.info("SCIM - addUser returned with response code: " + responseStatus);

                String response = postMethod.getResponseBodyAsString();
                logger.info(response);
                if (scimClient.evaluateResponseStatus(responseStatus)) {

                    scimClient.decodeSCIMResponse(response, SCIMConstants.JSON, SCIMClient.USER);
                } else {
                    scimClient.decodeSCIMException(response, SCIMConstants.JSON);
                }
            }
        //TODO:log errors properly - remove printing stack trace.
        } catch (UnsupportedEncodingException e) {
            //http client - unsupported encoding
            e.printStackTrace();
        } catch (CharonException e) {
            //error in creating or encoding scim user
            e.printStackTrace();
        } catch (IOException e) {
            //error in invoking http client
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (BadRequestException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IdentitySCIMException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public int getPriority() {
        return PRIORITY;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*public boolean doesHandle(MessageContext messageContext) {
        String method;
        AxisOperation op = messageContext.getOperationContext().getAxisOperation();
        if ((op.getName() != null) && ((method =
                JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)) {
            if (method.equals(SOAP_ACTION)) {
                return true;
            }
        }
        return false;
    }
*/

    @Override
    public String getSOAPAction() {
        return SOAP_ACTION;
    }

    public boolean isDisabled() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getExtensionName() {
        return EXTENSION_NAME;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean skipServiceInvocation() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean skipLowerPriorityExtensions() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isAfterServiceCall() {
        return true;
    }
}
