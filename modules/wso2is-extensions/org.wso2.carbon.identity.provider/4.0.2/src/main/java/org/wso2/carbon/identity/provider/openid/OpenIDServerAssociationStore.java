/*
 * Copyright 2005-2008 WSO2, Inc. (http://wso2.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.provider.openid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.association.Association;
import org.openid4java.association.AssociationException;
import org.openid4java.server.InMemoryServerAssociationStore;
import org.wso2.carbon.identity.provider.openid.dao.OpenIDAssociationDAO;

/**
 * This is the custom AssociationStore. Uses super's methods to generate
 * associations.
 * However this class persist the associations in the identity database. In the
 * case of loading an association it will first look in the super and if fails,
 * it will look in the database. The database may be shared in a clustered
 * environment.
 * 
 * @author WSO2 Inc.
 * 
 */

public class OpenIDServerAssociationStore extends InMemoryServerAssociationStore {

	private OpenIDAssociationDAO dao;
	
	private static Log log = LogFactory.getLog(OpenIDServerAssociationStore.class);

	/**
	 * Here we instantiate a DAO to access the identity database.
	 * 
	 * @param dbConnection
	 * @param privateAssociations
	 *            if this associationstore stores private associations
	 */
	public OpenIDServerAssociationStore(String associationsType) {
		dao = new OpenIDAssociationDAO(associationsType);
	}

	/**
	 * Super will generate the association and it will be persisted by the DAO.
	 * 
	 * @param type
	 *            association type defined in the OpenID 2.0
	 * @param expiryIn
	 *            date
	 */
	public synchronized Association generate(String type, int expiryIn) throws AssociationException {

		Association association = super.generate(type, expiryIn);

		log.debug("Stroing association " + association.getHandle() + " in the database.");
		dao.storeAssociation(association);

		return association;
	}

	/**
	 * First try to load from the memory, in case of failure look in the db.
	 */
	public synchronized Association load(String handle) {

		Association association = super.load(handle);

		if (association == null) {
			log.debug("Association "+ handle + " not found in cache. Loading from the database.");
			association = dao.loadAssociation(handle);
		}

		return association;
	}

	/**
	 * Removes the association from the memory and db.
	 */
	public synchronized void remove(String handle) {

		super.remove(handle);
		
		log.debug("Removing the association" + handle +" from the database");
		dao.removeAssociation(handle);
	}
}
