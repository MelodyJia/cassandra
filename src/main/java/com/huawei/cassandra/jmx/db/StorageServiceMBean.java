package com.huawei.cassandra.jmx.db;

import java.util.Map;

public interface StorageServiceMBean
{
    /** Human-readable load value.  Keys are IP addresses. */
    public Map<String, String> getLoadMap();
}
