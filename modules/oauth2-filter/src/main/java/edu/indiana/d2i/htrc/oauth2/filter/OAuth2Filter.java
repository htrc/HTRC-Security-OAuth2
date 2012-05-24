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
# File:  OAuth2Filter.java
# Description:  
#
# -----------------------------------------------------------------
# 
*/

package edu.indiana.d2i.htrc.oauth2.filter;

/**
 * @author Milinda Pathirage 
 * @author Yiming Sun
 *
 */


import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.types.ParameterStyle;
import org.apache.amber.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.indiana.d2i.htrc.audit.Auditor;
import edu.indiana.d2i.htrc.audit.AuditorFactory;
import edu.indiana.d2i.htrc.oauth2.common.ContextExtractor;
import edu.indiana.d2i.htrc.oauth2.common.Database;
import edu.indiana.d2i.htrc.oauth2.common.TokenStore;

public class OAuth2Filter implements Filter {
    
    public static final String PN_DSNAME = "ds.name";
    public static final String PN_LOG4J_PROPERTIES_PATH = "log4j.properties.path";
    public static final String PN_AUDITOR_CLASS = "auditor.class";
    
    private static Logger log = Logger.getLogger(OAuth2Filter.class);
    
    protected FilterConfig config;
    protected TokenStore tokenStore;
    protected AuditorFactory auditorFactory;

    public void init(FilterConfig filterConfig) throws ServletException {

        config = filterConfig;
        String dsName = config.getInitParameter(PN_DSNAME);
        String log4jPath = config.getInitParameter(PN_LOG4J_PROPERTIES_PATH);
        AuditorFactory.init(config.getInitParameter(PN_AUDITOR_CLASS));
        auditorFactory = new AuditorFactory();
        
        if (log4jPath != null) {
            PropertyConfigurator.configure(log4jPath);
            
        }
        
        try {
            tokenStore = new TokenStore(new Database(dsName));
        } catch (NamingException e) {
            throw new ServletException("Error connecting to database.", e);
        }
        
        log.info("Initializing OAuth filter...");
    }


    public FilterConfig getFilterConfig() {
        return config;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse res = (HttpServletResponse)servletResponse;
        
        ContextExtractor contextExtractor = new ContextExtractor(req, null);
        Auditor auditor = AuditorFactory.getAuditor(contextExtractor.getContextMap());
        
        String accessToken = null;
        
        try{
            // Make an OAuth Request out of this servlet request
            // We only support http HEADER parameter style.
            OAuthAccessResourceRequest accessResourceRequest = new OAuthAccessResourceRequest(req, ParameterStyle.HEADER);
            // Get access token
            accessToken = accessResourceRequest.getAccessToken();
            
            auditor.log("UNAUTHENTICATED_REQUEST", accessToken);
            
            if(!isValid(accessToken)){
                auditor.error("Invalid Token", "Invalid access token", accessToken);
                throw OAuthProblemException.error("Invalid Access Token.");
            }
            
            String userID = tokenStore.getUser(accessToken);
            HttpServletRequestWrapper reqWrapper = attachUserIDToRequest(req, userID);
            
            auditor.log("REQUEST_AUTHENTICATED", accessToken, userID);
            
//            filterChain.doFilter(servletRequest,servletResponse);
            filterChain.doFilter(reqWrapper, servletResponse);
        } catch (OAuthProblemException e) {
            auditor.error("OAuthProblemException", accessToken, e.getError(), e.getMessage());
            log.error("OAuthProblemException: ", e);
            res.setStatus(res.SC_UNAUTHORIZED);
            throw new ServletException(e.getDescription());
        } catch (OAuthSystemException oe) {
            auditor.error("OAuthSystemException", accessToken, oe.getMessage());
            log.error("OAuthSystemException: ", oe);
            res.setStatus(res.SC_INTERNAL_SERVER_ERROR);
            throw new ServletException("Internal OAuth processing error.", oe);
        }
    }

    public void destroy() {

    }
    
    private boolean isValid(String accessToken){
        return tokenStore.isValid(accessToken);
    }
    
    private HttpServletRequestWrapper attachUserIDToRequest(HttpServletRequest req, String userID) {
        
        class HTRCHttpServletRequestWrapper extends HttpServletRequestWrapper {
          private final String userID;
          HTRCHttpServletRequestWrapper(HttpServletRequest req, String userID) {
              super(req);
              this.userID = userID;
          }
          @Override
          public String getRemoteUser() {
              return this.userID;
          }
        };
        
        return new HTRCHttpServletRequestWrapper(req, userID);
    }
}
