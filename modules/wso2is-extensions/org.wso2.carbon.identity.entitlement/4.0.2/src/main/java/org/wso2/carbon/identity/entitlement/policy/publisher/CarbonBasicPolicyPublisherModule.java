/*
*  Copyright (c)  WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.carbon.identity.entitlement.policy.publisher;


import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.entitlement.dto.ModuleDataHolder;
import org.wso2.carbon.identity.entitlement.dto.ModulePropertyDTO;

import javax.xml.stream.XMLStreamException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Carbon implementation of PolicyPublisherModule
 */
public class CarbonBasicPolicyPublisherModule extends AbstractPolicyPublisherModule {

    private ConfigurationContext configCtx;

    private static final String MODULE_NAME = "Basic Auth Carbon Policy Publisher Module";
        
	private static Log log = LogFactory.getLog(CarbonBasicPolicyPublisherModule.class);

    private String serverUrl;

    private String serverUserName;

    private String serverPassword;

    @Override
    public void init(ModuleDataHolder propertyHolder) throws Exception {

        ModulePropertyDTO[] propertyDTOs = propertyHolder.getPropertyDTOs();
        for(ModulePropertyDTO dto : propertyDTOs){
            if("subscriberURL".equals(dto.getId())){
                serverUrl = dto.getValue();
            } else if("subscriberUserName".equals(dto.getId())){
                serverUserName = dto.getValue();
            } else if("subscriberPassword".equals(dto.getId())){
                serverPassword = dto.getValue();
            }
        }

        configCtx  = ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, null);
    }

    public String getModuleName() {
        return MODULE_NAME;
    }

    @Override
    public Properties loadProperties() {

        Properties properties = new Properties();

        Map<String, String> dataMap1 = new HashMap<String, String>();
        dataMap1.put(AbstractPolicyPublisherModule.REQUIRED, "true");
        dataMap1.put(AbstractPolicyPublisherModule.DISPLAY_NAME, "Subscriber URL");
        dataMap1.put(AbstractPolicyPublisherModule.ORDER, "1");

        Map<String, String> dataMap2 = new HashMap<String, String>();
        dataMap2.put(AbstractPolicyPublisherModule.REQUIRED, "true");
        dataMap2.put(AbstractPolicyPublisherModule.DISPLAY_NAME, "Subscriber UserName");
        dataMap2.put(AbstractPolicyPublisherModule.ORDER, "2");

        Map<String, String> dataMap3 = new HashMap<String, String>();
        dataMap3.put(AbstractPolicyPublisherModule.REQUIRED, "true");
        dataMap3.put(AbstractPolicyPublisherModule.DISPLAY_NAME, "Subscriber Password");
        dataMap3.put(AbstractPolicyPublisherModule.ORDER, "3");

        properties.put("subscriberURL", dataMap1);
        properties.put("subscriberUserName", dataMap2);
        properties.put("subscriberPassword", dataMap3);

        return properties;
    }

    public void publish(String policy) throws Exception {

        String  body = createBody(policy);

        if(serverUrl != null){
            serverUrl = serverUrl.trim();
            if (!serverUrl.endsWith("/")) {
                serverUrl += "/";
            }
        }

        String serverEndPoint = serverUrl + "EntitlementPolicyAdminService";

        MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpClient httpClient = new HttpClient(httpConnectionManager);
        ServiceClient client = null;

        try{
            client = new ServiceClient(configCtx, null);
            Options option = client.getOptions();
            option.setManageSession(true);
            HttpTransportProperties.Authenticator authenticator =
                                                new HttpTransportProperties.Authenticator();
            authenticator.setUsername(serverUserName);
            authenticator.setPassword(serverPassword);
            authenticator.setPreemptiveAuthentication(true);
            option.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, authenticator);
            option.setProperty(Constants.Configuration.TRANSPORT_URL, serverEndPoint);
            option.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Constants.VALUE_TRUE);
            option.setProperty(HTTPConstants.CACHED_HTTP_CLIENT, httpClient);
            OMElement omElement = client.sendReceive(AXIOMUtil.stringToOM(body));
            boolean success = false;
            if(omElement != null){
                success = Boolean.parseBoolean(omElement.getFirstElement().getText());
            }
            if(!success){
                throw new Exception("Policy publish fails due : Unexpected error has occurred");    
            }
        } catch (AxisFault axisFault) {
            log.error("Policy publish fails due : " + axisFault.getMessage(), axisFault);
            throw new Exception("Policy publish fails due : " + axisFault.getMessage());
        } catch (XMLStreamException e) {
            log.error("Policy publish fails due : " + e.getMessage(), e);
            throw new Exception("Policy publish fails due : " + e.getMessage());
        } finally {
            if(client != null){
                try{
                    client.cleanupTransport();
                    client.cleanup();
                } catch (AxisFault axisFault) {
                    log.error("Error while cleaning HTTP client", axisFault);
                }
            }
        }
    }
  
    private String createBody(String policy){

        return "      <xsd:addPolicy xmlns:xsd=\"http://org.apache.axis2/xsd\" xmlns:xsd1=\"http://dto.entitlement.identity.carbon.wso2.org/xsd\">" +
                "         <xsd:policy>" +
                "            <xsd1:active>false</xsd1:active>" +
                "             <xsd1:policy><![CDATA[" + policy + "]]>  </xsd1:policy>" +
                "          </xsd:policy>" +
                "      </xsd:addPolicy>";
    }
}
