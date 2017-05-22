package com.huawei.cassandra.jmx.client;

import java.io.IOException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMISocketFactory;
import java.util.HashMap;
import java.util.Map;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.rmi.ssl.SslRMIClientSocketFactory;

public class HelloClient implements AutoCloseable
{
	private static final String fmtUrl = "service:jmx:rmi:///jndi/rmi://[%s]:%d/TestJMXServer";
    private static final String ssObjName = "TestJMXServer:name=hello";
	private static final int defaultPort = 1099;                       // cassandra默认端口是7199
    final String host;
    final int port;
    private String username;
    private String password;
    
    private JMXConnector jmxc;
    private MBeanServerConnection mbeanServerConn;
    private HelloClientMBean hmProxy;
	
    /**
     * Creates a connection using the specified JMX host, port, username, and password.
     *
     * @param host hostname or IP address of the JMX agent
     * @param port TCP port of the remote JMX agent
     * @throws IOException on connection failures
     */
	public HelloClient(String host, int port, String username, String password) throws IOException
    {
        assert username != null && !username.isEmpty() && password != null && !password.isEmpty()
               : "neither username nor password can be blank";

        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        connect();
    }
	
	/**
     * Creates a connection using the specified JMX host and port.
     *
     * @param host hostname or IP address of the JMX agent
     * @param port TCP port of the remote JMX agent
     * @throws IOException on connection failures
     */
	public HelloClient(String host, int port) throws IOException
    {
        this.host = host;
        this.port = port;
        connect();
    }
	
	/**
     * Creates a connection using the specified JMX host and default port.
     *
     * @param host hostname or IP address of the JMX agent
     * @throws IOException on connection failures
     */
    public HelloClient(String host) throws IOException
    {
        this.host = host;
        this.port = defaultPort;
        connect();
    }
	
	/**
     * Create a connection to the JMX agent and setup the M[X]Bean proxies.
     *
     * @throws IOException on connection failures
     */
    private void connect() throws IOException
    {
        JMXServiceURL jmxUrl = new JMXServiceURL(String.format(fmtUrl, host, port));
        Map<String,Object> env = new HashMap<String,Object>();
        if (username != null)
        {
            String[] creds = { username, password };
            env.put(JMXConnector.CREDENTIALS, creds);
        }

        env.put("com.sun.jndi.rmi.factory.socket", getRMIClientSocketFactory());

        jmxc = JMXConnectorFactory.connect(jmxUrl, env);
        mbeanServerConn = jmxc.getMBeanServerConnection();

        try
        {
            ObjectName name = new ObjectName(ssObjName);
            hmProxy = JMX.newMBeanProxy(mbeanServerConn, name, HelloClientMBean.class);
        }
        catch (MalformedObjectNameException e)
        {
            throw new RuntimeException(
                    "Invalid ObjectName? Please report this as a bug.", e);
        }
    }
    
    private RMIClientSocketFactory getRMIClientSocketFactory() throws IOException
    {
        if (Boolean.parseBoolean(System.getProperty("ssl.enable")))
            return new SslRMIClientSocketFactory();
        else
            return RMISocketFactory.getDefaultSocketFactory();
    }
    
    public void print()
    {
        hmProxy.print();
    }

    public String getName()
    {
        return hmProxy.getName();
    }
    
	@Override
	public void close() throws Exception 
	{
		jmxc.close();
	}
}
