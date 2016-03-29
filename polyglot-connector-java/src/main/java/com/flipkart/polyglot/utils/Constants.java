package com.flipkart.polyglot.utils;

/**
 * Created by naveen.nahata on 22/03/16.
 */
public class Constants {
    public static final DataStoreType DEFAULT_DATA_STORE_TYPE = DataStoreType.TXNL;
    public static final String URL_PREFIX = "jdbc:polyglot://";
    public static final int DRIVER_MAJOR_VERSION = 1;
    public static final int DRIVER_MINOR_VERSION = 0;
    public static final boolean JDBC_COMPLIANT = false;


    public static final class Property {
        public static final String DATA_STORE_TYPE = "dataStoreType";
    }

    public static final class SQLExceptionMessages {
        public static final String MALFORMED_URL = "Malformed URL Exception";
        public static final String INIT_FAILED = "Failed to Initialize Vitess JDBC Driver";
    }

    public enum DataStoreType {
        TXNL,
        ARCHIVAL
    }
}
