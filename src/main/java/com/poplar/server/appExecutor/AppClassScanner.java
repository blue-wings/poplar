package com.poplar.server.appExecutor;

import com.poplar.server.appExecutor.appInterface.AbstractController;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * User: FR
 * Time: 5/26/15 3:42 PM
 */
public class AppClassScanner {

    private static Map<String, Object> APP_CLASS_INSTANCE_MAP = new HashMap<String, Object>();

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
             if(AbstractController.class.isAssignableFrom(c)){
                 APP_CLASS_INSTANCE_MAP.put(classpath, c.newInstance());
             }
         }
    }

    public static AbstractController getAppController(String classpath){
        return (AbstractController) APP_CLASS_INSTANCE_MAP.get(classpath);
    }

}
