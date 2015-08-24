package com.poplar.server.appExecutor.web;

import com.poplar.server.appExecutor.anno.RequestMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * User: FR
 * Time: 5/26/15 3:42 PM
 */
public class Dispatcher {

    private static Log LOG = LogFactory.getLog(Dispatcher.class);

    private static Map<String, ControllerProxy> APP_MAPPING_MAP = new HashMap<String, ControllerProxy>();

    public static ControllerProxy getMapping(String url){
        return APP_MAPPING_MAP.get(url);
    }

    public static void iniMapping(Object controllerIns){
        Class c = controllerIns.getClass();
        String controllerUrlPart = c.getAnnotation(RequestMapping.class)==null?"":((RequestMapping)c.getAnnotation(RequestMapping.class)).value();
        Method[] methods = c.getMethods();
        for(Method method : methods){
            RequestMapping methodMappingAnnotation = method.getAnnotation(RequestMapping.class);
            if(methodMappingAnnotation==null){
                continue;
            }
            String methodUrlPart = methodMappingAnnotation.value();
            String mappingUrl = controllerUrlPart+methodUrlPart;
            ControllerProxy controllerProxy = new ControllerProxy();
            controllerProxy.setControllerClass(c);
            controllerProxy.setControllerMethod(method);
            controllerProxy.setControllerInstance(controllerIns);
            controllerProxy.setUrl(mappingUrl);
            APP_MAPPING_MAP.put(mappingUrl, controllerProxy);
            if(LOG.isDebugEnabled()){
                LOG.debug(controllerProxy);
            }
        }

    }

}
