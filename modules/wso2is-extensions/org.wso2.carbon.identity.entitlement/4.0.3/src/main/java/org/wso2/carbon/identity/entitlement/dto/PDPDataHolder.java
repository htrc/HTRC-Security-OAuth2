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


package org.wso2.carbon.identity.entitlement.dto;

/**
 * 
 */
public class PDPDataHolder {

    private String[] policyFinders;

    private String[] pipAttributeFinders;

    private String[] pipResourceFinders;

    private boolean decisionCacheEnable;

    public String[] getPolicyFinders() {
        return policyFinders;
    }

    public void setPolicyFinders(String[] policyFinders) {
        this.policyFinders = policyFinders;
    }

    public String[] getPipAttributeFinders() {
        return pipAttributeFinders;
    }

    public void setPipAttributeFinders(String[] pipAttributeFinders) {
        this.pipAttributeFinders = pipAttributeFinders;
    }

    public String[] getPipResourceFinders() {
        return pipResourceFinders;
    }

    public void setPipResourceFinders(String[] pipResourceFinders) {
        this.pipResourceFinders = pipResourceFinders;
    }

    public boolean isDecisionCacheEnable() {
        return decisionCacheEnable;
    }

    public void setDecisionCacheEnable(boolean decisionCacheEnable) {
        this.decisionCacheEnable = decisionCacheEnable;
    }
}
