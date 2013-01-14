
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:24 PDT)
 */

        
            package org.wso2.carbon.identity.oauth2.stub.types.axis2;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://dto.oauth2.identity.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "OAuth2TokenValidationRequestDTO".equals(typeName)){
                   
                            return  org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationRequestDTO.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://dto.oauth2.identity.carbon.wso2.org/xsd".equals(namespaceURI) &&
                  "OAuth2TokenValidationResponseDTO".equals(typeName)){
                   
                            return  org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationResponseDTO.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    