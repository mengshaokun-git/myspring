package meng.springframework.aop;

import meng.springframework.container.SpringObject;

import java.lang.reflect.Method;

/**
 * 被切入对象
 * 即对象本身方法对象
 */
public class JoinPoint extends SpringObject {

    public JoinPoint(Object object, Method method, Object[] args){
        this.setObject(object);
        this.setMethod(method);
        this.setArgs(args);
    }

}
