package edu.indiana.d2i.htrc.oauth2.common;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserStore {
    private Database db;

    public UserStore(Database db) {
        this.db = db;
    }

    public boolean isUserAuthenticated(String userId, String password) {
        Connection con = null;
        PreparedStatement prepStmt = null;

        try {
            con = db.getConnection();

            String userQuery = "SELECT * FROM users WHERE uname=?";

            prepStmt = con.prepareStatement(userQuery);
            prepStmt.setString(1, userId);

            ResultSet rs = prepStmt.executeQuery();

            rs.last();

            if (rs.getRow() != 1) {
                // TODO: Log about duplicate tokens
                return false;
            }

            rs.first();

            return validate(rs.getString("password"), password);
        } catch (SQLException e) {
            // TODO: Log
            e.printStackTrace();
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
        return false;

    }

    private boolean validate(String pwdInDB, String pwd) {
        return BCrypt.checkpw(pwd, pwdInDB);
    }

    public static void main(String[] args) {
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:8889/oauth?"
                            + "user=root&password=root");

            preparedStatement = connect.prepareStatement("INSERT INTO users values(default, ?,?)");
            preparedStatement.setString(1, "milinda");
            preparedStatement.setString(2, BCrypt.hashpw("test123", BCrypt.gensalt()));
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
               if (connect != null) {
                    connect.close();
                }
            } catch (Exception e) {

            }
        }
    }
}
