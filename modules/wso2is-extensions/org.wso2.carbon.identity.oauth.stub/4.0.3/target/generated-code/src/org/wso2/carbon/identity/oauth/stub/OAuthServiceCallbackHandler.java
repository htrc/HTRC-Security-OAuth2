
/**
 * OAuthServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */

    package org.wso2.carbon.identity.oauth.stub;

    /**
     *  OAuthServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class OAuthServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public OAuthServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public OAuthServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for validateAuthenticationRequest method
            * override this method for handling normal response from validateAuthenticationRequest operation
            */
           public void receiveResultvalidateAuthenticationRequest(
                    org.wso2.carbon.identity.oauth.stub.types.Parameters result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from validateAuthenticationRequest operation
           */
            public void receiveErrorvalidateAuthenticationRequest(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getScopeAndAppName method
            * override this method for handling normal response from getScopeAndAppName operation
            */
           public void receiveResultgetScopeAndAppName(
                    org.wso2.carbon.identity.oauth.stub.types.Parameters result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getScopeAndAppName operation
           */
            public void receiveErrorgetScopeAndAppName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAccessToken method
            * override this method for handling normal response from getAccessToken operation
            */
           public void receiveResultgetAccessToken(
                    org.wso2.carbon.identity.oauth.stub.types.Parameters result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAccessToken operation
           */
            public void receiveErrorgetAccessToken(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for isOAuthConsumerValid method
            * override this method for handling normal response from isOAuthConsumerValid operation
            */
           public void receiveResultisOAuthConsumerValid(
                    boolean result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from isOAuthConsumerValid operation
           */
            public void receiveErrorisOAuthConsumerValid(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getOauthRequestToken method
            * override this method for handling normal response from getOauthRequestToken operation
            */
           public void receiveResultgetOauthRequestToken(
                    org.wso2.carbon.identity.oauth.stub.types.Parameters result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getOauthRequestToken operation
           */
            public void receiveErrorgetOauthRequestToken(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for authorizeOauthRequestToken method
            * override this method for handling normal response from authorizeOauthRequestToken operation
            */
           public void receiveResultauthorizeOauthRequestToken(
                    org.wso2.carbon.identity.oauth.stub.types.Parameters result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from authorizeOauthRequestToken operation
           */
            public void receiveErrorauthorizeOauthRequestToken(java.lang.Exception e) {
            }
                


    }
    