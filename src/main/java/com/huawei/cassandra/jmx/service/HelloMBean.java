package com.huawei.cassandra.jmx.service;

public interface HelloMBean
{
    String getName();
    void setName(String name);
    
    void print();
}
