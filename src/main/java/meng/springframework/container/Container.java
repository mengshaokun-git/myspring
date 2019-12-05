package meng.springframework.container;

import java.util.HashMap;

public class Container extends HashMap<String,Object> {

    private Container(){}

    public static Container INSTANTIATION = new Container();
}
