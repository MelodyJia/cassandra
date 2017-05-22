package com.huawei.cassandra.jmx.db;

import java.util.Map;

public class JMXTest
{
    
    public static void main(String[] args) throws Exception
    {
        CassNodeProbe prode = new CassNodeProbe("127.0.0.1");
        Map<String, String> nodeStorages = prode.getCassClusterStorage();
        System.out.println(nodeStorages);
        prode.close();
    }
    
}
