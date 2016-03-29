package com.flipkart.polyglot.jdbc.test;

import com.flipkart.polyglot.jdbc.PolyglotDriver;
import com.flipkart.vitess.jdbc.VitessConnection;
import com.flipkart.vitess.jdbc.VitessDriver;
import org.apache.phoenix.jdbc.PhoenixConnection;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by naveen.nahata on 23/03/16.
 */
public class PolyglotDriverTest {

    private static PolyglotDriver driver = new PolyglotDriver();

    @BeforeClass
    public static void setUp() {
        try {
            Class.forName("com.flipkart.polyglot.jdbc.PolyglotDriver");
        } catch (ClassNotFoundException e) {
            Assert.fail("Driver is not in the CLASSPATH -> " + e);
        }
    }

    @Test
    public void testGetPropertyInfo() {
        try {
            String dbURL = "jdbc:polyglot://user:password@txnstore:10.33.17.231:15991@archivalstore:10.34.17.147:2181:hbase/shipment/shipment?dataStoreType=txnl";
            Properties info = new Properties();
            Connection connection = DriverManager.getConnection(dbURL, info);
            Assert.assertEquals(connection instanceof VitessConnection, true);
        } catch (SQLException e) {
            Assert.fail("SQLException Not Expected");
        }
    }

}
