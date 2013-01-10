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
package org.wso2.carbon.identity.scim.provider.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.CarbonConstants;
import org.wso2.carbon.identity.scim.common.group.SCIMGroupHandler;
import org.wso2.carbon.identity.scim.common.impl.AbstractProvisioningHandler;
import org.wso2.carbon.identity.scim.common.utils.AttributeMapper;
import org.wso2.carbon.identity.scim.common.utils.IdentitySCIMException;
import org.wso2.carbon.identity.scim.common.utils.SCIMCommonUtils;
import org.wso2.carbon.user.api.Claim;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.UserCoreConstants;
import org.wso2.carbon.user.core.UserStoreManager;
import org.wso2.carbon.user.core.claim.ClaimManager;
import org.wso2.carbon.user.core.common.AbstractUserStoreManager;
import org.wso2.charon.core.attributes.Attribute;
import org.wso2.charon.core.exceptions.CharonException;
import org.wso2.charon.core.exceptions.NotFoundException;
import org.wso2.charon.core.extensions.UserManager;
import org.wso2.charon.core.objects.Group;
import org.wso2.charon.core.objects.SCIMObject;
import org.wso2.charon.core.objects.User;
import org.wso2.charon.core.schema.SCIMConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SCIMUserManager extends AbstractProvisioningHandler implements UserManager, Runnable {
    private UserStoreManager carbonUM = null;
    private ClaimManager carbonClaimManager = null;
    private String consumerName;

    private static Log log = LogFactory.getLog(SCIMUserManager.class);

    //to make provisioning to other providers asynchronously happen.
    private ExecutorService provisioningThreadPool = Executors.newFixedThreadPool(1);

    //variables used in runnable's run method in a particular instance of the object:
    private SCIMObject objectToBeProvisioned;
    private int provisioningMethod;

    public SCIMUserManager(UserStoreManager carbonUserStoreManager, String userName,
                           ClaimManager claimManager) {
        this.initConfigManager();
        carbonUM = carbonUserStoreManager;
        consumerName = userName;
        carbonClaimManager = claimManager;
    }

    //constructor used to initialize the object to be passes as an runnable to the thread pool.

    public SCIMUserManager(String userName, SCIMObject scimObject, int httpMethod) {
        consumerName = userName;
        objectToBeProvisioned = scimObject;
        provisioningMethod = httpMethod;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        try {
            this.provision(consumerName, objectToBeProvisioned, provisioningMethod);
        } catch (IdentitySCIMException e) {
            log.error("Error occurred in provisioning operation.");
            log.error(e.getMessage());
            provisioningThreadPool.shutdown();
        }
    }

    public User createUser(User user) throws CharonException {
        Map<String, String> claimsMap = AttributeMapper.getClaimsMap(user);
        //TODO: Do not accept the roles list - it is read only.
        try {
            carbonUM.addUser(user.getUserName(), user.getPassword(), null, claimsMap, null);
            log.info("User: " + user.getUserName() + " is created through SCIM.");
            //if a consumer is registered for this SCIM operation, provision as appropriate
            if (isSCIMConsumerEnabled(consumerName)) {
                //this.provision(consumerName, user, SCIMConstants.POST);
                provisioningThreadPool.submit(new SCIMUserManager(consumerName, user, SCIMConstants.POST));
                if (log.isDebugEnabled()) {
                    log.debug("User: " + user.getUserName() + " submitted for provisioning.");
                }
            }
        } catch (UserStoreException e) {
            throw new CharonException("Error in adding the user: " + user.getUserName() +
                                      " to the user store..", e);
        }
        return user;
    }

    public User getUser(String userId) throws CharonException {
        if (log.isDebugEnabled()) {
            log.debug("Retrieving user: " + userId);
        }
        User scimUser = null;
        try {
            //get the user name of the user with this id
            String[] userNames = carbonUM.getUserList(SCIMConstants.ID_URI, userId,
                                                      UserCoreConstants.DEFAULT_PROFILE);

            if (userNames == null && userNames.length == 0) {
                if (log.isDebugEnabled()) {
                    log.debug("User with SCIM id: " + userId + " does not exist in the system.");
                }
                return null;
            } else if (userNames != null && userNames.length == 0) {
                if (log.isDebugEnabled()) {
                    log.debug("User with SCIM id: " + userId + " does not exist in the system.");
                }
                return null;
            } else {
                //we assume (since id is unique per user) only one user exists for a given id
                scimUser = this.getSCIMUser(userNames[0]);

                log.info("User: " + scimUser.getUserName() + " is retrieved through SCIM.");
            }

        } catch (UserStoreException e) {
            throw new CharonException("Error in getting user information from Carbon User Store for" +
                                      "user: " + userId);
        }
        return scimUser;
    }

    public List<User> listUsers() throws CharonException {
        List<User> users = new ArrayList();
        try {
            String[] userNames = carbonUM.listUsers("*", -1);
            if (userNames != null && userNames.length != 0) {
                for (String userName : userNames) {
                    User scimUser = this.getSCIMUser(userName);
                    Map<String, Attribute> attrMap = scimUser.getAttributeList();
                    if (attrMap != null && !attrMap.isEmpty()) {
                        users.add(this.getSCIMUser(userName));
                    }
                }
            } else {
                return null;
            }
        } catch (org.wso2.carbon.user.core.UserStoreException e) {
            throw new CharonException("Error while retrieving users from user store..");
        }
        return users;
    }

    public List<User> listUsersByAttribute(Attribute attribute) {
        return null;
    }

    public List<User> listUsersByFilter(String s) {
        return null;
    }

    public List<User> listUsersBySort(String s, String s1) {
        return null;
    }

    public List<User> listUsersWithPagination(int i, int i1) {
        return null;
    }

    public User updateUser(User user) throws CharonException {
        if (log.isDebugEnabled()) {
            log.debug("Updating user: " + user.getUserName());
        }
        try {
            //get user claim values
            Map<String, String> claims = AttributeMapper.getClaimsMap(user);
            //set user claim values
            carbonUM.setUserClaimValues(user.getUserName(), claims, null);
            //if password is updated, set it separately
            if (user.getPassword() != null) {
                carbonUM.updateCredentialByAdmin(user.getUserName(), user.getPassword());
            }
            log.info("User: " + user.getUserName() + " updated successfully.");
            if (isSCIMConsumerEnabled(consumerName)) {
                provisioningThreadPool.submit(new SCIMUserManager(consumerName, user, SCIMConstants.PUT));
                if (log.isDebugEnabled()) {
                    log.debug("User: " + user.getUserName() + " submitted for provisioning.");
                }
            }
        } catch (org.wso2.carbon.user.core.UserStoreException e) {
            throw new CharonException("Error while updating attributes of user: " + user.getUserName());
        }

        return user;
    }

    public User updateUser(List<Attribute> attributes) {
        return null;
    }

    public void deleteUser(String userId) throws NotFoundException, CharonException {
        if (log.isDebugEnabled()) {
            log.debug("Deleting user: " + userId);
        }
        //get the user name of the user with this id
        String[] userNames = null;
        String userName = null;
        try {
            userNames = carbonUM.getUserList(SCIMConstants.ID_URI, userId,
                                             UserCoreConstants.DEFAULT_PROFILE);
            if (userNames == null && userNames.length == 0) {
                //resource with given id not found
                if (log.isDebugEnabled()) {
                    log.debug("User with id: " + userId + " not found.");
                }
                throw new NotFoundException();
            } else if (userNames != null && userNames.length == 0) {
                //resource with given id not found
                if (log.isDebugEnabled()) {
                    log.debug("User with id: " + userId + " not found.");
                }
                throw new NotFoundException();
            } else {
                //we assume (since id is unique per user) only one user exists for a given id
                userName = userNames[0];
                carbonUM.deleteUser(userName);
                log.info("User: " + userName + " is deleted through SCIM.");
            }
            if (isSCIMConsumerEnabled(consumerName)) {
                User user = new User();
                user.setId(userId);
                provisioningThreadPool.submit(new SCIMUserManager(consumerName, user, SCIMConstants.DELETE));
                if (log.isDebugEnabled()) {
                    log.debug("User: " + userId + " submitted for provisioning.");
                }
            }
        } catch (org.wso2.carbon.user.core.UserStoreException e) {
            throw new CharonException("Error in deleting user: " + userName);
        }
    }

    public Group createGroup(Group group) throws CharonException {
        try {
            /*if members are sent when creating the group, check whether users already exist in the
            user store*/
            List<String> userIds = group.getMembers();
            List<String> userDisplayNames = group.getMembersWithDisplayName();
            if (userIds != null && userIds.size() != 0) {
                for (String userId : userIds) {
                    String[] userNames = carbonUM.getUserList(SCIMConstants.ID_URI, userId,
                                                              UserCoreConstants.DEFAULT_PROFILE);
                    if (userNames == null || userNames.length == 0) {
                        String error = "User: " + userId + " doesn't exist in the user store. " +
                                       "Hence, can not create the group: " + group.getDisplayName();
                        throw new IdentitySCIMException(error);
                    } else {
                        if (!userDisplayNames.contains(userNames[0])) {
                            throw new IdentitySCIMException("Given SCIM user Id and name not matching..");
                        }
                    }
                }

                List<String> members = group.getMembersWithDisplayName();
                /*if (members != null && !members.isEmpty()) {
                for (String member : members) {
                    if (!carbonUM.isExistingUser(member)) {
                        String error = "User: " + member + " doesn't exist in the user store. " +
                                       "Hence, can not add the group: " + group.getDisplayName();
                        throw new IdentitySCIMException(error);
                    }
                }*/
                //add other scim attributes in the identity DB since user store doesn't support some attributes.
                SCIMGroupHandler scimGroupHandler = new SCIMGroupHandler(carbonUM.getTenantId());
                scimGroupHandler.createSCIMAttributes(group);
                carbonUM.addRole(group.getDisplayName(), members.toArray(new String[members.size()]), null);
                log.info("Group: " + group.getDisplayName() + " is created through SCIM.");
            } else {
                //add other scim attributes in the identity DB since user store doesn't support some attributes.
                SCIMGroupHandler scimGroupHandler = new SCIMGroupHandler(carbonUM.getTenantId());
                scimGroupHandler.createSCIMAttributes(group);
                carbonUM.addRole(group.getDisplayName(), null, null);
                log.info("Group: " + group.getDisplayName() + " is created through SCIM.");
            }
            if (isSCIMConsumerEnabled(consumerName)) {
                provisioningThreadPool.submit(new SCIMUserManager(consumerName, group, SCIMConstants.POST));
                if (log.isDebugEnabled()) {
                    log.debug("Group: " + group.getDisplayName() + " submitted for provisioning.");
                }
            }
        } catch (UserStoreException e) {
            throw new CharonException(e.getMessage());
        } catch (IdentitySCIMException e) {
            throw new CharonException(e.getMessage());
        }
        //TODO:after the group is added, read it from user store and return
        return group;
    }

    public Group getGroup(String id) throws CharonException {

        Group group = null;
        try {
            SCIMGroupHandler groupHandler = new SCIMGroupHandler(carbonUM.getTenantId());
            //get group name by Id
            String groupName = groupHandler.getGroupName(id);

            if (groupName != null) {
                group = getGroupWithName(groupName);
            } else {
                //returning null will send a resource not found error to client by Charon.
                return null;
            }
        } catch (org.wso2.carbon.user.core.UserStoreException e) {
            throw new CharonException("Error in retrieving group: " + id);
        } catch (IdentitySCIMException e) {
            throw new CharonException("Error in retrieving SCIM Group information from database.");
        }
        return group;
    }

    public List<Group> listGroups() throws CharonException {
        List<Group> groupList = new ArrayList<Group>();
        try {
            String[] roleNames = carbonUM.getRoleNames();
            //remove everyone and wso2anonymous role
            if (roleNames != null && roleNames.length != 0) {
                for (String roleName : roleNames) {
                    //skip internal roles
                    if ((CarbonConstants.REGISTRY_ANONNYMOUS_ROLE_NAME.equals(roleName)) ||
                        (((AbstractUserStoreManager) carbonUM).getEveryOneRoleName().equals(roleName)) ||
                        (((AbstractUserStoreManager) carbonUM).getAdminRoleName().equals(roleName))) {
                        continue;
                    }
                    groupList.add(this.getGroupWithName(roleName));
                }
            } else {
                return null;
            }
        } catch (org.wso2.carbon.user.core.UserStoreException e) {
            throw new CharonException("Error in obtaining role names from user store.");
        } catch (IdentitySCIMException e) {
            throw new CharonException("Error in retrieving SCIM Group information from database.");
        }
        return groupList;
    }

    public List<Group> listGroupsByAttribute(Attribute attribute) throws CharonException {
        return null;
    }

    public List<Group> listGroupsByFilter(String s) throws CharonException {
        return null;
    }

    public List<Group> listGroupsBySort(String s, String s1) throws CharonException {
        return null;
    }

    public List<Group> listGroupsWithPagination(int i, int i1) {
        return null;
    }

    public Group updateGroup(Group oldGroup, Group newGroup) throws CharonException {
        try {
            boolean updated = false;

            //check if the user ids sent in updated group exist in the user store and the associated user name
            //also a matching one.
            List<String> userIds = newGroup.getMembers();
            List<String> userDisplayNames = newGroup.getMembersWithDisplayName();
            if (userIds != null && userIds.size() != 0) {
                String[] userNames = null;
                for (String userId : userIds) {
                    userNames = carbonUM.getUserList(SCIMConstants.ID_URI, userId,
                                                     UserCoreConstants.DEFAULT_PROFILE);
                    if (userNames == null || userNames.length == 0) {
                        String error = "User: " + userId + " doesn't exist in the user store. " +
                                       "Hence, can not update the group: " + oldGroup.getDisplayName();
                        throw new IdentitySCIMException(error);
                    } else {
                        if (!userDisplayNames.contains(userNames[0])) {
                            throw new IdentitySCIMException("Given SCIM user Id and name not matching..");
                        }
                    }
                }
            }

            SCIMGroupHandler groupHandler = new SCIMGroupHandler(carbonUM.getTenantId());

            //update name if it is changed
            if (!(oldGroup.getDisplayName().equals(newGroup.getDisplayName()))) {
                //update group name in carbon UM
                carbonUM.updateRoleName(oldGroup.getDisplayName(), newGroup.getDisplayName());
                //update group name in SCIM_DB
                groupHandler.updateRoleName(oldGroup.getDisplayName(), newGroup.getDisplayName());
                updated = true;
            }

            //find out added members and deleted members..
            List<String> oldMembers = oldGroup.getMembersWithDisplayName();
            List<String> newMembers = newGroup.getMembersWithDisplayName();

            List<String> addedMembers = new ArrayList<String>();
            List<String> deletedMembers = new ArrayList<String>();

            //check for deleted members
            if (oldMembers != null && oldMembers.size() != 0) {
                for (String oldMember : oldMembers) {
                    if (newMembers != null && newMembers.contains(oldMember)) {
                        continue;
                    }
                    deletedMembers.add(oldMember);
                }
            }

            //check for added members
            if (newMembers != null && newMembers.size() != 0) {
                for (String newMember : newMembers) {
                    if (oldMembers != null && oldMembers.contains(newMember)) {
                        continue;
                    }
                    addedMembers.add(newMember);
                }
            }

            if (addedMembers.size() != 0 || deletedMembers.size() != 0) {
                carbonUM.updateUserListOfRole(newGroup.getDisplayName(),
                                              deletedMembers.toArray(new String[deletedMembers.size()]),
                                              addedMembers.toArray(new String[addedMembers.size()]));
                updated = true;
            }


            if (updated) {
                log.info("Group: " + newGroup.getDisplayName() + " is updated through SCIM.");
                /*if (isSCIMConsumerEnabled(consumerName)) {
                    this.provision(consumerName, newGroup, SCIMConstants.PUT);
                }*/
                if (isSCIMConsumerEnabled(consumerName)) {
                    provisioningThreadPool.submit(new SCIMUserManager(consumerName, newGroup, SCIMConstants.PUT));
                    if (log.isDebugEnabled()) {
                        log.debug("Group: " + newGroup.getDisplayName() + " submitted for provisioning.");
                    }
                }
            } else {
                log.warn("There is no updated field in the group: " + oldGroup.getDisplayName() +
                         ". Therefore ignoring the provisioning.");
            }

        } catch (UserStoreException e) {
            throw new CharonException(e.getMessage());
        } catch (IdentitySCIMException e) {
            throw new CharonException(e.getMessage());
        }
        return newGroup;
    }

    public Group updateGroup(List<Attribute> attributes) throws CharonException {
        return null;
    }

    public void deleteGroup(String groupId) throws NotFoundException, CharonException {
        try {
            //get group name by id
            SCIMGroupHandler groupHandler = new SCIMGroupHandler(carbonUM.getTenantId());
            String groupName = groupHandler.getGroupName(groupId);

            if (groupName != null) {
                //delete group in carbon UM
                carbonUM.deleteRole(groupName);

                //delete scim specific attributes stored in identity scim db
                groupHandler.deleteGroupAttributes(groupName);

                log.info("Group: " + groupName + " is deleted through SCIM.");

                //create a group object only with the group id and pass it to provision manager.
                Group group = new Group();
                group.setId(groupId);
                if (isSCIMConsumerEnabled(consumerName)) {
                    provisioningThreadPool.submit(new SCIMUserManager(consumerName, group, SCIMConstants.DELETE));
                    if (log.isDebugEnabled()) {
                        log.debug("Group: " + groupId + " submitted for provisioning.");
                    }
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Group with SCIM id: " + groupId + " doesn't exist in the system.");
                }
                throw new NotFoundException();
            }
        } catch (UserStoreException e) {
            throw new CharonException(e.getMessage());
        } catch (IdentitySCIMException e) {
            throw new CharonException(e.getMessage());
        }
    }

    @Override
    public boolean isSCIMConsumerEnabled(String consumerName) {
        //check if providers registered for consumerName
        return ((provisioningManager.isConsumerRegistered(consumerName)) &&
                (provisioningManager.isAppliedToSCIMOperation(consumerName)));
    }

    private User getSCIMUser(String userName) throws CharonException {
        User scimUser = null;
        try {
            //get claims related to SCIM claim dialect
            Claim[] claims = carbonClaimManager.getAllClaims(SCIMCommonUtils.SCIM_CLAIM_DIALECT);

            List<String> claimURIList = new ArrayList<String>();
            for (Claim claim : claims) {
                claimURIList.add(claim.getClaimUri());
            }
            //obtain user claim values
            Map<String, String> attributes = carbonUM.getUserClaimValues(
                    userName, claimURIList.toArray(new String[claimURIList.size()]), null);
            //get groups of user and add it as groups attribute
            String[] roles = carbonUM.getRoleListOfUser(userName);
            //TODO: for the moment, add groups under roles - change this to groups attribute
            // after group id support is implemented
            String roleValues = null;
            for (String role : roles) {
                if (roleValues != null) {
                    roleValues += role + ",";
                } else {
                    roleValues = role + ",";
                }
            }
            //construct the SCIM Object from the attributes
            scimUser = (User) AttributeMapper.constructSCIMObjectFromAttributes(
                    attributes, SCIMConstants.USER_INT);
        } catch (UserStoreException e) {
            throw new CharonException("Error in getting user information from Carbon User Store for" +
                                      "user: " + userName);
        } catch (CharonException e) {
            throw new CharonException("Error in getting user information from Carbon User Store for" +
                                      "user: " + userName);
        } catch (NotFoundException e) {
            throw new CharonException("Error in getting user information from Carbon User Store for" +
                                      "user: " + userName);
        }
        return scimUser;
    }

    private Group getGroupWithName(String groupName)
            throws CharonException, org.wso2.carbon.user.core.UserStoreException,
                   IdentitySCIMException {
        Group group = new Group();
        group.setDisplayName(groupName);
        String[] userNames = carbonUM.getUserListOfRole(groupName);

        //get the ids of the users and set them in the group with id + display name
        if (userNames != null && userNames.length != 0) {
            for (String userName : userNames) {
                User user = this.getSCIMUser(userName);
                if (user != null) {
                    group.setMember(user.getId(), userName);
                }
            }
        }
        //get other group attributes and set.
        SCIMGroupHandler groupHandler = new SCIMGroupHandler(carbonUM.getTenantId());
        group = groupHandler.getGroupWithAttributes(group, groupName);
        return group;
    }
}
