/*
*  Copyright (c) WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.identity.entitlement.ui.client;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.entitlement.stub.EntitlementAdminServiceStub;
import org.wso2.carbon.identity.entitlement.stub.dto.ModuleDataHolder;
import org.wso2.carbon.identity.entitlement.stub.dto.PDPDataHolder;
import org.wso2.carbon.identity.entitlement.stub.dto.PIPFinderDataHolder;
import org.wso2.carbon.identity.entitlement.stub.dto.PolicyFinderDataHolder;
import org.wso2.carbon.identity.entitlement.ui.util.ClientUtil;

/**
 * 
 */
public class EntitlementAdminServiceClient {

    private EntitlementAdminServiceStub stub;

    private static final Log log = LogFactory.getLog(EntitlementAdminServiceClient.class);

    /**
     * Instantiates EntitlementServiceClient
     *
     * @param cookie For session management
     * @param backendServerURL URL of the back end server where EntitlementPolicyAdminService is
     *        running.
     * @param configCtx ConfigurationContext
     * @throws org.apache.axis2.AxisFault
     */
    public EntitlementAdminServiceClient(String cookie, String backendServerURL,
            ConfigurationContext configCtx) throws AxisFault {
        String serviceURL = backendServerURL + "EntitlementAdminService";
        stub = new EntitlementAdminServiceStub(configCtx, serviceURL);
        ServiceClient client = stub._getServiceClient();
        Options option = client.getOptions();
        option.setManageSession(true);
        option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, cookie);
    }

    /**
     * Clears the decision cache maintained by the PDP.
     * @throws AxisFault
     */
    public void clearDecisionCache() throws AxisFault {

        try {
            stub.clearDecisionCache();
        } catch (Exception e) {
            String message = e.getMessage();
            handleException(message, e);
        }
    }

    /**
     * Clears the attribute cache maintained by the PDP.
     * @throws AxisFault
     */
    public void clearAttributeCache() throws AxisFault {

        try {
            stub.clearAllAttributeCaches();
        } catch (Exception e) {
            String message = e.getMessage();
            handleException(message, e);
        }
    }

    /**
     * Gets all subscriber ids
     *
     * @return subscriber ids as String array
     * @throws AxisFault throws
     */
    public String[] getSubscriberIds() throws AxisFault {

        try {
            return stub.getSubscriberIds();
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Gets subscriber data
     *
     * @param id subscriber id
     * @return subscriber data as SubscriberDTO object
     * @throws AxisFault throws
     */
    public ModuleDataHolder getSubscriber(String id) throws AxisFault {

        try {
            return stub.getSubscriber(id);
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Updates or creates subscriber data
     *
     * @param holder subscriber data as ModuleDataHolder object
     * @throws AxisFault throws
     */
    public void updateSubscriber(ModuleDataHolder holder,boolean update) throws AxisFault {

        try {
            stub.updateSubscriber(holder,update);
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }
    }

    /**
     * Removes publisher data
     *
     * @param id subscriber id
     * @throws AxisFault throws
     */
    public void deleteSubscriber(String id) throws AxisFault {

        try {
            stub.deleteSubscriber(id);
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }
    }

    /**
     * Publishes given set of policies to given set of subscribers
     *
     * @param policies policy ids as String array, if null or empty, all policies are published
     * @param subscriberId subscriber ids as String array, if null or empty, publish to all subscribers
     * @throws AxisFault throws
     */
    public void publishAll(String[] policies, String[] subscriberId) throws AxisFault {

        try {
            stub.publishPolicies(policies, subscriberId);
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }
    }

    /**
     * Get all publisher modules properties that is needed to configure
     *
     * @return publisher modules properties as ModuleDataHolder
     * @throws AxisFault throws
     */
    public ModuleDataHolder[] getPublisherModuleProperties() throws AxisFault {

        try {
            return stub.getPublisherModuleProperties();
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Evaluate XACML request with PDP
     *
     * @param request  XACML request as String
     * @return XACML response as String
     * @throws AxisFault if fails
     */
    public String getDecision(String request) throws AxisFault {
        try {
            if(request != null){
                request = request.trim().replaceAll("&lt;", "<"); //TODO should be properly fixed
                request = request.trim().replaceAll("&gt;", ">");
            }
            return ClientUtil.getStatus(stub.doTestRequest(request));
        } catch (Exception e) {
            handleException("Error occurred while policy evaluation", e);
        }
        return null;
    }

    public PDPDataHolder getPDPData() throws AxisFault {

        try {
            return stub.getPDPData();
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }

        return null;
    }


    public PolicyFinderDataHolder getPolicyFinderData(String finderName) throws AxisFault {

        try {
            return stub.getPolicyFinderData(finderName);
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }

        return null;
    }

    public PIPFinderDataHolder getPIPAttributeFinderData(String finderName) throws AxisFault {

        try {
            return stub.getPIPAttributeFinderData(finderName);
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }

        return null;
    }

    public PIPFinderDataHolder getPIPResourceFinderData(String finderName) throws AxisFault {

        try {
            return stub.getPIPResourceFinderData(finderName);
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }

        return null;
    }

    public void refreshAttributeFinder(String finderName) throws AxisFault {

        try {
            stub.refreshAttributeFinder(finderName);
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }
    }

    public void refreshResourceFinder(String finderName) throws AxisFault {

        try {
            stub.refreshResourceFinder(finderName);
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }
    }

    public void refreshPolicyFinder(String finderName) throws AxisFault {

        try {
            stub.refreshPolicyFinders(finderName);
        } catch (Exception e) {
            handleException(e.getMessage(), e);
        }
    }

    /**
     * Logs and wraps the given exception.
     *
     * @param msg Error message
     * @param e Exception
     * @throws AxisFault
     */
    private void handleException(String msg, Exception e) throws AxisFault {
        log.error(msg, e);
        throw new AxisFault(msg, e);
    }
}
