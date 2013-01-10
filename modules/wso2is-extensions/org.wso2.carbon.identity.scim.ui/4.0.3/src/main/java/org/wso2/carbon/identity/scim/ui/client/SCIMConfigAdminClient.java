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
package org.wso2.carbon.identity.scim.ui.client;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.wso2.carbon.identity.scim.common.stub.SCIMConfigAdminServiceStub;
import org.wso2.carbon.identity.scim.common.stub.config.SCIMProviderDTO;

/*
Point of reference for the UI for managing SCIM Provider Configurations.
 */

public class SCIMConfigAdminClient {

    /*private static Map<String, List<SCIMProviderDTO>> providers =
            new HashMap<String, List<SCIMProviderDTO>>();*/
    private SCIMConfigAdminServiceStub stub;

    /**
     * Instantiate SCIMConfigAdminClient
     *
     * @param cookie
     * @param backendServerURL
     * @param configContext
     */
    public SCIMConfigAdminClient(String cookie, String backendServerURL,
                                 ConfigurationContext configContext) throws AxisFault {
        String serviceURL = backendServerURL + "SCIMConfigAdminService";
        stub = new SCIMConfigAdminServiceStub(configContext, serviceURL);
        ServiceClient client = stub._getServiceClient();
        Options option = client.getOptions();
        option.setManageSession(true);
        option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, cookie);
    }

    /*Global Providers Operations..*/

    public SCIMProviderDTO[] getAllGlobalProviders(String consumerId) throws Exception {
        //check if given tenant id matches with the logged in user tenant id.
        /*if (providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> globalProviders = providers.get(consumerId);
            return globalProviders.toArray(new SCIMProviderDTO[globalProviders.size()]);
        }
        return null;*/
        return stub.getAllGlobalProviders(consumerId);
    }

    public void addGlobalProvider(String consumerId, SCIMProviderDTO scimProviderDTO)
            throws Exception {
        /*if (providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> globalProviders = providers.get(consumerId);
            for (SCIMProviderDTO globalProvider : globalProviders) {
                if (scimProviderDTO.getProviderId().equals(globalProvider.getProviderId())) {
                    throw new IdentitySCIMException("Provider with given id already exists.");
                }
            }
            globalProviders.add(scimProviderDTO);
        } else {
            List<SCIMProviderDTO> globalProviders = new ArrayList<SCIMProviderDTO>();
            globalProviders.add(scimProviderDTO);
            providers.put(consumerId, globalProviders);
        }*/
        stub.addGlobalProvider(consumerId, scimProviderDTO);
    }

    public SCIMProviderDTO getGlobalProvider(String consumerId, String providerId)
            throws Exception {
        /*if (providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> globalProviders = providers.get(consumerId);
            for (SCIMProviderDTO globalProvider : globalProviders) {
                if (providerId.equals(globalProvider.getProviderId())) {
                    return globalProvider;
                }
            }
        }
        return null;*/
        return stub.getGlobalProvider(consumerId, providerId);
    }

    public void updateGlobalProvider(String consumerId, SCIMProviderDTO scimProviderDTO)
            throws Exception {
        /*if (providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> globalProviders = providers.get(consumerId);
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
        stub.updateGlobalProvider(consumerId, scimProviderDTO);
    }

    public void deleteGlobalProvider(String consumerId, String providerId)
            throws Exception {
        /*if (providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> globalProviders = providers.get(consumerId);
            for (SCIMProviderDTO globalProvider : globalProviders) {
                if (providerId.equals(globalProvider.getProviderId())) {
                    globalProviders.remove(globalProvider);
                    return;
                }
            }
            throw new IdentitySCIMException("No provider registered with the given provider id.");
        } else {
            throw new IdentitySCIMException("No providers registered for the given consumer..");
        }*/
        stub.deleteGlobalProvider(consumerId, providerId);
    }

    /*User Providers Operations*/

    public SCIMProviderDTO[] getAllUserProviders(String consumerId) throws Exception {
        //check if given consumer id matches with the logged in user id.
        /*if (providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> userProviders = providers.get(consumerId);
            return userProviders.toArray(new SCIMProviderDTO[userProviders.size()]);
        }
        return null;*/
        return stub.getAllUserProviders(consumerId);
    }

    public void addUserProvider(String consumerId, SCIMProviderDTO scimProviderDTO)
            throws Exception {
        /*if (providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> userProviders = providers.get(consumerId);
            for (SCIMProviderDTO userProvider : userProviders) {
                if (scimProviderDTO.getProviderId().equals(userProvider.getProviderId())) {
                    throw new IdentitySCIMException("Provider with given id already exists.");
                }
            }
            userProviders.add(scimProviderDTO);
        } else {
            List<SCIMProviderDTO> userProviders = new ArrayList<SCIMProviderDTO>();
            userProviders.add(scimProviderDTO);
            providers.put(consumerId, userProviders);
        }*/
        stub.addUserProvider(consumerId, scimProviderDTO);
    }

    public SCIMProviderDTO getUserProvider(String consumerId, String providerId) throws Exception {
        /*if (providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> userProviders = providers.get(consumerId);
            for (SCIMProviderDTO userProvider : userProviders) {
                if (providerId.equals(userProvider.getProviderId())) {
                    return userProvider;
                }
            }
        }
        return null;*/
        return stub.getUserProvider(consumerId, providerId);
    }

    public void updateUserProvider(String consumerId, SCIMProviderDTO scimProviderDTO)
            throws Exception {
        /*if (providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> userProviders = providers.get(consumerId);
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
        stub.updateUserProvider(consumerId, scimProviderDTO);
    }

    public void deleteUserProvider(String consumerId, String providerId)
            throws Exception {
        /*if (providers.containsKey(consumerId)) {
            List<SCIMProviderDTO> userProviders = providers.get(consumerId);
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
        stub.deleteUserProvider(consumerId, providerId);
    }
}
