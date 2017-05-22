package com.huawei.cassandra.model;

public class Student
{
    private Integer id;
    private String address;
    private String name;
    private Integer age;
    private Integer height;
    
    @Override
    public String toString()
    {
        return "Student:[id=" + this.id + ", address=" + this.address
                + ", name=" + this.name + ", age=" + this.age + ", height="
                + this.height + "]";
    }
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getAge()
    {
        return age;
    }
    
    public void setAge(Integer age)
    {
        this.age = age;
    }
    
    public Integer getHeight()
    {
        return height;
    }
    
    public void setHeight(Integer height)
    {
        this.height = height;
    }
}
