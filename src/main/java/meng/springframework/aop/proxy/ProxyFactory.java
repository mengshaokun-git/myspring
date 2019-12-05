package meng.springframework.aop.proxy;

import meng.springframework.aop.CutPoint;
import meng.springframework.aop.JoinPoint;
import meng.springframework.boot.SpringApplication;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyFactory implements MethodInterceptor {

    //维护目标对象
    private Object target;

    public ProxyFactory(Object target){
        this.target = target;
    }

    //给目标对象创建一个代理对象
    public Object getProxyInstance(){
        //工具类
        Enhancer en = new Enhancer();
        //设置父类
        en.setSuperclass(target.getClass());
        //设置回调函数
        en.setCallback(this);
        //创建子类(代理对象)
        return en.create();
    }

    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object resultValue = null;
        for(String pointcutValue : SpringApplication.arounds.keySet()){
            // 如果符合切入点
            if(true){
                CutPoint cutPoint = SpringApplication.arounds.get(pointcutValue);
                cutPoint.setArgs(new JoinPoint(target, method, args));
                cutPoint.proceed();
            }
        }
        return resultValue;
    }
}
