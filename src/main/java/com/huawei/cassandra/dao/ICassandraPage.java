package com.huawei.cassandra.dao;

import java.util.Map;

import com.datastax.driver.core.PagingState;

public interface ICassandraPage
{
    Map<String, Object> page(PagingState pagingState);

}
