package meng.springframework.util;

import meng.springframework.annotation.ComponentScan;

import java.io.IOException;
import java.net.URLDecoder;

public class ClassPathUtil {

    /**
     * 获取扫描包路径
     * @return
     */
    public static String getScanPackPath(Class t) throws IOException {
        ComponentScan componentScan =
                (ComponentScan) t.getAnnotation(ComponentScan.class);
        return URLDecoder.decode(componentScan.value(),"UTF-8");
    }

    /**
     *获取classPath路径
     * @return
     */
    public static String getClassPath(Class t) throws IOException{
        return URLDecoder.decode(t.getClassLoader().getResource("").getPath(),"UTF-8");
    }
}
