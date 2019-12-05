package meng.springframework.annotation;

import java.lang.annotation.*;

/**
 * @Target 作用域
 * @Retention 生命周期
 * @Inherited 允许子类继承
 * @Documented 生成javadoc时会包含注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
}