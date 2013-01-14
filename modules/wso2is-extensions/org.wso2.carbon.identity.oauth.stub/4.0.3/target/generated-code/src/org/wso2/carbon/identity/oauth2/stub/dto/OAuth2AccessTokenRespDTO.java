
/**
 * OAuth2AccessTokenRespDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:24 PDT)
 */

            
                package org.wso2.carbon.identity.oauth2.stub.dto;
            

            /**
            *  OAuth2AccessTokenRespDTO bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class OAuth2AccessTokenRespDTO
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = OAuth2AccessTokenRespDTO
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
                        * field for Error
                        */

                        
                                    protected boolean localError ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localErrorTracker = false ;

                           public boolean isErrorSpecified(){
                               return localErrorTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getError(){
                               return localError;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Error
                               */
                               public void setError(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localErrorTracker =
                                       true;
                                   
                                            this.localError=param;
                                    

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
                        * field for ExpiresIn
                        */

                        
                                    protected long localExpiresIn ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localExpiresInTracker = false ;

                           public boolean isExpiresInSpecified(){
                               return localExpiresInTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getExpiresIn(){
                               return localExpiresIn;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ExpiresIn
                               */
                               public void setExpiresIn(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localExpiresInTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localExpiresIn=param;
                                    

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
                        * field for RespHeaders
                        * This was an Array!
                        */

                        
                                    protected org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader[] localRespHeaders ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRespHeadersTracker = false ;

                           public boolean isRespHeadersSpecified(){
                               return localRespHeadersTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader[]
                           */
                           public  org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader[] getRespHeaders(){
                               return localRespHeaders;
                           }

                           
                        


                               
                              /**
                               * validate the array for RespHeaders
                               */
                              protected void validateRespHeaders(org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param RespHeaders
                              */
                              public void setRespHeaders(org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader[] param){
                              
                                   validateRespHeaders(param);

                               localRespHeadersTracker = true;
                                      
                                      this.localRespHeaders=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader
                             */
                             public void addRespHeaders(org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader param){
                                   if (localRespHeaders == null){
                                   localRespHeaders = new org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader[]{};
                                   }

                            
                                 //update the setting tracker
                                localRespHeadersTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localRespHeaders);
                               list.add(param);
                               this.localRespHeaders =
                             (org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader[])list.toArray(
                            new org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader[list.size()]);

                             }
                             

                        /**
                        * field for TokenType
                        */

                        
                                    protected java.lang.String localTokenType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTokenTypeTracker = false ;

                           public boolean isTokenTypeSpecified(){
                               return localTokenTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTokenType(){
                               return localTokenType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TokenType
                               */
                               public void setTokenType(java.lang.String param){
                            localTokenTypeTracker = true;
                                   
                                            this.localTokenType=param;
                                    

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
                           namespacePrefix+":OAuth2AccessTokenRespDTO",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "OAuth2AccessTokenRespDTO",
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
                             } if (localErrorTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "error", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("error cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localError));
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
                             } if (localExpiresInTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "expiresIn", xmlWriter);
                             
                                               if (localExpiresIn==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("expiresIn cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExpiresIn));
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
                             } if (localRespHeadersTracker){
                                       if (localRespHeaders!=null){
                                            for (int i = 0;i < localRespHeaders.length;i++){
                                                if (localRespHeaders[i] != null){
                                                 localRespHeaders[i].serialize(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","respHeaders"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                            writeStartElement(null, "http://dto.oauth2.identity.carbon.wso2.org/xsd", "respHeaders", xmlWriter);

                                                           // write the nil attribute
                                                           writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                           xmlWriter.writeEndElement();
                                                    
                                                }

                                            }
                                     } else {
                                        
                                                writeStartElement(null, "http://dto.oauth2.identity.carbon.wso2.org/xsd", "respHeaders", xmlWriter);

                                               // write the nil attribute
                                               writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                               xmlWriter.writeEndElement();
                                        
                                    }
                                 } if (localTokenTypeTracker){
                                    namespace = "http://dto.oauth2.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "tokenType", xmlWriter);
                             

                                          if (localTokenType==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTokenType);
                                            
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
                                    } if (localCallbackURITracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "callbackURI"));
                                 
                                         elementList.add(localCallbackURI==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCallbackURI));
                                    } if (localErrorTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "error"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localError));
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
                                    } if (localExpiresInTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "expiresIn"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExpiresIn));
                            } if (localRefreshTokenTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "refreshToken"));
                                 
                                         elementList.add(localRefreshToken==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRefreshToken));
                                    } if (localRespHeadersTracker){
                             if (localRespHeaders!=null) {
                                 for (int i = 0;i < localRespHeaders.length;i++){

                                    if (localRespHeaders[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                          "respHeaders"));
                                         elementList.add(localRespHeaders[i]);
                                    } else {
                                        
                                                elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                          "respHeaders"));
                                                elementList.add(null);
                                            
                                    }

                                 }
                             } else {
                                 
                                        elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                          "respHeaders"));
                                        elementList.add(localRespHeaders);
                                    
                             }

                        } if (localTokenTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd",
                                                                      "tokenType"));
                                 
                                         elementList.add(localTokenType==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTokenType));
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
        public static OAuth2AccessTokenRespDTO parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            OAuth2AccessTokenRespDTO object =
                new OAuth2AccessTokenRespDTO();

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
                    
                            if (!"OAuth2AccessTokenRespDTO".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (OAuth2AccessTokenRespDTO)org.wso2.carbon.identity.oauth2.stub.types.axis2.ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","error").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"error" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setError(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","expiresIn").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"expiresIn" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setExpiresIn(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setExpiresIn(java.lang.Long.MIN_VALUE);
                                           
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","respHeaders").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    
                                                          nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                          if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                              list8.add(null);
                                                              reader.next();
                                                          } else {
                                                        list8.add(org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader.Factory.parse(reader));
                                                                }
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone8 = false;
                                                        while(!loopDone8){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone8 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","respHeaders").equals(reader.getName())){
                                                                    
                                                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                                                          list8.add(null);
                                                                          reader.next();
                                                                      } else {
                                                                    list8.add(org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader.Factory.parse(reader));
                                                                        }
                                                                }else{
                                                                    loopDone8 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setRespHeaders((org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                org.wso2.carbon.identity.oauth2.stub.types.ResponseHeader.class,
                                                                list8));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth2.identity.carbon.wso2.org/xsd","tokenType").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTokenType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
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
           
    