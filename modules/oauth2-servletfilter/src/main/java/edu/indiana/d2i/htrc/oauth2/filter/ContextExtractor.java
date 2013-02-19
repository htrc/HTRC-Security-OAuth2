package edu.indiana.d2i.htrc.oauth2.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

public class ContextExtractor {

    private static Logger log = Logger.getLogger(ContextExtractor.class);

    protected final Map<String, List<String>> contextMap;

    public ContextExtractor(HttpServletRequest httpServletRequest, HttpHeaders httpHeaders) {
        contextMap = new HashMap<String, List<String>>();
        extractFromRequest(contextMap, httpServletRequest);
        if (httpHeaders != null) {
            extractFromHeaders(contextMap, httpHeaders);
        } else {
            extractHeadersFromRequest(contextMap, httpServletRequest);
        }
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


    protected void extractFromHeaders(Map<String, List<String>> map, HttpHeaders httpHeaders) {
        if (httpHeaders != null) {
            MultivaluedMap<String, String> requestHeaders = httpHeaders.getRequestHeaders();
            Set<String> keySet = requestHeaders.keySet();
            for (String key : keySet) {
                List<String> list = requestHeaders.get(key.toLowerCase());
                if (list != null && !list.isEmpty()) {
                    if (log.isDebugEnabled()) {
                        StringBuilder builder = new StringBuilder(key);
                        builder.append(":");
                        for (String string : list) {
                            builder.append(string).append(" ");
                        }
                        log.debug(builder.toString());
                    }

                    List<String> list2 = map.get(key);
                    if (list2 == null) {
                        list2 = new ArrayList<String>(list);
                        map.put(key.toLowerCase(), list2);
                    } else {
                        list2.addAll(list);
                    }
                } else {
                    if (log.isDebugEnabled()) log.debug(key + ": null | empty");
                }
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
