package meng.springframework.container;

import java.lang.reflect.Method;

public class SpringObject {

    private Object object;

    private Method method;

    private Object[] args;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object... args) {
        this.args = args;
    }

    /**
     * 执行业务方法
     * @return
     * @throws Exception
     */
    public Object proceed() throws Exception {
        return this.getMethod().invoke(this.getObject(), this.getArgs());
    }

}
