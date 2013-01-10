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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.CarbonContext;
import org.wso2.carbon.identity.scim.common.config.SCIMProvisioningConfigManager;
import org.wso2.carbon.identity.scim.common.group.SCIMGroupHandler;
import org.wso2.carbon.identity.scim.common.impl.DefaultSCIMProvisioningHandler;
import org.wso2.carbon.identity.scim.common.utils.AttributeMapper;
import org.wso2.carbon.identity.scim.common.utils.IdentitySCIMException;
import org.wso2.carbon.identity.scim.common.utils.SCIMCommonConstants;
import org.wso2.carbon.identity.scim.common.utils.SCIMCommonUtils;
import org.wso2.carbon.user.api.AuthorizationManager;
import org.wso2.carbon.user.api.Claim;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.core.Permission;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.UserStoreManager;
import org.wso2.carbon.user.core.claim.ClaimManager;
import org.wso2.carbon.user.core.common.AbstractUserStoreManager;
import org.wso2.carbon.user.core.listener.UserOperationEventListener;
import org.wso2.carbon.user.core.service.RealmService;
import org.wso2.charon.core.exceptions.CharonException;
import org.wso2.charon.core.exceptions.NotFoundException;
import org.wso2.charon.core.objects.Group;
import org.wso2.charon.core.objects.User;
import org.wso2.charon.core.schema.SCIMConstants;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.charon.core.util.AttributeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is to perform SCIM related operation on User Operations.
 * For eg: when a user is created through UserAdmin API, we need to set some SCIM specific properties
 * as user attributes.
 */
public class SCIMUserOperationListener implements UserOperationEventListener {

    private static Log log = LogFactory.getLog(SCIMUserOperationListener.class);

    //to make provisioning to other providers asynchronously happen.
    private ExecutorService provisioningThreadPool = Executors.newCachedThreadPool();

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

    public boolean doPostAddUser(String userName, Object credential, String[] roleList,
                                 Map<String, String> claims, String profile,
                                 UserStoreManager userStoreManager)
            throws UserStoreException {
        /*add mandatory attributes in core schema like id, meta attributes etc
        if SCIM Enabled in User Store and if not already added.*/
        Map<String, String> attributes = null;
        if (((AbstractUserStoreManager) userStoreManager).isSCIMEnabled()) {
            //userStoreManager.get
            try {
                //get claim manager from user store manager
                ClaimManager claimManager = ((AbstractUserStoreManager) userStoreManager).getClaimManager();

                //get existingClaims related to SCIM claim dialect
                Claim[] existingClaims = claimManager.getAllClaims(SCIMCommonUtils.SCIM_CLAIM_DIALECT);
                List<String> claimURIList = new ArrayList<String>();
                for (Claim claim : existingClaims) {
                    claimURIList.add(claim.getClaimUri());
                }
                //obtain user claim values (since user is already added at this point by CARBON UM)
                attributes = userStoreManager.getUserClaimValues(
                        userName, claimURIList.toArray(new String[claimURIList.size()]), null);
                //if null, or if id attribute not present, add them
                if (attributes != null && !attributes.isEmpty()) {
                    if (!attributes.containsKey(SCIMConstants.ID_URI)) {
                        Map<String, String> updatesAttributes = this.getSCIMAttributes(userName, attributes);
                        //set the thread local to avoid calling the listener for setUserClaimValues.
                        SCIMCommonUtils.setThreadLocalToSkipSetUserClaimsListeners(true);
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
        //do provisioning
        try {
            //identify the scim consumer from  carbon context and perform provisioning.
            String consumerUserId = getSCIMConsumerId();
            User user = null;
            if (consumerUserId != null && isProvisioningActionAuthorized(false, null) &&
                isSCIMConsumerEnabled(consumerUserId)) {
                //if user created through management console, claim values are not present.
                if (attributes != null && attributes.size() != 0) {
                    user = (User) AttributeMapper.constructSCIMObjectFromAttributes(
                            attributes, SCIMConstants.USER_INT);
                } else {
                    user = new User();
                    user.setUserName(userName);
                }
                user.setPassword((String) credential);
                /*In SCIM, we do not manage groups through user resource. Hence, no need to send group
                 info in createUser request.*/

                //but if groups are set (through) carbon APIs, then need to send a update group request as well.
                //but for the moment, do group mgt operations through group resource.
                provisioningThreadPool.submit(new DefaultSCIMProvisioningHandler(
                        consumerUserId, user, SCIMConstants.POST, null));
            }
        } catch (NotFoundException e) {
            throw new UserStoreException("Error in constructing SCIM object from attributes when provisioning.");
        } catch (CharonException e) {
            throw new UserStoreException("Error in constructing SCIM object from attributes when provisioning.");
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

    public boolean doPostUpdateCredentialByAdmin(String userName, Object credential,
                                                 UserStoreManager userStoreManager)
            throws UserStoreException {
        //update last-modified-date
        if (((AbstractUserStoreManager) userStoreManager).isSCIMEnabled()) {
            Date date = new Date();
            String lastModifiedDate = AttributeUtil.formatDateTime(date);
            userStoreManager.setUserClaimValue(
                    userName, SCIMConstants.META_LAST_MODIFIED_URI, lastModifiedDate, null);
        }
        //do provisioning
        try {
            //identify the scim consumer from the user name in carbon context and perform provisioning.
            String consumerUserId = getSCIMConsumerId();
            if (isProvisioningActionAuthorized(false, null) && isSCIMConsumerEnabled(consumerUserId)) {
                //create User with updated credentials
                User user = new User();
                user.setUserName(userName);
                user.setPassword((String) credential);
                provisioningThreadPool.submit(new DefaultSCIMProvisioningHandler(
                        consumerUserId, user, SCIMConstants.PUT, null));
            }
        } catch (CharonException e) {
            throw new UserStoreException(
                    "Error in provisioning 'update credential by admin' operation");
        }
        return true;
    }

    public boolean doPreDeleteUser(String userName, UserStoreManager userStoreManager)
            throws UserStoreException {
        //do provisioning
        try {
            //identify the scim consumer from the user name in carbon context and perform provisioning.
            String consumerUserId = getSCIMConsumerId();
            User user = null;
            if (isProvisioningActionAuthorized(false, null) && isSCIMConsumerEnabled(consumerUserId)) {
                user = new User();
                user.setUserName(userName);
                provisioningThreadPool.submit(new DefaultSCIMProvisioningHandler(
                        consumerUserId, user, SCIMConstants.DELETE, null));
            }
        } catch (CharonException e) {
            throw new UserStoreException("Error in provisioning delete operation");
        }
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

    public boolean doPreSetUserClaimValues(String userName, Map<String, String> claims,
                                           String profileName, UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostSetUserClaimValues(String userName, Map<String, String> claims,
                                            String profileName, UserStoreManager userStoreManager)
            throws UserStoreException {
        //check if it is specified to skip this listner.
        if ((SCIMCommonUtils.getThreadLocalToSkipSetUserClaimsListeners() != null &&
             !SCIMCommonUtils.getThreadLocalToSkipSetUserClaimsListeners()) ||
            (SCIMCommonUtils.getThreadLocalToSkipSetUserClaimsListeners() == null)) {
            //update last-modified-date and proceed if scim enabled.
            if (((AbstractUserStoreManager) userStoreManager).isSCIMEnabled()) {
                Date date = new Date();
                String lastModifiedDate = AttributeUtil.formatDateTime(date);
                userStoreManager.setUserClaimValue(
                        userName, SCIMConstants.META_LAST_MODIFIED_URI, lastModifiedDate, null);
                String userNameInClaims = userStoreManager.getUserClaimValue(
                        userName, SCIMConstants.USER_NAME_URI, null);
                // do provisioning
                try {
                    //identify the scim consumer from the user name in carbon context and perform provisioning.
                    String consumerUserId = getSCIMConsumerId();
                    User user = null;
                    if (isProvisioningActionAuthorized(true, userNameInClaims) &&
                        isSCIMConsumerEnabled(consumerUserId)) {
                        //if no claim values are present, no need to do provisioning.
                        if (claims != null && claims.size() != 0) {
                            ClaimManager claimManager =
                                    ((AbstractUserStoreManager) userStoreManager).getClaimManager();
                            if (claimManager != null) {
                                //get existingClaims related to SCIM claim dialect
                                Claim[] existingClaims = claimManager.getAllClaims(
                                        SCIMCommonUtils.SCIM_CLAIM_DIALECT);
                                List<String> claimURIList = new ArrayList<String>();
                                for (Claim claim : existingClaims) {
                                    claimURIList.add(claim.getClaimUri());
                                }
                                //obtain user claim values (since claims already updated at is point by CARBON UM)
                                Map<String, String> attributes = userStoreManager.getUserClaimValues(
                                        userName, claimURIList.toArray(new String[claimURIList.size()]), null);
                                user = (User) AttributeMapper.constructSCIMObjectFromAttributes(
                                        attributes, SCIMConstants.USER_INT);
                                provisioningThreadPool.submit(new DefaultSCIMProvisioningHandler(
                                        consumerUserId, user, SCIMConstants.PUT, null));
                            }
                        }
                    }
                } catch (CharonException e) {
                    throw new UserStoreException("Error in constructing SCIM User object from claims" +
                                                 "while provisioning 'update user' operation.");
                } catch (NotFoundException e) {
                    throw new UserStoreException("Error in constructing SCIM User object from claims" +
                                                 "while provisioning 'update user' operation.");
                } catch (org.wso2.carbon.user.api.UserStoreException e) {
                    throw new UserStoreException("Error in retrieving claim values while provisioning " +
                                                 "'update user' operation.");
                }
            }
        }
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

    public boolean doPostAddRole(String roleName, String[] userList, Permission[] permissions,
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
        //do provisioning
        try {
            //identify the scim consumer from the user name in carbon context and perform provisioning.
            String consumerUserId = getSCIMConsumerId();
            Group group = null;
            if (isProvisioningActionAuthorized(false, null) && isSCIMConsumerEnabled(consumerUserId)) {
                //if user created through management console, claim values are not present.
                group = new Group();
                group.setDisplayName(roleName);
                if (userList != null && userList.length != 0) {
                    for (String user : userList) {
                        Map<String, Object> members = new HashMap<String, Object>();
                        members.put(SCIMConstants.CommonSchemaConstants.DISPLAY, user);
                        group.setMember(members);
                    }
                }
                provisioningThreadPool.submit(new DefaultSCIMProvisioningHandler(
                        consumerUserId, group, SCIMConstants.POST, null));
            }
        } catch (CharonException e) {
            throw new UserStoreException("Error in constructing SCIM object from attributes when provisioning.");
        }

        return true;
    }

    public boolean doPreDeleteRole(String roleName, UserStoreManager userStoreManager)
            throws UserStoreException {
        SCIMGroupHandler scimGroupHandler = new SCIMGroupHandler(userStoreManager.getTenantId());
        try {
            //delete group attributes - no need to check existence here,
            //since it is checked in below method.
            scimGroupHandler.deleteGroupAttributes(roleName);
        } catch (IdentitySCIMException e) {
            throw new UserStoreException("Error retrieving group information from SCIM Tables.", e);
        }
        //do provisioning
        try {
            //identify the scim consumer from the user name in carbon context and perform provisioning.
            String consumerUserId = getSCIMConsumerId();
            Group group = null;
            if (isProvisioningActionAuthorized(false, null) && isSCIMConsumerEnabled(consumerUserId)) {
                group = new Group();
                group.setDisplayName(roleName);
                provisioningThreadPool.submit(new DefaultSCIMProvisioningHandler(
                        consumerUserId, group, SCIMConstants.DELETE, null));
            }
        } catch (CharonException e) {
            throw new UserStoreException("Error in provisioning delete operation");
        }
        return true;
    }

    public boolean doPostDeleteRole(String roleName, UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPreUpdateRoleName(String s, String s1, UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostUpdateRoleName(String roleName, String newRoleName,
                                        UserStoreManager userStoreManager)
            throws UserStoreException {
        //TODO:set last update date
        //do provisioning
        try {
            //identify the scim consumer from the user name in carbon context and perform provisioning.
            String consumerUserId = getSCIMConsumerId();
            if (isProvisioningActionAuthorized(false, null) && isSCIMConsumerEnabled(consumerUserId)) {
                //add old role name details.
                Map<String, Object> additionalInformation = new HashMap<String, Object>();
                additionalInformation.put(SCIMCommonConstants.IS_ROLE_NAME_CHANGED_ON_UPDATE, true);
                additionalInformation.put(SCIMCommonConstants.OLD_GROUP_NAME, roleName);

                //create group with updated role name
                Group group = new Group();
                group.setDisplayName(newRoleName);

                provisioningThreadPool.submit(new DefaultSCIMProvisioningHandler(
                        consumerUserId, group, SCIMConstants.PUT, additionalInformation));
            }
        } catch (CharonException e) {
            throw new UserStoreException("Error in provisioning delete operation");
        }
        return true;
    }

    public boolean doPreUpdateUserListOfRole(String s, String[] strings, String[] strings1,
                                             UserStoreManager userStoreManager)
            throws UserStoreException {
        return true;
    }

    public boolean doPostUpdateUserListOfRole(String roleName, String[] deletedUsers,
                                              String[] newUsers, UserStoreManager userStoreManager)
            throws UserStoreException {
        //TODO:set last update date
        //do provisioning
        try {
            String consumerUserId = getSCIMConsumerId();
            if (isProvisioningActionAuthorized(false, null) && isSCIMConsumerEnabled(consumerUserId)) {
                //create group with updated new user list
                Group group = new Group();
                group.setDisplayName(roleName);
                //get the user list of role..at this point, user list is updated through carbon UM
                String[] userList = userStoreManager.getUserListOfRole(roleName);
                if (userList != null && userList.length != 0) {
                    for (String user : userList) {
                        Map<String, Object> members = new HashMap<String, Object>();
                        members.put(SCIMConstants.CommonSchemaConstants.DISPLAY, user);
                        group.setMember(members);
                    }
                }
                provisioningThreadPool.submit(new DefaultSCIMProvisioningHandler(
                        consumerUserId, group, SCIMConstants.PUT, null));
            }
        } catch (CharonException e) {
            throw new UserStoreException("Error in provisioning delete operation");
        }
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

    private boolean isSCIMConsumerEnabled(String consumerName) {
        return SCIMProvisioningConfigManager.isConsumerRegistered(consumerName);
    }

    private String getSCIMConsumerId() throws CharonException {
        /*identify the scim consumer from the info in carbon context and the thread local variable
          which signals from which route the user management operation was invoked.*/
        String currentUser = CarbonContext.getCurrentContext().getUsername();
        String tenantDomain = CarbonContext.getCurrentContext().getTenantDomain();
        if (log.isDebugEnabled()) {
            log.debug("Provisioning consumer info: based on carbon context details:" +
                      "user name: " + currentUser + ", tenant domain: " + tenantDomain);
        }
        //construct user id
        String consumerUserId = null;
        if (SCIMCommonUtils.getThreadLocalIsManagedThroughSCIMEP() != null &&
            SCIMCommonUtils.getThreadLocalIsManagedThroughSCIMEP()) {
            consumerUserId = currentUser + "@" + tenantDomain;
        } else {
            consumerUserId = tenantDomain;
        }
        /*if (consumerUserId == null) {
            throw new CharonException("Consumer Id is null for the provisioning request");
        }*/
        return consumerUserId;
    }

    /**
     * Authorize provisioning action. If it is profile update, we allow normal users with login
     * permission to update their profile.
     * //TODO: to check whether one is updating their own profile, what we do now is to compare usernaeme
     * taken from carbon context with the username attribute of UserProfile. But userId can be something
     * else than username. Correct way is to check the corresponding SCIM attribute for the
     * UserNameAttribute of user-mgt.xml. Ref: SCIMUserManager#updateUser method.
     *
     * @param isProfileUpdate
     * @return
     */
    private boolean isProvisioningActionAuthorized(boolean isProfileUpdate,
                                                   String userNameOfProfile)
            throws UserStoreException {
        String currentUser = null;
        String tenantDomain = null;
        try {
            //get current user
            currentUser = CarbonContext.getCurrentContext().getUsername();
            tenantDomain = CarbonContext.getCurrentContext().getTenantDomain();
            if (currentUser != null && tenantDomain != null) {
                //get tenant realm and AuthorizationManager
                RealmService realmService = (RealmService)
                        PrivilegedCarbonContext.getCurrentContext().getOSGiService(RealmService.class);
                int tenantId = realmService.getTenantManager().getTenantId(tenantDomain);
                UserRealm userRealm = realmService.getTenantUserRealm(tenantId);
                AuthorizationManager authzManager = userRealm.getAuthorizationManager();
                //if it is a provisioning admin, authorize
                boolean authorized = authzManager.isUserAuthorized(
                        currentUser, SCIMCommonConstants.PROVISIONING_ADMIN_PERMISSION,
                        SCIMCommonConstants.RESOURCE_TO_BE_AUTHORIZED);
                if (authorized) {
                    return true;
                }
                //else, check if it is a profile update req and user is updating his profile.
                if (!authorized && isProfileUpdate) {
                    if (currentUser.equals(userNameOfProfile) &&
                        authzManager.isUserAuthorized(currentUser, SCIMCommonConstants.PROVISIONING_USER_PERMISSION,
                                                      SCIMCommonConstants.RESOURCE_TO_BE_AUTHORIZED)) {
                        return true;
                    }
                }
            }
        } catch (org.wso2.carbon.user.api.UserStoreException e) {
            throw new UserStoreException("Error in authorizing user: " + currentUser + tenantDomain
                                         + " for provisioning.");
        }
        return false;
    }

}
