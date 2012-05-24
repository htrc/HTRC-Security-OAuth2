/*
#
# Copyright 2012 The Trustees of Indiana University
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or areed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# -----------------------------------------------------------------
#
# Project: OAuth2
# File:  TokenStore.java
# Description:  
#
# -----------------------------------------------------------------
# 
*/


package edu.indiana.d2i.htrc.oauth2.common;

/**
 * @author Milinda Pathirage
 * @author Yiming Sun
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.amber.oauth2.as.issuer.MD5Generator;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.log4j.Logger;

public class TokenStore {
    
    private static Logger log = Logger.getLogger(TokenStore.class);
    
    private Database db;

    public TokenStore(Database db) {
        this.db = db;
    }

    /**
     * Generate random token, store it in the token store and return it.
     *
     * @return token
     */
    public String newToken(String user, int tokenTTLSeconds) throws OAuthSystemException {
        Connection con = null;
        PreparedStatement prepStmt = null;
        String token = generateToken();

        try {
            con = db.getConnection();
            String insertStatement = "INSERT INTO tokens(token, user, created_at, expired_at) VALUES (?,?,?,?)";
            prepStmt = con.prepareStatement(insertStatement);

            prepStmt.setString(1, token);
            prepStmt.setString(2, user);
            prepStmt.setTimestamp(3, getCreatedAt());
            prepStmt.setTimestamp(4, getExpiresAt(tokenTTLSeconds));

            prepStmt.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException: ", e);
//            // TODO: Log
//            e.printStackTrace();
        } finally {
            if (prepStmt != null) {
                try {
                    prepStmt.close();
                } catch (SQLException e) {

                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {

                }
            }
        }

        return token;
    }

    /**
     * Validate given token against the token store.
     * - Check for existence
     * - Check the expired tokens
     *
     * @param token access token from the client
     * @return true of valid, false otherwise.
     */
    public boolean isValid(String token) {
        boolean valid = false;
        Timestamp timestamp = null;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        
        try{
            con = db.getConnection();

            String validateToken = "SELECT * FROM tokens WHERE token=?";

            prepStmt = con.prepareStatement(validateToken);
            prepStmt.setString(1, token);

            rs = prepStmt.executeQuery();
            
            if (rs.next()) {
                timestamp = rs.getTimestamp("expired_at");
                if (log.isDebugEnabled()) {
                    log.debug("token expire timestamp: " + token + " " + timestamp.getTime() + " now: " + System.currentTimeMillis());
                }
                // check for duplicate tokens. which should not occur
                if (rs.next()) {
                    log.error("Duplicate tokens found: " + token);
                    valid = false;
                } else {
                    valid = isExpired(timestamp);
                    if (!valid) {
                        log.error("Expired token: " + token);
                    }
                }
            } else {
                valid = false;
            }
            
        } catch (SQLException e) {
            log.error("SQLException: ", e);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.warn("Unable to close ResultSet", e);
                }
            }
            if(prepStmt != null){
                try {
                    prepStmt.close();
                } catch (SQLException e) {
                    log.warn("Unable to close PreparedStatement", e);
                }
            }
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    log.warn("Unable to close Connection", e);
                }
            }
        }
        return valid;

    }
    
    public String getUser(String token) {
        String user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = db.getConnection();
            
            statement = connection.prepareStatement("SELECT user FROM tokens WHERE token=?");
            
            statement.setString(1, token);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                user = resultSet.getString("user");
            }
            
        } catch (SQLException e) {
            log.error("SQLException: ", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    
                }
            }
            
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    
                }
            }
            
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    
                }
            }
        }
        
        return user;
    }

    private static String generateToken() throws OAuthSystemException {
        MD5Generator md5TokenGenerator = new MD5Generator();
        return md5TokenGenerator.generateValue();
    }

    private static Timestamp getCreatedAt() {
        Calendar cal = Calendar.getInstance();
        return new Timestamp(cal.getTime().getTime());
    }

    private static Timestamp getExpiresAt(int ttlSeconds) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, ttlSeconds);
        
        return new Timestamp(cal.getTime().getTime());
    }

    private static Timestamp getExpiresAt() {
        return getExpiresAt(3600);
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.HOUR, 1);
//
//        return new Timestamp(cal.getTime().getTime());
    }

    private static boolean isExpired(Timestamp expiresAt) {
        Calendar cal = Calendar.getInstance();

        return (expiresAt.getTime() > cal.getTime().getTime());
    }
}
