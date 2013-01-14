
/**
 * OAuthAdminServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */

    package org.wso2.carbon.identity.oauth.stub;

    /**
     *  OAuthAdminServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class OAuthAdminServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public OAuthAdminServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public OAuthAdminServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
               // No methods generated for meps other than in-out
                
           /**
            * auto generated Axis2 call back method for getAllOAuthApplicationData method
            * override this method for handling normal response from getAllOAuthApplicationData operation
            */
           public void receiveResultgetAllOAuthApplicationData(
                    org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllOAuthApplicationData operation
           */
            public void receiveErrorgetAllOAuthApplicationData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getOAuthApplicationData method
            * override this method for handling normal response from getOAuthApplicationData operation
            */
           public void receiveResultgetOAuthApplicationData(
                    org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getOAuthApplicationData operation
           */
            public void receiveErrorgetOAuthApplicationData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for registerOAuthConsumer method
            * override this method for handling normal response from registerOAuthConsumer operation
            */
           public void receiveResultregisterOAuthConsumer(
                    java.lang.String[] result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from registerOAuthConsumer operation
           */
            public void receiveErrorregisterOAuthConsumer(java.lang.Exception e) {
            }
                
               // No methods generated for meps other than in-out
                
               // No methods generated for meps other than in-out
                


    }
    