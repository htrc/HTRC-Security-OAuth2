
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:24 PDT)
 */

        
            package org.wso2.carbon.identity.oauth.stub.types.axis2;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://common.core.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "AuthenticationException".equals(typeName)){
                   
                            return  org.wso2.carbon.core.common.xsd.AuthenticationException.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://org.apache.axis2/xsd".equals(namespaceURI) &&
                  "Exception".equals(typeName)){
                   
                            return  org.wso2.carbon.identity.oauth.stub.types.axis2.Exception.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://base.identity.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "IdentityException".equals(typeName)){
                   
                            return  org.wso2.carbon.identity.base.xsd.IdentityException.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oauth.identity.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "Parameters".equals(typeName)){
                   
                            return  org.wso2.carbon.identity.oauth.stub.types.Parameters.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://dto.oauth.identity.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "OAuthConsumerDTO".equals(typeName)){
                   
                            return  org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerDTO.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://oauth.identity.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "IdentityOAuthAdminException".equals(typeName)){
                   
                            return  org.wso2.carbon.identity.oauth.stub.types.IdentityOAuthAdminException.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    