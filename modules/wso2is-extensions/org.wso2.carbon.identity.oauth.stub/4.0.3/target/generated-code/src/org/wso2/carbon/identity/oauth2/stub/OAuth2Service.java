

/**
 * OAuth2Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */

    package org.wso2.carbon.identity.oauth2.stub;

    /*
     *  OAuth2Service java interface
     */

    public interface OAuth2Service {
          

        /**
          * Auto generated method signature
          * 
                    * @param authorize3
                
         */

         
                     public org.wso2.carbon.identity.oauth2.stub.dto.OAuth2AuthorizeRespDTO authorize(

                        org.wso2.carbon.identity.oauth2.stub.dto.OAuth2AuthorizeReqDTO oAuth2AuthorizeReqDTO4)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param authorize3
            
          */
        public void startauthorize(

            org.wso2.carbon.identity.oauth2.stub.dto.OAuth2AuthorizeReqDTO oAuth2AuthorizeReqDTO4,

            final org.wso2.carbon.identity.oauth2.stub.OAuth2ServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param issueAccessToken7
                
         */

         
                     public org.wso2.carbon.identity.oauth2.stub.dto.OAuth2AccessTokenRespDTO issueAccessToken(

                        org.wso2.carbon.identity.oauth2.stub.dto.OAuth2AccessTokenReqDTO tokenReqDTO8)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param issueAccessToken7
            
          */
        public void startissueAccessToken(

            org.wso2.carbon.identity.oauth2.stub.dto.OAuth2AccessTokenReqDTO tokenReqDTO8,

            final org.wso2.carbon.identity.oauth2.stub.OAuth2ServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param issueUserInformation11
                
         */

         
                     public org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoRespDTO issueUserInformation(

                        org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoReqDTO userinfoReqDTO12)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param issueUserInformation11
            
          */
        public void startissueUserInformation(

            org.wso2.carbon.identity.oauth2.stub.dto.OAuth2UserInfoReqDTO userinfoReqDTO12,

            final org.wso2.carbon.identity.oauth2.stub.OAuth2ServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param validateClientInfo15
                
         */

         
                     public org.wso2.carbon.identity.oauth2.stub.dto.OAuth2ClientValidationResponseDTO validateClientInfo(

                        java.lang.String clientId16,java.lang.String callbackURI17)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param validateClientInfo15
            
          */
        public void startvalidateClientInfo(

            java.lang.String clientId16,java.lang.String callbackURI17,

            final org.wso2.carbon.identity.oauth2.stub.OAuth2ServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    