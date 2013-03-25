/*
*
* Copyright 2013 The Trustees of Indiana University
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/

package edu.indiana.d2i.htrc.oauth2.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class ContextExtractor {

    private static Logger log = Logger.getLogger(ContextExtractor.class);

    protected final Map<String, List<String>> contextMap;

    public ContextExtractor(HttpServletRequest httpServletRequest) {
        contextMap = new HashMap<String, List<String>>();
        extractFromRequest(contextMap, httpServletRequest);
        extractHeadersFromRequest(contextMap, httpServletRequest);
    }

    public List<String> getContext(String key) {
        List<String> list = null;
        List<String> list2 = contextMap.get(key.toLowerCase());
        if (list2 != null) {
            list = Collections.unmodifiableList(list2);
        }
        return list;
    }

    public Map<String, List<String>> getContextMap() {
        return this.contextMap;
    }


    protected void extractFromRequest(Map<String, List<String>> map, HttpServletRequest httpServletRequest) {
        String remoteAddr = httpServletRequest.getRemoteAddr();
        putIntoMap(map, "remoteaddr", remoteAddr);
        if (log.isDebugEnabled()) log.debug("remoteAddr: " + remoteAddr);

        String remoteHost = httpServletRequest.getRemoteHost();
        putIntoMap(map, "remotehost", remoteHost);
        if (log.isDebugEnabled()) log.debug("remoteHost: " + remoteHost);

        int remotePort = httpServletRequest.getRemotePort();
        putIntoMap(map, "remoteport", Integer.toString(remotePort));
        if (log.isDebugEnabled()) log.debug("remotePort: " + remotePort);
        String remoteUser = httpServletRequest.getRemoteUser();
        putIntoMap(map, "remoteuser", remoteUser);
        if (log.isDebugEnabled()) log.debug("remoteUser: " + remoteUser);

        String requestURI = httpServletRequest.getRequestURI();
        putIntoMap(map, "requesturi", requestURI);
        if (log.isDebugEnabled()) log.debug("requestURI: " + requestURI);

    }

    protected void putIntoMap(Map<String, List<String>> map, String name, String value) {
        if (name != null && value != null) {
            if (!"".equals(name) && !"".equals(value)) {
                List<String> list = map.get(name);
                if (list == null) {
                    list = new ArrayList<String>();
                    map.put(name, list);
                }
                list.add(value);
            }
        }
    }


    // the warning is due to raw Enumeration type returned from HttpServletRequest.getHeaderNames()
    @SuppressWarnings("unchecked")
    protected void extractHeadersFromRequest(Map<String, List<String>> map, HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String value = httpServletRequest.getHeader(headerName);
            List<String> valueList = decomposeValueToList(value);
            map.put(headerName, valueList);
            if (log.isDebugEnabled()) {
                StringBuilder builder = new StringBuilder(headerName);
                builder.append(":");
                for (String string : valueList) {
                    builder.append(string).append(" ");
                }
                log.debug(builder.toString());
            }
        }
    }

    protected List<String> decomposeValueToList(String value) {
        List<String> list = new ArrayList<String>();

        StringTokenizer tokenizer = new StringTokenizer(value, ",");
        while (tokenizer.hasMoreTokens()) {
            String val = tokenizer.nextToken().trim();
            if (!"".equals(val)) {
                list.add(val);
            }
        }
        return list;
    }
}
