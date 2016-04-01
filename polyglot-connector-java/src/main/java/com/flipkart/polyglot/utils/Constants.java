
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
