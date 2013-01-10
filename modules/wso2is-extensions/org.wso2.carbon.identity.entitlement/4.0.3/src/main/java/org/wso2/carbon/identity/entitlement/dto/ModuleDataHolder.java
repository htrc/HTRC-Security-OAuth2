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

import org.wso2.carbon.registry.core.Resource;

import java.util.*;

/**
 * 
 */
public class ModuleDataHolder {

    public static final String MODULE_NAME = "EntitlementModuleName";

    private String moduleName;

    private ModulePropertyDTO[] propertyDTOs;

    private ModuleStatusHolder[] statusHolders;

    public ModuleDataHolder() {
    }

    public ModuleDataHolder(Resource resource) {

        List<ModulePropertyDTO> propertyDTOs = new ArrayList<ModulePropertyDTO>();
        if(resource != null && resource.getProperties() != null){
            Properties properties = resource.getProperties();
            for(Map.Entry<Object, Object> entry : properties.entrySet()){
                ModulePropertyDTO dto = new ModulePropertyDTO();
                dto.setId((String)entry.getKey());
                Object value = entry.getValue();
                if(value instanceof ArrayList){
                    List list = (ArrayList) entry.getValue();
                    if(list != null && list.size() > 0 && list.get(0) != null){
                        dto.setValue((String)list.get(0));
                        if(list.size() > 1  && list.get(1) != null){
                            dto.setDisplayName((String)list.get(1));
                        }
                        if(list.size() > 2  && list.get(2) != null){
                            dto.setDisplayOrder(Integer.parseInt((String)list.get(2)));
                        }
                        if(list.size() > 3  && list.get(3) != null){
                            dto.setRequired(Boolean.parseBoolean((String)list.get(3)));
                        }
                    }
                } else {
                    dto.setValue((String)value);    
                }

                if(MODULE_NAME.equals(dto.getId())){
                    moduleName = dto.getValue();
                    continue;
                }

                propertyDTOs.add(dto);
            }
        }

        this.propertyDTOs = propertyDTOs.toArray(new ModulePropertyDTO[propertyDTOs.size()]);
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public ModulePropertyDTO[] getPropertyDTOs() {
        return propertyDTOs;
    }

    public void setPropertyDTOs(ModulePropertyDTO[] propertyDTOs) {
        this.propertyDTOs = propertyDTOs;
    }

    public ModuleStatusHolder[] getStatusHolders() {
        return statusHolders;
    }

    public void setStatusHolders(ModuleStatusHolder[] statusHolders) {
        this.statusHolders = statusHolders;
    }

}
