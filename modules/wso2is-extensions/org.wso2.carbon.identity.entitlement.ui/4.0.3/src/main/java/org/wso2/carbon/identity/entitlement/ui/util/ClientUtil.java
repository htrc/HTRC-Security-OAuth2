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
package org.wso2.carbon.identity.entitlement.ui.util;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.llom.util.AXIOMUtil;

import javax.xml.namespace.QName;

/**
 *
 */
public class ClientUtil {
    /**
     * Helper method to extact the boolean response
     *
     * @param xmlstring XACML resource as String
     * @return  Decision
     * @throws Exception if fails
     */
    public static String getStatus(String xmlstring) throws Exception {
        OMElement response = null;
        OMElement result = null;
        OMElement decision = null;
        response = AXIOMUtil.stringToOM(xmlstring);

        result = response.getFirstChildWithName(new QName("Result"));
        if (result != null) {
            decision = result.getFirstChildWithName(new QName("Decision"));
            if (decision != null) {
                return decision.getText();
            }
        }

        return "Invalid Status";
    }
}
