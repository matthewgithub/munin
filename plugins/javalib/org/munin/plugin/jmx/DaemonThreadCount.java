package org.munin.plugin.jmx;
/**
 *
 * @author Diyar
 */
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.FileNotFoundException;
import java.io.IOException;
public class DaemonThreadCount
{
    public static void main(String args[])throws FileNotFoundException,IOException
    {
  if (args.length == 1) {
            if (args[0].equals("config")) {
                System.out.println(
                        "graph_title DaemonThreadCount\n" +
                        "graph_vlabel DaemonThreadCount\n" +
                        "graph_info Returns the current number of live daemon threads.\n" +  
                        "graph_category jvm\n" + 
                        "DaemonThreadCount.label DaemonThreadCount\n"
);
            }
         else {
                    String[] connectionInfo= ConfReader.GetConnectionInfo();

        try{
            JMXServiceURL u = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" +connectionInfo[0] + ":" + connectionInfo[1] + "/jmxrmi");
            JMXConnector c=JMXConnectorFactory.connect(u);
            MBeanServerConnection connection=c.getMBeanServerConnection();
            ThreadMXBean threadmxbean=ManagementFactory.newPlatformMXBeanProxy(connection, ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
            
            System.out.println("DaemonThreadCount.value "+threadmxbean.getDaemonThreadCount());
            
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }

}
}
}