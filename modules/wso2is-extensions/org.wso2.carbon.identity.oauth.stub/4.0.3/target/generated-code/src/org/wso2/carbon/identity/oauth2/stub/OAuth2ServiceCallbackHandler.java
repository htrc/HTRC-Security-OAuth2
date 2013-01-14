
/**
 * OAuth2ServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */

    package org.wso2.carbon.identity.oauth2.stub;

    /**
     *  OAuth2ServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class OAuth2ServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public OAuth2ServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public OAuth2ServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for authorize method
            * override this method for handling normal response from authorize operation
            */
           public void receiveResultauthorize(
                    org.wso2.carbon.identity.oauth2.stub.dto.OAuth2AuthorizeRespDTO result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from authorize operation
           */
            public void receiveErrorauthorize(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for issueAccessToken method
            * override this method for handling normal response from issueAccessToken operation
            */
           public void receiveResultissueAccessToken(
                    org.wso2.carbon.identity.oauth2.stub.dto.OAuth2AccessTokenRespDTO result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from issueAccessToken operation
           */
            public void receiveErrorissueAccessToken(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for issueUserInformation method
            * override this method for handling normal response from issueUserInformation operation
            */
           public void receiveResultissueUserInformation(
                    org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoRespDTO result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from issueUserInformation operation
           */
            public void receiveErrorissueUserInformation(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for validateClientInfo method
            * override this method for handling normal response from validateClientInfo operation
            */
           public void receiveResultvalidateClientInfo(
                    org.wso2.carbon.identity.oauth2.stub.dto.OAuth2ClientValidationResponseDTO result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from validateClientInfo operation
           */
            public void receiveErrorvalidateClientInfo(java.lang.Exception e) {
            }
                


    }
    