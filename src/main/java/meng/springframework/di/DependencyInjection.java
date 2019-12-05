package meng.springframework.di;

import meng.springframework.annotation.Autowired;
import meng.springframework.boot.SpringApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class DependencyInjection {

    private static Logger logger = LoggerFactory.getLogger(DependencyInjection.class);

    /**
     * 自动注入
     */
    public static void Autowired() {
        for(Object o : SpringApplication.container.values()){
            Field[] fields = o.getClass().getDeclaredFields();
            if(fields != null && fields.length > 0){
                for(Field field : fields){
                    if(field.getAnnotation(Autowired.class) != null){
                        try {
                            field.setAccessible(true);
                            field.set(o, getBean(field.getType()));
                            logger.info(field+" Injection of success！");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        return getBean(clazz.getTypeName());
    }

    private static <T> T getBean(String containerKey) {
        return (T)SpringApplication.container.get(containerKey);
    }
}
