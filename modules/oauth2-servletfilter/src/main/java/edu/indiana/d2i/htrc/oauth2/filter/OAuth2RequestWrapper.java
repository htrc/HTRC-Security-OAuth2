package edu.indiana.d2i.htrc.oauth2.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class OAuth2RequestWrapper extends HttpServletRequestWrapper {

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
}
