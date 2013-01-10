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

import org.wso2.carbon.CarbonConstants;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.wso2.carbon.identity.entitlement.dto.AttributeTreeNodeDTO;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;
import org.wso2.carbon.registry.api.Resource;
import org.wso2.carbon.registry.core.Collection;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.user.api.Claim;
import org.wso2.carbon.user.api.ClaimManager;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.UserCoreConstants;

import java.util.*;

/**
 * this is default implementation of the policy meta data finder module which finds the resource in the
 * under-line registry
 */
public class CarbonPolicyEditorDataFinder extends AbstractPolicyEditorDataFinder {

    public static final String MODULE_NAME =  "Carbon Attribute Finder Module";

    private Registry registry;

    private String[] defaultActions = new String[]{"read", "write", "delete", "edit"};
    
    @Override
    public void init(Properties properties) throws Exception {

    }

    @Override
    public String getModuleName() {
        return  MODULE_NAME;
    }

    @Override
    public AttributeTreeNodeDTO getAttributeValueData(String categoryId) throws Exception {

        registry = EntitlementServiceComponent.getRegistryService().getSystemRegistry(CarbonContext.
                getCurrentContext().getTenantId());        
        if(EntitlementConstants.RESOURCE_CATEGORY_ID.equals(categoryId)){
            AttributeTreeNodeDTO nodeDTO = new AttributeTreeNodeDTO("/");
            getChildResources(nodeDTO, "_system");
            return nodeDTO;
        } else if(EntitlementConstants.ACTION_CATEGORY_ID.equals(categoryId)){
            AttributeTreeNodeDTO nodeDTO = new AttributeTreeNodeDTO("");
            for (String action : defaultActions){
                AttributeTreeNodeDTO childNode = new AttributeTreeNodeDTO(action);
                nodeDTO.addChildNode(childNode);
            }
            return nodeDTO;
        } else if(EntitlementConstants.SUBJECT_CATEGORY_ID.equals(categoryId)){
            AttributeTreeNodeDTO nodeDTO = new AttributeTreeNodeDTO("");
            int tenantId =  CarbonContext.getCurrentContext().getTenantId();
            UserStoreManager userStoreManager = EntitlementServiceComponent.getRealmservice().
                    getTenantUserRealm(tenantId).getUserStoreManager();
            for(String role : userStoreManager.getRoleNames()){
                if(CarbonConstants.REGISTRY_ANONNYMOUS_ROLE_NAME.equals(role)){
                    continue;
                }
                AttributeTreeNodeDTO childNode = new AttributeTreeNodeDTO(role);
                nodeDTO.addChildNode(childNode);
            }
            return nodeDTO;
        }

        return null;
    }

    /**
     * This helps to find resources un a recursive manner
     * @param node attribute value node
     * @param parentResource  parent resource Name
     * @return child resource set
     * @throws org.wso2.carbon.registry.core.exceptions.RegistryException throws
     */
    private AttributeTreeNodeDTO getChildResources(AttributeTreeNodeDTO node,
                                                String parentResource)  throws RegistryException {

        if(registry.resourceExists(parentResource)){
            String[] resourcePath = parentResource.split("/");
            AttributeTreeNodeDTO childNode =
                                new AttributeTreeNodeDTO(resourcePath[resourcePath.length-1]);
            node.addChildNode(childNode);
            Resource root = registry.get(parentResource);
            if(root instanceof Collection){
                Collection collection = (Collection) root;
                String[] resources = collection.getChildren();
                for(String resource : resources){
                    getChildResources(childNode, resource);
                }
            }
        }

        return node;
    }


    @Override
    public boolean isHierarchicalTree() {
        return true;
    }

    @Override
    public Map<String, String> getSupportedAttributeIds(String category)  throws Exception {
        Map<String, String> values = new HashMap<String, String>();        
        if(EntitlementConstants.SUBJECT_CATEGORY_ID.equals(category)){
            int tenantId =  CarbonContext.getCurrentContext().getTenantId();
            ClaimManager claimManager = EntitlementServiceComponent.getRealmservice().
                    getTenantUserRealm(tenantId).getClaimManager();
            Claim[] claims = claimManager.getAllClaims(UserCoreConstants.DEFAULT_CARBON_DIALECT);
            for(Claim claim : claims){
                if(claim.isSupportedByDefault()){
                    values.put(claim.getDisplayTag(), claim.getClaimUri());
                }
            }
        }
        return values;
    }

    @Override
    public Set<String> getAttributeDataTypes(String attributeType) throws Exception {        
        Set<String> values = new HashSet<String>();
        values.add("http://www.w3.org/2001/XMLSchema#string");
        values.add("http://www.w3.org/2001/XMLSchema#boolean");
        values.add("http://www.w3.org/2001/XMLSchema#integer");
        values.add("http://www.w3.org/2001/XMLSchema#double");
        values.add("http://www.w3.org/2001/XMLSchema#time");
        values.add("http://www.w3.org/2001/XMLSchema#date");
        values.add("urn:oasis:names:tc:xacml:1.0:data-type:x500Name");
        values.add("urn:oasis:names:tc:xacml:2.0:data-type:ipAddress");
        return values;
    }
}
