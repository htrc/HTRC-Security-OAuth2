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

import java.util.Properties;

/**
 * policy publisher module that is used to publish policies to external PDPs. External PDP can be
 * identity server or else can be any thing. Therefore this interface provide an extension to publish
 * policies to different modules.
 *
 */
public interface PolicyPublisherModule {

    /**
     * initializes policy publisher retriever module
     * 
	 * @param properties Properties, that need to initialize the module or that need to populate
     * in the publisher configuration user interface. These properties can be
     * defined in entitlement-config.xml file
     * @throws Exception throws when initialization is failed
     */
    public void init(Properties properties) throws Exception;

    /**
     * Load the properties need to initialize the module or that need to populate
     * in the publisher configuration user interface. These properties can be
     * 
     * @return Properties
     */
    public Properties loadProperties();

    /**
     * gets name of this module
     * 
     * @return name as String
     */
    public String getModuleName();

    /**
     * publishes policy to given subscriber
     *
     * @param policy policy as String
     * @throws Exception throws, if any error is occurred.
     */
    public void publish(String policy) throws Exception;

}
