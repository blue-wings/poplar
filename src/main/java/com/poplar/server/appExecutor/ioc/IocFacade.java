package com.poplar.server.appExecutor.ioc;

import com.poplar.server.appExecutor.anno.Autowired;
import com.poplar.server.appExecutor.executor.AppExecutor;
import com.poplar.server.exception.IOCCircleDependencyException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by work on 15-8-21.
 */
public class IocFacade {

    private static Map<Class, Object> isCreating = new HashMap<Class, Object>();
    private static Map<Class, Object> complete = new HashMap<Class, Object>();

    public static void iniDependencies(Map<Class,Object> nameObjectMap) throws IllegalAccessException {
        for(Map.Entry<Class, Object> entry : nameObjectMap.entrySet()){
            if(complete.containsKey(entry.getKey())){
                continue;
            }
            iniDependency("", entry.getValue(), nameObjectMap);
        }
    }

    private static void iniDependency(String nameList, Object obj, Map<Class,Object> nameObjectMap) throws IllegalAccessException {
        Class c = obj.getClass();
        nameList+=","+c.getName();
        if(isCreating.containsKey(c)){
            throw new IOCCircleDependencyException("circle dependency between "+nameList);
        }
        isCreating.put(c, obj);
        Field[] fields = c.getDeclaredFields();
        for(Field field : fields){
            Annotation annotation = field.getAnnotation(Autowired.class);
            if(annotation!=null){
                Object injectBean = nameObjectMap.get(field.getType());
                iniDependency(nameList, injectBean, nameObjectMap);
                field.setAccessible(true);
                field.set(obj, injectBean);
            }
        }
        isCreating.remove(c);
    }

}
