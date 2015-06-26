package com.poplar.server.appExecutor;

import com.poplar.server.context.app.AppRequest;
import com.poplar.server.context.app.AppResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
* User: FR
* Time: 6/26/15 2:24 PM
*/
public class ControllerProxy {
    private String url;
    private Class controllerClass;
    private Method controllerMethod;
    private Object controllerInstance;

    public AppResponse invoke(AppRequest appRequest) throws InvocationTargetException, IllegalAccessException {
        Object[] params = new Object[1];
        params[0]=appRequest;
        return (AppResponse)controllerMethod.invoke(controllerInstance, params);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }

    public void setControllerMethod(Method controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    public Object getControllerInstance() {
        return controllerInstance;
    }

    public void setControllerInstance(Object controllerInstance) {
        this.controllerInstance = controllerInstance;
    }
}
