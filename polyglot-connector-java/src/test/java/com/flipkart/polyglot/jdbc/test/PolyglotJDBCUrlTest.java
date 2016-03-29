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
