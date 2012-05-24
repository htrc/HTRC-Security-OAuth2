package edu.indiana.d2i.htrc.oauth2.common;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private DataSource ds;

    public Database(String dsName) throws NamingException {
        initDataSource(dsName);
    }
    
    private void initDataSource(String dsName) throws NamingException {
        Context initContext = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        ds = (DataSource)envContext.lookup(dsName);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
