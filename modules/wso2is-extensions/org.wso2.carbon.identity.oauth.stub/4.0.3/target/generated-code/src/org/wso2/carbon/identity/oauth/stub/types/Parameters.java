
/**
 * Parameters.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:24 PDT)
 */

            
                package org.wso2.carbon.identity.oauth.stub.types;
            

            /**
            *  Parameters bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class Parameters
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = Parameters
                Namespace URI = http://oauth.identity.carbon.wso2.org/xsd
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for AccessTokenIssued
                        */

                        
                                    protected boolean localAccessTokenIssued ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccessTokenIssuedTracker = false ;

                           public boolean isAccessTokenIssuedSpecified(){
                               return localAccessTokenIssuedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getAccessTokenIssued(){
                               return localAccessTokenIssued;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccessTokenIssued
                               */
                               public void setAccessTokenIssued(boolean param){
                            
                                       // setting primitive attribute tracker to true
                                       localAccessTokenIssuedTracker =
                                       true;
                                   
                                            this.localAccessTokenIssued=param;
                                    

                               }
                            

                        /**
                        * field for AppName
                        */

                        
                                    protected java.lang.String localAppName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAppNameTracker = false ;

                           public boolean isAppNameSpecified(){
                               return localAppNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAppName(){
                               return localAppName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AppName
                               */
                               public void setAppName(java.lang.String param){
                            localAppNameTracker = true;
                                   
                                            this.localAppName=param;
                                    

                               }
                            

                        /**
                        * field for AuthorizedbyUserName
                        */

                        
                                    protected java.lang.String localAuthorizedbyUserName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAuthorizedbyUserNameTracker = false ;

                           public boolean isAuthorizedbyUserNameSpecified(){
                               return localAuthorizedbyUserNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAuthorizedbyUserName(){
                               return localAuthorizedbyUserName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuthorizedbyUserName
                               */
                               public void setAuthorizedbyUserName(java.lang.String param){
                            localAuthorizedbyUserNameTracker = true;
                                   
                                            this.localAuthorizedbyUserName=param;
                                    

                               }
                            

                        /**
                        * field for AuthorizedbyUserPassword
                        */

                        
                                    protected java.lang.String localAuthorizedbyUserPassword ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAuthorizedbyUserPasswordTracker = false ;

                           public boolean isAuthorizedbyUserPasswordSpecified(){
                               return localAuthorizedbyUserPasswordTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAuthorizedbyUserPassword(){
                               return localAuthorizedbyUserPassword;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuthorizedbyUserPassword
                               */
                               public void setAuthorizedbyUserPassword(java.lang.String param){
                            localAuthorizedbyUserPasswordTracker = true;
                                   
                                            this.localAuthorizedbyUserPassword=param;
                                    

                               }
                            

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
                        * field for CallbackConfirmed
                        */

                        
                                    protected java.lang.String localCallbackConfirmed ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCallbackConfirmedTracker = false ;

                           public boolean isCallbackConfirmedSpecified(){
                               return localCallbackConfirmedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCallbackConfirmed(){
                               return localCallbackConfirmed;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CallbackConfirmed
                               */
                               public void setCallbackConfirmed(java.lang.String param){
                            localCallbackConfirmedTracker = true;
                                   
                                            this.localCallbackConfirmed=param;
                                    

                               }
                            

                        /**
                        * field for DisplayName
                        */

                        
                                    protected java.lang.String localDisplayName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDisplayNameTracker = false ;

                           public boolean isDisplayNameSpecified(){
                               return localDisplayNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDisplayName(){
                               return localDisplayName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DisplayName
                               */
                               public void setDisplayName(java.lang.String param){
                            localDisplayNameTracker = true;
                                   
                                            this.localDisplayName=param;
                                    

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
                        * field for OauthCallback
                        */

                        
                                    protected java.lang.String localOauthCallback ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOauthCallbackTracker = false ;

                           public boolean isOauthCallbackSpecified(){
                               return localOauthCallbackTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOauthCallback(){
                               return localOauthCallback;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OauthCallback
                               */
                               public void setOauthCallback(java.lang.String param){
                            localOauthCallbackTracker = true;
                                   
                                            this.localOauthCallback=param;
                                    

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
                        * field for OauthToken
                        */

                        
                                    protected java.lang.String localOauthToken ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOauthTokenTracker = false ;

                           public boolean isOauthTokenSpecified(){
                               return localOauthTokenTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOauthToken(){
                               return localOauthToken;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OauthToken
                               */
                               public void setOauthToken(java.lang.String param){
                            localOauthTokenTracker = true;
                                   
                                            this.localOauthToken=param;
                                    

                               }
                            

                        /**
                        * field for OauthTokenSecret
                        */

                        
                                    protected java.lang.String localOauthTokenSecret ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOauthTokenSecretTracker = false ;

                           public boolean isOauthTokenSecretSpecified(){
                               return localOauthTokenSecretTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOauthTokenSecret(){
                               return localOauthTokenSecret;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OauthTokenSecret
                               */
                               public void setOauthTokenSecret(java.lang.String param){
                            localOauthTokenSecretTracker = true;
                                   
                                            this.localOauthTokenSecret=param;
                                    

                               }
                            

                        /**
                        * field for OauthTokenVerifier
                        */

                        
                                    protected java.lang.String localOauthTokenVerifier ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOauthTokenVerifierTracker = false ;

                           public boolean isOauthTokenVerifierSpecified(){
                               return localOauthTokenVerifierTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOauthTokenVerifier(){
                               return localOauthTokenVerifier;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OauthTokenVerifier
                               */
                               public void setOauthTokenVerifier(java.lang.String param){
                            localOauthTokenVerifierTracker = true;
                                   
                                            this.localOauthTokenVerifier=param;
                                    

                               }
                            

                        /**
                        * field for Scope
                        */

                        
                                    protected java.lang.String localScope ;
                                
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
                           * @return java.lang.String
                           */
                           public  java.lang.String getScope(){
                               return localScope;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Scope
                               */
                               public void setScope(java.lang.String param){
                            localScopeTracker = true;
                                   
                                            this.localScope=param;
                                    

                               }
                            

                        /**
                        * field for Version
                        */

                        
                                    protected java.lang.String localVersion ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVersionTracker = false ;

                           public boolean isVersionSpecified(){
                               return localVersionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getVersion(){
                               return localVersion;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Version
                               */
                               public void setVersion(java.lang.String param){
                            localVersionTracker = true;
                                   
                                            this.localVersion=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://oauth.identity.carbon.wso2.org/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":Parameters",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "Parameters",
                           xmlWriter);
                   }

               
                   }
                if (localAccessTokenIssuedTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "accessTokenIssued", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("accessTokenIssued cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccessTokenIssued));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAppNameTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "appName", xmlWriter);
                             

                                          if (localAppName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAppName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAuthorizedbyUserNameTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "authorizedbyUserName", xmlWriter);
                             

                                          if (localAuthorizedbyUserName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAuthorizedbyUserName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAuthorizedbyUserPasswordTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "authorizedbyUserPassword", xmlWriter);
                             

                                          if (localAuthorizedbyUserPassword==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAuthorizedbyUserPassword);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBaseStringTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "baseString", xmlWriter);
                             

                                          if (localBaseString==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBaseString);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCallbackConfirmedTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "callbackConfirmed", xmlWriter);
                             

                                          if (localCallbackConfirmed==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCallbackConfirmed);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDisplayNameTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "displayName", xmlWriter);
                             

                                          if (localDisplayName==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDisplayName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHttpMethodTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "httpMethod", xmlWriter);
                             

                                          if (localHttpMethod==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHttpMethod);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthCallbackTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthCallback", xmlWriter);
                             

                                          if (localOauthCallback==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthCallback);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthConsumerKeyTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthConsumerKey", xmlWriter);
                             

                                          if (localOauthConsumerKey==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthConsumerKey);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthNonceTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthNonce", xmlWriter);
                             

                                          if (localOauthNonce==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthNonce);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthSignatureTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthSignature", xmlWriter);
                             

                                          if (localOauthSignature==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthSignature);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthSignatureMethodTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthSignatureMethod", xmlWriter);
                             

                                          if (localOauthSignatureMethod==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthSignatureMethod);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthTimeStampTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthTimeStamp", xmlWriter);
                             

                                          if (localOauthTimeStamp==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthTimeStamp);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthTokenTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthToken", xmlWriter);
                             

                                          if (localOauthToken==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthToken);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthTokenSecretTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthTokenSecret", xmlWriter);
                             

                                          if (localOauthTokenSecret==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthTokenSecret);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOauthTokenVerifierTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "oauthTokenVerifier", xmlWriter);
                             

                                          if (localOauthTokenVerifier==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOauthTokenVerifier);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localScopeTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "scope", xmlWriter);
                             

                                          if (localScope==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localScope);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localVersionTracker){
                                    namespace = "http://oauth.identity.carbon.wso2.org/xsd";
                                    writeStartElement(null, namespace, "version", xmlWriter);
                             

                                          if (localVersion==null){
                                              // write the nil attribute
                                              
                                                     writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localVersion);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://oauth.identity.carbon.wso2.org/xsd")){
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

                 if (localAccessTokenIssuedTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "accessTokenIssued"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccessTokenIssued));
                            } if (localAppNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "appName"));
                                 
                                         elementList.add(localAppName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAppName));
                                    } if (localAuthorizedbyUserNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "authorizedbyUserName"));
                                 
                                         elementList.add(localAuthorizedbyUserName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthorizedbyUserName));
                                    } if (localAuthorizedbyUserPasswordTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "authorizedbyUserPassword"));
                                 
                                         elementList.add(localAuthorizedbyUserPassword==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthorizedbyUserPassword));
                                    } if (localBaseStringTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "baseString"));
                                 
                                         elementList.add(localBaseString==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBaseString));
                                    } if (localCallbackConfirmedTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "callbackConfirmed"));
                                 
                                         elementList.add(localCallbackConfirmed==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCallbackConfirmed));
                                    } if (localDisplayNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "displayName"));
                                 
                                         elementList.add(localDisplayName==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDisplayName));
                                    } if (localHttpMethodTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "httpMethod"));
                                 
                                         elementList.add(localHttpMethod==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHttpMethod));
                                    } if (localOauthCallbackTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthCallback"));
                                 
                                         elementList.add(localOauthCallback==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthCallback));
                                    } if (localOauthConsumerKeyTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthConsumerKey"));
                                 
                                         elementList.add(localOauthConsumerKey==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthConsumerKey));
                                    } if (localOauthNonceTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthNonce"));
                                 
                                         elementList.add(localOauthNonce==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthNonce));
                                    } if (localOauthSignatureTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthSignature"));
                                 
                                         elementList.add(localOauthSignature==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthSignature));
                                    } if (localOauthSignatureMethodTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthSignatureMethod"));
                                 
                                         elementList.add(localOauthSignatureMethod==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthSignatureMethod));
                                    } if (localOauthTimeStampTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthTimeStamp"));
                                 
                                         elementList.add(localOauthTimeStamp==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthTimeStamp));
                                    } if (localOauthTokenTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthToken"));
                                 
                                         elementList.add(localOauthToken==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthToken));
                                    } if (localOauthTokenSecretTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthTokenSecret"));
                                 
                                         elementList.add(localOauthTokenSecret==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthTokenSecret));
                                    } if (localOauthTokenVerifierTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "oauthTokenVerifier"));
                                 
                                         elementList.add(localOauthTokenVerifier==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOauthTokenVerifier));
                                    } if (localScopeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "scope"));
                                 
                                         elementList.add(localScope==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localScope));
                                    } if (localVersionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd",
                                                                      "version"));
                                 
                                         elementList.add(localVersion==null?null:
                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVersion));
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
        public static Parameters parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Parameters object =
                new Parameters();

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
                    
                            if (!"Parameters".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Parameters)org.wso2.carbon.identity.oauth.stub.types.axis2.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","accessTokenIssued").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"accessTokenIssued" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccessTokenIssued(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","appName").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAppName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","authorizedbyUserName").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthorizedbyUserName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","authorizedbyUserPassword").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthorizedbyUserPassword(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","baseString").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","callbackConfirmed").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCallbackConfirmed(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","displayName").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDisplayName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","httpMethod").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","oauthCallback").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOauthCallback(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","oauthConsumerKey").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","oauthNonce").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","oauthSignature").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","oauthSignatureMethod").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","oauthTimeStamp").equals(reader.getName())){
                                
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
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","oauthToken").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOauthToken(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","oauthTokenSecret").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOauthTokenSecret(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","oauthTokenVerifier").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOauthTokenVerifier(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","scope").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setScope(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                            
                                       } else {
                                           
                                           
                                           reader.getElementText(); // throw away text nodes if any.
                                       }
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org/xsd","version").equals(reader.getName())){
                                
                                       nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                       if (!"true".equals(nillableValue) && !"1".equals(nillableValue)){
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setVersion(
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
           
    