
/**
 * OAuth2AuthorizeRespDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:24 PDT)
 */

            
                package org.wso2.carbon.identity.oauth2.stub.dto;
            

            /**
            *  OAuth2AuthorizeRespDTO bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class OAuth2AuthorizeRespDTO
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = OAuth2AuthorizeRespDTO
                Namespace URI = http://dto.oauth2.identity.carbon.wso2.org/xsd
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for AccessToken
                        */

                        
                                    protected java.lang.String localAccessToken ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccessTokenTracker = false ;

                           public boolean isAccessTokenSpecified(){
                               return localAccessTokenTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccessToken(){
                               return localAccessToken;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccessToken
                               */
                               public void setAccessToken(java.lang.String param){
                            localAccessTokenTracker = true;
                                   
                                            this.localAccessToken=param;
                                    

                               }
                            

                        /**
                        * field for Authenticated
                        */

                        
                                    protected boolean localAuthenticated ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAuthenticatedTracker = false ;

                           public boolean isAuthenticatedSpecified(){
                               return localAuthenticatedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getAuthenticated(){
                               return localAuthenticated;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Authenticated
                               */
                               public void setAuthenticated(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localAuthenticatedTracker =
                                       true;
                                   
                                            this.localAuthenticated=param;
                                    

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
                        * field for Authorized
                        */

                        
                                    protected boolean localAuthorized ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAuthorizedTracker = false ;

                           public boolean isAuthorizedSpecified(){
                               return localAuthorizedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getAuthorized(){
                               return localAuthorized;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Authorized
                               */
                               public void setAuthorized(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localAuthorizedTracker =
                                       true;
                                   
                                            this.localAuthorized=param;
                                    

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
                        * field for ErrorCode
                        */

                        
                                    protected java.lang.String localErrorCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localErrorCodeTracker = false ;

                           public boolean isErrorCodeSpecified(){
                               return localErrorCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getErrorCode(){
                               return localErrorCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ErrorCode
                               */
                               public void setErrorCode(java.lang.String param){
                            localErrorCodeTracker = true;
                                   
                                            this.localErrorCode=param;
                                    

                               }
                            

                        /**
                        * field for ErrorMsg
                        */

                        
                                    protected java.lang.String localErrorMsg ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localErrorMsgTracker = false ;

                           public boolean isErrorMsgSpecified(){
                               return localErrorMsgTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getErrorMsg(){
                               return localErrorMsg;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ErrorMsg
                               */
                               public void setErrorMsg(java.lang.String param){
                            localErrorMsgTracker = true;
                                   
                                            this.localErrorMsg=param;
                                    

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
                        * field for ValidityPeriod
                        */

                        
                                    protected long localValidityPeriod ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localValidityPeriodTracker = false ;

                           public boolean isValidityPeriodSpecified(){
                               return localValidityPeriodTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getValidityPeriod(){
                               return localValidityPeriod;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ValidityPeriod
                               */
                               public void setValidityPeriod(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localValidityPeriodTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localValidityPeriod=param;
                                    

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
                           namespacePrefix+":OAuth2AuthorizeRespDTO",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "OAuth2AuthorizeRespDTO",
                           xmlWriter);
                   }

               
                   }
                if (localAccessTokenTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "accessToken", xmlWriter);
                             

                                          if (localAccessToken==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccessToken);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAuthenticatedTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "authenticated", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("authenticated cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthenticated));
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
                             } if (localAuthorizedTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "authorized", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("authorized cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthorized));
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
                             } if (localErrorCodeTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "errorCode", xmlWriter);
                             

                                          if (localErrorCode==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localErrorCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localErrorMsgTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "errorMsg", xmlWriter);
                             

                                          if (localErrorMsg==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localErrorMsg);
                                            
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

                        } if (localValidityPeriodTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "validityPeriod", xmlWriter);
                             
                                               if (localValidityPeriod==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("validityPeriod cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localValidityPeriod));
                                               }
                                    
                                   xmlWriter.writeEndElement();
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

                 if (localAccessTokenTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "accessToken"));
                                 
                                         elementList.add(localAccessToken==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccessToken));
                                    } if (localAuthenticatedTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "authenticated"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthenticated));
                            } if (localAuthorizationCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "authorizationCode"));
                                 
                                         elementList.add(localAuthorizationCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthorizationCode));
                                    } if (localAuthorizedTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "authorized"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthorized));
                            } if (localCallbackURITracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "callbackURI"));
                                 
                                         elementList.add(localCallbackURI==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCallbackURI));
                                    } if (localErrorCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "errorCode"));
                                 
                                         elementList.add(localErrorCode==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localErrorCode));
                                    } if (localErrorMsgTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "errorMsg"));
                                 
                                         elementList.add(localErrorMsg==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localErrorMsg));
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

                        } if (localValidityPeriodTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "validityPeriod"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localValidityPeriod));
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
        public static OAuth2AuthorizeRespDTO parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            OAuth2AuthorizeRespDTO object =
                new OAuth2AuthorizeRespDTO();

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
                    
                            if (!"OAuth2AuthorizeRespDTO".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (OAuth2AuthorizeRespDTO)org.wso2.carbon.identity.oauth2.stub.types.axis2.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list8 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","accessToken").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccessToken(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","authenticated").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"authenticated" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthenticated(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","authorized").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"authorized" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthorized(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","errorCode").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setErrorCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","errorMsg").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setErrorMsg(
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
                                                  list8.add(null);
                                                       
                                                  reader.next();
                                              } else {
                                            list8.add(reader.getElementText());
                                            }
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone8 = false;
                                            while(!loopDone8){
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
                                                    loopDone8 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","scope").equals(reader.getName())){
                                                         
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list8.add(null);
                                                                   
                                                              reader.next();
                                                          } else {
                                                        list8.add(reader.getElementText());
                                                        }
                                                    }else{
                                                        loopDone8 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                                    object.setScope((java.lang.String[])
                                                        list8.toArray(new java.lang.String[list8.size()]));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","validityPeriod").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"validityPeriod" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setValidityPeriod(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setValidityPeriod(java.lang.Long.MIN_VALUE);
                                           
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
           
    