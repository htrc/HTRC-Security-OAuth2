package org.wso2.carbon.identity.provider.openid.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.base.IdentityConstants;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.core.model.OpenIDRememberMeDO;
import org.wso2.carbon.identity.core.persistence.JDBCPersistenceManager;
import org.wso2.carbon.identity.core.util.IdentityDatabaseUtil;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.identity.provider.openid.OpenIDServerConstants;

public class OpenIDRememberMeDAO {

	protected Log log = LogFactory.getLog(OpenIDRememberMeDAO.class);

	/**
	 * Updates the remember me token
	 * @param rememberMe
	 * @throws IdentityException
	 */
	public void updateToken(OpenIDRememberMeDO rememberMe) throws IdentityException {

		Connection connection = null;
		PreparedStatement prepStmt = null;

		try {
			connection = JDBCPersistenceManager.getInstance().getDBConnection();

			if (isTokenExist(connection, rememberMe)) {
				prepStmt = connection.prepareStatement(OpenIDSQLQueries.UPDATE_REMEMBER_ME_TOKEN);
				prepStmt.setString(2, rememberMe.getUserName());
				prepStmt.setInt(3, IdentityUtil.getTenantIdOFUser(rememberMe.getUserName()));
				prepStmt.setString(1, rememberMe.getToken());
				prepStmt.execute();
				connection.commit();
				log.debug("RememberMe token of " + rememberMe.getUserName() +
				          " successfully updated in the database.");
			} else {
				prepStmt = connection.prepareStatement(OpenIDSQLQueries.STORE_REMEMBER_ME_TOKEN);
				prepStmt.setString(1, rememberMe.getUserName());
				prepStmt.setInt(2, IdentityUtil.getTenantIdOFUser(rememberMe.getUserName()));
				prepStmt.setString(3, rememberMe.getToken());
				prepStmt.execute();
				connection.commit();
				log.debug("RememberMe token of " + rememberMe.getUserName() +
				          " successfully stored in the database.");
			}

		} catch (SQLException e) {
			log.error("RememberMe token of " + rememberMe.getUserName() +
			          " could not stored in the database. Error while accessing the database", e);
		} finally {
			IdentityDatabaseUtil.closeStatement(prepStmt);
			IdentityDatabaseUtil.closeConnection(connection);
		}
	}

	/**
	 * Return the remember me token after validations. Expiry will be checked. 
	 * @param rememberMe
	 * @return
	 * @throws IdentityException
	 */
	public String getToken(OpenIDRememberMeDO rememberMe) throws IdentityException {
		Connection connection = null;
		PreparedStatement prepStmt = null;

		try {
			connection = JDBCPersistenceManager.getInstance().getDBConnection();
			prepStmt = connection.prepareStatement(OpenIDSQLQueries.LOAD_REMEMBER_ME_TOKEN);
			prepStmt.setString(1, rememberMe.getUserName());
			prepStmt.setInt(2, IdentityUtil.getTenantIdOFUser(rememberMe.getUserName()));

			OpenIDRememberMeDO remDO =
			                           buildRememberMeDO(prepStmt.executeQuery(),
			                                             rememberMe.getUserName());

			// TODO : we should return the DO instead the token and check the
			// timestamp
			return remDO.getToken();

		} catch (SQLException e) {
			log.error("Unable to load RememberMe token for " + rememberMe.getUserName() +
			          " Error while accessing the database", e);
		} finally {
			IdentityDatabaseUtil.closeStatement(prepStmt);
			IdentityDatabaseUtil.closeConnection(connection);
		}

		return null;
	}

	/**
	 * Check if the token already exist in the database.
	 * 
	 * @param connection
	 * @param rememberMe
	 * @return
	 * @throws SQLException
	 * @throws IdentityException
	 */
	private boolean isTokenExist(Connection connection, OpenIDRememberMeDO rememberMe)
	                                                                                  throws IdentityException {

		PreparedStatement prepStmt = null;
		ResultSet results = null;
		boolean result = false;

		try {
			prepStmt = connection.prepareStatement(OpenIDSQLQueries.CHECK_REMEMBER_ME_TOKEN_EXIST);
			prepStmt.setString(1, rememberMe.getUserName());
			prepStmt.setInt(2, IdentityUtil.getTenantIdOFUser(rememberMe.getUserName()));
			results = prepStmt.executeQuery();

			if (results.next()) {
				result = true;
			}
		} catch (SQLException e) {
			log.error("Failed to load the RememberME token data for " + rememberMe.getUserName() +
			          ". Error while accessing the database", e);
		} finally {
			IdentityDatabaseUtil.closeResultSet(results);
			IdentityDatabaseUtil.closeStatement(prepStmt);
		}

		return result;
	}

	/**
	 * Builds the OpenIDRememberMeDo
	 * 
	 * @param results
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	private OpenIDRememberMeDO buildRememberMeDO(ResultSet results, String username) {

		OpenIDRememberMeDO remDO = new OpenIDRememberMeDO();

		try {

			if (!results.next()) {
				log.debug("RememberMe token not found for the user " + username);
				return remDO;
			}

			// check for expiry of the remember me token
			Timestamp timestamp = results.getTimestamp(4);
			String expiry = IdentityUtil.getProperty(IdentityConstants.OPENID_REMEMBER_ME_EXPIRY);

			if (timestamp != null && expiry != null) {
				long t0 = timestamp.getTime();
				long t1 = new Date().getTime();
				long delta = Long.parseLong(expiry) * 1000 * 60; 
				
				if (t1 - t0 > delta) {
					log.debug("Remember Me token expired for user " + username);
					return remDO;
				}
			}

			remDO.setUserName(results.getString(1));
			remDO.setToken(results.getString(3));
			remDO.setTimestamp(timestamp);

		} catch (SQLException e) {
			log.error("Failed to create RememberMeDO for the user " + username +
			          ". Error while accessing the database", e);
		} finally {
			IdentityDatabaseUtil.closeResultSet(results);
		}

		return remDO;
	}

}
