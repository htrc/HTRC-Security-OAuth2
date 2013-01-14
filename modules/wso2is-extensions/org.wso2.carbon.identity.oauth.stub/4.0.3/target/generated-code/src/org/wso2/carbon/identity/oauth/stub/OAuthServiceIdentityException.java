
/**
 * OAuthServiceIdentityException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */

package org.wso2.carbon.identity.oauth.stub;

public class OAuthServiceIdentityException extends java.lang.Exception{

    private static final long serialVersionUID = 1358010867807L;
    
    private org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException faultMessage;

    
        public OAuthServiceIdentityException() {
            super("OAuthServiceIdentityException");
        }

        public OAuthServiceIdentityException(java.lang.String s) {
           super(s);
        }

        public OAuthServiceIdentityException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public OAuthServiceIdentityException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException msg){
       faultMessage = msg;
    }
    
    public org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException getFaultMessage(){
       return faultMessage;
    }
}
    