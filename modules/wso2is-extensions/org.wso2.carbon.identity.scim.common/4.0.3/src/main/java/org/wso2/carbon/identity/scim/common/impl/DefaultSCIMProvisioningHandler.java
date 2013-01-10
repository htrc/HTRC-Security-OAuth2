package org.wso2.carbon.identity.scim.common.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.scim.common.api.ProvisioningHandler;
import org.wso2.carbon.identity.scim.common.config.SCIMProvisioningConfigManager;
import org.wso2.carbon.identity.scim.common.utils.IdentitySCIMException;
import org.wso2.charon.core.config.SCIMConfigConstants;
import org.wso2.charon.core.config.SCIMConsumer;
import org.wso2.charon.core.config.SCIMProvider;
import org.wso2.charon.core.objects.Group;
import org.wso2.charon.core.objects.SCIMObject;
import org.wso2.charon.core.objects.User;
import org.wso2.charon.core.schema.SCIMConstants;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultSCIMProvisioningHandler implements ProvisioningHandler {

    private static Log logger = LogFactory.getLog(DefaultSCIMProvisioningHandler.class.getName());
    protected SCIMProvisioningConfigManager provisioningManager;

    //to make provisioning requests sent to different providers parallel.
    private ExecutorService provisioningThreadPool = Executors.newCachedThreadPool();

    private int objectType;
    //variables used in runnable's run method in a particular instance of the object:
    private String consumerName;
    private SCIMObject objectToBeProvisioned;
    private int provisioningMethod;
    private Map<String, Object> additionalProvisioningInformation;

    public DefaultSCIMProvisioningHandler(String consumerId, User user, int httpMethod,
                                          Map<String, Object> additionalInformation) {
        initConfigManager();
        objectType = SCIMConstants.USER_INT;
        consumerName = consumerId;
        objectToBeProvisioned = user;
        provisioningMethod = httpMethod;
        additionalProvisioningInformation = additionalInformation;
    }

    public DefaultSCIMProvisioningHandler(String consumerId, Group group, int httpMethod,
                                          Map<String, Object> additionalInformation) {
        initConfigManager();
        objectType = SCIMConstants.GROUP_INT;
        consumerName = consumerId;
        objectToBeProvisioned = group;
        provisioningMethod = httpMethod;
        additionalProvisioningInformation = additionalInformation;
    }

    public void initConfigManager() {
        this.provisioningManager = SCIMProvisioningConfigManager.getInstance();
    }

    //public abstract boolean isSCIMConsumerEnabled(String paramString);

    /*public void provisionUser(String consumerName, SCIMObject scimObject, int httpMethodInt)
            throws IdentitySCIMException {
        try {
            //create scim client and encode scim object
            SCIMClient scimClient = new SCIMClient();
            String encodedSCIMObject = scimClient.encodeSCIMObject((AbstractSCIMObject) scimObject,
                                                                   SCIMConstants.JSON);

            //get all the providers to whom the provisioning should be done, for this particular consumer
            SCIMConsumer consumer = provisioningManager.getSCIMConfig().getConsumerProcessed(consumerName);

            //iterate through all the providers and do provisioning
            Map<String, SCIMProvider> scimProviders = consumer.getScimProviders();

            for (Map.Entry<String, SCIMProvider> scimProviderEntry : scimProviders.entrySet()) {
                //get endpoint url according to the scim object to be provisioned
                String endpointUrl = getSCIMEndpointURL(scimObject, scimProviderEntry.getValue());

                if (endpointUrl != null) {
                    //get user name & password for the provider registered for the consumer
                    String userName = scimProviderEntry.getValue().getProperty(
                            SCIMConfigConstants.ELEMENT_NAME_USERNAME);
                    String password = scimProviderEntry.getValue().getProperty(
                            SCIMConfigConstants.ELEMENT_NAME_PASSWORD);

                    //get the content type defined for the relevant provider
                    String contentType = scimProviderEntry.getValue().getProperty(SCIMConstants.CONTENT_TYPE_HEADER);
                    if (contentType == null) {
                        contentType = SCIMConstants.APPLICATION_JSON;
                    }
                    //identify http method & invoke provisioning operation accordingly

                    //create relevant http method
                    switch (httpMethodInt) {
                        case SCIMConstants.DELETE:
                            //delete the object at the scim endpoint
                            String scimObjectEndpointURL = endpointUrl + ((AbstractSCIMObject) scimObject).getId();
                            DeleteMethod deleteMethod = new DeleteMethod(scimObjectEndpointURL);
                            //add basic auth header
                            deleteMethod.addRequestHeader(SCIMConstants.AUTHORIZATION_HEADER,
                                                          BasicAuthUtil.getBase64EncodedBasicAuthHeader(userName, password));

                            //submit provisioning for this provider
                            provisioningThreadPool.submit(new ProvisioningClient(new HttpClient(), deleteMethod, objectType));
                            break;

                        case SCIMConstants.POST:
                            //create the object at the scim endpoint
                            PostMethod postMethod = new PostMethod(endpointUrl);
                            //add basic auth header
                            postMethod.addRequestHeader(SCIMConstants.AUTHORIZATION_HEADER,
                                                        BasicAuthUtil.getBase64EncodedBasicAuthHeader(userName, password));

                            RequestEntity requestEntity = new StringRequestEntity(encodedSCIMObject,
                                                                                  contentType, null);
                            postMethod.setRequestEntity(requestEntity);

                            //submit provisioning for this provider
                            provisioningThreadPool.submit(new ProvisioningClient(new HttpClient(), postMethod, objectType));
                            *//*int responseStatus = client.executeMethod(postMethod);
                            logger.info("SCIM - operation returned with response code: " + responseStatus);

                            String response = postMethod.getResponseBodyAsString();
                            logger.info(response);
                            if (scimClient.evaluateResponseStatus(responseStatus)) {
                                scimClient.decodeSCIMResponse(response, SCIMConstants.JSON,
                                                              SCIMConstants.GROUP_INT);
                            } else {
                                scimClient.decodeSCIMException(response, SCIMConstants.JSON);
                            }*//*
                            break;

                        case SCIMConstants.PUT:
                            String scimObjEndpointURL = endpointUrl + ((AbstractSCIMObject) scimObject).getId();
                            PutMethod putMethod = new PutMethod(scimObjEndpointURL);
                            //add basic auth header
                            putMethod.addRequestHeader(SCIMConstants.AUTHORIZATION_HEADER,
                                                       BasicAuthUtil.getBase64EncodedBasicAuthHeader(userName, password));
                            RequestEntity updateRequestEntity = new StringRequestEntity(encodedSCIMObject,
                                                                                        contentType, null);
                            putMethod.setRequestEntity(updateRequestEntity);
                            //submit provisioning for this provider
                            provisioningThreadPool.submit(new ProvisioningClient(new HttpClient(), putMethod, objectType));
                            break;
                        //update the object at the scim endpoint

                    }
                }
            }
        } catch (BadRequestException e) {
            throw new IdentitySCIMException(e.getMessage());
        } catch (HttpException e) {
            throw new IdentitySCIMException(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new IdentitySCIMException(e.getMessage());
        } catch (IOException e) {
            throw new IdentitySCIMException(e.getMessage());
        } catch (CharonException e) {
            throw new IdentitySCIMException(e.getDescription());
        }
    }*/

    public void provision() {

        //get all the providers to whom the provisioning should be done, for this particular consumer
        //SCIMConsumer consumer = provisioningManager.getSCIMConfig().getConsumerProcessed(consumerName);

        SCIMConsumer consumer = null;
        try {
            consumer = provisioningManager.getSCIMConsumerConfig(consumerName);
        } catch (IdentitySCIMException e) {
            //if exception occurred, log the error and return
            logger.error(e.getMessage());
            logger.error(e.getStackTrace());
            return;
        }

        //iterate through all the providers and do provisioning
        Map<String, SCIMProvider> scimProviders = consumer.getScimProviders();

        for (SCIMProvider scimProviderEntry : scimProviders.values()) {
            if (SCIMConstants.USER_INT == objectType) {
                provisioningThreadPool.submit(new ProvisioningClient(
                        scimProviderEntry, (User) objectToBeProvisioned, provisioningMethod,
                        additionalProvisioningInformation));
            } else if (SCIMConstants.GROUP_INT == objectType) {
                provisioningThreadPool.submit(new ProvisioningClient(
                        scimProviderEntry, (Group) objectToBeProvisioned, provisioningMethod,
                        additionalProvisioningInformation));
            }
        }
    }

    /**
     * Obtian the matching endpoint url for the scim object to be provisioned, from the relevant
     * SCIM Provider.
     *
     * @param scimObject
     * @param scimProvider
     * @return
     * @throws IdentitySCIMException
     */
    /*private String getSCIMEndpointURL(SCIMObject scimObject, SCIMProvider scimProvider)
            throws IdentitySCIMException {
        if (scimObject instanceof User) {
            objectType = SCIMConstants.USER_INT;
            return scimProvider.getProperty(SCIMConfigConstants.ELEMENT_NAME_USER_ENDPOINT);
        } else if (scimObject instanceof Group) {
            objectType = SCIMConstants.GROUP_INT;
            return scimProvider.getProperty(SCIMConfigConstants.ELEMENT_NAME_GROUP_ENDPOINT);
        } else {
            String error = "No matching endpoint found for the given SCIM Object..";
            throw new IdentitySCIMException(error);
        }
    }*/

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        //if (objectType == SCIMConstants.USER_INT) {
        this.provision();
        /*} else if (objectType == SCIMConstants.GROUP_INT) {
            this.provisionGroup(consumerName, (Group) objectToBeProvisioned, provisioningMethod);
        }*/
    }
}