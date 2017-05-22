package com.huawei.cassandra.jmx.client;



public class JmxClient
{
    public static void main(String[] args) throws Exception
    {
        HelloClient client = new HelloClient("localhost", 8099, "hello", "world");
        client.print();
        //System.out.println(client.getName());
        client.close();
    }
    
}
