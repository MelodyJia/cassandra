package com.huawei.cassandra.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.huawei.cassandra.dao.ICassandraDao;
import com.huawei.cassandra.factory.SessionRepository;
import com.huawei.cassandra.model.Student;

/**
 * 
 * 利用Querybuilder减轻cql的拼接<br>
 * 注意：驱动版本不同，Querybuilder的用法有些许不同，有些版本的某些方法变成非静态的了
 * 
 */
public class QueryBuildCassandraDaoImpl implements ICassandraDao
{
    private static final Session session = SessionRepository.getSession();
    
    @Override
    public List<Student> listAllStudent()
    {
        List<Student> students = new ArrayList<Student>(10);
        ResultSet rs = session.execute(
                QueryBuilder.select("id", "address", "name", "age", "height")
                .from("mycas", "student"));
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
        ResultSet rs = session.execute(
                QueryBuilder.select("id", "address", "name", "age", "height")
                .from("mycas", "student")
                .where(QueryBuilder.eq("id", id))
                .and(QueryBuilder.eq("address", address))
                .and(QueryBuilder.eq("name", name)));
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
        session.execute(
                QueryBuilder.insertInto("mycas", "student")
                .values(new String[]{"id", "address", "name", "age", "height"},
                        new Object[]{student.getId(), student.getAddress(), 
                            student.getName(), student.getAge(), student.getHeight()}));
    }
    
    @Override
    public void updateStudent(Student student)
    {
        session.execute(
                QueryBuilder.update("mycas", "student")
                .with(QueryBuilder.set("age", student.getAge()))
                .and(QueryBuilder.set("height", student.getHeight()))
                .where(QueryBuilder.eq("id", student.getId()))
                .and(QueryBuilder.eq("address", student.getAddress()))
                .and(QueryBuilder.eq("name", student.getName())));
    }
    
    @Override
    public void removeStudent(int id, String address, String name)
    {
        session.execute(QueryBuilder.delete()
                .from("mycas", "student")
                .where(QueryBuilder.eq("id", id))
                .and(QueryBuilder.eq("address", address))
                .and(QueryBuilder.eq("name", name)));
    }
}
