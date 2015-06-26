package com.poplar.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * User: FR
 * Time: 5/29/15 3:06 PM
 */
public class ConfigLoader {
    private static Properties properties;

    public ConfigLoader() throws IOException {
        this.properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(new File(this.getClass().getResource("/").getPath()+"/config.properties"));
        properties.load(fileInputStream);
        System.out.println(properties.toString());
    }

    public static String getValueStr(String key){
        return properties.getProperty(key);
    }

    public static int getValueInt(String key){
        return Integer.parseInt(properties.getProperty(key));
    }
}
