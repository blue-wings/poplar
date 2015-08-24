package com.poplar.server.appExecutor.aop;

import com.poplar.server.appExecutor.anno.After;
import com.poplar.server.appExecutor.anno.Around;
import com.poplar.server.appExecutor.anno.Before;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by work on 15-8-21.
 */
public class AopFacade {

    private static Map<Method, String> ADVISOR_POINTS = new HashMap<Method, String>();
    private static Map<String, Object> ADVISORS = new HashMap<String, Object>();

    public static void addAdvisor(Class advisorClass) throws IllegalAccessException, InstantiationException {
        Method[] methods = advisorClass.getMethods();
        if(ADVISORS.get(advisorClass.getName())==null){
            ADVISORS.put(advisorClass.getName(), advisorClass.newInstance());
        }
        for(Method method : methods){
            if(getAnnoValue(method)!=null){
                ADVISOR_POINTS.put(method, advisorClass.getName());
            }
        }
    }

    private static String getAnnoValue(Method method) {
        Before before = method.getAnnotation(Before.class);
        if(before!=null){
            return before.position();
        }
        After after = method.getAnnotation(After.class);
        if(after!=null){
            return after.position();
        }
        Around around = method.getAnnotation(Around.class);
        if(around!=null){
            return around.position();
        }
        return null;
    }

    public static void wrap(Map<Class,Object> nameObjectMap){
        Map<String, Method> methodPotions = new HashMap<String, Method>();
        for(Map.Entry<Class, Object> entry : nameObjectMap.entrySet()){
            if(!entry.getKey().isInterface()){
                continue;
            }
            Class c = entry.getValue().getClass();
            Class[] interfaces = c.getInterfaces();
            if(interfaces==null || interfaces.length==0){
                continue;
            }
            String className = c.getName();
            Method[] methods = c.getMethods();
            Method[] interfaceMethods = interfaces[0].getMethods();
            for(Method method : methods){
                for(Method interfaceMethod : interfaceMethods){
                    if(!method.getName().equals(interfaceMethod.getName())){
                        continue;
                    }
                    String methodName = method.getName();
                    methodPotions.put(className+"."+methodName, method);
                }
            }
        }

        for(Map.Entry<String, Method> entry : methodPotions.entrySet()){
            Object target = nameObjectMap.get(entry.getValue().getDeclaringClass().getInterfaces()[0]);
            for(Map.Entry<Method, String> advisorEntry : ADVISOR_POINTS.entrySet()){
                Method method = advisorEntry.getKey();
                String position = getAnnoValue(method);
                if(position==null){
                    continue;
                }
                Pattern pattern = Pattern.compile(position);
                Matcher m = pattern.matcher(entry.getKey());
                if(m.find()){
                    Object advisor = ADVISORS.get(advisorEntry.getValue());
                    Object proxy = wrap(target, entry.getValue(), advisor, method);
                    nameObjectMap.put(entry.getValue().getDeclaringClass().getInterfaces()[0], proxy);
                }
            }
        }

    }

    private static Object wrap(Object target, Method targetMethod, Object advisor, Method advisorMethod){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(advisorMethod.getAnnotation(Before.class)!=null || advisorMethod.getAnnotation(Around.class)!=null){
                    advisorMethod.invoke(advisor);
                }
                Object result = targetMethod.invoke(target, args);
                if(advisorMethod.getAnnotation(After.class)!=null || advisorMethod.getAnnotation(Around.class)!=null){
                    advisorMethod.invoke(advisor);
                }
                return result;
            }
        });
    }

}
