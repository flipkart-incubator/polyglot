
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

package com.flipkart.polyglot.jdbc.test;

import com.flipkart.polyglot.jdbc.PolyglotJDBCUrl;
import com.flipkart.vitess.jdbc.VitessJDBCUrl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

/**
 * Created by naveen.nahata on 23/03/16.
 */
public class PolyglotJDBCUrlTest {

    @Test
    public void testPolyglotJDBCConstructorWithParams() throws Exception {
        Properties info = new Properties();
        PolyglotJDBCUrl polyglotJDBCUrl = new PolyglotJDBCUrl("jdbc:polyglot://user:password@txnstore:10.33.17.231:15991@archivalstore:10.34.17.147:2181:hbase/shipment/shipment?dataStoreType=archival",
                info);
        Assert.assertEquals("jdbc:vitess://user:password@10.33.17.231:15991/shipment/shipment?dataStoreType=archival",polyglotJDBCUrl.getTxnStroeUrl());
        Assert.assertEquals("jdbc:phoenix:10.34.17.147:2181:hbase",polyglotJDBCUrl.getArchivalStoreUrl());
    }

    @Test
    public void testPolyglotJDBCConstructorWithoutParams() throws Exception {
        Properties info = new Properties();
        info.setProperty("dataStoreType","archival");
        PolyglotJDBCUrl polyglotJDBCUrl = new PolyglotJDBCUrl("jdbc:polyglot://user:password@txnstore:10.33.17.231:15991@archivalstore:10.34.17.147:2181:hbase/shipment/shipment",
                info);
        Assert.assertEquals("jdbc:vitess://user:password@10.33.17.231:15991/shipment/shipment",polyglotJDBCUrl.getTxnStroeUrl());
        Assert.assertEquals("jdbc:phoenix:10.34.17.147:2181:hbase",polyglotJDBCUrl.getArchivalStoreUrl());
    }

}
