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

package org.wso2.carbon.identity.entitlement.pap;

import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.pap.PolicyEditorDataFinderModule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Abstract implementation of PolicyEditorDataFinderModule
 */
public abstract class AbstractPolicyEditorDataFinder implements PolicyEditorDataFinderModule {

    @Override
    public Map<String, String> getSupportedAttributeIds(String attributeType) throws Exception {
        return null;
    }

    @Override
    public boolean isFullPathSupported() {
        return true;
    }

    @Override
    public boolean isHierarchicalTree() {
        return true; 
    }

    @Override
    public Set<String> getAttributeDataTypes(String attributeType) throws Exception {
        return null;
    }

    @Override
    public Map<String, String> getSupportedCategories() {
        
        Map<String, String> newMap = new HashMap<String, String>();
        
        newMap.put(EntitlementConstants.RESOURCE_CATEGORY_ID, EntitlementConstants.RESOURCE_CATEGORY_URI);
        newMap.put(EntitlementConstants.SUBJECT_CATEGORY_ID, EntitlementConstants.SUBJECT_CATEGORY_URI);
        newMap.put(EntitlementConstants.ACTION_CATEGORY_ID, EntitlementConstants.ACTION_CATEGORY_URI);
        newMap.put(EntitlementConstants.ENVIRONMENT_CATEGORY_ID, EntitlementConstants.ENVIRONMENT_CATEGORY_URI);
        return newMap;
    }

    
    @Override
    public String getDefaultAttributeId(String category) {
        
        if(EntitlementConstants.RESOURCE_CATEGORY_ID.equals(category)){
            return EntitlementConstants.RESOURCE_ID_DEFAULT;
        } else if(EntitlementConstants.ACTION_CATEGORY_ID.equals(category)){
            return EntitlementConstants.ACTION_ID_DEFAULT;
        } else if(EntitlementConstants.SUBJECT_CATEGORY_ID.equals(category)){
            return EntitlementConstants.SUBJECT_ID_DEFAULT;
        } else if(EntitlementConstants.ENVIRONMENT_CATEGORY_ID.equals(category)){
            return EntitlementConstants.ENVIRONMENT_ID_DEFAULT;
        }

        return null;
    }

    @Override
    public String getDefaultAttributeDataType(String category) {
        return EntitlementConstants.STRING_DATA_TYPE;
    }

    @Override
    public Map<String, String> getSupportedRuleFunctions() {
        Map<String, String> newMap = new HashMap<String, String>();
        newMap.put("greater-than-or-equal-and-less-than-or-equal to" , "greater-than-or-equal-and-less-than-or-equal");
        newMap.put("greater-than-and-less-than-or-equal to" , "greater-than-and-less-than-or-equal");
        newMap.put("greater-than-or-equal-and-less-than to" , "greater-than-or-equal-and-less-than");
        newMap.put("greater-than-and-less-than to" , "greater-than-and-less-than");
        newMap.put("greater-than to" , "greater-than");
        newMap.put("greater-than-or-equal to" , "greater-than-or-equal");
        newMap.put("less-than to" , "less-than");
        newMap.put("less-than-or-equal to" , "less-than-or-equal");
        newMap.put("equal to" , "equal");
        newMap.put("regexp equal to" , "regexp-match");
        newMap.put("equal with at least one of", "at-least-one-member-of");
        newMap.put("in", "is-in");
        newMap.put("equal with all of", "set-equals");
        return newMap;
    }

    @Override
    public Map<String, String> getSupportedTargetFunctions() {
        Map<String, String> newMap = new HashMap<String, String>();
        newMap.put("matching to" , "equal");
        newMap.put("regexp matching to" , "regexp-match");
        return newMap;
    }
       
}
