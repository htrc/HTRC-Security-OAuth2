package edu.indiana.d2i.htrc.oauth2.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class OAuth2RequestWrapper extends HttpServletRequestWrapper {

    private String remoteUser;


    public OAuth2RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void setRemoteUser(String user){
        this.remoteUser = user;

    }

    @Override
    public String getRemoteUser() {
        return remoteUser;
    }
}
