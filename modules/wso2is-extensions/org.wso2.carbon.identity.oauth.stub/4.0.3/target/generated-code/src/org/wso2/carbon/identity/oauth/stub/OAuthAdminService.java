

/**
 * OAuthAdminService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */

    package org.wso2.carbon.identity.oauth.stub;

    /*
     *  OAuthAdminService java interface
     */

    public interface OAuthAdminService {
          
       /**
         * Auto generated method signature for Asynchronous Invocations
         * 
                 * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
         */
        public void  registerOAuthApplicationData(
         org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO application4

        ) throws java.rmi.RemoteException
        
        
               ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getAllOAuthApplicationData5
                
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
         */

         
                     public org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO[] getAllOAuthApplicationData(

                        )
                        throws java.rmi.RemoteException
             
          ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getAllOAuthApplicationData5
            
          */
        public void startgetAllOAuthApplicationData(

            

            final org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param getOAuthApplicationData8
                
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
         */

         
                     public org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO getOAuthApplicationData(

                        java.lang.String consumerKey9)
                        throws java.rmi.RemoteException
             
          ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getOAuthApplicationData8
            
          */
        public void startgetOAuthApplicationData(

            java.lang.String consumerKey9,

            final org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param registerOAuthConsumer12
                
             * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
         */

         
                     public java.lang.String[] registerOAuthConsumer(

                        )
                        throws java.rmi.RemoteException
             
          ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param registerOAuthConsumer12
            
          */
        public void startregisterOAuthConsumer(

            

            final org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     
       /**
         * Auto generated method signature for Asynchronous Invocations
         * 
                 * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
         */
        public void  removeOAuthApplicationData(
         java.lang.String consumerKey16

        ) throws java.rmi.RemoteException
        
        
               ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException;

        
       /**
         * Auto generated method signature for Asynchronous Invocations
         * 
                 * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
         */
        public void  updateConsumerApplication(
         org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO consumerAppDTO18

        ) throws java.rmi.RemoteException
        
        
               ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException;

        

        
       //
       }
    