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
        Class[] paramTypes = controllerMethod.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for(int i=0; i<paramTypes.length; i++){
            if(paramTypes[i].isAssignableFrom(AppRequest.class)){
                params[i]=appRequest;
            }
        }
        Class responseType = controllerMethod.getReturnType();
        if(responseType.isAssignableFrom(AppResponse.class)){
            return (AppResponse)controllerMethod.invoke(controllerInstance, params);
        }
        controllerMethod.invoke(controllerInstance, params);
        return null;
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

    @Override
    public String toString() {
        return "ControllerProxy{" +
                "url='" + url + '\'' +
                ", controllerClass=" + controllerClass.getName() +
                ", controllerMethod=" + controllerMethod.getName() +
                '}';
    }
}
