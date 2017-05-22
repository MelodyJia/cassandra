package com.huawei.cassandra;

import java.util.Map;

import com.datastax.driver.core.PagingState;
import com.huawei.cassandra.dao.ICassandraPage;
import com.huawei.cassandra.dao.impl.CassandraPageDao;

public class PagingTest
{
    
    public static void main(String[] args)
    {
        ICassandraPage cassPage = new CassandraPageDao();
        Map<String, Object> result = cassPage.page(null);
        PagingState pagingState = (PagingState) result.get("pagingState");
        System.out.println(result.get("teachers"));
        while (pagingState != null)
        {
            // PagingState对象可以被序列化成字符串或字节数组
            System.out.println("==============================================");
            result = cassPage.page(pagingState);
            pagingState = (PagingState) result.get("pagingState");
            System.out.println(result.get("teachers"));
        }
    }
    
}
