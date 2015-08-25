package com.poplar.server.appExecutor.aop;

import com.poplar.server.appExecutor.anno.After;
import com.poplar.server.appExecutor.anno.Around;
import com.poplar.server.appExecutor.anno.Before;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by work on 15-8-25.
 */
public class AopChain {

    private Object targetObject;
    private List<AdvisorProxy> advisorProxyList=new ArrayList<AdvisorProxy>();

    public AopChain() {
    }

    public AopChain(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public List<AdvisorProxy> getAdvisorProxyList() {
        return advisorProxyList;
    }

    public void setAdvisorProxyList(List<AdvisorProxy> advisorProxyList) {
        this.advisorProxyList = advisorProxyList;
    }

    public void addAdvisorProxy(Object advisor, Method advisorMethod, Method targetMethod){
        AdvisorProxy advisorProxy = new AdvisorProxy(advisor, advisorMethod, targetMethod);
        advisorProxyList.add(advisorProxy);
    }

    public void executeBeforeAdvisorChain(Method targetMethod) throws InvocationTargetException, IllegalAccessException {
        for(AdvisorProxy advisorProxy : advisorProxyList){
            if(advisorProxy.getAdvisorMethod().getAnnotation(Before.class)==null && advisorProxy.getAdvisorMethod().getAnnotation(Around.class)==null){
                continue;
            }
            if(advisorProxy.getTargetMethod().getName().equals(targetMethod.getName())){
                advisorProxy.getAdvisorMethod().invoke(advisorProxy.getAdvisor());
            }
        }
    }

    public void executeAfterAdvisorChain(Method targetMethod) throws InvocationTargetException, IllegalAccessException {
        for(AdvisorProxy advisorProxy : advisorProxyList){
            if(advisorProxy.getAdvisorMethod().getAnnotation(After.class)==null && advisorProxy.getAdvisorMethod().getAnnotation(Around.class)==null){
                continue;
            }
            if(advisorProxy.getTargetMethod().getName().equals(targetMethod.getName())){
                advisorProxy.getAdvisorMethod().invoke(advisorProxy.getAdvisor());
            }
        }
    }
}
