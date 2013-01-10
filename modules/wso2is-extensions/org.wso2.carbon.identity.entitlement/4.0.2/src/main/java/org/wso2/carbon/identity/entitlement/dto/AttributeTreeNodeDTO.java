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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * encapsulate attribute node of a attribute tree which shows in the XACML policy editor UI
 * each node object represent one category.
 */
public class AttributeTreeNodeDTO {

    /**
     * Node name
     */
    private String name;

    /**
     * Category id of this node
     */
    private String categoryId;

    /**
     * Category unique uri of this node
     */
    private String categoryUri;

    /**
     * support data type of this node
     */
    private String[] attributeDataTypes = new String[]{};

    /**
     * supported attribute ids for this node
     */
    private String[] supportedAttributeIds = new String[]{};

    /**
     * whether node is defined by child node name or by full path name with parent node names
     */
    private boolean fullPathSupported;

    /**
     * whether node must be shown as hierarchical tree or flat tree in UI 
     */
    private boolean hierarchicalTree;

    /**
     * module name which node has been created.
     */
    private String moduleName;

    /**
     * children of the Node
     */
    private AttributeTreeNodeDTO[] childNodes = new AttributeTreeNodeDTO[]{};

    /**
     * default attribute data type supported by this node
     */
    private String defaultAttributeDataType;

    /**
     * default attribute id supported by this node
     */
    private String defaultAttributeId;

    public AttributeTreeNodeDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryUri() {
        return categoryUri;
    }

    public void setCategoryUri(String categoryUri) {
        this.categoryUri = categoryUri;
    }

    public AttributeTreeNodeDTO[] getChildNodes() {
        return  Arrays.copyOf(childNodes, childNodes.length);
    }

    public boolean isFullPathSupported() {
        return fullPathSupported;
    }

    public void setFullPathSupported(boolean fullPathSupported) {
        this.fullPathSupported = fullPathSupported;
    }

    public boolean isHierarchicalTree() {
        return hierarchicalTree;
    }

    public void setHierarchicalTree(boolean hierarchicalTree) {
        this.hierarchicalTree = hierarchicalTree;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String[] getAttributeDataTypes() {
        return  Arrays.copyOf(attributeDataTypes, attributeDataTypes.length);
    }

    public void setAttributeDataTypes(String[] attributeDataTypes) {
        this.attributeDataTypes = Arrays.copyOf(attributeDataTypes, attributeDataTypes.length);
    }

    public void setSupportedAttributeIds(String[] supportedAttributeIds) {
        this.supportedAttributeIds = Arrays.copyOf(supportedAttributeIds, supportedAttributeIds.length);
    }

    public void addAttributeDataType(String attributeDataType) {
        Set<String> attributeDataTypes = new HashSet<String>(Arrays.asList(this.attributeDataTypes));
        attributeDataTypes.add(attributeDataType);
        this.attributeDataTypes = attributeDataTypes.toArray(new String[attributeDataTypes.size()]);
    }

    public void setChildNodes(AttributeTreeNodeDTO[] childNodes) {
        this.childNodes = Arrays.copyOf(childNodes, childNodes.length);
    }

    public void addChildNode(AttributeTreeNodeDTO node){
        Set<AttributeTreeNodeDTO> valueNodes = new HashSet<AttributeTreeNodeDTO>(Arrays.asList(this.childNodes));
        valueNodes.add(node);
        this.childNodes = valueNodes.toArray(new AttributeTreeNodeDTO[valueNodes.size()]);
    }

    public String[] getSupportedAttributeIds() {
        return  Arrays.copyOf(supportedAttributeIds, supportedAttributeIds.length);
    }

    public void addSupportedAttributeId(String supportedAttributeId) {
        Set<String> attributeIds = new HashSet<String>(Arrays.asList(this.supportedAttributeIds));
        attributeIds.add(supportedAttributeId);
        this.supportedAttributeIds = attributeIds.toArray(new String[attributeIds.size()]);
    }

    public String getDefaultAttributeDataType() {
        return defaultAttributeDataType;
    }

    public void setDefaultAttributeDataType(String defaultAttributeDataType) {
        this.defaultAttributeDataType = defaultAttributeDataType;
    }

    public String getDefaultAttributeId() {
        return defaultAttributeId;
    }

    public void setDefaultAttributeId(String defaultAttributeId) {
        this.defaultAttributeId = defaultAttributeId;
    }    
}
