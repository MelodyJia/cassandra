package com.huawei.cassandra.jmx.service;

public class Hello implements HelloMBean
{
    
    private String name = "default name";
    
    @Override
    public String getName()
    {
        return this.name;
    }
    
    @Override
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Override
    public void print()
    {
        System.out.println("hello, print");
    }
    
}
