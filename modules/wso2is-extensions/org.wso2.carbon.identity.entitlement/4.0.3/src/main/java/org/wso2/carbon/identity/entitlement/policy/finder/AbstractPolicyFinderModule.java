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

import org.wso2.balana.ParsingException;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.entitlement.EntitlementUtil;
import org.wso2.carbon.identity.entitlement.cache.DecisionCache;
import org.wso2.carbon.identity.entitlement.cache.EntitlementPolicyCache;
import org.wso2.carbon.identity.entitlement.pdp.EntitlementEngine;

import java.util.*;

/**
 * Abstract implementation of a policy finder module. This can be easily extended by any module that does not
 * support policy ordering and that support dynamic policy changes
 */
public abstract class AbstractPolicyFinderModule implements CarbonPolicyFinderModule {


    /**
     * This method must be called by the module when its policies are updated
     */
    public final void invalidateCache() {
        DecisionCache.getInstance().invalidateCache();
        EntitlementPolicyCache.getInstance().invalidateCache();
    }

    @Override
    public String[] getPolicies() {
        
        List<String> policies = new ArrayList<String>();

        String[] policyIdentifiers = getPolicyIdentifiers();
        if(policyIdentifiers != null){
            //sort identifiers according to natural order
            Arrays.sort(policyIdentifiers);
            for(String identifier : policyIdentifiers){
                String policy = getPolicy(identifier);
                policies.add(policy);
            }
        }

        return policies.toArray(new String[policies.size()]);
    }


    @Override
    public boolean isDefaultCategoriesSupported() {
        return true;
    }

    @Override
    public String getPolicyCombiningAlgorithm() {
        return null;
    }

    @Override
    public boolean isPolicyOrderingSupport() {
        return false;
    }
}
