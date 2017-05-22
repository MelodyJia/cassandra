package com.huawei.cassandra;

import java.util.List;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Session;
import com.huawei.cassandra.dao.ICassandraDao;
import com.huawei.cassandra.dao.impl.PreparedCassandraDaoImpl;
import com.huawei.cassandra.factory.SessionRepository;
import com.huawei.cassandra.model.Student;

public class CassandraStudentTest
{
    
    public static void main(String[] args)
    {
        //ICassandraDao cassDao = new PrimaryCassandraDaoImpl();
        //ICassandraDao cassDao = new QueryBuildCassandraDaoImpl();
        ICassandraDao cassDao = new PreparedCassandraDaoImpl();
        
        //getStudent(cassDao);
        //saveStudent(cassDao);
        //updateStudent(cassDao);
        //removeStudent(cassDao);
        batch();
        listStudent(cassDao);
    }
    
    public static void listStudent(ICassandraDao cassDao)
    {
        List<Student> studentList = cassDao.listAllStudent();
        for (Student student : studentList)
        {
            System.out.println(student);
        }
    }
    
    public static void getStudent(ICassandraDao cassDao)
    {
        Student student = cassDao.getStudentByKeys(1, "guangdong", "lixiao");
        System.out.println(student);
    }
    
    public static void saveStudent(ICassandraDao cassDao)
    {
        Student student = new Student();
        student.setId(2);
        student.setAddress("hunan");
        student.setName("youzhibing");
        student.setAge(26);
        student.setHeight(160);
        cassDao.saveStudent(student);
    }
    
    public static void updateStudent(ICassandraDao cassDao)
    {
        Student student = new Student();
        student.setId(2);
        student.setAddress("hunan");
        student.setName("youzhibing");
        student.setAge(28);
        student.setHeight(158);
        cassDao.updateStudent(student);
    }
    
    public static void removeStudent(ICassandraDao cassDao)
    {
        cassDao.removeStudent(2, "hunan", "youzhibing");
    }
    
    public static void batch()
    {
        Session session = SessionRepository.getSession();
        BoundStatement insertBind1 = new BoundStatement(
                session.prepare("insert into mycas.student(id,address,name,age,height) values(?,?,?,?,?);"))
                       .bind(3, "guangxi", "huangfeihong", 67, 175);
        
        BoundStatement insertBind2 = new BoundStatement(
                session.prepare("insert into mycas.student(id,address,name,age,height) values(?,?,?,?,?);"))
                       .bind(4, "hunan", "youzhibing", 26, 160);
        
        BoundStatement updateBind = new BoundStatement(
                session.prepare("update mycas.student set age=?, height=? where id=? and address=? and name=?;"))
                       .bind(72, 173, 3, "guangxi", "huangfeihong");
        
        BoundStatement deleteBind = new BoundStatement(
                session.prepare("delete from mycas.student where id=? and address=? and name=?;"))
                       .bind(4, "hunan", "youzhibing");
        
        BatchStatement batchStatement = new BatchStatement();
        batchStatement.add(insertBind1);
        batchStatement.add(insertBind2);
        batchStatement.add(updateBind);
        batchStatement.add(deleteBind);
        session.execute(batchStatement);
    }
}
