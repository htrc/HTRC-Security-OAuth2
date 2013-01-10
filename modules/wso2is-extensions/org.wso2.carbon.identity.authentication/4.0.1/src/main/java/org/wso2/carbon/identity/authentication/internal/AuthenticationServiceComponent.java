/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.identity.authentication.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.identity.authentication.AuthenticationService;
import org.wso2.carbon.identity.authentication.AuthenticationServiceImpl;
import org.wso2.carbon.identity.authentication.SharedKeyAccessService;
import org.wso2.carbon.identity.authentication.SharedKeyAccessServiceImpl;
import org.wso2.carbon.user.core.service.RealmService;

import java.util.UUID;

/**
 * @scr.component name="org.wso2.carbon.identity.authentication.internal.AuthenticationServiceComponent" immediate="true"
 * @scr.reference name="user.realmservice.default" interface="org.wso2.carbon.user.core.service.RealmService"
 * cardinality="1..1" policy="dynamic" bind="setRealmService"  unbind="unsetRealmService"
 */
public class AuthenticationServiceComponent {

    private static Log log = LogFactory.getLog(AuthenticationServiceComponent.class);

    private ServiceRegistration authenticationService;
    private ServiceRegistration sharedKeyAccessService;
    private RealmService realmService = null;

    protected void activate(ComponentContext componentContext) {

        // Generate access key
        String accessKey = UUID.randomUUID().toString();
        SharedKeyAccessService keyAccessService = new SharedKeyAccessServiceImpl(accessKey);
        // Publish access key
        sharedKeyAccessService = componentContext.getBundleContext().registerService(
                SharedKeyAccessService.class.getName(),
                keyAccessService, null);

        // Publish the authentication service
        authenticationService = componentContext.getBundleContext().registerService(
                AuthenticationService.class.getName(),
                new AuthenticationServiceImpl(keyAccessService, realmService), null);

    }

    protected void deactivate(ComponentContext componentContext) {
        componentContext.getBundleContext().ungetService(authenticationService.getReference());
        componentContext.getBundleContext().ungetService(sharedKeyAccessService.getReference());
    }

    protected void setRealmService(RealmService realmService) {
        this.realmService = realmService;
    }

    protected void unsetRealmService(RealmService realmService) {
        this.realmService = null;
    }
}
