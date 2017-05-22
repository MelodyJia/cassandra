package com.huawei.cassandra.jmx.service;

import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXAuthenticator;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXPrincipal;
import javax.management.remote.JMXServiceURL;
import javax.security.auth.Subject;


public class HelloService
{
    private static final int RMI_PORT = 8099;  
    private static final String JMX_SERVER_NAME = "TestJMXServer";
    private static final String USER_NAME = "hello";
    private static final String PASS_WORD = "world";
    
    public static void main(String[] args) throws Exception
    {
        HelloService service = new HelloService(); 
        service.startJmxServer();
    }
    
    private void startJmxServer() throws Exception
    {
        //MBeanServer mbs = MBeanServerFactory.createMBeanServer(jmxServerName);  
        MBeanServer mbs = this.getMBeanServer(); 
      
        // 在本地主机上创建并输出一个注册实例，来接收特定端口的请求
        LocateRegistry.createRegistry(RMI_PORT, null, RMISocketFactory.getDefaultSocketFactory()); 
        
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + RMI_PORT + "/" + JMX_SERVER_NAME);  
        System.out.println("JMXServiceURL: " + url.toString());  
        
        Map<String, Object> env = this.putAuthenticator();
        
        //JMXConnectorServer jmxConnServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);   // 不加认证 
        JMXConnectorServer jmxConnServer = JMXConnectorServerFactory.newJMXConnectorServer(url, env, mbs);      // 加认证 
        jmxConnServer.start(); 
    }
    
    private MBeanServer getMBeanServer() throws Exception
    {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName objName = new ObjectName(JMX_SERVER_NAME + ":name=" + "hello");  
        mbs.registerMBean(new Hello(), objName);
        return mbs;
    }
    
    private Map<String, Object> putAuthenticator()
    {
        Map<String,Object> env = new HashMap<String,Object>();
        JMXAuthenticator auth = createJMXAuthenticator();
        env.put(JMXConnectorServer.AUTHENTICATOR, auth);

        env.put("com.sun.jndi.rmi.factory.socket", RMISocketFactory.getDefaultSocketFactory());
        return env;
    }
    
    private JMXAuthenticator createJMXAuthenticator() 
    {
        return new JMXAuthenticator() 
        {
            public Subject authenticate(Object credentials) 
            {
                String[] sCredentials = (String[]) credentials;
                if (null == sCredentials || sCredentials.length != 2)
                {
                    throw new SecurityException("Authentication failed!");
                }
                String userName = sCredentials[0];
                String password = sCredentials[1];
                if (USER_NAME.equals(userName) && PASS_WORD.equals(password)) 
                {
                    Set<JMXPrincipal> principals = new HashSet<JMXPrincipal>();
                    principals.add(new JMXPrincipal(userName));
                    return new Subject(true, principals, Collections.EMPTY_SET,
                            Collections.EMPTY_SET);
                }
                throw new SecurityException("Authentication failed!");
            }
        };
    }
}
