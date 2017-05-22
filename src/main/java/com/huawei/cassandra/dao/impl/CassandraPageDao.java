package com.huawei.cassandra.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.huawei.cassandra.dao.ICassandraPage;
import com.huawei.cassandra.factory.SessionRepository;
import com.huawei.cassandra.model.Teacher;

public class CassandraPageDao implements ICassandraPage
{
    private static final Session session = SessionRepository.getSession();
    
    private static final String CQL_TEACHER_PAGE = "select * from mycas.teacher;";
    
    @Override
    public Map<String, Object> page(PagingState pagingState)
    {
        final int RESULTS_PER_PAGE = 2;
        Map<String, Object> result = new HashMap<String, Object>(2);
        List<Teacher> teachers = new ArrayList<Teacher>(RESULTS_PER_PAGE);

        Statement st = new SimpleStatement(CQL_TEACHER_PAGE);
        st.setFetchSize(RESULTS_PER_PAGE);
        
        // 第一页没有分页状态
        if (pagingState != null)
        {            
            st.setPagingState(pagingState);
        }
        
        ResultSet rs = session.execute(st);
        result.put("pagingState", rs.getExecutionInfo().getPagingState());
        
        //请注意，我们不依赖RESULTS_PER_PAGE，因为fetch size并不意味着cassandra总是返回准确的结果集
        //它可能返回比fetch size稍微多一点或者少一点，另外，我们可能在结果集的结尾
        int remaining = rs.getAvailableWithoutFetching();
        for (Row row : rs)
        {
            Teacher teacher = this.obtainTeacherFromRow(row);
            teachers.add(teacher);
            
            if (--remaining == 0) 
            {
                break;
            }
        }
        result.put("teachers", teachers);
        return result;
    }

    private Teacher obtainTeacherFromRow(Row row)
    {
        Teacher teacher = new Teacher();
        teacher.setAddress(row.getString("address"));
        teacher.setAge(row.getInt("age"));
        teacher.setHeight(row.getInt("height"));
        teacher.setId(row.getInt("id"));
        teacher.setName(row.getString("name"));
        
        return teacher;
    }
 
}
