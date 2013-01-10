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

package org.wso2.carbon.identity.entitlement.policy.search;

import org.wso2.carbon.identity.entitlement.dto.AttributeValueDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *   TODO
 */
public class CarbonPolicySearchAttributeFinderModule implements PolicySearchAttributeFinderModule{

    public void init() {

    }

    public String getAppId() {
        return null;
    }

    @Override
    public Map<String, Set<AttributeValueDTO>> getPossibleRequestAttributes(AttributeValueDTO[]
                                                                    mandatoryRequestAttributes) {
        return null;
    }

    @Override
    public Map<String, AttributeValueDTO> getPossibleRequestAttributesPerPolicy(String policyId) {
        return null;
    }

    public boolean isAllCombinationsSupported() {
        return false;
    }

    public boolean isMultipleDecisionSupport() {
        return false;
    }

    public Set<String> getAllCombinationSupportedCategories() {
        return null; 
    }
}
