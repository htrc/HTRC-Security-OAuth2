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

package org.wso2.carbon.identity.entitlement.policy.publisher;

import org.wso2.carbon.identity.entitlement.dto.ModuleDataHolder;
import org.wso2.carbon.identity.entitlement.dto.ModulePropertyDTO;
import org.wso2.carbon.identity.entitlement.internal.EntitlementServiceComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * This is  abstract implementation of PolicyPublisherModule. Here we have implemented the init()
 * method.
 * If you want to configure properties of a publisher module from management UI, 
 * you want to write your publisher module by extending this abstract class
 *
 * Then you can init() your module each time policy is published.
 */
public abstract class AbstractPolicyPublisherModule implements PolicyPublisherModule{

    protected static final String REQUIRED = "required";

    protected static final String DISPLAY_NAME= "displayName";

    protected static final String ORDER = "order";

    public void init(Properties properties) throws Exception {

        List<ModulePropertyDTO>  propertyDTOs = new ArrayList<ModulePropertyDTO>();
        
        if(properties == null || properties.size() == 0){
            properties = loadProperties();
        }

        if(properties != null){
            for(Map.Entry<Object, Object> entry : properties.entrySet()){

                Map attributeMap;

                Object value = entry.getValue();
                if(value instanceof Map){
                    attributeMap = (Map) value;
                } else {
                    return;
                }

                ModulePropertyDTO dto = new ModulePropertyDTO();
                dto.setModule(getModuleName());
                dto.setId((String)entry.getKey());
                if(attributeMap.get(DISPLAY_NAME) != null){
                    dto.setDisplayName((String)attributeMap.get(DISPLAY_NAME));
                } else {
                    throw new Exception("Invalid policy publisher configuration : Display name can not be null");
                }
                if(attributeMap.get(ORDER) != null){
                    dto.setDisplayOrder(Integer.parseInt((String)attributeMap.get(ORDER)));
                }
                if(attributeMap.get(REQUIRED) != null){
                    dto.setRequired(Boolean.parseBoolean((String)attributeMap.get(REQUIRED)));
                }
                propertyDTOs.add(dto);
            }
        }

        ModulePropertyDTO preDefined1 = new ModulePropertyDTO();
        preDefined1.setId(PolicyPublisher.SUBSCRIBER_ID);
        preDefined1.setModule(getModuleName());
        preDefined1.setDisplayName(PolicyPublisher.SUBSCRIBER_DISPLAY_NAME);
        preDefined1.setRequired(true);
        preDefined1.setDisplayOrder(0);
        propertyDTOs.add(preDefined1);

        ModuleDataHolder holder = new ModuleDataHolder();
        holder.setModuleName(getModuleName());
        holder.setPropertyDTOs(propertyDTOs.toArray(new ModulePropertyDTO[propertyDTOs.size()]));
        EntitlementServiceComponent.getEntitlementConfig().
                            addModulePropertyHolder(PolicyPublisherModule.class.getName(), holder);

    }

    @Override
    public Properties loadProperties() {
        return null;
    }

    /**
     * This would init module, each time policy is published
     *
     * @param propertyHolder publisher module data as ModuleDataHolder
     * @throws Exception throws if init fails
     */
    public abstract void init(ModuleDataHolder propertyHolder) throws Exception;

}
