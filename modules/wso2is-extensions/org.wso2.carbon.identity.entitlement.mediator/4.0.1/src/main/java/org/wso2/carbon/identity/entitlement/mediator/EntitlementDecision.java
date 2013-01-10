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

package org.wso2.carbon.identity.entitlement.mediator;

import org.apache.axiom.om.OMElement;

/**
 * Encapsulate the XACML Decision with XACML response and time stamp
 */
public class EntitlementDecision {

    /**
     * XACML response
     */
    private OMElement[] response;

    /**
     * time stamp
     */
    private long cachedTime;

    public OMElement[] getResponse() {
        return response;
    }

    public void setResponse(OMElement[] response) {
        this.response = response;
    }

    public long getCachedTime() {
        return cachedTime;
    }

    public void setCachedTime(long cachedTime) {
        this.cachedTime = cachedTime;
    }
    
}
