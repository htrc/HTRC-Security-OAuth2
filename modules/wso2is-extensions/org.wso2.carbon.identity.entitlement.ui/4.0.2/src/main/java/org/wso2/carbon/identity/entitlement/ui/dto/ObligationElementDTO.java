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

package org.wso2.carbon.identity.entitlement.ui.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *  encapsulates obligation and advice expression data
 */
public class ObligationElementDTO {

    public static  final int OBLIGATION = 1;

    public static  final int ADVICE = 2;

    private int type;

    private String id;

    private String effect;

    private List<AttributeAssignmentElementDTO> assignmentElementDTOs =
            new ArrayList<AttributeAssignmentElementDTO>();


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AttributeAssignmentElementDTO> getAssignmentElementDTOs() {
        return assignmentElementDTOs;
    }

    public void setAssignmentElementDTOs(List<AttributeAssignmentElementDTO> assignmentElementDTOs) {
        this.assignmentElementDTOs = assignmentElementDTOs;
    }

    public void addAssignmentElementDTO(AttributeAssignmentElementDTO assignmentElementDTO) {
        this.assignmentElementDTOs.add(assignmentElementDTO);
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
