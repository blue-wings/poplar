package com.poplar.server.appExecutor;

import com.poplar.server.appExecutor.anno.Controller;
import com.poplar.server.appExecutor.anno.RequestMapping;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: FR
 * Time: 5/26/15 3:42 PM
 */
public class AppClassScanner {

    private static Map<String, ControllerProxy> APP_MAPPING_MAP = new HashMap<String, ControllerProxy>();

    public AppClassScanner() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        this.scan();
    }

    public void scan() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        URL url = this.getClass().getResource("/");
        String appBasePath = url.getPath()+"/"+ "com/poplar/app";
        String[] extensions = {"class"};
        Collection<File> files = FileUtils.listFiles(new File(appBasePath), extensions, true);
         for(File file : files){
             String filePath = file.getAbsolutePath();
             String classpath = filePath.substring(filePath.indexOf("/com/poplar/app/") + 1, filePath.lastIndexOf("."));
             classpath = classpath.replaceAll("/",".");
             Class c = Class.forName(classpath);
             Annotation annotation = c.getAnnotation(Controller.class);
             Object controllerInstance = c.newInstance();
             if(annotation != null){
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
                     controllerProxy.setControllerInstance(controllerInstance);
                     controllerProxy.setUrl(mappingUrl);
                     APP_MAPPING_MAP.put(mappingUrl, controllerProxy);
                 }
             }
         }

    }

    public static ControllerProxy getMapping(String url){
        return APP_MAPPING_MAP.get(url);
    }

}
