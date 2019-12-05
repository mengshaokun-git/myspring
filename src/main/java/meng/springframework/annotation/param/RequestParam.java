package meng.springframework.annotation.param;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {


    /**
     * 参数名
     * @return
     */
    String value() default "";


    /**
     * 参数名
     * @return
     */
    String name() default "";

    /**
     * 是否必传
     * @return
     */
    boolean required() default true;

    /**
     * 默认值
     * @return
     */
    String defaultValue() default "";
}
