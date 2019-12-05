package meng.springframework.mvc;

import meng.springframework.annotation.Controller;
import meng.springframework.annotation.RequestMapping;
import meng.springframework.annotation.param.RequestParam;
import meng.springframework.boot.SpringApplication;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpringMvc {

    public static void loadMvc(){
        for (Map.Entry<String,Object> entry:SpringApplication.container.entrySet()) {
            String key = entry.getKey();
            Class c = entry.getValue().getClass();
            //排除非controller类
            if (!c.isAnnotationPresent(Controller.class)){
                continue;
            }
            String baseUrl = "";
            if (c.isAnnotationPresent(RequestMapping.class)){
                RequestMapping mapping = (RequestMapping) c.getAnnotation(RequestMapping.class);
                baseUrl = mapping.value();
            }
            Method[] methods = c.getMethods();
            for (Method method :methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)){
                    continue;
                }
                String requestUrl = method.getAnnotation(RequestMapping.class).value();
                requestUrl = (baseUrl +"/"+requestUrl).replaceAll("/+","/");

                SpringApplication.handlerMapping.put(requestUrl,method);
                SpringApplication.controllerMapping.put(requestUrl,key);

                //获取param
                //获取参数
                Parameter[] parameters = method.getParameters();
                List<RequestParam> params = new ArrayList<>();
                for (Parameter parameter:parameters) {
                    if (parameter.isAnnotationPresent(RequestParam.class)){
                        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                        params.add(requestParam);
                    }
                }
                SpringApplication.methodTheRequestParams.put(method.getName(),params);

            }
        }
    }
}
