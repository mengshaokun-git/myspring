package meng.springframework.container;

import meng.springframework.util.ClassPathUtil;

import java.io.File;
import java.io.IOException;


public class ContainerInjection {

    private static String scanPackPath;
    private static String classPath;

    /**
     * 扫描需要注入到容器的类
     * @param f
     * @throws ClassNotFoundException
     */
    private static void scanClass(File f) throws Exception {
        if(f.isDirectory()){
            // 如果是文件夹则进入下一层路径扫描
            File[] files = f.listFiles();
            if(files != null && files.length > 0){
                for(File file : files){
                    scanClass(file);
                }
            }
        }else if(f.isFile()){
            // 如果是文件则判断是否带有特定注解，从而注入到IOC容器中
            String className = f.getPath().replace(classPath,"")
                    .replaceAll("/",".")
                    .replace(".class","");
            Class c = Class.forName(className);
            //加载普通对象
            IocHandle.loadIoc(c);
        }
    }



    //初始化IOC
    public static void loadIoc(Class c) throws IOException {
        scanPackPath = ClassPathUtil.getScanPackPath(c);
        classPath = ClassPathUtil.getClassPath(c);
        // 获取扫描包的URL 由于苹果与Windows的文件目录结构不同 所以目前只支持Mac - -
        String scanPackPathUrl = classPath +
                scanPackPath.replaceAll("\\.","/");
        // 扫描包文件夹
        File f = new File(scanPackPathUrl);
        try{
            scanClass(f);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
