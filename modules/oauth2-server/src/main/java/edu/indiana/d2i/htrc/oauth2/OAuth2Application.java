/*
#
# Copyright 2012 The Trustees of Indiana University
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or areed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# -----------------------------------------------------------------
#
# Project: OAuth2
# File:  OAuth2Application.java
# Description:  
#
# -----------------------------------------------------------------
# 
*/

package edu.indiana.d2i.htrc.oauth2;

/**
 * @author Milinda Pathirage
 * @author Yiming Sun
 */

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.indiana.d2i.htrc.audit.Auditor;
import edu.indiana.d2i.htrc.audit.AuditorFactory;
import edu.indiana.d2i.htrc.oauth2.endpoints.ResourceEndpoint;
import edu.indiana.d2i.htrc.oauth2.endpoints.TokenEndpoint;

@ApplicationPath("/")
public class OAuth2Application extends Application {
    @Context
    private ServletConfig servletConfig;
    
    public static final String PN_DSNAME = "ds-name";
    public static final String PN_TOKEN_TTL_SECONDS = "token-ttl-seconds";
    public static final String PN_LOG4J_PROPERTIES_PATH = "log4j-properties-path";
    public static final String PN_AUDITOR_CLASS = "auditor-class";
    
    private static Logger log = Logger.getLogger(OAuth2Application.class);
    
    private static String dsName;
    private static int tokenTTLSeconds;
    private static AuditorFactory auditorFactory;
    

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> hashSet = new HashSet<Class<?>>();
        hashSet.add(TokenEndpoint.class);
        hashSet.add(ResourceEndpoint.class);
        return hashSet;
    }

    @PostConstruct
    private void init() {
        dsName = servletConfig.getInitParameter(PN_DSNAME);
        tokenTTLSeconds = Integer.valueOf(servletConfig.getInitParameter(PN_TOKEN_TTL_SECONDS));
        AuditorFactory.init(servletConfig.getInitParameter(PN_AUDITOR_CLASS));
        auditorFactory = new AuditorFactory();
        
        String log4jPropertiesPath = servletConfig.getInitParameter(PN_LOG4J_PROPERTIES_PATH);
        if (log4jPropertiesPath != null) {
            PropertyConfigurator.configure(log4jPropertiesPath);
        }
    }

    public static String getDSName() {
        return dsName;
    }
    
    public static int getTokenTTLSeconds() {
        return tokenTTLSeconds;
    }
    
    public static synchronized Auditor getAuditor(Map<String, List<String>> contextMap) {
        return auditorFactory.getAuditor(contextMap);
    }
}
