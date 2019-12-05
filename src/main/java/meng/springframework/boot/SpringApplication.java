package meng.springframework.boot;

import meng.springframework.annotation.param.RequestParam;
import meng.springframework.aop.AopContainerInjection;
import meng.springframework.aop.CutPoint;
import meng.springframework.container.Container;
import meng.springframework.container.ContainerInjection;
import meng.springframework.di.DependencyInjection;
import meng.springframework.mvc.SpringMvc;
import meng.springframework.server.NioHttpServer;
import meng.springframework.tomcat.InsideTomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringApplication {

    private static Logger logger = LoggerFactory.getLogger(SpringApplication.class);

    //普通对象容器
    public static Container container = Container.INSTANTIATION;
    //切面容器(环绕)
    public static Map<String, CutPoint> arounds = new HashMap<>();
    //切面容器(前环绕)
    public static Container befers = Container.INSTANTIATION;
    //切面容器(后环绕)
    public static Container afters = Container.INSTANTIATION;

    //保存url和controller对象以及具体方法的映射关系
    public static Map<String,Method> handlerMapping = new HashMap<>();
    public static Map<String, Object> controllerMapping = new HashMap<>();

    //保存method的RequestParam注解内容
    public static Map<String, List<RequestParam>> methodTheRequestParams = new HashMap<>();

    public static void run(Class t) throws Exception{
        //初始化AOP
        AopContainerInjection.loadAop(t);
        //初始化IOC
        ContainerInjection.loadIoc(t);
        //初始化DI
        DependencyInjection.Autowired();
        //初始化mvc
        SpringMvc.loadMvc();
        //启动Tomcat
        InsideTomcat.run();
    }

}
