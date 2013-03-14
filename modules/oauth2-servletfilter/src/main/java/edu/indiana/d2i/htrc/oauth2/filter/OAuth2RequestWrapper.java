package edu.indiana.d2i.htrc.oauth2.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class OAuth2RequestWrapper extends HttpServletRequestWrapper {
    public static final String KEY_REMOTE_USER = "htrc-remote-user";

    private String remoteUser;
    private String remoteAddress;


    public OAuth2RequestWrapper(HttpServletRequest request) {
        super(request);
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
        if (headers == null && name.equals(KEY_REMOTE_USER)){
            List<String> values = Collections.emptyList();
            values.add(remoteUser);

            return Collections.enumeration(values);
        }

        return headers;
    }

    public Enumeration getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        names.add(KEY_REMOTE_USER);
        return Collections.enumeration(names);
    }
}
