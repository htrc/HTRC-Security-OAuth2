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
package org.wso2.carbon.identity.entitlement.ui;

/**
 * Policy editor related constants
 */
public class PolicyEditorConstants {


    public static final String ATTRIBUTE_SEPARATOR = ",";

    public static final String TARGET_ELEMENT = "Target";

    public static final String ANY_OF_ELEMENT = "AnyOf"; 

    public static final String ALL_OF_ELEMENT = "AllOf";

    public static final String COMBINE_FUNCTION_AND = "AND";

    public static final String COMBINE_FUNCTION_OR = "OR";

    public static final String COMBINE_FUNCTION_END = "END";

    public static final String MATCH_ELEMENT = "Match";

    public static final String  MATCH_ID = "MatchId";

    public static final String  ATTRIBUTE_ID = "AttributeId";

    public static final String  CATEGORY = "Category";

    public static final String  DATA_TYPE = "DataType";

    public static final String  ISSUER = "Issuer";

    public static final String  SOA_CATEGORY_USER = "User";

    public static final String  SOA_CATEGORY_RESOURCE = "Resource";

    public static final String  SOA_CATEGORY_ACTION = "Action";

    public static final String  SOA_CATEGORY_ENVIRONMENT = "Environment";

    public static final String  MUST_BE_PRESENT = "MustBePresent";

    public static final String ATTRIBUTE_DESIGNATOR = "AttributeDesignator";


    public static final class RuleFunctions {

        public static final String FUNCTION_GREATER_EQUAL_AND_LESS_EQUAL = "greater-than-or-equal-and-less-than-or-equal";

        public static final String FUNCTION_GREATER_AND_LESS_EQUAL= "greater-than-and-less-than-or-equal";

        public static final String FUNCTION_GREATER_EQUAL_AND_LESS = "greater-than-or-equal-and-less-than";

        public static final String FUNCTION_GREATER_AND_LESS = "greater-than-and-less-than";

        public static final String FUNCTION_GREATER = "greater-than";

        public static final String FUNCTION_GREATER_EQUAL = "greater-than-or-equal";

        public static final String FUNCTION_LESS = "less-than";

        public static final String FUNCTION_AT_LEAST_ONE = "at-least-one-member-of";

        public static final String FUNCTION_IS_IN = "is-in";

        public static final String FUNCTION_SET_EQUALS = "set-equals";

        public static final String FUNCTION_LESS_EQUAL = "less-than-or-equal";

        public static final String FUNCTION_ADD = "add";

        public static final String FUNCTION_MULTIPLY = "multiply";

        public static final String FUNCTION_SUBTRACT = "sub";

        public static final String FUNCTION_DIVIDE = "divide";

        public static final String FUNCTION_EQUAL = "equal";

    }

    public static final class PreFunctions {

        public static final String PRE_FUNCTION_IS = "is";

        public static final String PRE_FUNCTION_IS_NOT = "is-not";
        
        public static final String PRE_FUNCTION_ARE = "are";

        public static final String PRE_FUNCTION_ARE_NOT = "are-not";

        public static final String CAN_DO = "can";

        public static final String CAN_NOT_DO = "can not";
    }

    public static final class TargetPreFunctions {

        public static final String PRE_FUNCTION_IS = "is";

    }

    public static final class BasicEditorFunctions {

        public static final String EQUAL = "equal";

        public static final String REGEXP_EQUAL = "regexp equal";

    }
    public static final class TargetFunctions {

        public static final String FUNCTION_EQUAL = "equal";
        
    }


    public static final String RULE_EFFECT_PERMIT = "Permit";

    public static final String RULE_EFFECT_DENY = "Deny";

    public static final String DAY_TIME_DURATION  = "http://www.w3.org/2001/XMLSchema#dayTimeDuration";
    
    public static final String YEAR_MONTH_DURATION  = "http://www.w3.org/2001/XMLSchema#yearMonthDuration";

    public static final String STRING_DATA_TYPE = "http://www.w3.org/2001/XMLSchema#string";    


    public static final class CombiningAlog {

        public static final String RULE_COMBINING_DENY_OVERRIDE = "deny-overrides";

        public static final String RULE_COMBINING_PERMIT_OVERRIDE = "permit-overrides";

        public static final String RULE_COMBINING_FIRST_APPLICABLE = "first-applicable";

        public static final String RULE_COMBINING_ORDER_PERMIT_OVERRIDE = "ordered-permit-overrides";

        public static final String RULE_COMBINING_ORDER_DENY_OVERRIDE = "ordered-deny-overrides";

        public static final String RULE_COMBINING_DENY_UNLESS_PERMIT = "deny-unless-permit";

        public static final String RULE_COMBINING_PERMIT_UNLESS_DENY = "permit-unless-deny";
        
    }



    public static final String RULE_ALGORITHM_IDENTIFIER_1 = "urn:oasis:names:tc:xacml:1.0:" +
                                                                        "rule-combining-algorithm:";

    public static final String RULE_ALGORITHM_IDENTIFIER_3 = "urn:oasis:names:tc:xacml:3.0:" +
                                                                        "rule-combining-algorithm:";

    public static final String POLICY_EDITOR_SEPARATOR = "|";

    public static final int POLICY_EDITOR_ROW_DATA = 7;

    public static final String DYNAMIC_SELECTOR_CATEGORY =  "Category";

    public static final String DYNAMIC_SELECTOR_FUNCTION =  "Function";

    public static final String SUBJECT_ID_DEFAULT= "urn:oasis:names:tc:xacml:1.0:subject:subject-id";

    public static final String SUBJECT_ID_ROLE= "http://wso2.org/claims/role";

    public static final String RESOURCE_ID_DEFAULT = "urn:oasis:names:tc:xacml:1.0:resource:resource-id";

    public static final String ACTION_ID_DEFAULT = "urn:oasis:names:tc:xacml:1.0:action:action-id";

    public static final String ENVIRONMENT_ID_DEFAULT = "urn:oasis:names:tc:xacml:1.0:environment:environment-id";

    public static final String RESOURCE_CATEGORY_URI = "urn:oasis:names:tc:xacml:3.0:" +
            "attribute-category:resource";

    public static final String SUBJECT_CATEGORY_URI = "urn:oasis:names:tc:xacml:1.0:" +
            "subject-category:access-subject";

    public static final String ACTION_CATEGORY_URI = "urn:oasis:names:tc:xacml:3.0:" +
            "attribute-category:action";

    public static final String ENVIRONMENT_CATEGORY_URI = "urn:oasis:names:tc:xacml:3.0:" +
            "attribute-category:environment";

    public static final String SOA_POLICY_EDITOR = "SOA";

    public static final String ANY = "*";    
}
