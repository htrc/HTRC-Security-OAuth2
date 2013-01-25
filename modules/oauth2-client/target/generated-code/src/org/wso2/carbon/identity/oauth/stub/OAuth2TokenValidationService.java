

/**
 * OAuth2TokenValidationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */

    package org.wso2.carbon.identity.oauth.stub;

    /*
     *  OAuth2TokenValidationService java interface
     */

    public interface OAuth2TokenValidationService {
          

        /**
          * Auto generated method signature
          * 
                    * @param validate0
                
         */

         
                     public org.wso2.carbon.identity.oauth2.dto.xsd.OAuth2TokenValidationResponseDTO validate(

                        org.wso2.carbon.identity.oauth2.dto.xsd.OAuth2TokenValidationRequestDTO validationReqDTO1)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param validate0
            
          */
        public void startvalidate(

            org.wso2.carbon.identity.oauth2.dto.xsd.OAuth2TokenValidationRequestDTO validationReqDTO1,

            final org.wso2.carbon.identity.oauth.stub.OAuth2TokenValidationServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    