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

package org.wso2.carbon.identity.entitlement.dto;

import java.util.Date;

/**
 *
 */
public class ModuleStatusHolder {

    /**
     * unique key to identify status
     */
    private String key;

    /**
     * whether this is success status or not
     */
    private boolean success;

    /**
     * time instance
     */
    private String timeInstance;

    /**
     * message
     */
    private String message;

    public ModuleStatusHolder(String key, String message) {
        this.key = key;
        this.message = message;
        this.success = false;
        this.timeInstance = (new Date()).toString();
    }

    public ModuleStatusHolder(String key) {
        this.key = key;
        this.success = true;
        this.timeInstance = (new Date()).toString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTimeInstance() {
        return timeInstance;
    }

    public void setTimeInstance(String timeInstance) {
        this.timeInstance = timeInstance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
