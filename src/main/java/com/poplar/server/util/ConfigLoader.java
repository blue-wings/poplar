package com.poplar.server.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * User: FR
 * Time: 5/29/15 3:06 PM
 */
public class ConfigLoader {
    private static Log LOG = LogFactory.getLog(ConfigLoader.class);

    private static Properties properties;

    public ConfigLoader() throws IOException {
        this.properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(new File(this.getClass().getResource("/").getPath()+"/poplarConfig.properties"));
        properties.load(fileInputStream);
        if(LOG.isDebugEnabled()){
            LOG.debug(properties.toString());
        }
    }

    public static String getStrValue(String key){
        return properties.getProperty(key);
    }

    public static int getIntValue(String key){
        return Integer.parseInt(properties.getProperty(key));
    }

    public static long getLongValue(String key){
        return Long.parseLong(properties.getProperty(key));
    }
}
