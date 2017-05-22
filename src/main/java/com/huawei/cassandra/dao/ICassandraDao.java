package com.huawei.cassandra.dao;

import java.util.List;

import com.huawei.cassandra.model.Student;

public interface ICassandraDao
{
    /**
     * 获取全部student信息
     * <功能详细描述>
     * @return [参数说明]
     *
     * @return List<Student> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    List<Student> listAllStudent();
    
    /**
     * 根据主键获取某个student信息
     * <功能详细描述>
     * @param id partition key
     * @param address cluster key
     * @param name cluster key
     * @return [参数说明]
     *
     * @return Student [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    Student getStudentByKeys(int id, String address, String name);
    
    /**
     * 插入一个student信息
     * <功能详细描述>
     * @param student [参数说明]
     *
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    void saveStudent(Student student);
    
    /**
     * 修改某个student信息<br>
     * 根据全部主键修改
     * @param student [参数说明]
     *
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    void updateStudent(Student student);
    
    /**
     * 删除某个student信息
     * <功能详细描述>
     * @param id
     * @param address
     * @param name [参数说明]
     *
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    void removeStudent(int id, String address, String name);
}
