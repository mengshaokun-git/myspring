package meng.springframework.annotation.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Around {

    /**
     * 在哪里绑定通知的切入点表达式
     * @return
     */
    String value();
}
