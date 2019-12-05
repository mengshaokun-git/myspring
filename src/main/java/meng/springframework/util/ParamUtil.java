package meng.springframework.util;

import meng.springframework.annotation.param.RequestParam;
import meng.springframework.boot.SpringApplication;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class ParamUtil {

    public static RequestParam getRequestParams(Method method, Parameter parameter){
        List<RequestParam> params = SpringApplication.methodTheRequestParams.get(method.getName());
        RequestParam param = parameter.getAnnotation(RequestParam.class);
        if (param == null){
            return null;
        }
        for (RequestParam requestParam: params) {
            if (param.value().equals(requestParam.value())){
                return requestParam;
            }
        }
        return null;
    }
}
