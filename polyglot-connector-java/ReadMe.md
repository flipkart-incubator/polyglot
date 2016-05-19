This wiki is to describe how to use polyglot java connector :

Sample URL :
"jdbc:polyglot://user:password@txnstore:txnstorehost:txnstoreport@archivalstore:archivalStoreUrl/keyspace_name/database_name?dataStoreType=(txnl or archival)"

Based on dataStoreType it does use txnlStore or archivalStore. Addition to this required properties for vitess connector and phoenix connector could be passed either as params or in url.

### Sample Code Snippet to use Polyglot Driver

### Connecting with Archival Store : 

                Connectoin conn;
                Properties prop = new Properties();
                 Class.forName("com.flipkart.polyglot.jdbc.PolyglotDriver");
                 conn =  DriverManager.getConnection("jdbc:polyglot://txnstore:10.33.17.231:15991@archivalstore:localhost:2181:hbase/vt_shipment/shipment?dataStoreType=archival",prop);
                ResultSet rst = conn.createStatement().executeQuery("select * from tableName");
                while (rst.next()) {
                    System.out.println(rst1.getString(1) + " " + rst1.getString(2) + " " + rst1.getString(3) + " " + rst1.getString(4) + " "
                    );
                }

### Connecting with Transaction Store : 

                 Connection conn;
                 Properties prop = new Properties();;
                 Class.forName("com.flipkart.polyglot.jdbc.PolyglotDriver");
                 conn =  DriverManager.getConnection("jdbc:polyglot://txnstore:10.33.17.231:15991@archivalstore:172.17.94.213:2181:hbase/vt_shipment/shipment?dataStoreType=txnl",prop);

                ResultSet rst = conn.createStatement().executeQuery("select * from tableName");
                while (rst.next()) {
                    System.out.println(rst.getString(1) + " " + rst.getString(2));
                }

Addition parameter e.g. : execute_type = stream,simple , Default : Simple 

                          tablet_type = Master, RdOnly, Replica Default : Master
Could be passed.
                          

