package meng.springframework.aop;

import meng.springframework.container.SpringObject;

import java.lang.reflect.Method;

/**
 * 切入点对应的切入对象
 * 即带有@Aspect注解类下带有切入点定义的类方法对象
 * 其中参数在代理中动态设置JoinPoint（被切入对象）
 */
public class CutPoint extends SpringObject {

    public CutPoint(Object object, Method method){
        this.setObject(object);
        this.setMethod(method);
    }

}