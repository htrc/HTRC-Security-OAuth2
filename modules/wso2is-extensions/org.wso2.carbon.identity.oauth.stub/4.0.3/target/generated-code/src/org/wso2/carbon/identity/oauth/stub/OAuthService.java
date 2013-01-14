

/**
 * OAuthService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */

    package org.wso2.carbon.identity.oauth.stub;

    /*
     *  OAuthService java interface
     */

    public interface OAuthService {
          

        /**
          * Auto generated method signature
          * 
                    * @param validateAuthenticationRequest8
                
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException : 
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException : 
         */

         
                     public org.wso2.carbon.identity.oauth.stub.types.Parameters validateAuthenticationRequest(

                        org.wso2.carbon.identity.oauth.stub.types.Parameters params9)
                        throws java.rmi.RemoteException
             
          ,org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException
          ,org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param validateAuthenticationRequest8
            
          */
        public void startvalidateAuthenticationRequest(

            org.wso2.carbon.identity.oauth.stub.types.Parameters params9,

            final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getScopeAndAppName12
                
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceException : 
         */

         
                     public org.wso2.carbon.identity.oauth.stub.types.Parameters getScopeAndAppName(

                        java.lang.String oauthToken13)
                        throws java.rmi.RemoteException
             
          ,org.wso2.carbon.identity.oauth.stub.OAuthServiceException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getScopeAndAppName12
            
          */
        public void startgetScopeAndAppName(

            java.lang.String oauthToken13,

            final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getAccessToken16
                
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException : 
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException : 
         */

         
                     public org.wso2.carbon.identity.oauth.stub.types.Parameters getAccessToken(

                        org.wso2.carbon.identity.oauth.stub.types.Parameters params17)
                        throws java.rmi.RemoteException
             
          ,org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException
          ,org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getAccessToken16
            
          */
        public void startgetAccessToken(

            org.wso2.carbon.identity.oauth.stub.types.Parameters params17,

            final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param isOAuthConsumerValid20
                
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException : 
         */

         
                     public boolean isOAuthConsumerValid(

                        org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerDTO oauthConsumer21)
                        throws java.rmi.RemoteException
             
          ,org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param isOAuthConsumerValid20
            
          */
        public void startisOAuthConsumerValid(

            org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerDTO oauthConsumer21,

            final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getOauthRequestToken24
                
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException : 
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException : 
         */

         
                     public org.wso2.carbon.identity.oauth.stub.types.Parameters getOauthRequestToken(

                        org.wso2.carbon.identity.oauth.stub.types.Parameters params25)
                        throws java.rmi.RemoteException
             
          ,org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException
          ,org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getOauthRequestToken24
            
          */
        public void startgetOauthRequestToken(

            org.wso2.carbon.identity.oauth.stub.types.Parameters params25,

            final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param authorizeOauthRequestToken28
                
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException : 
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException : 
         */

         
                     public org.wso2.carbon.identity.oauth.stub.types.Parameters authorizeOauthRequestToken(

                        org.wso2.carbon.identity.oauth.stub.types.Parameters params29)
                        throws java.rmi.RemoteException
             
          ,org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException
          ,org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param authorizeOauthRequestToken28
            
          */
        public void startauthorizeOauthRequestToken(

            org.wso2.carbon.identity.oauth.stub.types.Parameters params29,

            final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    