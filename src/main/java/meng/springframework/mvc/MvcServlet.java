package meng.springframework.mvc;


import com.alibaba.fastjson.JSON;
import meng.springframework.annotation.param.RequestParam;
import meng.springframework.boot.SpringApplication;
import meng.springframework.util.IsObjectNullUtils;
import meng.springframework.util.ParamUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class MvcServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("执行了init方法");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            //处理请求
            doDispatch(req,resp);
        }catch (Exception e){
            e.printStackTrace();
            resp.getWriter().write("500! Server Exception");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }


    private void doDispatch(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String url = request.getServletPath();
        Method method = SpringApplication.handlerMapping.get(url);
        if (method == null){
            response.getWriter().write("404 NOT FOUND!");
            return;
        }

        //获取参数
        Parameter[] parameters = method.getParameters();
        //参数注入
        Object[] paramValues = new Object[parameters.length];
        for (int i=0;i<parameters.length;i++){
            Parameter parameter = parameters[i];
            RequestParam param = ParamUtil.getRequestParams(method,parameter);
            String requestParam = parameter.getType().getSimpleName();
            //根据参数名称 去做某些处理
            if (requestParam.equals("HttpServletRequest")){
                paramValues[i] = request;
                continue;
            }
            if (requestParam.equals("HttpServletResponse")){
                paramValues[i] = response;
            }
            if (requestParam.equals("String")){
                String[] values = null;
                String value = null;
                if (param != null){
                    values = request.getParameterMap().get(param.value());
                    if (values == null){
                        if (!param.required()){
                            //非必传 赋值默认值
                            if (!IsObjectNullUtils.is(param.defaultValue())){
                                value = param.defaultValue();
                            }
                        }else {
                            response.setContentType("text/html;charset=UTF-8");
                            response.getWriter().write("缺少所需参数 "+ param.value());
                            return;
                        }
                    }else {
                        value = Arrays.toString(values).replaceAll("\\[|\\]","").replaceAll(".\\s",",");
                    }
                }else {
                    values = request.getParameterMap().get(parameter.getName());//请求中携带的参数值
                    value = Arrays.toString(values).replaceAll("\\[|\\]","").replaceAll(".\\s",",");
                }
                paramValues[i] = value;
            }
        }
        //利用反射机制调用controller中的方法
        Object controller = SpringApplication.container.get(SpringApplication.controllerMapping.get(url));
        Object o = method.invoke(controller,paramValues);
//        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(o));
    }

}
