
/**
 * OAuthConsumerDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:24 PDT)
 */

            
                package org.wso2.carbon.identity.oauth.stub.dto;
            

            /**
            *  OAuthConsumerDTO bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class OAuthConsumerDTO
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = OAuthConsumerDTO
                Namespace URI = http://dto.oauth.identity.carbon.wso2.org/xsd
                Namespace Prefix = ns5
                */
            

                        /**
                        * field for BaseString
                        */

                        
                                    protected java.lang.String localBaseString ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBaseStringTracker = false ;

                           public boolean isBaseStringSpecified(){
                               return localBaseStringTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBaseString(){
                               return localBaseString;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BaseString
                               */
                               public void setBaseString(java.lang.String param){
                            localBaseStringTracker = true;
                                   
                                            this.localBaseString=param;
                                    

                               }
                            

                        /**
                        * field for HttpMethod
                        */

                        
                                    protected java.lang.String localHttpMethod ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHttpMethodTracker = false ;

                           public boolean isHttpMethodSpecified(){
                               return localHttpMethodTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHttpMethod(){
                               return localHttpMethod;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HttpMethod
                               */
                               public void setHttpMethod(java.lang.String param){
                            localHttpMethodTracker = true;
                                   
                                            this.localHttpMethod=param;
                                    

                               }
                            

                        /**
                        * field for OauthConsumerKey
                        */

                        
                                    protected java.lang.String localOauthConsumerKey ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOauthConsumerKeyTracker = false ;

                           public boolean isOauthConsumerKeySpecified(){
                               return localOauthConsumerKeyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOauthConsumerKey(){
                               return localOauthConsumerKey;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OauthConsumerKey
                               */
                               public void setOauthConsumerKey(java.lang.String param){
                            localOauthConsumerKeyTracker = true;
                                   
                                            this.localOauthConsumerKey=param;
                                    

                               }
                            

                        /**
                        * field for OauthNonce
                        */

                        
                                    protected java.lang.String localOauthNonce ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOauthNonceTracker = false ;

                           public boolean isOauthNonceSpecified(){
                               return localOauthNonceTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOauthNonce(){
                               return localOauthNonce;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OauthNonce
                               */
                               public void setOauthNonce(java.lang.String param){
                            localOauthNonceTracker = true;
                                   
                                            this.localOauthNonce=param;
                                    

                               }
                            

                        /**
                        * field for OauthSignature
                        */

                        
                                    protected java.lang.String localOauthSignature ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOauthSignatureTracker = false ;

                           public boolean isOauthSignatureSpecified(){
                               return localOauthSignatureTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOauthSignature(){
                               return localOauthSignature;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OauthSignature
                               */
                               public void setOauthSignature(java.lang.String param){
                            localOauthSignatureTracker = true;
                                   
                                            this.localOauthSignature=param;
                                    

                               }
                            

                        /**
                        * field for OauthSignatureMethod
                        */

                        
                                    protected java.lang.String localOauthSignatureMethod ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOauthSignatureMethodTracker = false ;

                           public boolean isOauthSignatureMethodSpecified(){
                               return localOauthSignatureMethodTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOauthSignatureMethod(){
                               return localOauthSignatureMethod;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OauthSignatureMethod
                               */
                               public void setOauthSignatureMethod(java.lang.String param){
                            localOauthSignatureMethodTracker = true;
                                   
                                            this.localOauthSignatureMethod=param;
                                    

                               }
                            

                        /**
                        * field for OauthTimeStamp
                        */

                        
                                    protected java.lang.String localOauthTimeStamp ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOauthTimeStampTracker = false ;

                           public boolean isOauthTimeStampSpecified(){
                               return localOauthTimeStampTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOauthTimeStamp(){
                               return localOauthTimeStamp;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OauthTimeStamp
                               */
                               public void setOauthTimeStamp(java.lang.String param){
                            localOauthTimeStampTracker = true;
                                   
                                            this.localOauthTimeStamp=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://dto.oauth.identity.carbon.wso2.org/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":OAuthConsumerDTO",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "OAuthConsumerDTO",
                           xmlWriter);
                   }

               
                   }
                if (localBaseStringTracker){
                                    namespace = "http://dto.oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "baseString", xmlWriter);
                             

                                          if (localBaseString==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBaseString);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHttpMethodTracker){
                                    namespace = "http://dto.oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "httpMethod", xmlWriter);
                             

                                          if (localHttpMethod==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHttpMethod);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthConsumerKeyTracker){
                                    namespace = "http://dto.oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthConsumerKey", xmlWriter);
                             

                                          if (localOauthConsumerKey==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthConsumerKey);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthNonceTracker){
                                    namespace = "http://dto.oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthNonce", xmlWriter);
                             

                                          if (localOauthNonce==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthNonce);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthSignatureTracker){
                                    namespace = "http://dto.oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthSignature", xmlWriter);
                             

                                          if (localOauthSignature==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthSignature);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthSignatureMethodTracker){
                                    namespace = "http://dto.oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthSignatureMethod", xmlWriter);
                             

                                          if (localOauthSignatureMethod==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthSignatureMethod);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthTimeStampTracker){
                                    namespace = "http://dto.oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthTimeStamp", xmlWriter);
                             

                                          if (localOauthTimeStamp==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthTimeStamp);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://dto.oauth.identity.carbon.wso2.org/xsd")){
                return "ns5";
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

                 if (localBaseStringTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd",
                                                                      "baseString"));
                                 
                                         elementList.add(localBaseString==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBaseString));
                                    } if (localHttpMethodTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd",
                                                                      "httpMethod"));
                                 
                                         elementList.add(localHttpMethod==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHttpMethod));
                                    } if (localOauthConsumerKeyTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthConsumerKey"));
                                 
                                         elementList.add(localOauthConsumerKey==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthConsumerKey));
                                    } if (localOauthNonceTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthNonce"));
                                 
                                         elementList.add(localOauthNonce==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthNonce));
                                    } if (localOauthSignatureTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthSignature"));
                                 
                                         elementList.add(localOauthSignature==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthSignature));
                                    } if (localOauthSignatureMethodTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthSignatureMethod"));
                                 
                                         elementList.add(localOauthSignatureMethod==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthSignatureMethod));
                                    } if (localOauthTimeStampTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthTimeStamp"));
                                 
                                         elementList.add(localOauthTimeStamp==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthTimeStamp));
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
        public static OAuthConsumerDTO parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            OAuthConsumerDTO object =
                new OAuthConsumerDTO();

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
                    
                            if (!"OAuthConsumerDTO".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (OAuthConsumerDTO)org.wso2.carbon.identity.oauth.stub.types.axis2.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd","baseString").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBaseString(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd","httpMethod").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHttpMethod(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd","oauthConsumerKey").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOauthConsumerKey(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd","oauthNonce").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOauthNonce(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd","oauthSignature").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOauthSignature(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd","oauthSignatureMethod").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOauthSignatureMethod(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://dto.oauth.identity.carbon.wso2.org/xsd","oauthTimeStamp").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOauthTimeStamp(
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
           
    