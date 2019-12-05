package meng.springframework.container;

import meng.springframework.annotation.Component;
import meng.springframework.annotation.Controller;
import meng.springframework.annotation.Service;
import meng.springframework.aop.AopHandle;
import meng.springframework.aop.proxy.ProxyFactory;
import meng.springframework.boot.SpringApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IocHandle {

    private static Logger logger = LoggerFactory.getLogger(IocHandle.class);

    private IocHandle(){}

    /**
     * 将组件实例注入IOC容器
     * @param c
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void loadIoc(Class c)
            throws IllegalAccessException,InstantiationException {
        if(c.getAnnotation(Component.class) != null || c.getAnnotation(Controller.class) != null
                || c.getAnnotation(Service.class) != null){
            SpringApplication.container.put(c.getTypeName(), iocObject(c));
            logger.info(c.getTypeName()+" add container success！");
        }
    }

    /**
     * 判断是否存在符合的切入点，符合则返回代理对象实例，否则返回自身对象实例
     * @param c
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static Object iocObject(Class c) throws IllegalAccessException, InstantiationException {
        if(AopHandle.isCut(c)){
            return new ProxyFactory(c.newInstance()).getProxyInstance();
        }else{
            return c.newInstance();
        }
    }
}
