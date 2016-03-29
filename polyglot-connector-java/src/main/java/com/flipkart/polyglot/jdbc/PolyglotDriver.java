package com.flipkart.polyglot.jdbc;

import com.flipkart.polyglot.utils.Constants;
import com.flipkart.polyglot.utils.Constants.DataStoreType;
import com.flipkart.vitess.jdbc.VitessConnection;
import com.flipkart.vitess.jdbc.VitessDriver;
import org.apache.commons.lang.ArrayUtils;
import org.apache.phoenix.jdbc.PhoenixDriver;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by naveen.nahata on 22/03/16.
 */
public class PolyglotDriver implements Driver {

    static {
        try {
            DriverManager.registerDriver(new PolyglotDriver());
            DriverManager.registerDriver(new VitessDriver());
            DriverManager.registerDriver(new PhoenixDriver());
        } catch (SQLException e) {
            throw new RuntimeException(
                    Constants.SQLExceptionMessages.INIT_FAILED + " : " + e.getErrorCode() + " - " + e
                            .getMessage());
        }
    }

    public Connection connect(String url, Properties info) throws SQLException {
        if(acceptsURL(url)){
            PolyglotJDBCUrl polyglotJDBCUrl = new PolyglotJDBCUrl(url, info);
            DataStoreType dataStoreType = polyglotJDBCUrl.getDataStoreType();
            switch (dataStoreType){
                case TXNL :
                    return DriverManager.getConnection(polyglotJDBCUrl.getTxnStroeUrl(), info);
                case ARCHIVAL:
                    return DriverManager.getConnection(polyglotJDBCUrl.getArchivalStoreUrl(),info);
            }
        }
        return  null;
    }

    public boolean acceptsURL(String url) throws SQLException {
        return null != url && url.startsWith(Constants.URL_PREFIX);
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        PolyglotJDBCUrl polyglotJDBCUrl = new PolyglotJDBCUrl(url, info);
        VitessDriver vitessDriver = new VitessDriver();
        DriverPropertyInfo[] vitessDpi = vitessDriver.getPropertyInfo(polyglotJDBCUrl.getTxnStroeUrl(),info);
        PhoenixDriver phoenixDriver = new PhoenixDriver();
        DriverPropertyInfo[] phoenixDpi = phoenixDriver.getPropertyInfo(polyglotJDBCUrl.getArchivalStoreUrl(),info);
        DriverPropertyInfo[] dpi = (DriverPropertyInfo[]) ArrayUtils.addAll(vitessDpi,phoenixDpi);
        return dpi;
    }

    public int getMajorVersion() {
        return Constants.DRIVER_MAJOR_VERSION;
    }

    public int getMinorVersion() {
        return Constants.DRIVER_MINOR_VERSION;
    }

    public boolean jdbcCompliant() {
        return Constants.JDBC_COMPLIANT;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
