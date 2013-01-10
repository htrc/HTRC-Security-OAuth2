package org.wso2.carbon.identity.entitlement.mediator;

import org.apache.axiom.om.OMElement;
import org.wso2.carbon.caching.core.CacheEntry;

public class EntitlementCacheEntry extends CacheEntry {

    private OMElement[] omElementArray;


    public EntitlementCacheEntry(OMElement[] omElementArray) {
        this.omElementArray = omElementArray;
    }

    public OMElement[] getOmElementArray() {
        return omElementArray;
    }
}
