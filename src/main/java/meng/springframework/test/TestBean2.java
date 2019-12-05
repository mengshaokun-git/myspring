package meng.springframework.test;

import meng.springframework.annotation.Autowired;
import meng.springframework.annotation.Component;

@Component
public class TestBean2 {

    @Autowired
    private TestBean1 testBean1;

    public void play(){
        testBean1.play();
    }
}
