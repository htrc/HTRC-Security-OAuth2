
/**
 * OAuthAdminServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v5  Built on : Aug 07, 2012 (01:20:15 PDT)
 */
        package org.wso2.carbon.identity.oauth.stub;

        

        /*
        *  OAuthAdminServiceStub java implementation
        */

        
        public class OAuthAdminServiceStub extends org.apache.axis2.client.Stub
        implements OAuthAdminService{
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
     _service = new org.apache.axis2.description.AxisService("OAuthAdminService" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[6];
        
                    __operation = new org.apache.axis2.description.RobustOutOnlyAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "registerOAuthApplicationData"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "getAllOAuthApplicationData"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[1]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "getOAuthApplicationData"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[2]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "registerOAuthConsumer"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[3]=__operation;
            
        
                    __operation = new org.apache.axis2.description.RobustOutOnlyAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "removeOAuthApplicationData"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[4]=__operation;
            
        
                    __operation = new org.apache.axis2.description.RobustOutOnlyAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org", "updateConsumerApplication"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[5]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "registerOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "registerOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "registerOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "getAllOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "getAllOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "getAllOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "getOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "getOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "getOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "registerOAuthConsumer"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "registerOAuthConsumer"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "registerOAuthConsumer"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "removeOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "removeOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "removeOAuthApplicationData"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "updateConsumerApplication"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "updateConsumerApplication"),"org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://org.apache.axis2/xsd","OAuthAdminServiceException"), "updateConsumerApplication"),"org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException");
           


    }

    /**
      *Constructor that takes in a configContext
      */

    public OAuthAdminServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public OAuthAdminServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
    public OAuthAdminServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"https://localhost:9443/services/OAuthAdminService.OAuthAdminServiceHttpsSoap12Endpoint/" );
                
    }

    /**
     * Default Constructor
     */
    public OAuthAdminServiceStub() throws org.apache.axis2.AxisFault {
        
                    this("https://localhost:9443/services/OAuthAdminService.OAuthAdminServiceHttpsSoap12Endpoint/" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public OAuthAdminServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



         
                
                /**
                  * Auto generated method signature
                  * 
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
                  */
                public void  registerOAuthApplicationData(
                 org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO application20

                ) throws java.rmi.RemoteException
                
                
                        ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException
                {
                org.apache.axis2.context.MessageContext _messageContext = null;

                try {
                org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
                _operationClient.getOptions().setAction("urn:registerOAuthApplicationData");
                _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

                
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              
                org.apache.axiom.soap.SOAPEnvelope env = null;
                 _messageContext = new org.apache.axis2.context.MessageContext();

                
                                                    //Style is Doc.
                                                    org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthApplicationData dummyWrappedType = null;
                                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                                    application20,
                                                                    dummyWrappedType,
                                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                                    "registerOAuthApplicationData")));
                                                                

              //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
                // create message context with that soap envelope

            _messageContext.setEnvelope(env);

            // add the message contxt to the operation client
            _operationClient.addMessageContext(_messageContext);

             _operationClient.execute(true);

           
               }catch(org.apache.axis2.AxisFault f){
                  org.apache.axiom.om.OMElement faultElt = f.getDetail();
                  if (faultElt!=null){
                      if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"registerOAuthApplicationData"))){
                          //make the fault by reflection
                          try{
                              java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"registerOAuthApplicationData"));
                              java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                              java.lang.Exception ex=
                                      (java.lang.Exception) exceptionClass.newInstance();
                              //message class
                              java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"registerOAuthApplicationData"));
                              java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                              java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                              java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                         new java.lang.Class[]{messageClass});
                              m.invoke(ex,new java.lang.Object[]{messageObject});
                              
                              if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException){
                                throw (org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException)ex;
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
           
             return;
           }
            
                    /**
                     * Auto generated method signature
                     * 
                     * @see org.wso2.carbon.identity.oauth.stub.OAuthAdminService#getAllOAuthApplicationData
                     * @param getAllOAuthApplicationData21
                    
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
                     */

                    

                            public  org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO[] getAllOAuthApplicationData(

                            )
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
              _operationClient.getOptions().setAction("urn:getAllOAuthApplicationData");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationData dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "getAllOAuthApplicationData")));
                                                
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
                                             org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationDataResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return getGetAllOAuthApplicationDataResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationDataResponse)object);
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAllOAuthApplicationData"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAllOAuthApplicationData"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAllOAuthApplicationData"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException)ex;
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
                * @see org.wso2.carbon.identity.oauth.stub.OAuthAdminService#startgetAllOAuthApplicationData
                    * @param getAllOAuthApplicationData21
                
                */
                public  void startgetAllOAuthApplicationData(

                 

                  final org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
             _operationClient.getOptions().setAction("urn:getAllOAuthApplicationData");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationData dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                     dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "getAllOAuthApplicationData")));
                                                
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
                                                                         org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationDataResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetAllOAuthApplicationData(
                                            getGetAllOAuthApplicationDataResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationDataResponse)object));
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetAllOAuthApplicationData(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAllOAuthApplicationData"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAllOAuthApplicationData"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getAllOAuthApplicationData"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException){
														callback.receiveErrorgetAllOAuthApplicationData((org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetAllOAuthApplicationData(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAllOAuthApplicationData(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAllOAuthApplicationData(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAllOAuthApplicationData(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAllOAuthApplicationData(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAllOAuthApplicationData(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAllOAuthApplicationData(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetAllOAuthApplicationData(f);
                                            }
									    } else {
										    callback.receiveErrorgetAllOAuthApplicationData(f);
									    }
									} else {
									    callback.receiveErrorgetAllOAuthApplicationData(f);
									}
								} else {
								    callback.receiveErrorgetAllOAuthApplicationData(error);
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
                                    callback.receiveErrorgetAllOAuthApplicationData(axisFault);
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
                     * @see org.wso2.carbon.identity.oauth.stub.OAuthAdminService#getOAuthApplicationData
                     * @param getOAuthApplicationData24
                    
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
                     */

                    

                            public  org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO getOAuthApplicationData(

                            java.lang.String consumerKey25)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
              _operationClient.getOptions().setAction("urn:getOAuthApplicationData");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationData dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    consumerKey25,
                                                    dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "getOAuthApplicationData")));
                                                
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
                                             org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationDataResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return getGetOAuthApplicationDataResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationDataResponse)object);
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOAuthApplicationData"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOAuthApplicationData"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOAuthApplicationData"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException)ex;
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
                * @see org.wso2.carbon.identity.oauth.stub.OAuthAdminService#startgetOAuthApplicationData
                    * @param getOAuthApplicationData24
                
                */
                public  void startgetOAuthApplicationData(

                 java.lang.String consumerKey25,

                  final org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
             _operationClient.getOptions().setAction("urn:getOAuthApplicationData");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationData dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    consumerKey25,
                                                     dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "getOAuthApplicationData")));
                                                
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
                                                                         org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationDataResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetOAuthApplicationData(
                                            getGetOAuthApplicationDataResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationDataResponse)object));
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetOAuthApplicationData(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOAuthApplicationData"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOAuthApplicationData"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getOAuthApplicationData"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException){
														callback.receiveErrorgetOAuthApplicationData((org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorgetOAuthApplicationData(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOAuthApplicationData(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOAuthApplicationData(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOAuthApplicationData(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOAuthApplicationData(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOAuthApplicationData(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOAuthApplicationData(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetOAuthApplicationData(f);
                                            }
									    } else {
										    callback.receiveErrorgetOAuthApplicationData(f);
									    }
									} else {
									    callback.receiveErrorgetOAuthApplicationData(f);
									}
								} else {
								    callback.receiveErrorgetOAuthApplicationData(error);
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
                                    callback.receiveErrorgetOAuthApplicationData(axisFault);
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
                     * @see org.wso2.carbon.identity.oauth.stub.OAuthAdminService#registerOAuthConsumer
                     * @param registerOAuthConsumer28
                    
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
                     */

                    

                            public  java.lang.String[] registerOAuthConsumer(

                            )
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
              _operationClient.getOptions().setAction("urn:registerOAuthConsumer");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumer dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "registerOAuthConsumer")));
                                                
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
                                             org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumerResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return getRegisterOAuthConsumerResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumerResponse)object);
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"registerOAuthConsumer"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"registerOAuthConsumer"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"registerOAuthConsumer"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException){
                          throw (org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException)ex;
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
                * @see org.wso2.carbon.identity.oauth.stub.OAuthAdminService#startregisterOAuthConsumer
                    * @param registerOAuthConsumer28
                
                */
                public  void startregisterOAuthConsumer(

                 

                  final org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
             _operationClient.getOptions().setAction("urn:registerOAuthConsumer");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumer dummyWrappedType = null;
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                     dummyWrappedType,
                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                    "registerOAuthConsumer")));
                                                
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
                                                                         org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumerResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultregisterOAuthConsumer(
                                            getRegisterOAuthConsumerResponse_return((org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumerResponse)object));
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorregisterOAuthConsumer(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"registerOAuthConsumer"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"registerOAuthConsumer"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"registerOAuthConsumer"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException){
														callback.receiveErrorregisterOAuthConsumer((org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException)ex);
											            return;
										            }
										            
					
										            callback.receiveErrorregisterOAuthConsumer(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorregisterOAuthConsumer(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorregisterOAuthConsumer(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorregisterOAuthConsumer(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorregisterOAuthConsumer(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorregisterOAuthConsumer(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorregisterOAuthConsumer(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorregisterOAuthConsumer(f);
                                            }
									    } else {
										    callback.receiveErrorregisterOAuthConsumer(f);
									    }
									} else {
									    callback.receiveErrorregisterOAuthConsumer(f);
									}
								} else {
								    callback.receiveErrorregisterOAuthConsumer(error);
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
                                    callback.receiveErrorregisterOAuthConsumer(axisFault);
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
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
                  */
                public void  removeOAuthApplicationData(
                 java.lang.String consumerKey32

                ) throws java.rmi.RemoteException
                
                
                        ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException
                {
                org.apache.axis2.context.MessageContext _messageContext = null;

                try {
                org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
                _operationClient.getOptions().setAction("urn:removeOAuthApplicationData");
                _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

                
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              
                org.apache.axiom.soap.SOAPEnvelope env = null;
                 _messageContext = new org.apache.axis2.context.MessageContext();

                
                                                    //Style is Doc.
                                                    org.wso2.carbon.identity.oauth.stub.types.axis2.RemoveOAuthApplicationData dummyWrappedType = null;
                                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                                    consumerKey32,
                                                                    dummyWrappedType,
                                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                                    "removeOAuthApplicationData")));
                                                                

              //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
                // create message context with that soap envelope

            _messageContext.setEnvelope(env);

            // add the message contxt to the operation client
            _operationClient.addMessageContext(_messageContext);

             _operationClient.execute(true);

           
               }catch(org.apache.axis2.AxisFault f){
                  org.apache.axiom.om.OMElement faultElt = f.getDetail();
                  if (faultElt!=null){
                      if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeOAuthApplicationData"))){
                          //make the fault by reflection
                          try{
                              java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeOAuthApplicationData"));
                              java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                              java.lang.Exception ex=
                                      (java.lang.Exception) exceptionClass.newInstance();
                              //message class
                              java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"removeOAuthApplicationData"));
                              java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                              java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                              java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                         new java.lang.Class[]{messageClass});
                              m.invoke(ex,new java.lang.Object[]{messageObject});
                              
                              if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException){
                                throw (org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException)ex;
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
           
             return;
           }
             
                
                /**
                  * Auto generated method signature
                  * 
                     * @throws org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException : 
                  */
                public void  updateConsumerApplication(
                 org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO consumerAppDTO34

                ) throws java.rmi.RemoteException
                
                
                        ,org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException
                {
                org.apache.axis2.context.MessageContext _messageContext = null;

                try {
                org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
                _operationClient.getOptions().setAction("urn:updateConsumerApplication");
                _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

                
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              
                org.apache.axiom.soap.SOAPEnvelope env = null;
                 _messageContext = new org.apache.axis2.context.MessageContext();

                
                                                    //Style is Doc.
                                                    org.wso2.carbon.identity.oauth.stub.types.axis2.UpdateConsumerApplication dummyWrappedType = null;
                                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                                    consumerAppDTO34,
                                                                    dummyWrappedType,
                                                                    optimizeContent(new javax.xml.namespace.QName("http://oauth.identity.carbon.wso2.org",
                                                                    "updateConsumerApplication")));
                                                                

              //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
                // create message context with that soap envelope

            _messageContext.setEnvelope(env);

            // add the message contxt to the operation client
            _operationClient.addMessageContext(_messageContext);

             _operationClient.execute(true);

           
               }catch(org.apache.axis2.AxisFault f){
                  org.apache.axiom.om.OMElement faultElt = f.getDetail();
                  if (faultElt!=null){
                      if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateConsumerApplication"))){
                          //make the fault by reflection
                          try{
                              java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateConsumerApplication"));
                              java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                              java.lang.Exception ex=
                                      (java.lang.Exception) exceptionClass.newInstance();
                              //message class
                              java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateConsumerApplication"));
                              java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                              java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                              java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                         new java.lang.Class[]{messageClass});
                              m.invoke(ex,new java.lang.Object[]{messageObject});
                              
                              if (ex instanceof org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException){
                                throw (org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException)ex;
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
           
             return;
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
     //https://localhost:9443/services/OAuthAdminService.OAuthAdminServiceHttpsSoap12Endpoint/
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthApplicationData param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthApplicationData.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationData param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationData.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationDataResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationDataResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationData param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationData.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationDataResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationDataResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumer param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumer.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumerResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumerResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.RemoveOAuthApplicationData param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.RemoveOAuthApplicationData.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(org.wso2.carbon.identity.oauth.stub.types.axis2.UpdateConsumerApplication param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.UpdateConsumerApplication.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO param1,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthApplicationData dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthApplicationData wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthApplicationData();

                                 
                                              wrappedType.setApplication(param1);
                                         

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthApplicationData.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationData dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationData wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationData();

                                 

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationData.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             

                                
                                private org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO[] getGetAllOAuthApplicationDataResponse_return(
                                org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationDataResponse wrappedType){
                                
                                        return wrappedType.get_return();
                                    
                                }
                             
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    java.lang.String param1,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationData dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationData wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationData();

                                 
                                              wrappedType.setConsumerKey(param1);
                                         

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationData.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             

                                
                                private org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO getGetOAuthApplicationDataResponse_return(
                                org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationDataResponse wrappedType){
                                
                                        return wrappedType.get_return();
                                    
                                }
                             
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumer dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumer wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumer();

                                 

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumer.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             

                                
                                private java.lang.String[] getRegisterOAuthConsumerResponse_return(
                                org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumerResponse wrappedType){
                                
                                        return wrappedType.get_return();
                                    
                                }
                             
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    java.lang.String param1,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.RemoveOAuthApplicationData dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.RemoveOAuthApplicationData wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.RemoveOAuthApplicationData();

                                 
                                              wrappedType.setConsumerKey(param1);
                                         

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.RemoveOAuthApplicationData.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,
                                    org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO param1,
                                    org.wso2.carbon.identity.oauth.stub.types.axis2.UpdateConsumerApplication dummyWrappedType,
                                 boolean optimizeContent) throws org.apache.axis2.AxisFault{

                                try{
                                org.wso2.carbon.identity.oauth.stub.types.axis2.UpdateConsumerApplication wrappedType = new org.wso2.carbon.identity.oauth.stub.types.axis2.UpdateConsumerApplication();

                                 
                                              wrappedType.setConsumerAppDTO(param1);
                                         

                               org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                  
                                        emptyEnvelope.getBody().addChild(wrappedType.getOMElement(org.wso2.carbon.identity.oauth.stub.types.axis2.UpdateConsumerApplication.MY_QNAME,factory));
                                    

                                return emptyEnvelope;
                               } catch(org.apache.axis2.databinding.ADBException e){
                                    throw org.apache.axis2.AxisFault.makeFault(e);
                               }
                               }



                                
                             
                             /* methods to provide back word compatibility */

                             


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
        
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthApplicationData.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthApplicationData.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationData.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationData.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationDataResponse.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.GetAllOAuthApplicationDataResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationData.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationData.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationDataResponse.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.GetOAuthApplicationDataResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumer.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumer.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumerResponse.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.RegisterOAuthConsumerResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.RemoveOAuthApplicationData.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.RemoveOAuthApplicationData.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.UpdateConsumerApplication.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.UpdateConsumerApplication.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.class.equals(type)){
                
                           return org.wso2.carbon.identity.oauth.stub.types.axis2.OAuthAdminServiceException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   