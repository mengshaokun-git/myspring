package meng.springframework.test;

import meng.springframework.annotation.Component;
import meng.springframework.annotation.aop.Around;
import meng.springframework.aop.JoinPoint;

@meng.springframework.annotation.aop.Aspect
@Component
public class Aspect {

    @Around("meng.springframework.test.TestBean1")
    public void test(JoinPoint point) throws Exception{
        System.out.println("执行之前");
        point.proceed();
        System.out.println("执行之后");
    }
}
