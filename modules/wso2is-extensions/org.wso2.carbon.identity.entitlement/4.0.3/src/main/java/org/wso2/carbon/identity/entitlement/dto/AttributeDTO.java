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

package org.wso2.carbon.identity.entitlement.dto;

/**
 * This encapsulates the attribute element data of the XACML policy
 */
public class AttributeDTO {

    private String attributeValue;

    private String attributeDataType;

    private String attributeId;

    private String attributeType;

    private String functionId;

    private String policyId;

    public String getAttributeDataType() {
        return attributeDataType;
    }

    public void setAttributeDataType(String attributeDataType) {
        this.attributeDataType = attributeDataType;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeDTO)) return false;

        AttributeDTO dto = (AttributeDTO) o;

        if (attributeDataType != null ? !attributeDataType.equals(dto.attributeDataType) : dto.attributeDataType != null)
            return false;
        if (attributeId != null ? !attributeId.equals(dto.attributeId) : dto.attributeId != null)
            return false;
        if (attributeType != null ? !attributeType.equals(dto.attributeType) : dto.attributeType != null)
            return false;
        if (attributeValue != null ? !attributeValue.equals(dto.attributeValue) : dto.attributeValue != null)
            return false;
        if (functionId != null ? !functionId.equals(dto.functionId) : dto.functionId != null)
            return false;
        if (policyId != null ? !policyId.equals(dto.policyId) : dto.policyId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = attributeValue != null ? attributeValue.hashCode() : 0;
        result = 31 * result + (attributeDataType != null ? attributeDataType.hashCode() : 0);
        result = 31 * result + (attributeId != null ? attributeId.hashCode() : 0);
        result = 31 * result + (attributeType != null ? attributeType.hashCode() : 0);
        result = 31 * result + (functionId != null ? functionId.hashCode() : 0);
        result = 31 * result + (policyId != null ? policyId.hashCode() : 0);
        return result;
    }
}
