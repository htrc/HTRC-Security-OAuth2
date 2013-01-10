package org.wso2.carbon.identity.entitlement.internal;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.entitlement.EntitlementConstants;
import org.xml.sax.SAXException;

public class SchemaBuilder implements Runnable {

    private static Log log = LogFactory.getLog(SchemaBuilder.class);

    private EntitlementConfigHolder configHolder;

    public SchemaBuilder(EntitlementConfigHolder configHolder) {
        this.configHolder = configHolder;
    }

    @Override
    public void run() {
        try {
            buildPolicySchema();
            log.info("XACML policy schema loaded successfully.");
        } catch (Exception e) {
            configHolder.getEngineProperties().setProperty(EntitlementExtensionBuilder.PDP_SCHEMA_VALIDATION, "false");
            log.warn("Error while loading policy schema. Schema validation will be disabled.");
        }
    }

    /**
     * Builds the policy schema map. There are three schemas.
     * 
     * @param configHolder holder EntitlementConfigHolder
     * @throws SAXException if fails
     */
    public void buildPolicySchema() throws SAXException {

        if (!"true".equalsIgnoreCase((String) configHolder.getEngineProperties().get(
                EntitlementExtensionBuilder.PDP_SCHEMA_VALIDATION))) {
            log.warn("PDP schema validation disabled.");
            return;
        }

        String[] schemaNSs = new String[] { EntitlementConstants.XACML_1_POLICY_XMLNS,
                EntitlementConstants.XACML_2_POLICY_XMLNS,
                EntitlementConstants.XACML_3_POLICY_XMLNS };

        for (String schemaNS : schemaNSs) {

            String schemaFile;

            if (EntitlementConstants.XACML_1_POLICY_XMLNS.equals(schemaNS)) {
                schemaFile = EntitlementConstants.XACML_1_POLICY_SCHEMA_FILE;
            } else if (EntitlementConstants.XACML_2_POLICY_XMLNS.equals(schemaNS)) {
                schemaFile = EntitlementConstants.XACML_2_POLICY_SCHEMA_FILE;
            } else {
                schemaFile = EntitlementConstants.XACML_3_POLICY_SCHEMA_FILE;
            }

            InputStream schemaFileStream = EntitlementExtensionBuilder.class
                    .getResourceAsStream("/" + schemaFile);
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new StreamSource(schemaFileStream));
            configHolder.getPolicySchemaMap().put(schemaNS, schema);
        }
    }

}
