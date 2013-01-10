/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.carbon.identity.scim.common.utils;

import org.wso2.carbon.base.ServerConfiguration;

/**
 * This class is to be used as a Util class for SCIM common things.
 * TODO:rename class name.
 */
public class SCIMCommonUtils {

    public static final String SCIM_CLAIM_DIALECT = "urn:scim:schemas:core:1.0";
    private static String scimGroupLocation;
    private static String scimUserLocation;

    public static void init() {
        //to initialize scim urls once.
        //construct SCIM_USER_LOCATION and SCIM_GROUP_LOCATION like: https://localhost:9443/wso2/scim/Groups
        String portOffSet = ServerConfiguration.getInstance().getFirstProperty("Ports.Offset");
        int httpsPort = 9443 + Integer.parseInt(portOffSet);
        String scimURL = "https://" + ServerConfiguration.getInstance().getFirstProperty("HostName")
                         + ":" + String.valueOf(httpsPort) + "/wso2/scim/";
        scimUserLocation = scimURL + "Users/";
        scimGroupLocation = scimURL + "Groups/";
    }

    public static String getSCIMUserURL(String id) {
        return scimUserLocation + id;
    }

    public static String getSCIMGroupURL(String id) {
        return scimGroupLocation + id;
    }

    public static String getSCIMUserURL(){
        return scimUserLocation;
    }

    public static String getSCIMGroupURL(){
        return scimGroupLocation;
    }
}
