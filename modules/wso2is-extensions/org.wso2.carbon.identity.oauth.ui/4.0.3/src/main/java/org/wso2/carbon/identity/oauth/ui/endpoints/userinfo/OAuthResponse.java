package org.wso2.carbon.identity.oauth.ui.endpoints.userinfo;

import org.apache.amber.oauth2.common.message.OAuthMessage;

import javax.ws.rs.core.Response;
import java.util.Map;

public class OAuthResponse implements OAuthMessage {

    protected int responseStatus;
    protected java.lang.String uri;
    protected java.lang.String body;
    protected java.util.Map<java.lang.String,java.lang.String> headers;

    public OAuthResponse(String uri, int responseStatus) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public String getLocationUri() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLocationUri(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getBody() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBody(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getHeader(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addHeader(String s, String s2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map<String, String> getHeaders() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setHeaders(Map<String, String> stringStringMap) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static org.apache.amber.oauth2.common.message.OAuthResponse.OAuthErrorResponseBuilder errorResponse(int scBadRequest) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public static org.apache.amber.oauth2.common.message.OAuthResponse.OAuthResponseBuilder status(int code) { /* compiled code */
        return null;
    }




    public int getResponseStatus() {
        return responseStatus;
    }

    public static class OAuthResponseBuilder {
        protected org.apache.amber.oauth2.common.parameters.OAuthParametersApplier applier;
        protected java.util.Map<java.lang.String,java.lang.Object> parameters;
        protected int responseCode;
        protected java.lang.String location;
        private int responseStatus;

        public OAuthResponseBuilder(int responseCode) { /* compiled code */ }

        public org.apache.amber.oauth2.common.message.OAuthResponse.OAuthResponseBuilder location(java.lang.String location) { /* compiled code */
            return null;
        }

        public org.apache.amber.oauth2.common.message.OAuthResponse.OAuthResponseBuilder setScope(java.lang.String value) { /* compiled code */
            return null;
        }

        public org.apache.amber.oauth2.common.message.OAuthResponse.OAuthResponseBuilder setParam(java.lang.String key, java.lang.String value) { /* compiled code */
            return null;
        }

        public org.apache.amber.oauth2.common.message.OAuthResponse buildQueryMessage() throws org.apache.amber.oauth2.common.exception.OAuthSystemException { /* compiled code */
            return null;
        }

        public org.apache.amber.oauth2.common.message.OAuthResponse buildBodyMessage() throws org.apache.amber.oauth2.common.exception.OAuthSystemException { /* compiled code */
            return null;
        }

        public org.apache.amber.oauth2.common.message.OAuthResponse buildJSONMessage() throws org.apache.amber.oauth2.common.exception.OAuthSystemException { /* compiled code */
            return null;
        }

        public org.apache.amber.oauth2.common.message.OAuthResponse buildHeaderMessage() throws org.apache.amber.oauth2.common.exception.OAuthSystemException { /* compiled code */
            return null;
        }

        public int getResponseStatus() {
            return responseStatus;
        }

        public String getBody() {
            return null;
        }
    }


}


