package meng.springframework.annotation.aop;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Aspect {

     String value() default "";
}
