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


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/*
 * Servlet request wrapper implementation which allow us to change the
  * remote user attribute inside the Oauth2 filter. Also this augment the
  * getHeaders method of servlet request to return custom 'htrc-remote-user'
  * header.
 */
public class OAuth2RequestWrapper extends HttpServletRequestWrapper {
    public static final String KEY_REMOTE_USER = "htrc-remote-user";
    public static final String KEY_REMOTE_ADDRESS = "htrc-remote-address";
    public static final String KEY_REQUEST_ID = "htrc-request-id";

    private String remoteUser;
    private String remoteAddress;
    private String requestId;


    public OAuth2RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void setRequestId(String id){
        this.requestId = id;
    }

    public void setRemoteUser(String user){
        this.remoteUser = user;

    }

    public void setRemoteAddress(String address){
        this.remoteAddress = address;

    }

    @Override
    public String getRemoteUser() {
        return remoteUser;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getHeader(String name) {
        String header = super.getHeader(name);
        if (header == null && name.equals(KEY_REMOTE_USER)){
            return remoteUser;
        }

        return header;
    }

    @Override
    public Enumeration getHeaders(String name) {
        Enumeration headers = super.getHeaders(name);
        if ((headers == null || !headers.hasMoreElements())  && name.equals(KEY_REMOTE_USER)){
            List<String> values = new ArrayList<String>();
            values.add(remoteUser);

            return Collections.enumeration(values);
        } else if((headers == null || !headers.hasMoreElements())  && name.equals(KEY_REMOTE_ADDRESS)){
            List<String> values = new ArrayList<String>();
            values.add(getRemoteAddress());

            return Collections.enumeration(values);
        } else if ((headers == null || !headers.hasMoreElements())  && name.equals(KEY_REQUEST_ID)){
            List<String> values = new ArrayList<String>();
            values.add(requestId);

            return Collections.enumeration(values);
        }

        return headers;
    }

    public Enumeration getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        names.add(KEY_REMOTE_USER);
        names.add(KEY_REMOTE_ADDRESS);
        names.add(KEY_REQUEST_ID);
        return Collections.enumeration(names);
    }
}
