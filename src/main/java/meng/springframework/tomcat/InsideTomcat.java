package meng.springframework.tomcat;

import meng.springframework.mvc.MvcServlet;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;

public class InsideTomcat {

    //tomcat端口号
    private static String port;
    //tomcat的字符编码集
    private static String code;
    //拦截请求路径
    private static String hinderUrl;
    //请求转发路径
    private static String shiftUrl;
    //tomcat对象
    private static Tomcat tomcat;
    //servlet对象
    private static HttpServlet servlet;

    static{
       init();
    }


    public static void init(){
        //先写死值 后续更灵活
        port = "8080";
        code = "UTF-8";
        hinderUrl = "/";
        shiftUrl = "/";
        tomcat = new Tomcat();
        servlet = new MvcServlet();

        //创建连接器
        Connector connector = tomcat.getConnector();
        connector.setPort(Integer.valueOf(port));
        connector.setURIEncoding(code);

        //设置HOST
        Host host = tomcat.getHost();
        host.setAppBase("webapps");

        //获取目录绝对路径
        String classPath = System.getProperty("user.dir");

        //配置tomcat上下文
        Context context = tomcat.addContext(host,hinderUrl,classPath);

        //配置请求拦截转发
        Wrapper wrapper = tomcat.addServlet(shiftUrl,"MvcServlet",servlet);
        wrapper.addMapping(shiftUrl);
    }

    public static void run() throws LifecycleException {
        //启动tomcat
        InsideTomcat.tomcat.start();
        //保持tomcat启动状态
        InsideTomcat.tomcat.getServer().await();
    }
}
