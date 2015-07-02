package com.poplar.server.appExecutor;

import com.poplar.server.appExecutor.anno.Controller;
import com.poplar.server.appExecutor.anno.RequestMapping;
import com.poplar.server.exception.AppClassScanException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * User: FR
 * Time: 5/26/15 3:42 PM
 */
public class AppClassScanner {

    private static Log LOG = LogFactory.getLog(AppClassScanner.class);

    private static Map<String, ControllerProxy> APP_MAPPING_MAP = new HashMap<String, ControllerProxy>();
    private static AppClassScanner CLASS_SCANNER;

    private AppClassScanner() throws IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
        URL url = this.getClass().getResource("/");
        if(url==null){
            jarScan();
        }else{
            packageScan(url);
        }
    }

    public static void scan() throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        if(CLASS_SCANNER==null){
            synchronized (AppClassScanner.class){
                if(CLASS_SCANNER==null){
                    CLASS_SCANNER = new AppClassScanner();
                }
            }
        }
    }

    private void jarScan() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        File jarFile =null;
        for(File file :  new File(".").listFiles()){
            if(file.getAbsolutePath().endsWith(".jar")){
                jarFile = file;
                break;
            }
        }
        if(jarFile==null){
            throw new AppClassScanException("no jar file exists");
        }
        LOG.debug(jarFile.getAbsolutePath());
        Enumeration<JarEntry> entryEnumeration = new JarFile(jarFile.getAbsolutePath()).entries();
        while (entryEnumeration.hasMoreElements()){
            JarEntry jarEntry = entryEnumeration.nextElement();
            if(jarEntry.getName().matches("com/poplar/app/(.*)\\.class")){
                createBean(jarEntry.getName());
            }
        }
    }

    private void packageScan(URL url) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String appBasePath = url.getPath()+"/"+ "com/poplar/app";
        String[] extensions = {"class"};
        Collection<File> files = FileUtils.listFiles(new File(appBasePath), extensions, true);
        for(File file : files){
            String filePath = file.getAbsolutePath();
            createBean(filePath);
        }
    }

    private void createBean(String filePath) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
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
                if(LOG.isDebugEnabled()){
                    LOG.debug(controllerProxy);
                }
            }
        }
    }

    public static ControllerProxy getMapping(String url){
        return APP_MAPPING_MAP.get(url);
    }

}
