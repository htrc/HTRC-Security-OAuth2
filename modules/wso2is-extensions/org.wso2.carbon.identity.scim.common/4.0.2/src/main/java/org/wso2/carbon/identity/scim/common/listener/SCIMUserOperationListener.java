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
package org.wso2.carbon.identity.scim.common.listener;

import org.wso2.carbon.identity.scim.common.group.SCIMGroupHandler;
import org.wso2.carbon.identity.scim.common.utils.IdentitySCIMException;
import org.wso2.carbon.identity.scim.common.utils.SCIMCommonUtils;
import org.wso2.carbon.user.api.Claim;
import org.wso2.carbon.user.core.Permission;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.UserStoreManager;
import org.wso2.carbon.user.core.common.AbstractUserStoreManager;
import org.wso2.carbon.user.core.listener.UserOperationEventListener;
import org.wso2.carbon.user.core.claim.ClaimManager;
import org.wso2.charon.core.schema.SCIMConstants;
import org.wso2.charon.core.util.AttributeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This is to perform SCIM related operation on User Operations.
 * For eg: when a user is created through UserAdmin API, we need to set some SCIM specific properties
 * as user attributes.
 */
public class SCIMUserOperationListener implements UserOperationEventListener {
    public int getExecutionOrderId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean doPreAuthenticate(String s, Object o, UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostAuthenticate(String userName, boolean authenticated,
                                      UserStoreManager userStoreManager)
            throws UserStoreException {
        return authenticated;
    }

    public boolean doPreAddUser(String s, Object o, String[] strings,
                                Map<String, String> stringStringMap, String s1,
                                UserStoreManager userStoreManager) throws UserStoreException {
        return true;
    }

    public boolean doPostAddUser(String userName, UserStoreManager userStoreManager)
            throws UserStoreException {
        //add mandatory attributes in core schema like id, meta attributes etc

        //if SCIM Enabled.
        if (((AbstractUserStoreManager) userStoreManager).isSCIMEnabled()) {
            //userStoreManager.get
            try {
                //get claim manager from user store manager
                ClaimManager claimManager = ((AbstractUserStoreManager) userStoreManager).getClaimManager();

                //get claims related to SCIM claim dialect
                Claim[] claims = claimManager.getAllClaims(SCIMCommonUtils.SCIM_CLAIM_DIALECT);
                List<String> claimURIList = new ArrayList<String>();
                for (Claim claim : claims) {
                    claimURIList.add(claim.getClaimUri());
                }
                //obtain user claim values (since user is already added at this point by CARBON UM)
                Map<String, String> attributes = userStoreManager.getUserClaimValues(
                        userName, claimURIList.toArray(new String[claimURIList.size()]), null);
                //if null, or if id attribute not present, add them
                if (attributes != null && !attributes.isEmpty()) {
                    if (!attributes.containsKey(SCIMConstants.ID_URI)) {
                        Map<String, String> updatesAttributes = this.getSCIMAttributes(userName, attributes);
                        userStoreManager.setUserClaimValues(userName, updatesAttributes, null);
                    }
                } else {
                    Map<String, String> newAttributes = this.getSCIMAttributes(userName, null);
                    userStoreManager.setUserClaimValues(userName, newAttributes, null);
                }
            } catch (org.wso2.carbon.user.api.UserStoreException e) {
                throw new UserStoreException("Error when updating SCIM attributes of the user.");
            }
        }

        return true;
    }

    public boolean doPreUpdateCredential(String s, Object o, Object o1,
                                         UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostUpdateCredential(String s, UserStoreManager userStoreManager)
            throws UserStoreException {
        //TODO: set last modified time
        return true;
    }

    public boolean doPreUpdateCredentialByAdmin(String s, Object o,
                                                UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostUpdateCredentialByAdmin(String s, UserStoreManager userStoreManager)
            throws UserStoreException {
        //TODO: set last modified time
        return true;
    }

    public boolean doPreDeleteUser(String s, UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostDeleteUser(String s, UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPreSetUserClaimValue(String s, String s1, String s2, String s3,
                                          UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostSetUserClaimValue(String s, UserStoreManager userStoreManager)
            throws UserStoreException {
        //TODO: need to set last modified time.
        return true;
    }

    public boolean doPreSetUserClaimValues(String s, Map<String, String> stringStringMap, String s1,
                                           UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostSetUserClaimValues(String s, UserStoreManager userStoreManager)
            throws UserStoreException {
        //TODO: need to set last modified time.
        return true;
    }

    public boolean doPreDeleteUserClaimValues(String s, String[] strings, String s1,
                                              UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostDeleteUserClaimValues(String s, UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPreDeleteUserClaimValue(String s, String s1, String s2,
                                             UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostDeleteUserClaimValue(String s, UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPreAddRole(String s, String[] strings, Permission[] permissions,
                                UserStoreManager userStoreManager) throws UserStoreException {
        return true;
    }

    public boolean doPostAddRole(String roleName, String[] strings, Permission[] permissions,
                                 UserStoreManager userStoreManager) throws UserStoreException {
        SCIMGroupHandler scimGroupHandler = new SCIMGroupHandler(userStoreManager.getTenantId());
        //query role name from identity table
        try {
            if (!scimGroupHandler.isGroupExisting(roleName)) {
                //if no attributes - i.e: group added via mgt console, not via SCIM endpoint
                //add META
                scimGroupHandler.addMandatoryAttributes(roleName);
            }
        } catch (IdentitySCIMException e) {
            throw new UserStoreException("Error retrieving group information from SCIM Tables.", e);
        }
        return true;
    }

    public boolean doPreDeleteRole(String s, UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostDeleteRole(String roleName, UserStoreManager userStoreManager)
            throws UserStoreException {
        SCIMGroupHandler scimGroupHandler = new SCIMGroupHandler(userStoreManager.getTenantId());
        try {
            //delete group attributes - no need to check existence here,
            //since it is checked in below method.
            scimGroupHandler.deleteGroupAttributes(roleName);
        } catch (IdentitySCIMException e) {
            throw new UserStoreException("Error retrieving group information from SCIM Tables.", e);
        }
        return true;
    }

    public boolean doPreUpdateRoleName(String s, String s1, UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostUpdateRoleName(String s, String s1, UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPreUpdateUserListOfRole(String s, String[] strings, String[] strings1,
                                             UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostUpdateUserListOfRole(String s, String[] strings, String[] strings1,
                                              UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPreUpdateRoleListOfUser(String s, String[] strings, String[] strings1,
                                             UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostUpdateRoleListOfUser(String s, String[] strings, String[] strings1,
                                              UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public Map<String, String> getSCIMAttributes(String userName, Map<String, String> claimsMap) {
        Map<String, String> attributes = null;
        if (claimsMap != null && !claimsMap.isEmpty()) {
            attributes = claimsMap;
        } else {
            attributes = new HashMap<String, String>();
        }
        String id = UUID.randomUUID().toString();
        attributes.put(SCIMConstants.ID_URI, id);

        Date date = new Date();
        String createdDate = AttributeUtil.formatDateTime(date);
        attributes.put(SCIMConstants.META_CREATED_URI, createdDate);

        attributes.put(SCIMConstants.META_LAST_MODIFIED_URI, createdDate);

        attributes.put(SCIMConstants.USER_NAME_URI, userName);

        return attributes;
        //TODO: add other optional attributes like location etc.
    }

    //TODO:update last updated value etc if updated through um directly.
}
