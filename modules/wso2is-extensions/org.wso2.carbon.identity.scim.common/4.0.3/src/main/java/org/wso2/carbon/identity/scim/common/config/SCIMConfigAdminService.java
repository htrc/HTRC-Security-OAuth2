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
package org.wso2.carbon.identity.scim.common.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.scim.common.utils.IdentitySCIMException;
import org.wso2.carbon.identity.scim.common.utils.SCIMCommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the service class for SCIMConfigAdminService which exposes the functionality of
 * managing SCIM Configuration -both globally(per tenant) and individually(per user account)
 */
public class SCIMConfigAdminService {

    public static final Log log = LogFactory.getLog(SCIMConfigAdminService.class);
    /*Global Providers Operations..*/

    public SCIMProviderDTO[] getAllGlobalProviders(String consumerId) throws IdentitySCIMException {
        SCIMProviderDTO[] scimProviderDTOs = new SCIMProviderDTO[0];
        //check if given tenant id matches with the logged in user tenant id.
        /*if (SCIMCommonUtils.providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> globalProviders = SCIMCommonUtils.providers.get(consumerId);
            if (globalProviders != null && globalProviders.size() != 0) {
                scimProviderDTOs = new SCIMProviderDTO[globalProviders.size()];
                int i = 0;
                for (SCIMProviderDTO globalProvider : globalProviders) {
                    scimProviderDTOs[i] = globalProvider;
                    i++;
                }
                return scimProviderDTOs;
            }
        }*/
        if (consumerId.equals(SCIMCommonUtils.getGlobalConsumerId())) {
            SCIMProviderDAO providerDAO = new SCIMProviderDAO();
            List<SCIMProviderDTO> globalProviders = providerDAO.getAllProviders(consumerId);
            if (globalProviders != null && globalProviders.size() != 0) {
                scimProviderDTOs = new SCIMProviderDTO[globalProviders.size()];
                int i = 0;
                for (SCIMProviderDTO globalProvider : globalProviders) {
                    scimProviderDTOs[i] = globalProvider;
                    i++;
                }
                return scimProviderDTOs;
            }

        } else {
            String errorMessage = "Security error: consumer:" + consumerId + " is trying to obtain " +
                                  "provisioning configuration of :" + SCIMCommonUtils.getGlobalConsumerId();
            log.error(errorMessage);
            throw new IdentitySCIMException(errorMessage);
        }
        return scimProviderDTOs;
    }

    public void addGlobalProvider(String consumerId, SCIMProviderDTO scimProviderDTO)
            throws IdentitySCIMException {
        /*if (SCIMCommonUtils.providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> globalProviders = SCIMCommonUtils.providers.get(consumerId);
            for (SCIMProviderDTO globalProvider : globalProviders) {
                if (scimProviderDTO.getProviderId().equals(globalProvider.getProviderId())) {
                    throw new IdentitySCIMException("Provider with given id already exists.");
                }
            }
            globalProviders.add(scimProviderDTO);
        } else {
            List<SCIMProviderDTO> globalProviders = new ArrayList<SCIMProviderDTO>();
            globalProviders.add(scimProviderDTO);
            SCIMCommonUtils.providers.put(consumerId, globalProviders);
        }*/
        if (consumerId.equals(SCIMCommonUtils.getGlobalConsumerId())) {
            SCIMProviderDAO providerDAO = new SCIMProviderDAO();
            providerDAO.addProvider(consumerId, scimProviderDTO);

        } else {
            String errorMessage = "Security error: consumer:" + consumerId + " is trying to add " +
                                  "provisioning configuration to :" + SCIMCommonUtils.getGlobalConsumerId();
            log.error(errorMessage);
            throw new IdentitySCIMException(errorMessage);
        }
    }

    public SCIMProviderDTO getGlobalProvider(String consumerId, String providerId)
            throws IdentitySCIMException {
        /*if (SCIMCommonUtils.providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> globalProviders = SCIMCommonUtils.providers.get(consumerId);
            for (SCIMProviderDTO globalProvider : globalProviders) {
                if (providerId.equals(globalProvider.getProviderId())) {
                    return globalProvider;
                }
            }
        }
        return null;*/
        SCIMProviderDTO scimProviderDTO = null;
        if (consumerId.equals(SCIMCommonUtils.getGlobalConsumerId())) {
            SCIMProviderDAO providerDAO = new SCIMProviderDAO();
            scimProviderDTO = providerDAO.getProvider(consumerId, providerId);

        } else {
            String errorMessage = "Security error: consumer:" + consumerId + " is trying to obtain " +
                                  "provisioning configuration of :" + SCIMCommonUtils.getGlobalConsumerId();
            log.error(errorMessage);
            throw new IdentitySCIMException(errorMessage);
        }
        return scimProviderDTO;
    }

    public void updateGlobalProvider(String consumerId, SCIMProviderDTO scimProviderDTO)
            throws IdentitySCIMException {
        /*if (SCIMCommonUtils.providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> globalProviders = SCIMCommonUtils.providers.get(consumerId);
            for (SCIMProviderDTO globalProvider : globalProviders) {
                if (globalProvider.getProviderId().equals(scimProviderDTO.getProviderId())) {
                    globalProviders.remove(globalProvider);
                    globalProviders.add(scimProviderDTO);
                    return;
                }
            }
            throw new IdentitySCIMException("No provider registered with the given provider id.");
        } else {
            throw new IdentitySCIMException("No providers registered for the given consumer..");
        }*/
        if (consumerId.equals(SCIMCommonUtils.getGlobalConsumerId())) {
            SCIMProviderDAO providerDAO = new SCIMProviderDAO();
            providerDAO.updateProvider(consumerId, scimProviderDTO);

        } else {
            String errorMessage = "Security error: consumer:" + consumerId + " is trying to update " +
                                  "provisioning configuration to :" + SCIMCommonUtils.getGlobalConsumerId();
            log.error(errorMessage);
            throw new IdentitySCIMException(errorMessage);
        }
    }

    public void deleteGlobalProvider(String consumerId, String providerId)
            throws IdentitySCIMException {
        if (consumerId.equals(SCIMCommonUtils.getGlobalConsumerId())) {
            SCIMProviderDAO providerDAO = new SCIMProviderDAO();
            providerDAO.deleteProvider(consumerId, providerId);

        } else {
            String errorMessage = "Security error: consumer:" + consumerId + " is trying to delete " +
                                  "provisioning configuration to :" + SCIMCommonUtils.getGlobalConsumerId();
            log.error(errorMessage);
            throw new IdentitySCIMException(errorMessage);
        }
    }

    public SCIMProviderDTO[] getAllUserProviders(String consumerId) throws IdentitySCIMException {
        /*SCIMProviderDTO[] scimProviderDTOs = new SCIMProviderDTO[0];
        //check if given consumer id matches with the logged in user id.
        if (SCIMCommonUtils.providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> userProviders = SCIMCommonUtils.providers.get(consumerId);
            scimProviderDTOs = new SCIMProviderDTO[userProviders.size()];
            int i = 0;
            for (SCIMProviderDTO userProvider : userProviders) {
                scimProviderDTOs[i] = userProvider;
                i++;
            }
            return scimProviderDTOs;
        }
        return scimProviderDTOs;*/
        SCIMProviderDTO[] scimProviderDTOs = new SCIMProviderDTO[0];
        if (consumerId.equals(SCIMCommonUtils.getUserConsumerId())) {
            SCIMProviderDAO providerDAO = new SCIMProviderDAO();
            List<SCIMProviderDTO> globalProviders = providerDAO.getAllProviders(consumerId);
            if (globalProviders != null && globalProviders.size() != 0) {
                scimProviderDTOs = new SCIMProviderDTO[globalProviders.size()];
                int i = 0;
                for (SCIMProviderDTO globalProvider : globalProviders) {
                    scimProviderDTOs[i] = globalProvider;
                    i++;
                }
                return scimProviderDTOs;
            }

        } else {
            String errorMessage = "Security error: consumer:" + consumerId + " is trying to obtain " +
                                  "provisioning configuration of :" + SCIMCommonUtils.getUserConsumerId();
            log.error(errorMessage);
            throw new IdentitySCIMException(errorMessage);
        }
        return scimProviderDTOs;
    }

    public void addUserProvider(String consumerId, SCIMProviderDTO scimProviderDTO)
            throws IdentitySCIMException {
        /*if (SCIMCommonUtils.providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> userProviders = SCIMCommonUtils.providers.get(consumerId);
            for (SCIMProviderDTO userProvider : userProviders) {
                if (scimProviderDTO.getProviderId().equals(userProvider.getProviderId())) {
                    throw new IdentitySCIMException("Provider with given id already exists.");
                }
            }
            userProviders.add(scimProviderDTO);
        } else {
            List<SCIMProviderDTO> userProviders = new ArrayList<SCIMProviderDTO>();
            userProviders.add(scimProviderDTO);
            SCIMCommonUtils.providers.put(consumerId, userProviders);
        }*/
        if (consumerId.equals(SCIMCommonUtils.getUserConsumerId())) {
            SCIMProviderDAO providerDAO = new SCIMProviderDAO();
            providerDAO.addProvider(consumerId, scimProviderDTO);

        } else {
            String errorMessage = "Security error: consumer:" + consumerId + " is trying to add " +
                                  "provisioning configuration to :" + SCIMCommonUtils.getUserConsumerId();
            log.error(errorMessage);
            throw new IdentitySCIMException(errorMessage);
        }
    }

    public SCIMProviderDTO getUserProvider(String consumerId, String providerId)
            throws IdentitySCIMException {
        /*if (SCIMCommonUtils.providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> userProviders = SCIMCommonUtils.providers.get(consumerId);
            for (SCIMProviderDTO userProvider : userProviders) {
                if (providerId.equals(userProvider.getProviderId())) {
                    return userProvider;
                }
            }
        }
        return null;*/
        SCIMProviderDTO scimProviderDTO = null;
        if (consumerId.equals(SCIMCommonUtils.getUserConsumerId())) {
            SCIMProviderDAO providerDAO = new SCIMProviderDAO();
            scimProviderDTO = providerDAO.getProvider(consumerId, providerId);

        } else {
            String errorMessage = "Security error: consumer:" + consumerId + " is trying to obtain " +
                                  "provisioning configuration of :" + SCIMCommonUtils.getUserConsumerId();
            log.error(errorMessage);
            throw new IdentitySCIMException(errorMessage);
        }
        return scimProviderDTO;
    }

    public void updateUserProvider(String consumerId, SCIMProviderDTO scimProviderDTO)
            throws IdentitySCIMException {
        /*if (SCIMCommonUtils.providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> userProviders = SCIMCommonUtils.providers.get(consumerId);
            for (SCIMProviderDTO userProvider : userProviders) {
                if (userProvider.getProviderId().equals(scimProviderDTO.getProviderId())) {
                    userProviders.remove(userProvider);
                    userProviders.add(scimProviderDTO);
                    return;
                }
            }
            throw new IdentitySCIMException("No provider registered with the given provider id.");
        } else {
            throw new IdentitySCIMException("No providers registered for the given consumer..");
        }*/
        if (consumerId.equals(SCIMCommonUtils.getUserConsumerId())) {
            SCIMProviderDAO providerDAO = new SCIMProviderDAO();
            providerDAO.updateProvider(consumerId, scimProviderDTO);

        } else {
            String errorMessage = "Security error: consumer:" + consumerId + " is trying to update " +
                                  "provisioning configuration to :" + SCIMCommonUtils.getUserConsumerId();
            log.error(errorMessage);
            throw new IdentitySCIMException(errorMessage);
        }
    }

    public void deleteUserProvider(String consumerId, String providerId)
            throws IdentitySCIMException {
        /*if (SCIMCommonUtils.providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> userProviders = SCIMCommonUtils.providers.get(consumerId);
            for (SCIMProviderDTO userProvider : userProviders) {
                if (providerId.equals(userProvider.getProviderId())) {
                    userProviders.remove(userProvider);
                    return;
                }
            }
            throw new IdentitySCIMException("No provider registered with the given provider id.");
        } else {
            throw new IdentitySCIMException("No providers registered for the given consumer..");
        }*/
        if (consumerId.equals(SCIMCommonUtils.getUserConsumerId())) {
            SCIMProviderDAO providerDAO = new SCIMProviderDAO();
            providerDAO.deleteProvider(consumerId, providerId);

        } else {
            String errorMessage = "Security error: consumer:" + consumerId + " is trying to delete " +
                                  "provisioning configuration to :" + SCIMCommonUtils.getUserConsumerId();
            log.error(errorMessage);
            throw new IdentitySCIMException(errorMessage);
        }
    }
}
