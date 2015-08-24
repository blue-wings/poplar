package com.poplar.server.appExecutor.beans;

import com.poplar.server.appExecutor.anno.Aop;
import com.poplar.server.appExecutor.anno.Controller;
import com.poplar.server.appExecutor.anno.Service;
import com.poplar.server.appExecutor.aop.AopFacade;
import com.poplar.server.appExecutor.ioc.IocFacade;
import com.poplar.server.appExecutor.web.Dispatcher;
import com.poplar.server.exception.AppClassScanException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by work on 15-8-20.
 */
public class AnnoBeanContext implements BeanContext {

    private static Log LOG = LogFactory.getLog(AnnoBeanContext.class);

    private ConcurrentHashMap<Class, Object> beanNameMap = new ConcurrentHashMap<Class, Object>();

    public AnnoBeanContext() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
        loadBeans();
    }

    public void loadBeans() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        URL url = this.getClass().getResource("/");
        if(url==null){
            jarScan();
        }else{
            packageScan(url);
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
        AopFacade.wrap(beanNameMap);
        IocFacade.iniDependencies(beanNameMap);
    }

    private void createBean(String filePath) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String classpath = filePath.substring(filePath.indexOf("/com/poplar/app/") + 1, filePath.lastIndexOf("."));
        classpath = classpath.replaceAll("/",".");
        Class c = Class.forName(classpath);
        Annotation annotation = c.getAnnotation(Service.class);
        boolean isController = false;
        if(annotation==null){
            isController = c.getAnnotation(Controller.class)==null?false:true;
            annotation = isController?c.getAnnotation(Controller.class):null;
        }
        if(annotation!=null){
            Object object = c.newInstance();
            beanNameMap.put(object.getClass(), object);
            Class[] interfaces = c.getInterfaces();
            for(Class inter : interfaces){
                beanNameMap.put(inter, object);
            }
            if(isController){
                Dispatcher.iniMapping(object);
            }
        }else if(c.getAnnotation(Aop.class)!=null){
            AopFacade.addAdvisor(c);
        }
    }

    public Object getByName(String name) {
        return null;
    }

    public void removeByName(String name) {

    }

    public void clear() {

    }
}
