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

package org.wso2.carbon.identity.entitlement.policy.finder;

import org.wso2.carbon.identity.entitlement.dto.AttributeDTO;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Policy finder module is a extension point where XACML policies can be load in to the PDP
 * from different sources.
 *
 */
public interface CarbonPolicyFinderModule {

    /**
     * Policy search is done by creating requests from all combinations of the attributes that is
     * found by policy finder module 
     */
    public final static int ALL_COMBINATIONS = 0;

    /**
     * Policy search is done by creating requests from combinations of category of the attributes that is
     * found by policy finder module
     */
    public final static int COMBINATIONS_BY_CATEGORY = 1;

    /**
     * Policy search is done by creating requests from combinations of given parameter
     * of the attributes that is found by policy finder module
     */
    public final static int COMBINATIONS_BY_PARAMETER = 2;

    /**
     * Policy search is done by creating requests from combinations of given parameter
     * and category of the attributes that is found by policy finder module
     */
    public final static int COMBINATIONS_BY_CATEGORY_AND_PARAMETER = 3;

    /**
     * Policy search is done by creating requests from the attributes that is
     * found by policy finder module
     */
    public final static int NO_COMBINATIONS = 4;


    /**
     * initializes policy finder retriever module
     *
	 * @param properties Properties, that need to initialize the module
     * @throws Exception throws when initialization is failed
     */
    public void init(Properties properties) throws Exception;

    /**
     * gets name of this module
     *
     * @return name as String
     */
    public String getModuleName();

    /**
     * gets priority of the module, if integer value is high, then priority is also high 
     *
     * @return  integer value
     */
    public int getModulePriority();

    /**
     * 
     * @return
     */
    public String[] getPolicies();

    /**
     *
     * @return
     */
    public String[] getPolicyIdentifiers();

    /**
     *
     * @param policyId
     * @return
     */
    public String getPolicy(String policyId);

    /**
     *
     * @param identifier
     * @param givenAttribute
     * @return
     */
    public Map<String, Set<AttributeDTO>> getSearchAttributes(String identifier, Set<AttributeDTO> givenAttribute);

    /**
     *
     * @return
     */
    public int getSupportedSearchAttributesScheme();

    /**
     *
     * @return
     */
    public boolean isDefaultCategoriesSupported();

    /**
     *
     * @return
     */
    public boolean isPolicyOrderingSupport();

    /**
     * 
     * @return
     */
    public String getPolicyCombiningAlgorithm();


}
