package com.huawei.cassandra.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.huawei.cassandra.dao.ICassandraDao;
import com.huawei.cassandra.factory.SessionRepository;
import com.huawei.cassandra.model.Student;

public class PreparedCassandraDaoImpl implements ICassandraDao
{
    private static final Session session = SessionRepository.getSession();
    
    private static final String STUDENT_LIST_ALL = "select id,address,name,age,height from mycas.student;";
    private static final String GET_STUDENT = "select id,address,name,age,height from mycas.student where id=? and address=? and name=?;";
    private static final String SAVE_STUDENT = "insert into mycas.student(id,address,name,age,height) values(?,?,?,?,?);";
    private static final String UPDATE_STUDENT = "update mycas.student set age=?, height=? where id=? and address=? and name=?;";
    private static final String REMOVE_STUDENT = "delete from mycas.student where id=? and address=? and name=?;";
    
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
        Student student = null;
        PreparedStatement prepareStatement = session.prepare(GET_STUDENT);
        BoundStatement bindStatement = new BoundStatement(prepareStatement).bind(id, address, name);
        ResultSet rs = session.execute(bindStatement);
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
        PreparedStatement prepareStatement = session.prepare(SAVE_STUDENT);
        BoundStatement bindStatement = new BoundStatement(prepareStatement)
            .bind(student.getId(), student.getAddress(), student.getName(), student.getAge(), student.getHeight());
        session.execute(bindStatement);
    }
    
    @Override
    public void updateStudent(Student student)
    {
        PreparedStatement prepareStatement = session.prepare(UPDATE_STUDENT);
        BoundStatement bindStatement = new BoundStatement(prepareStatement)
            .bind(student.getAge(), student.getHeight(), student.getId(), student.getAddress(), student.getName());
        session.execute(bindStatement);
    }
    
    @Override
    public void removeStudent(int id, String address, String name)
    {
        PreparedStatement prepareStatement = session.prepare(REMOVE_STUDENT);
        BoundStatement bindStatement = new BoundStatement(prepareStatement)
            .bind(id, address, name);
        session.execute(bindStatement);
    }
}
