
/**
 * OAuth2AccessTokenReqDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:24 PDT)
 */

            
                package org.wso2.carbon.identity.oauth2.stub.dto;
            

            /**
            *  OAuth2AccessTokenReqDTO bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class OAuth2AccessTokenReqDTO
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = OAuth2AccessTokenReqDTO
                Namespace URI = http://dto.oauth2.identity.carbon.wso2.org/xsd
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for Assertion
                        */

                        
                                    protected java.lang.String localAssertion ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAssertionTracker = false ;

                           public boolean isAssertionSpecified(){
                               return localAssertionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAssertion(){
                               return localAssertion;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Assertion
                               */
                               public void setAssertion(java.lang.String param){
                            localAssertionTracker = true;
                                   
                                            this.localAssertion=param;
                                    

                               }
                            

                        /**
                        * field for AuthorizationCode
                        */

                        
                                    protected java.lang.String localAuthorizationCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAuthorizationCodeTracker = false ;

                           public boolean isAuthorizationCodeSpecified(){
                               return localAuthorizationCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAuthorizationCode(){
                               return localAuthorizationCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuthorizationCode
                               */
                               public void setAuthorizationCode(java.lang.String param){
                            localAuthorizationCodeTracker = true;
                                   
                                            this.localAuthorizationCode=param;
                                    

                               }
                            

                        /**
                        * field for CallbackURI
                        */

                        
                                    protected java.lang.String localCallbackURI ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCallbackURITracker = false ;

                           public boolean isCallbackURISpecified(){
                               return localCallbackURITracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCallbackURI(){
                               return localCallbackURI;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CallbackURI
                               */
                               public void setCallbackURI(java.lang.String param){
                            localCallbackURITracker = true;
                                   
                                            this.localCallbackURI=param;
                                    

                               }
                            

                        /**
                        * field for ClientId
                        */

                        
                                    protected java.lang.String localClientId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localClientIdTracker = false ;

                           public boolean isClientIdSpecified(){
                               return localClientIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getClientId(){
                               return localClientId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ClientId
                               */
                               public void setClientId(java.lang.String param){
                            localClientIdTracker = true;
                                   
                                            this.localClientId=param;
                                    

                               }
                            

                        /**
                        * field for ClientSecret
                        */

                        
                                    protected java.lang.String localClientSecret ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localClientSecretTracker = false ;

                           public boolean isClientSecretSpecified(){
                               return localClientSecretTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getClientSecret(){
                               return localClientSecret;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ClientSecret
                               */
                               public void setClientSecret(java.lang.String param){
                            localClientSecretTracker = true;
                                   
                                            this.localClientSecret=param;
                                    

                               }
                            

                        /**
                        * field for GrantType
                        */

                        
                                    protected java.lang.String localGrantType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGrantTypeTracker = false ;

                           public boolean isGrantTypeSpecified(){
                               return localGrantTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGrantType(){
                               return localGrantType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GrantType
                               */
                               public void setGrantType(java.lang.String param){
                            localGrantTypeTracker = true;
                                   
                                            this.localGrantType=param;
                                    

                               }
                            

                        /**
                        * field for RefreshToken
                        */

                        
                                    protected java.lang.String localRefreshToken ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRefreshTokenTracker = false ;

                           public boolean isRefreshTokenSpecified(){
                               return localRefreshTokenTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRefreshToken(){
                               return localRefreshToken;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RefreshToken
                               */
                               public void setRefreshToken(java.lang.String param){
                            localRefreshTokenTracker = true;
                                   
                                            this.localRefreshToken=param;
                                    

                               }
                            

                        /**
                        * field for ResourceOwnerPassword
                        */

                        
                                    protected java.lang.String localResourceOwnerPassword ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResourceOwnerPasswordTracker = false ;

                           public boolean isResourceOwnerPasswordSpecified(){
                               return localResourceOwnerPasswordTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResourceOwnerPassword(){
                               return localResourceOwnerPassword;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResourceOwnerPassword
                               */
                               public void setResourceOwnerPassword(java.lang.String param){
                            localResourceOwnerPasswordTracker = true;
                                   
                                            this.localResourceOwnerPassword=param;
                                    

                               }
                            

                        /**
                        * field for ResourceOwnerUsername
                        */

                        
                                    protected java.lang.String localResourceOwnerUsername ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResourceOwnerUsernameTracker = false ;

                           public boolean isResourceOwnerUsernameSpecified(){
                               return localResourceOwnerUsernameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResourceOwnerUsername(){
                               return localResourceOwnerUsername;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResourceOwnerUsername
                               */
                               public void setResourceOwnerUsername(java.lang.String param){
                            localResourceOwnerUsernameTracker = true;
                                   
                                            this.localResourceOwnerUsername=param;
                                    

                               }
                            

                        /**
                        * field for Scope
                        * This was an Array!
                        */

                        
                                    protected java.lang.String[] localScope ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localScopeTracker = false ;

                           public boolean isScopeSpecified(){
                               return localScopeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String[]
                           */
                           public  java.lang.String[] getScope(){
                               return localScope;
                           }

                           
                        


                               
                              /**
                               * validate the array for Scope
                               */
                              protected void validateScope(java.lang.String[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Scope
                              */
                              public void setScope(java.lang.String[] param){
                              
                                   validateScope(param);

                               localScopeTracker = true;
                                      
                                      this.localScope=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param java.lang.String
                             */
                             public void addScope(java.lang.String param){
                                   if (localScope == null){
                                   localScope = new java.lang.String[]{};
                                   }

                            
                                 //update the setting tracker
                                localScopeTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localScope);
                               list.add(param);
                               this.localScope =
                             (java.lang.String[])list.toArray(
                            new java.lang.String[list.size()]);

                             }
                             

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://dto.oauth2.identity.carbon.wso2.org/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":OAuth2AccessTokenReqDTO",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "OAuth2AccessTokenReqDTO",
                           xmlWriter);
                   }

               
                   }
                if (localAssertionTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "assertion", xmlWriter);
                             

                                          if (localAssertion==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAssertion);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAuthorizationCodeTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "authorizationCode", xmlWriter);
                             

                                          if (localAuthorizationCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAuthorizationCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCallbackURITracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "callbackURI", xmlWriter);
                             

                                          if (localCallbackURI==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCallbackURI);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localClientIdTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "clientId", xmlWriter);
                             

                                          if (localClientId==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localClientId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localClientSecretTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "clientSecret", xmlWriter);
                             

                                          if (localClientSecret==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localClientSecret);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGrantTypeTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "grantType", xmlWriter);
                             

                                          if (localGrantType==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGrantType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRefreshTokenTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "refreshToken", xmlWriter);
                             

                                          if (localRefreshToken==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRefreshToken);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResourceOwnerPasswordTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "resourceOwnerPassword", xmlWriter);
                             

                                          if (localResourceOwnerPassword==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResourceOwnerPassword);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResourceOwnerUsernameTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "resourceOwnerUsername", xmlWriter);
                             

                                          if (localResourceOwnerUsername==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResourceOwnerUsername);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localScopeTracker){
                             if (localScope!=null) {
                                   namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                   for (int i = 0;i < localScope.length;i++){
                                        
                                            if (localScope[i] != null){
                                        
                                                writeStartElement(null, namespace, "scope", xmlWriter);

                                            
                                                        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localScope[i]));
                                                    
                                                xmlWriter.writeEndElement();
                                              
                                                } else {
                                                   
                                                           // write null attribute
                                                            namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                                            writeStartElement(null, namespace, "scope", xmlWriter);
                                                            writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                            xmlWriter.writeEndElement();
                                                       
                                                }

                                   }
                             } else {
                                 
                                         // write the null attribute
                                        // write null attribute
                                           writeStartElement(null, "http://dto.oauth2.identity.carbon.wso2.org/xsd", "scope", xmlWriter);

                                           // write the nil attribute
                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                           xmlWriter.writeEndElement();
                                    
                             }

                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://dto.oauth2.identity.carbon.wso2.org/xsd")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localAssertionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "assertion"));
                                 
                                         elementList.add(localAssertion==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAssertion));
                                    } if (localAuthorizationCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "authorizationCode"));
                                 
                                         elementList.add(localAuthorizationCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthorizationCode));
                                    } if (localCallbackURITracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "callbackURI"));
                                 
                                         elementList.add(localCallbackURI==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCallbackURI));
                                    } if (localClientIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "clientId"));
                                 
                                         elementList.add(localClientId==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localClientId));
                                    } if (localClientSecretTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "clientSecret"));
                                 
                                         elementList.add(localClientSecret==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localClientSecret));
                                    } if (localGrantTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "grantType"));
                                 
                                         elementList.add(localGrantType==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGrantType));
                                    } if (localRefreshTokenTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "refreshToken"));
                                 
                                         elementList.add(localRefreshToken==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRefreshToken));
                                    } if (localResourceOwnerPasswordTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "resourceOwnerPassword"));
                                 
                                         elementList.add(localResourceOwnerPassword==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResourceOwnerPassword));
                                    } if (localResourceOwnerUsernameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "resourceOwnerUsername"));
                                 
                                         elementList.add(localResourceOwnerUsername==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResourceOwnerUsername));
                                    } if (localScopeTracker){
                            if (localScope!=null){
                                  for (int i = 0;i < localScope.length;i++){
                                      
                                         if (localScope[i] != null){
                                          elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                              "scope"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localScope[i]));
                                          } else {
                                             
                                                    elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                              "scope"));
                                                    elementList.add(null);
                                                
                                          }
                                      

                                  }
                            } else {
                              
                                    elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                              "scope"));
                                    elementList.add(null);
                                
                            }

                        }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static OAuth2AccessTokenReqDTO parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            OAuth2AccessTokenReqDTO object =
                new OAuth2AccessTokenReqDTO();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"OAuth2AccessTokenReqDTO".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (OAuth2AccessTokenReqDTO)org.wso2.carbon.identity.oauth2.stub.types.axis2.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list10 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","assertion").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAssertion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","authorizationCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthorizationCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","callbackURI").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCallbackURI(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","clientId").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setClientId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","clientSecret").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setClientSecret(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","grantType").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGrantType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","refreshToken").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRefreshToken(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","resourceOwnerPassword").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResourceOwnerPassword(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","resourceOwnerUsername").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResourceOwnerUsername(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","scope").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                              nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                              if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                  list10.add(null);
                                                       
                                                  reader.next();
                                              } else {
                                            list10.add(reader.getElementText());
                                            }
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone10 = false;
                                            while(!loopDone10){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone10 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","scope").equals(reader.getName())){
                                                         
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list10.add(null);
                                                                   
                                                              reader.next();
                                                          } else {
                                                        list10.add(reader.getElementText());
                                                        }
                                                    }else{
                                                        loopDone10 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                                    object.setScope((java.lang.String[])
                                                        list10.toArray(new java.lang.String[list10.size()]));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    