/*
*Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.carbon.identity.oauth2.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.base.IdentityException;
import org.wso2.carbon.identity.core.persistence.JDBCPersistenceManager;
import org.wso2.carbon.identity.core.util.IdentityDatabaseUtil;
import org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.dto.OAuth2AccessTokenRespDTO;
import org.wso2.carbon.identity.oauth2.model.AuthzCodeDO;
import org.wso2.carbon.identity.oauth2.model.AccessTokenDO;
import org.wso2.carbon.identity.oauth2.model.RefreshTokenValidationDataDO;
import org.wso2.carbon.identity.oauth2.util.OAuth2Util;

import java.sql.*;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Data Access Layer functionality for Token management in OAuth 2.0 implementation. This includes
 * storing and retrieving access tokens, authorization codes and refresh tokens.
 */
public class TokenMgtDAO {

    private static final Log log = LogFactory.getLog(TokenMgtDAO.class);

    public void storeAuthorizationCode(String authzCode, String consumerKey,
                                       AuthzCodeDO authzCodeDO) throws IdentityOAuth2Exception {
        Connection connection = null;
        PreparedStatement prepStmt = null;
        try {
            connection = JDBCPersistenceManager.getInstance().getDBConnection();
            prepStmt = connection.prepareStatement(SQLQueries.STORE_AUTHORIZATION_CODE);
            prepStmt.setString(1, authzCode);
            prepStmt.setString(2, consumerKey);
            prepStmt.setString(3, OAuth2Util.buildScopeString(authzCodeDO.getScope()));
            prepStmt.setString(4, authzCodeDO.getAuthorizedUser());
            prepStmt.setTimestamp(5, authzCodeDO.getIssuedTime(),
                    Calendar.getInstance(TimeZone.getTimeZone("UTC")));
            prepStmt.setLong(6, authzCodeDO.getValidityPeriod());
            prepStmt.execute();
            connection.commit();
        } catch (IdentityException e) {
            String errorMsg = "Error when getting an Identity Persistence Store instance.";
            log.error(errorMsg, e);
            throw new IdentityOAuth2Exception(errorMsg, e);
        } catch (SQLException e) {
            log.error("Error when executing the SQL : " + SQLQueries.STORE_AUTHORIZATION_CODE);
            log.error(e.getMessage(), e);
            throw new IdentityOAuth2Exception("Error when storing the access code for consumer key : " + consumerKey);
        } finally {
            IdentityDatabaseUtil.closeAllConnections(connection, null, prepStmt);
        }
    }

    public void storeAccessToken(String accessToken, String consumerKey,
                                 AccessTokenDO accessTokenDO, Connection connection) throws SQLException {
        PreparedStatement prepStmt = null;
        try {            
            prepStmt = connection.prepareStatement(SQLQueries.STORE_ACCESS_TOKEN);
            prepStmt.setString(1, accessToken);
            prepStmt.setString(2, accessTokenDO.getRefreshToken());
            prepStmt.setString(3, consumerKey);
            prepStmt.setString(4, accessTokenDO.getAuthzUser());
            prepStmt.setTimestamp(5, accessTokenDO.getIssuedTime(), Calendar.getInstance(TimeZone.getTimeZone("UTC")));
            prepStmt.setLong(6, accessTokenDO.getValidityPeriod() * 1000);
            prepStmt.setString(7, OAuth2Util.buildScopeString(accessTokenDO.getScope()));
            prepStmt.setString(8, accessTokenDO.getTokenState());
            prepStmt.setString(9, OAuth2Util.USER_TYPE_FOR_USER_TOKEN);
            prepStmt.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            IdentityDatabaseUtil.closeAllConnections(null, null, prepStmt);
        }
    }

    public boolean storeAccessToken(String accessToken, String consumerKey,
                                    AccessTokenDO accessTokenDO) throws IdentityOAuth2Exception {
        Connection connection = null;
        try {
            connection = JDBCPersistenceManager.getInstance().getDBConnection();
            connection.setAutoCommit(false);
            storeAccessToken(accessToken, consumerKey, accessTokenDO, connection);
            connection.commit();
            return true;
        } catch (IdentityException e) {
            String errorMsg = "Error when getting an Identity Persistence Store instance.";
            log.error(errorMsg, e);
            throw new IdentityOAuth2Exception(errorMsg, e);
        } catch (SQLException e) {
            //Could be due to failiing unique key constraint
            String errorMsg = "Error saving Access Token :" + e.getMessage();
            log.error(errorMsg, e);
            //throw e;
            //log.error(errorMsg, e);
            //throw new IdentityOAuth2Exception(errorMsg, e);
        } finally {
            IdentityDatabaseUtil.closeAllConnections(connection, null, null);
        }
        return false;
    }

    public OAuth2AccessTokenRespDTO getValidAccessTokenIfExist(String consumerKey, String userName)
            throws IdentityOAuth2Exception {
        Connection connection = null;
        try {
            connection = JDBCPersistenceManager.getInstance().getDBConnection();
            return getValidAccessTokenIfExist(consumerKey, userName, connection);
        } catch (IdentityException e) {
            String errorMsg = "Error when getting an Identity Persistence Store instance.";
            log.error(errorMsg, e);
            throw new IdentityOAuth2Exception(errorMsg, e);
        } finally {
            IdentityDatabaseUtil.closeAllConnections(connection, null, null);
        }
    }

    public OAuth2AccessTokenRespDTO getValidAccessTokenIfExist(String consumerKey, String userName, Connection connection)
            throws IdentityOAuth2Exception {
        PreparedStatement prepStmt = null;
        ResultSet resultSet = null;
        long timestampSkew;
        long currentTime;
        long validityPeriod;
        long issuedTime;
        String accessToken;
        String refreshToken;
        String sql;
        try {
            String oracleSQL =
                    "SELECT * FROM( " +
                            " SELECT ACCESS_TOKEN, REFRESH_TOKEN, TIME_CREATED, VALIDITY_PERIOD, TOKEN_STATE " +
                            " FROM IDN_OAUTH2_ACCESS_TOKEN WHERE CONSUMER_KEY = ? " +
                            " AND AUTHZ_USER = ? " +
                            " AND TOKEN_STATE='ACTIVE' AND USER_TYPE= ? " +
                            " ORDER BY TIME_CREATED DESC) " +
                            " WHERE ROWNUM < 2 ";
            //We set USER_TYPE as user as login request use to generate only user tokens not application tokens
            String mySQLSQL = "SELECT ACCESS_TOKEN, REFRESH_TOKEN, TIME_CREATED, " +
                    " VALIDITY_PERIOD, TOKEN_STATE FROM IDN_OAUTH2_ACCESS_TOKEN " +
                    " WHERE CONSUMER_KEY = ? AND AUTHZ_USER = ? AND TOKEN_STATE='ACTIVE' AND USER_TYPE= ? ORDER BY TIME_CREATED DESC " +
                    " LIMIT 1";

            if (connection.getMetaData().getDriverName().contains("MySQL")
                    || connection.getMetaData().getDriverName().contains("H2")) {
                sql = mySQLSQL;
            } else {
                sql = oracleSQL;
            }
            prepStmt = connection.prepareStatement(sql);
            prepStmt.setString(1, consumerKey);
            prepStmt.setString(2, userName);
            prepStmt.setString(3, OAuth2Util.USER_TYPE_FOR_USER_TOKEN);
            resultSet = prepStmt.executeQuery();

            //Read the latest ACCESS_TOKEN record for CONSUMER_KEY+AUTHZ_USER combination
            if (resultSet.next()) {
                timestampSkew = OAuthServerConfiguration.getInstance().
                        getDefaultTimeStampSkewInSeconds() * 1000;
                currentTime = System.currentTimeMillis();
                issuedTime = resultSet.getTimestamp("TIME_CREATED",
                        Calendar.getInstance(TimeZone.getTimeZone("UTC"))).getTime();
                validityPeriod = resultSet.getLong("VALIDITY_PERIOD");
                //TODO revise the logic
                if (((issuedTime + validityPeriod) - (currentTime + timestampSkew)) > 1000) {
                    accessToken = resultSet.getString("ACCESS_TOKEN");
                    refreshToken = resultSet.getString("REFRESH_TOKEN");
                    OAuth2AccessTokenRespDTO tokenRespDTO = new OAuth2AccessTokenRespDTO();
                    tokenRespDTO.setAccessToken(accessToken);
                    tokenRespDTO.setRefreshToken(refreshToken);
                    tokenRespDTO.setExpiresIn(((issuedTime + validityPeriod) - (currentTime + timestampSkew)) / 1000);
                    return tokenRespDTO;
                }
            }
            return null;

        } catch (SQLException e) {
            log.error("Error when executing the SQL : " + SQLQueries.VALIDATE_AUTHZ_CODE);
            log.error(e.getMessage(), e);
            throw new IdentityOAuth2Exception("Error when validating an authorization code", e);
        } finally {
            IdentityDatabaseUtil.closeAllConnections(null, resultSet, prepStmt);
        }
    }

    public AuthzCodeDO validateAuthorizationCode(String consumerKey, String authorizationKey) throws IdentityOAuth2Exception {
        Connection connection = null;
        PreparedStatement prepStmt = null;
        ResultSet resultSet;

        try {
            connection = JDBCPersistenceManager.getInstance().getDBConnection();
            prepStmt = connection.prepareStatement(SQLQueries.VALIDATE_AUTHZ_CODE);
            prepStmt.setString(1, consumerKey);
            prepStmt.setString(2, authorizationKey);
            resultSet = prepStmt.executeQuery();

            if (resultSet.next()) {
                String authorizedUser = resultSet.getString(1);
                String scopeString = resultSet.getString(2);
                Timestamp issuedTime = resultSet.getTimestamp(3,
                        Calendar.getInstance(TimeZone.getTimeZone("UTC")));
                long validityPeriod = resultSet.getLong(4);

                return new AuthzCodeDO(authorizedUser,
                        OAuth2Util.buildScopeArray(scopeString),
                        issuedTime, validityPeriod);
            }

        } catch (IdentityException e) {
            String errorMsg = "Error when getting an Identity Persistence Store instance.";
            log.error(errorMsg, e);
            throw new IdentityOAuth2Exception(errorMsg, e);
        } catch (SQLException e) {
            log.error("Error when executing the SQL : " + SQLQueries.VALIDATE_AUTHZ_CODE);
            log.error(e.getMessage(), e);
            throw new IdentityOAuth2Exception("Error when validating an authorization code", e);
        } finally {
            IdentityDatabaseUtil.closeAllConnections(connection, null, prepStmt);
        }

        return null;
    }

    public void cleanUpAuthzCode(String authzCode) throws IdentityOAuth2Exception {
        Connection connection = null;
        PreparedStatement prepStmt = null;

        try {
            connection = JDBCPersistenceManager.getInstance().getDBConnection();
            prepStmt = connection.prepareStatement(SQLQueries.REMOVE_AUTHZ_CODE);
            prepStmt.setString(1, authzCode);

            prepStmt.execute();
            connection.commit();

        } catch (IdentityException e) {
            String errorMsg = "Error when getting an Identity Persistence Store instance.";
            log.error(errorMsg, e);
            throw new IdentityOAuth2Exception(errorMsg, e);
        } catch (SQLException e) {
            log.error("Error when executing the SQL : " + SQLQueries.REMOVE_AUTHZ_CODE);
            log.error(e.getMessage(), e);
            throw new IdentityOAuth2Exception("Error when cleaning up an authorization code", e);
        } finally {
            IdentityDatabaseUtil.closeAllConnections(connection, null, prepStmt);
        }
    }

    public RefreshTokenValidationDataDO validateRefreshToken(String consumerKey,
                                                             String refreshToken)
            throws IdentityOAuth2Exception {
        RefreshTokenValidationDataDO validationDataDO = new RefreshTokenValidationDataDO();
        Connection connection = null;
        PreparedStatement prepStmt = null;
        ResultSet resultSet;

        try {
            connection = JDBCPersistenceManager.getInstance().getDBConnection();
            prepStmt = connection.prepareStatement(SQLQueries.VALIDATE_REFRESH_TOKEN);
            prepStmt.setString(1, consumerKey);
            prepStmt.setString(2, refreshToken);
            resultSet = prepStmt.executeQuery();

            if (resultSet.next()) {
                validationDataDO.setAccessToken(resultSet.getString(1));
                validationDataDO.setAuthorizedUser(resultSet.getString(2));
                validationDataDO.setScope(OAuth2Util.buildScopeArray(resultSet.getString(2)));
            }

        } catch (IdentityException e) {
            String errorMsg = "Error when getting an Identity Persistence Store instance.";
            log.error(errorMsg, e);
            throw new IdentityOAuth2Exception(errorMsg, e);
        } catch (SQLException e) {
            log.error("Error when executing the SQL : " + SQLQueries.VALIDATE_REFRESH_TOKEN);
            log.error(e.getMessage(), e);
            throw new IdentityOAuth2Exception("Error when validating a refresh token", e);
        } finally {
            IdentityDatabaseUtil.closeAllConnections(connection, null, prepStmt);
        }

        return validationDataDO;
    }

    public void cleanUpAccessToken(String accessToken) throws IdentityOAuth2Exception {
        Connection connection = null;
        PreparedStatement prepStmt = null;

        try {
            connection = JDBCPersistenceManager.getInstance().getDBConnection();
            prepStmt = connection.prepareStatement(SQLQueries.REMOVE_ACCESS_TOKEN);
            prepStmt.setString(1, accessToken);

            prepStmt.execute();
            connection.commit();

        } catch (IdentityException e) {
            String errorMsg = "Error when getting an Identity Persistence Store instance.";
            log.error(errorMsg, e);
            throw new IdentityOAuth2Exception(errorMsg, e);
        } catch (SQLException e) {
            log.error("Error when executing the SQL : " + SQLQueries.REMOVE_ACCESS_TOKEN);
            log.error(e.getMessage(), e);
            throw new IdentityOAuth2Exception("Error when cleaning up an access token", e);
        } finally {
            IdentityDatabaseUtil.closeAllConnections(connection, null, prepStmt);
        }
    }

    public AccessTokenDO validateBearerToken(String accessToken)
            throws IdentityOAuth2Exception {
        AccessTokenDO dataDO = null;
        Connection connection = null;
        PreparedStatement prepStmt = null;
        ResultSet resultSet;

        try {
            connection = JDBCPersistenceManager.getInstance().getDBConnection();
            prepStmt = connection.prepareStatement(SQLQueries.VALIDATE_BEARER_TOKEN);
            prepStmt.setString(1, accessToken);
            resultSet = prepStmt.executeQuery();

            if (resultSet.next()) {
                String authorizedUser = resultSet.getString(1);
                String[] scope = OAuth2Util.buildScopeArray(resultSet.getString(2));
                Timestamp timestamp = resultSet.getTimestamp(3,
                        Calendar.getInstance(TimeZone.getTimeZone("UTC")));
                long validityPeriod = resultSet.getLong(4);
                dataDO = new AccessTokenDO(authorizedUser, scope, timestamp, validityPeriod);
            }

        } catch (IdentityException e) {
            String errorMsg = "Error when getting an Identity Persistence Store instance.";
            log.error(errorMsg, e);
            throw new IdentityOAuth2Exception(errorMsg, e);
        } catch (SQLException e) {
            log.error("Error when executing the SQL : " + SQLQueries.VALIDATE_BEARER_TOKEN);
            log.error(e.getMessage(), e);
            throw new IdentityOAuth2Exception("Error when validating a bearer token", e);
        } finally {
            IdentityDatabaseUtil.closeAllConnections(connection, null, prepStmt);
        }

        return dataDO;
    }


    /**
     * Sets state of access token
     *
     * @param consumerKey
     * @param authorizedUser
     * @param tokenState
     * @throws IdentityOAuth2Exception
     */
    public void setAccessTokenState(String consumerKey, String authorizedUser, String tokenState, String tokenStateId, String userType)
            throws IdentityOAuth2Exception {
        Connection connection = null;
        PreparedStatement prepStmt = null;
        try {
            connection = JDBCPersistenceManager.getInstance().getDBConnection();
            connection.setAutoCommit(false);
            prepStmt = connection.prepareStatement(SQLQueries.UPDATE_TOKE_STATE);
            prepStmt.setString(1, tokenState);
            prepStmt.setString(2, tokenStateId);
            prepStmt.setString(3, consumerKey);
            prepStmt.setString(4, authorizedUser);
            prepStmt.setString(5, userType);
            prepStmt.executeUpdate();
            connection.commit();
        } catch (IdentityException e) {
            String errorMsg = "Error when getting an Identity Persistence Store instance.";
            log.error(errorMsg, e);
            throw new IdentityOAuth2Exception(errorMsg, e);
        } catch (SQLException e) {
            log.error("Error when executing the SQL : " + SQLQueries.UPDATE_TOKE_STATE);
            log.error(e.getMessage(), e);
            throw new IdentityOAuth2Exception("Error while updating token state", e);
        } finally {
            IdentityDatabaseUtil.closeAllConnections(connection, null, prepStmt);
        }
    }

}
