
/**
 * OAuth2TokenValidationServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */

    package org.wso2.carbon.identity.oauth2.stub;

    /**
     *  OAuth2TokenValidationServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class OAuth2TokenValidationServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public OAuth2TokenValidationServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public OAuth2TokenValidationServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for validate method
            * override this method for handling normal response from validate operation
            */
           public void receiveResultvalidate(
                    org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationResponseDTO result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from validate operation
           */
            public void receiveErrorvalidate(java.lang.Exception e) {
            }
                


    }
    