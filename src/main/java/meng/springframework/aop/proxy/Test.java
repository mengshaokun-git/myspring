package meng.springframework.aop.proxy;

public class Test {

    public static void main(String[] args) throws Exception{
        User user = new User();
        User factory =  (User) new ProxyFactory(user).getProxyInstance();
        factory.play("1231231312");
    }
}
