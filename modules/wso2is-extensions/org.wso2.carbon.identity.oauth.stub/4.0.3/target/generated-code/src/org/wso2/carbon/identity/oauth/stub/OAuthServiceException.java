
/**
 * OAuthServiceException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */

package org.wso2.carbon.identity.oauth.stub;

public class OAuthServiceException extends java.lang.Exception{

    private static final long serialVersionUID = 1358010867798L;
    
    private org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceException faultMessage;

    
        public OAuthServiceException() {
            super("OAuthServiceException");
        }

        public OAuthServiceException(java.lang.String s) {
           super(s);
        }

        public OAuthServiceException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public OAuthServiceException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceException msg){
       faultMessage = msg;
    }
    
    public org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceException getFaultMessage(){
       return faultMessage;
    }
}
    