package com.huawei.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;


public class CassandraTest
{

    public static void main(String[] args)
    {
        
    }
    
    public static void quickStart()
    {
        Cluster cluster = null;
        try {
            cluster = Cluster.builder()                                                    // (1)
                    .addContactPoint("127.0.0.1")                   // cassandra服务器ip
                    .withCredentials("admin", "admin")              // 若没有启用账号认证，此处可以去掉
                    .build();
            Session session = cluster.connect();                                           // (2)

            ResultSet rs = session.execute("select release_version from system.local");    // (3)
            Row row = rs.one();
            System.out.println(row.getString("release_version"));                          // (4)
        } finally {
            if (cluster != null) cluster.close();                                          // (5)
        }
    }
    
}
