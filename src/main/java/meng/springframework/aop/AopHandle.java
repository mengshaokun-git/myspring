package meng.springframework.aop;

import meng.springframework.annotation.aop.Around;
import meng.springframework.annotation.aop.Aspect;
import meng.springframework.boot.SpringApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class AopHandle {

    private static Logger logger = LoggerFactory.getLogger(AopHandle.class);


    public static void lodAop(Class c) throws Exception{
        loadAround(c);
    }

    /**
     * 加载切入点，组装切入点与切入方法之间的对应关系
     * @param c
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static void loadAround(Class c) throws IllegalAccessException, InstantiationException {
        if(c.getAnnotation(Aspect.class) != null){
            Method[] methods = c.getDeclaredMethods();
            for(Method method : methods){
                if(method.getAnnotation(Around.class) != null){
                    Around around = method.getAnnotation(Around.class);
                    SpringApplication.arounds.put(around.value(),
                            new CutPoint(c.newInstance(), method));
                    logger.info(c.getTypeName()+" Loading Around successful!");
                }
            }
        }
    }

    /**
     * 加载符合切入点的对象，生成代理对象并装载至IOC容器
     * @param c
     */
    public static boolean isCut(Class c) {
        for(String pointcutValue : SpringApplication.arounds.keySet()){
            //如果匹配到符合的切入点，则为该类生成代理对象（之后注入该对象则会为其注入代理对象）
            if(c.getTypeName().equals(pointcutValue)){
                return true;
            }
        }
        return false;
    }
}
