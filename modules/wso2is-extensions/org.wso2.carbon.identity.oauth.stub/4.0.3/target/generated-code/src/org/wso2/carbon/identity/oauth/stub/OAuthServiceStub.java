
/**
 * OAuthServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */
        package org.wso2.carbon.identity.oauth.stub;

        

        /*
        *  OAuthServiceStub java implementation
        */

        
        public class OAuthServiceStub extends org.apache.axis2.client.Stub
        implements OAuthService{
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static int counter = 0;

        private static synchronized java.lang.String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1; 
            return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
        }

    
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("OAuthService" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[6];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "validateAuthenticationRequest"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "getScopeAndAppName"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[1]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "getAccessToken"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[2]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "isOAuthConsumerValid"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[3]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "getOauthRequestToken"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[4]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "authorizeOauthRequestToken"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[5]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "validateAuthenticationRequest"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "validateAuthenticationRequest"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "validateAuthenticationRequest"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityException"), "validateAuthenticationRequest"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityException"), "validateAuthenticationRequest"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityException"), "validateAuthenticationRequest"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceException"), "getScopeAndAppName"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceException"), "getScopeAndAppName"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceException"), "getScopeAndAppName"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "getAccessToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "getAccessToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "getAccessToken"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityOAuthAdminException"), "getAccessToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityOAuthAdminException"), "getAccessToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityOAuthAdminException"), "getAccessToken"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityOAuthAdminException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityException"), "isOAuthConsumerValid"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityException"), "isOAuthConsumerValid"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityException"), "isOAuthConsumerValid"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "getOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "getOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "getOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityOAuthAdminException"), "getOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityOAuthAdminException"), "getOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityOAuthAdminException"), "getOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityOAuthAdminException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "authorizeOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "authorizeOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceAuthenticationException"), "authorizeOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityException"), "authorizeOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityException"), "authorizeOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthServiceIdentityException"), "authorizeOauthRequestToken"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException");
           


    }

    /**
      *Constructor that takes in a configContext
      */

    public OAuthServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public OAuthServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
        
	
        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
        
            //Set the soap version
            _serviceClient.getOptions().setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
        
    
    }

    /**
     * Default Constructor
     */
    public OAuthServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"https://localhost:9443/services/OAuthService.OAuthServiceHttpsSoap12Endpoint/" );
                
    }

    /**
     * Default Constructor
     */
    public OAuthServiceStub() throws org.apache.axis2.AxisFault {
        
                    this("https://localhost:9443/services/OAuthService.OAuthServiceHttpsSoap12Endpoint/" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public OAuthServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * 
                     * @see org.wso2.carbon.identity.oauth.stub.OAuthService#validateAuthenticationRequest
                     * @param validateAuthenticationRequest32
                    
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException : 
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException : 
                     */

                    

                            public  org.wso2.carbon.identity.oauth.stub.types.Parameters validateAuthenticationRequest(

                            org.wso2.carbon.identity.oauth.stub.types.Parameters params33)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException
                        ,org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("urn:validateAuthenticationRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequest dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    params33,
                                                    dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "validateAuthenticationRequest")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequestResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return getValidateAuthenticationRequestResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequestResponse)object);
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"validateAuthenticationRequest"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"validateAuthenticationRequest"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"validateAuthenticationRequest"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException)ex;
                        }
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * 
                * @see org.wso2.carbon.identity.oauth.stub.OAuthService#startvalidateAuthenticationRequest
                    * @param validateAuthenticationRequest32
                
                */
                public  void startvalidateAuthenticationRequest(

                 org.wso2.carbon.identity.oauth.stub.types.Parameters params33,

                  final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
             _operationClient.getOptions().setAction("urn:validateAuthenticationRequest");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequest dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    params33,
                                                     dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "validateAuthenticationRequest")));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequestResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultvalidateAuthenticationRequest(
                                            getValidateAuthenticationRequestResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequestResponse)object));
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorvalidateAuthenticationRequest(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"validateAuthenticationRequest"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"validateAuthenticationRequest"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"validateAuthenticationRequest"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException){
														callback.receiveErrorvalidateAuthenticationRequest((org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException)ex);
											            return;
										            }
										            
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException){
														callback.receiveErrorvalidateAuthenticationRequest((org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorvalidateAuthenticationRequest(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorvalidateAuthenticationRequest(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorvalidateAuthenticationRequest(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorvalidateAuthenticationRequest(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorvalidateAuthenticationRequest(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorvalidateAuthenticationRequest(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorvalidateAuthenticationRequest(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorvalidateAuthenticationRequest(f);
                                            }
									    } else {
										    callback.receiveErrorvalidateAuthenticationRequest(f);
									    }
									} else {
									    callback.receiveErrorvalidateAuthenticationRequest(f);
									}
								} else {
								    callback.receiveErrorvalidateAuthenticationRequest(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorvalidateAuthenticationRequest(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[0].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[0].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * 
                     * @see org.wso2.carbon.identity.oauth.stub.OAuthService#getScopeAndAppName
                     * @param getScopeAndAppName36
                    
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceException : 
                     */

                    

                            public  org.wso2.carbon.identity.oauth.stub.types.Parameters getScopeAndAppName(

                            java.lang.String oauthToken37)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,org.wso2.carbon.identity.oauth.stub.OAuthServiceException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
              _operationClient.getOptions().setAction("urn:getScopeAndAppName");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppName dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    oauthToken37,
                                                    dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "getScopeAndAppName")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppNameResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return getGetScopeAndAppNameResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppNameResponse)object);
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getScopeAndAppName"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getScopeAndAppName"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getScopeAndAppName"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthServiceException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * 
                * @see org.wso2.carbon.identity.oauth.stub.OAuthService#startgetScopeAndAppName
                    * @param getScopeAndAppName36
                
                */
                public  void startgetScopeAndAppName(

                 java.lang.String oauthToken37,

                  final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
             _operationClient.getOptions().setAction("urn:getScopeAndAppName");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppName dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    oauthToken37,
                                                     dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "getScopeAndAppName")));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppNameResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetScopeAndAppName(
                                            getGetScopeAndAppNameResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppNameResponse)object));
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetScopeAndAppName(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getScopeAndAppName"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getScopeAndAppName"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getScopeAndAppName"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceException){
														callback.receiveErrorgetScopeAndAppName((org.wso2.carbon.identity.oauth.stub.OAuthServiceException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetScopeAndAppName(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetScopeAndAppName(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetScopeAndAppName(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetScopeAndAppName(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetScopeAndAppName(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetScopeAndAppName(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetScopeAndAppName(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetScopeAndAppName(f);
                                            }
									    } else {
										    callback.receiveErrorgetScopeAndAppName(f);
									    }
									} else {
									    callback.receiveErrorgetScopeAndAppName(f);
									}
								} else {
								    callback.receiveErrorgetScopeAndAppName(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetScopeAndAppName(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[1].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[1].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * 
                     * @see org.wso2.carbon.identity.oauth.stub.OAuthService#getAccessToken
                     * @param getAccessToken40
                    
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException : 
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException : 
                     */

                    

                            public  org.wso2.carbon.identity.oauth.stub.types.Parameters getAccessToken(

                            org.wso2.carbon.identity.oauth.stub.types.Parameters params41)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException
                        ,org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
              _operationClient.getOptions().setAction("urn:getAccessToken");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessToken dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    params41,
                                                    dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "getAccessToken")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessTokenResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return getGetAccessTokenResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessTokenResponse)object);
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAccessToken"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAccessToken"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAccessToken"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException)ex;
                        }
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * 
                * @see org.wso2.carbon.identity.oauth.stub.OAuthService#startgetAccessToken
                    * @param getAccessToken40
                
                */
                public  void startgetAccessToken(

                 org.wso2.carbon.identity.oauth.stub.types.Parameters params41,

                  final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
             _operationClient.getOptions().setAction("urn:getAccessToken");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessToken dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    params41,
                                                     dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "getAccessToken")));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessTokenResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAccessToken(
                                            getGetAccessTokenResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessTokenResponse)object));
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAccessToken(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAccessToken"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAccessToken"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAccessToken"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException){
														callback.receiveErrorgetAccessToken((org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException)ex);
											            return;
										            }
										            
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException){
														callback.receiveErrorgetAccessToken((org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetAccessToken(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAccessToken(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAccessToken(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAccessToken(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAccessToken(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAccessToken(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAccessToken(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAccessToken(f);
                                            }
									    } else {
										    callback.receiveErrorgetAccessToken(f);
									    }
									} else {
									    callback.receiveErrorgetAccessToken(f);
									}
								} else {
								    callback.receiveErrorgetAccessToken(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetAccessToken(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[2].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[2].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * 
                     * @see org.wso2.carbon.identity.oauth.stub.OAuthService#isOAuthConsumerValid
                     * @param isOAuthConsumerValid44
                    
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException : 
                     */

                    

                            public  boolean isOAuthConsumerValid(

                            org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerDTO oauthConsumer45)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
              _operationClient.getOptions().setAction("urn:isOAuthConsumerValid");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValid dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    oauthConsumer45,
                                                    dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "isOAuthConsumerValid")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValidResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return getIsOAuthConsumerValidResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValidResponse)object);
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"isOAuthConsumerValid"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"isOAuthConsumerValid"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"isOAuthConsumerValid"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * 
                * @see org.wso2.carbon.identity.oauth.stub.OAuthService#startisOAuthConsumerValid
                    * @param isOAuthConsumerValid44
                
                */
                public  void startisOAuthConsumerValid(

                 org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerDTO oauthConsumer45,

                  final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
             _operationClient.getOptions().setAction("urn:isOAuthConsumerValid");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValid dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    oauthConsumer45,
                                                     dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "isOAuthConsumerValid")));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValidResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultisOAuthConsumerValid(
                                            getIsOAuthConsumerValidResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValidResponse)object));
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorisOAuthConsumerValid(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"isOAuthConsumerValid"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"isOAuthConsumerValid"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"isOAuthConsumerValid"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException){
														callback.receiveErrorisOAuthConsumerValid((org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorisOAuthConsumerValid(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorisOAuthConsumerValid(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorisOAuthConsumerValid(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorisOAuthConsumerValid(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorisOAuthConsumerValid(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorisOAuthConsumerValid(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorisOAuthConsumerValid(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorisOAuthConsumerValid(f);
                                            }
									    } else {
										    callback.receiveErrorisOAuthConsumerValid(f);
									    }
									} else {
									    callback.receiveErrorisOAuthConsumerValid(f);
									}
								} else {
								    callback.receiveErrorisOAuthConsumerValid(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorisOAuthConsumerValid(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[3].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[3].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * 
                     * @see org.wso2.carbon.identity.oauth.stub.OAuthService#getOauthRequestToken
                     * @param getOauthRequestToken48
                    
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException : 
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException : 
                     */

                    

                            public  org.wso2.carbon.identity.oauth.stub.types.Parameters getOauthRequestToken(

                            org.wso2.carbon.identity.oauth.stub.types.Parameters params49)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException
                        ,org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
              _operationClient.getOptions().setAction("urn:getOauthRequestToken");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestToken dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    params49,
                                                    dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "getOauthRequestToken")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestTokenResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return getGetOauthRequestTokenResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestTokenResponse)object);
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOauthRequestToken"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOauthRequestToken"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOauthRequestToken"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException)ex;
                        }
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * 
                * @see org.wso2.carbon.identity.oauth.stub.OAuthService#startgetOauthRequestToken
                    * @param getOauthRequestToken48
                
                */
                public  void startgetOauthRequestToken(

                 org.wso2.carbon.identity.oauth.stub.types.Parameters params49,

                  final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
             _operationClient.getOptions().setAction("urn:getOauthRequestToken");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestToken dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    params49,
                                                     dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "getOauthRequestToken")));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestTokenResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetOauthRequestToken(
                                            getGetOauthRequestTokenResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestTokenResponse)object));
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetOauthRequestToken(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOauthRequestToken"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOauthRequestToken"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOauthRequestToken"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException){
														callback.receiveErrorgetOauthRequestToken((org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException)ex);
											            return;
										            }
										            
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException){
														callback.receiveErrorgetOauthRequestToken((org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityOAuthAdminException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetOauthRequestToken(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOauthRequestToken(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOauthRequestToken(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOauthRequestToken(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOauthRequestToken(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOauthRequestToken(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOauthRequestToken(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOauthRequestToken(f);
                                            }
									    } else {
										    callback.receiveErrorgetOauthRequestToken(f);
									    }
									} else {
									    callback.receiveErrorgetOauthRequestToken(f);
									}
								} else {
								    callback.receiveErrorgetOauthRequestToken(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorgetOauthRequestToken(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[4].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[4].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * 
                     * @see org.wso2.carbon.identity.oauth.stub.OAuthService#authorizeOauthRequestToken
                     * @param authorizeOauthRequestToken52
                    
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException : 
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException : 
                     */

                    

                            public  org.wso2.carbon.identity.oauth.stub.types.Parameters authorizeOauthRequestToken(

                            org.wso2.carbon.identity.oauth.stub.types.Parameters params53)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException
                        ,org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
              _operationClient.getOptions().setAction("urn:authorizeOauthRequestToken");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestToken dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    params53,
                                                    dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "authorizeOauthRequestToken")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestTokenResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return getAuthorizeOauthRequestTokenResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestTokenResponse)object);
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"authorizeOauthRequestToken"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"authorizeOauthRequestToken"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"authorizeOauthRequestToken"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException)ex;
                        }
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * 
                * @see org.wso2.carbon.identity.oauth.stub.OAuthService#startauthorizeOauthRequestToken
                    * @param authorizeOauthRequestToken52
                
                */
                public  void startauthorizeOauthRequestToken(

                 org.wso2.carbon.identity.oauth.stub.types.Parameters params53,

                  final org.wso2.carbon.identity.oauth.stub.OAuthServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
             _operationClient.getOptions().setAction("urn:authorizeOauthRequestToken");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestToken dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    params53,
                                                     dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "authorizeOauthRequestToken")));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestTokenResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultauthorizeOauthRequestToken(
                                            getAuthorizeOauthRequestTokenResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestTokenResponse)object));
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorauthorizeOauthRequestToken(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"authorizeOauthRequestToken"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"authorizeOauthRequestToken"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"authorizeOauthRequestToken"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException){
														callback.receiveErrorauthorizeOauthRequestToken((org.wso2.carbon.identity.oauth.stub.OAuthServiceAuthenticationException)ex);
											            return;
										            }
										            
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException){
														callback.receiveErrorauthorizeOauthRequestToken((org.wso2.carbon.identity.oauth.stub.OAuthServiceIdentityException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorauthorizeOauthRequestToken(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorauthorizeOauthRequestToken(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorauthorizeOauthRequestToken(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorauthorizeOauthRequestToken(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorauthorizeOauthRequestToken(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorauthorizeOauthRequestToken(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorauthorizeOauthRequestToken(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorauthorizeOauthRequestToken(f);
                                            }
									    } else {
										    callback.receiveErrorauthorizeOauthRequestToken(f);
									    }
									} else {
									    callback.receiveErrorauthorizeOauthRequestToken(f);
									}
								} else {
								    callback.receiveErrorauthorizeOauthRequestToken(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorauthorizeOauthRequestToken(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[5].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[5].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                


       /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
       return returnMap;
    }

    
    
    private javax.xml.namespace.QName[] opNameArray = null;
    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        

        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;   
            }
        }
        return false;
    }
     //https://localhost:9443/services/OAuthService.OAuthServiceHttpsSoap12Endpoint/
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequestResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequestResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppName param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppName.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppNameResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppNameResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceException param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceException.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessToken param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessToken.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessTokenResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessTokenResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityOAuthAdminException param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityOAuthAdminException.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValid param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValid.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValidResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValidResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestToken param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestToken.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestTokenResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestTokenResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestToken param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestToken.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestTokenResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestTokenResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    org.wso2.carbon.identity.oauth.stub.types.Parameters param1,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequest dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequest wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequest();

                                 
                                              wrappedType.setParams(param1);
                                         

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequest.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             

                                
                                private org.wso2.carbon.identity.oauth.stub.types.Parameters getValidateAuthenticationRequestResponse_return(
                                org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequestResponse wrappedType){
                                
                                        return wrappedType.get_return();
                                    
                                }
                             
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    java.lang.String param1,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppName dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppName wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppName();

                                 
                                              wrappedType.setOauthToken(param1);
                                         

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppName.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             

                                
                                private org.wso2.carbon.identity.oauth.stub.types.Parameters getGetScopeAndAppNameResponse_return(
                                org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppNameResponse wrappedType){
                                
                                        return wrappedType.get_return();
                                    
                                }
                             
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    org.wso2.carbon.identity.oauth.stub.types.Parameters param1,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessToken dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessToken wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessToken();

                                 
                                              wrappedType.setParams(param1);
                                         

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessToken.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             

                                
                                private org.wso2.carbon.identity.oauth.stub.types.Parameters getGetAccessTokenResponse_return(
                                org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessTokenResponse wrappedType){
                                
                                        return wrappedType.get_return();
                                    
                                }
                             
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerDTO param1,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValid dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValid wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValid();

                                 
                                              wrappedType.setOauthConsumer(param1);
                                         

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValid.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             

                                
                                private boolean getIsOAuthConsumerValidResponse_return(
                                org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValidResponse wrappedType){
                                
                                        return wrappedType.get_return();
                                    
                                }
                             
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    org.wso2.carbon.identity.oauth.stub.types.Parameters param1,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestToken dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestToken wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestToken();

                                 
                                              wrappedType.setParams(param1);
                                         

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestToken.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             

                                
                                private org.wso2.carbon.identity.oauth.stub.types.Parameters getGetOauthRequestTokenResponse_return(
                                org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestTokenResponse wrappedType){
                                
                                        return wrappedType.get_return();
                                    
                                }
                             
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    org.wso2.carbon.identity.oauth.stub.types.Parameters param1,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestToken dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestToken wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestToken();

                                 
                                              wrappedType.setParams(param1);
                                         

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestToken.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             

                                
                                private org.wso2.carbon.identity.oauth.stub.types.Parameters getAuthorizeOauthRequestTokenResponse_return(
                                org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestTokenResponse wrappedType){
                                
                                        return wrappedType.get_return();
                                    
                                }
                             


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequest.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequestResponse.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.ValidateAuthenticationRequestResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppName.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppName.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppNameResponse.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.GetScopeAndAppNameResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessToken.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessToken.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessTokenResponse.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.GetAccessTokenResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityOAuthAdminException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityOAuthAdminException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValid.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValid.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValidResponse.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.IsOAuthConsumerValidResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestToken.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestToken.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestTokenResponse.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.GetOauthRequestTokenResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityOAuthAdminException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityOAuthAdminException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestToken.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestToken.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestTokenResponse.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.AuthorizeOauthRequestTokenResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceAuthenticationException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthServiceIdentityException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   