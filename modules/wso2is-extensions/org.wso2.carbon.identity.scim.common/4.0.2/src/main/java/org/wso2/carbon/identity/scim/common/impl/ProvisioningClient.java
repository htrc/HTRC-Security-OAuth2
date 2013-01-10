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
package org.wso2.carbon.identity.scim.common.impl;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.charon.core.client.SCIMClient;
import org.wso2.charon.core.exceptions.AbstractCharonException;
import org.wso2.charon.core.exceptions.BadRequestException;
import org.wso2.charon.core.exceptions.CharonException;
import org.wso2.charon.core.schema.SCIMConstants;

import java.io.IOException;

public class ProvisioningClient implements Runnable {

    private static Log logger = LogFactory.getLog(ProvisioningClient.class.getName());
    private HttpClient httpClient;
    private HttpMethodBase httpMethod;
    private int objectType;

    public ProvisioningClient(HttpClient httpClient, HttpMethodBase httpMethod, int scimObjectType) {
        this.httpClient = httpClient;
        this.httpMethod = httpMethod;
        this.objectType = scimObjectType;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        try {
            SCIMClient scimClient = new SCIMClient();

            int responseStatus = httpClient.executeMethod(httpMethod);
            logger.info("SCIM - operation returned with response code: " + responseStatus);

            String response = httpMethod.getResponseBodyAsString();
            if (logger.isDebugEnabled()) {
                logger.debug(response);
            }
            if (scimClient.evaluateResponseStatus(responseStatus)) {
                //try to decode the scim object to verify that it gets decoded without issue.
                scimClient.decodeSCIMResponse(response, SCIMConstants.JSON,
                                              objectType);
            } else {
                //decode scim exception and extract the specific error message.
                AbstractCharonException exception =
                        scimClient.decodeSCIMException(response, SCIMConstants.JSON);
                logger.error(exception.getDescription());
            }
        } catch (BadRequestException e) {
            logger.error("Error when decoding SCIM response obtained from provisioning operation.");
        } catch (CharonException e) {
            logger.error("Error when decoding SCIM response obtained from provisioning operation.");
        } catch (HttpException e) {
            logger.error("Error when invoking http client for provisioning.");
        } catch (IOException e) {
            logger.error("Error when invoking http client for provisioning or when obtaining the response.");
        }

    }
}
