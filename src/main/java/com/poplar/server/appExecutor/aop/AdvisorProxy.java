package com.poplar.server.appExecutor.aop;

import java.lang.reflect.Method;

/**
 * Created by work on 15-8-25.
 */
public class AdvisorProxy {

    private Object advisor;
    private Method advisorMethod;
    private Method targetMethod;

    public AdvisorProxy() {
    }

    public AdvisorProxy(Object advisor, Method advisorMethod, Method targetMethod) {
        this.advisor = advisor;
        this.advisorMethod = advisorMethod;
        this.targetMethod = targetMethod;
    }

    public Object getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Object advisor) {
        this.advisor = advisor;
    }

    public Method getAdvisorMethod() {
        return advisorMethod;
    }

    public void setAdvisorMethod(Method advisorMethod) {
        this.advisorMethod = advisorMethod;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

}
