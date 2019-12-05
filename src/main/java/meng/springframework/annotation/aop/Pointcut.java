package meng.springframework.annotation.aop;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pointcut {

    /**
     * 切入点表达式
     * 允许表达式值为空(为空的情况下，表示没有切入点)
     * @return
     */
    String value() default "";
}
