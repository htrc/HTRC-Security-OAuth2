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
package org.wso2.carbon.identity.core;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.user.core.claim.Claim;
import org.wso2.carbon.user.core.claim.ClaimManager;
import org.wso2.carbon.user.core.UserRealm;
import org.wso2.carbon.user.core.UserStoreException;

public class IdentityClaimManager {

    private static Log log = LogFactory.getLog(IdentityClaimManager.class);

    // Maintains a single instance of UserStore.
    private static IdentityClaimManager claimManager;

    // To enable attempted thread-safety using double-check locking
    private static Object lock = new Object();

    // Making the class singleton
    private IdentityClaimManager() throws IdentityException {
    }

    public static IdentityClaimManager getInstance() throws IdentityException {

        // Enables attempted thread-safety using double-check locking
        if (claimManager == null) {
            synchronized (lock) {
                if (claimManager == null) {
                    claimManager = new IdentityClaimManager();
                    if (log.isDebugEnabled()) {
                        log.debug("IdentityClaimManager singleton instance created successfully");
                    }
                }
            }
        }
        return claimManager;
    }

        /**
     * Returns all supported claims.
     * 
     * @return
     * @throws IdentityException
     */
    public Claim[] getAllSupportedClaims(UserRealm realm) throws IdentityException {
        try {
            ClaimManager claimAdmin = realm.getClaimManager();
            return (Claim[]) claimAdmin.getAllSupportClaimsByDefault();
        } catch (org.wso2.carbon.user.api.UserStoreException e) {
            log.error("Error occurred while loading supported claims", e);
            getException("Error occurred while loading supported claima", e);
        }

        return new Claim[0];
    }

    /**
     * Returns all supported claims for the given dialect.
     * 
     * @return
     * @throws IdentityException
     */
    public Claim[] getAllSupportedClaims(String dialectUri,UserRealm realm)
            throws IdentityException {
        Claim[] claims = new Claim[0];
        ClaimManager claimAdmin;
        ArrayList<Claim> requiredClaims = null;

        try {

            claimAdmin = realm.getClaimManager();
            claims = (Claim[]) claimAdmin.getAllClaims(dialectUri);
            requiredClaims = new ArrayList<Claim>();
            for (int i = 0; i < claims.length; i++) {
                if (claims[i].isSupportedByDefault()) {
                    requiredClaims.add(claims[i]);
                }
            }
            return requiredClaims.toArray(new Claim[requiredClaims.size()]);
        } catch (org.wso2.carbon.user.api.UserStoreException e) {
            log.error("Error occurred while loading supported claims from the dialect "
                    + dialectUri, e);
            getException("Error occurred while loading supported claims from the dialect "
                    + dialectUri, e);
        }
        return claims;
    }

    /**
     * Creates an IdentityException instance wrapping the given error message and
     * 
     * @param message Error message
     * @param e Exception
     * @throws IdentityException
     */
    private void getException(String message, Exception e) throws IdentityException {
        log.error(message, e);
        throw new IdentityException(message, e);
    }
}
