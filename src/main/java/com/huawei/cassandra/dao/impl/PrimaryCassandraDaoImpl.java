package com.huawei.cassandra.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.huawei.cassandra.dao.ICassandraDao;
import com.huawei.cassandra.factory.SessionRepository;
import com.huawei.cassandra.model.Student;

/**
 * session直接支持执行cql语句<br>
 * 无论是增、删、查、改，都是session.execute(cql)方式<br>
 * 关键是拼接cql
 *
 */
public class PrimaryCassandraDaoImpl implements ICassandraDao
{
    private static final Session session = SessionRepository.getSession();
    
    private static final String STUDENT_LIST_ALL = "select id,address,name,age,height from mycas.student;"; // 字符串中的;最好加上
    
    @Override
    public List<Student> listAllStudent()
    {
        List<Student> students = new ArrayList<Student>(10);
        ResultSet rs = session.execute(STUDENT_LIST_ALL);
        for (Row row : rs)
        {
            Student student = new Student();
            student.setAddress(row.getString("address"));
            student.setAge(row.getInt("age"));
            student.setHeight(row.getInt("height"));
            student.setId(row.getInt("id"));
            student.setName(row.getString("name"));
            
            students.add(student);
        }
        return students;
    }
    
    @Override
    public Student getStudentByKeys(int id, String address, String name)
    {
        // 字符串注意单引号'
        Student student = null;
        String cql = "select id,address,name,age,height from mycas.student where id=" 
                + id + " and address='" + address + "' and name='" + name + "';";
        ResultSet rs = session.execute(cql);
        Iterator<Row> rsIterator = rs.iterator();
        if (rsIterator.hasNext())
        {
            Row row = rsIterator.next();
            student = new Student();
            student.setAddress(row.getString("address"));
            student.setAge(row.getInt("age"));
            student.setHeight(row.getInt("height"));
            student.setId(row.getInt("id"));
            student.setName(row.getString("name"));
        }
        return student;
    }
    
    @Override
    public void saveStudent(Student student)
    {
        // 字符串注意单引号'
        String cql = "insert into mycas.student(id,address,name,age,height) values(" 
                + student.getId() + ",'" + student.getAddress() + "','" + student.getName()
                + "'," + student.getAge() + "," + student.getHeight() + ");";
        System.out.println(cql);
        session.execute(cql);
    }
    
    @Override
    public void updateStudent(Student student)
    {
        // 字符串注意单引号'
        String cql = "update mycas.student set age=" + student.getAge() + ",height=" + student.getHeight()
                + " where id=" + student.getId() + " and address='" + student.getAddress() + "' and "
                + " name='" + student.getName() + "'";
        System.out.println(cql);
        session.execute(cql);
    }
    
    @Override
    public void removeStudent(int id, String address, String name)
    {
        // 字符串注意单引号'
        String cql = "delete from mycas.student where id=" + id +" and address='" + address
                + "' and name='" + name + "'";
        System.out.println(cql);
        session.execute(cql);
    }
    
}
