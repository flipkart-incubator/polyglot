
/*
 * Copyright (c) 2016. the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.flipkart.polyglot.jdbc;

import com.flipkart.polyglot.utils.Constants;
import com.flipkart.polyglot.utils.Constants.DataStoreType;
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

    /**
     * Connect with polyglot driver using url and info properties
     * @param url
     * @param info
     * @return
     * @throws SQLException
     */
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

    /**
     * Return true if url start with jdbc:polyglot
     * @param url
     * @return
     * @throws SQLException
     */
    public boolean acceptsURL(String url) throws SQLException {
        return null != url && url.startsWith(Constants.URL_PREFIX);
    }

    /**
     * Get Driver property infos
     * @param url
     * @param info
     * @return
     * @throws SQLException
     */
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        PolyglotJDBCUrl polyglotJDBCUrl = new PolyglotJDBCUrl(url, info);
        VitessDriver vitessDriver = new VitessDriver();
        DriverPropertyInfo[] vitessDpi = vitessDriver.getPropertyInfo(polyglotJDBCUrl.getTxnStroeUrl(),info);
        PhoenixDriver phoenixDriver = new PhoenixDriver();
        DriverPropertyInfo[] phoenixDpi = phoenixDriver.getPropertyInfo(polyglotJDBCUrl.getArchivalStoreUrl(),info);
        DriverPropertyInfo[] dpi = (DriverPropertyInfo[]) ArrayUtils.addAll(vitessDpi,phoenixDpi);
        return dpi;
    }

    /**
     * Driver Major version
     * @return
     */
    public int getMajorVersion() {
        return Constants.DRIVER_MAJOR_VERSION;
    }

    /**
     * Driver Minor version
     * @return
     */
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
