package meng.springframework.test;

import meng.springframework.annotation.ComponentScan;
import meng.springframework.boot.SpringApplication;
import meng.springframework.di.DependencyInjection;

@ComponentScan("meng.springframework.test")
public class TestMain {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(TestMain.class);
        TestBean2 bean2 = DependencyInjection.getBean(TestBean2.class);
        bean2.play();
    }
}
