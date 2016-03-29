package com.flipkart.polyglot.jdbc;


import com.flipkart.polyglot.utils.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.phoenix.util.PhoenixRuntime;

/**
 * Created by naveen.nahata on 23/03/16.
 */
public class PolyglotJDBCUrl {

    private final String txnStroeUrl;
    private final String archivalStoreUrl;
    private final String url;
    private final Constants.DataStoreType dataStoreType;

    public PolyglotJDBCUrl(String url, Properties info) throws SQLException {
        info = getURLParamProperties(url, info);

                /* URL pattern e.g. jdbc:polyglot://username:password@txnstore:ip1:port1,ip2:port2@archivalstore:ip3:port3/keyspace/catalog?
        property1=value1.. */

        final Pattern p = Pattern
                .compile("^jdbc:(polyglot)://((\\w+)(:(\\w*))?@)?(txnstore:(\\S+)@)?(archivalstore:(\\S+))?/(\\w+)/(\\w+)(\\?(\\S+))?");
        final Matcher m = p.matcher(url);
        if (!m.find()) {
            throw new SQLException(Constants.SQLExceptionMessages.MALFORMED_URL);
        }
        for (int i = 1; i <= m.groupCount(); i++) {
            System.out.println(i + " : " + m.group(i));
        }
        this.txnStroeUrl = getTxnlUrl(m);
        /* hbase phoenix jdbc url format
        jdbc:phoenix [ :<zookeeper quorum> [ :<port number> ] [ :<root node> ] [ :<principal> ] [ :<keytab file> ] ]
        */
        this.archivalStoreUrl = getArchivalUrl(m);
        this.url = url;
        String dataStoreType = info.getProperty(Constants.Property.DATA_STORE_TYPE);
        this.dataStoreType = getDataStoreType(dataStoreType);
    }

    public String getTxnStroeUrl() {
        return txnStroeUrl;
    }

    public String getArchivalStoreUrl() {
        return archivalStoreUrl;
    }

    public String getUrl() {
        return url;
    }

    public Constants.DataStoreType getDataStoreType() {
        return dataStoreType;
    }

    private static Properties getURLParamProperties(String url, Properties info) {

     /*
      * Parse parameters after the ? in the URL
		 */
        if (null == info) {
            info = new Properties();
        }
        int index = url.indexOf("?");

        if (index != -1) {
            String paramString = url.substring(index + 1, url.length());
            url = url.substring(0, index);

            StringTokenizer queryParams = new StringTokenizer(paramString, "&");

            while (queryParams.hasMoreTokens()) {
                String parameterValuePair = queryParams.nextToken();

                int indexOfEquals = parameterValuePair.indexOf('=');

                String parameter = null;
                String value = null;

                if (indexOfEquals != -1) {
                    parameter = parameterValuePair.substring(0, indexOfEquals);

                    if (indexOfEquals + 1 < parameterValuePair.length()) {
                        value = parameterValuePair.substring(indexOfEquals + 1);
                    }
                }

                if ((value != null && value.length() > 0) && (parameter != null
                        && parameter.length() > 0)) {
                    try {
                        info.put(parameter, URLDecoder.decode(value, "UTF-8"));
                    } catch (UnsupportedEncodingException badEncoding) {
                        info.put(parameter, URLDecoder.decode(value));
                    } catch (NoSuchMethodError nsme) {
                        info.put(parameter, URLDecoder.decode(value));
                    }
                }
            }
        }
        return info;
    }

    public static Constants.DataStoreType getDataStoreType(String dataStoreType) {
        switch (dataStoreType.toLowerCase()) {
            case "txnl":
                return Constants.DataStoreType.TXNL;
            case "archival":
                return Constants.DataStoreType.ARCHIVAL;
            default:
                return Constants.DEFAULT_DATA_STORE_TYPE;
        }
    }

    private String getTxnlUrl(Matcher m) {
        String url = com.flipkart.vitess.util.Constants.URL_PREFIX;
        if (m.group(2) != null)
            url = url + m.group(2);
        if (m.group(7) != null)
            url = url + m.group(7);
        if (m.group(10) != null) {
            url = url + "/" + m.group(10);
        }
        if (m.group(11) != null) {
            url = url + "/" + m.group(11);
        }
        if (m.group(12) != null) {
            url = url + m.group(12);
        }
        return url;
    }

    private String getArchivalUrl(Matcher m) {
        String url = PhoenixRuntime.JDBC_PROTOCOL;
        if (m.group(9) != null) {
            url = url + ":" + m.group(9);
        }
        return url;
    }
}
