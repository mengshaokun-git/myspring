package meng.springframework.test;

import meng.springframework.annotation.Controller;
import meng.springframework.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/test")
    public Map test(String a){
        System.out.println("成功调用！"+a);
        Map<String,Object> retMap = new HashMap<String, Object>();
        retMap.put("code",200);
        retMap.put("msg","调用成功");
        return retMap;
    }
}
