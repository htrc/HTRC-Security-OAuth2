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

package org.wso2.carbon.identity.entitlement.policy.store;

import org.wso2.carbon.identity.entitlement.dto.PolicyStoreDTO;

import java.util.Properties;

/**
 *
 */
public interface CarbonPolicyStore {

    /**
     * init policy store module
     *
     * @param properties
     */
    public void init(Properties properties);

    /**
     * add policy in to the store
     *
     * @param policy
     */
    public boolean addPolicy(PolicyStoreDTO policy);

    /**
     * set policy combining algorithm
     *
     * @param algorithm
     */
    public void setPolicyCombiningAlgorithm(String algorithm);

    /**
     * delete policy from the store
     *
     * @param policyIdentifier
     */
    public boolean deletePolicy(String policyIdentifier);



}
