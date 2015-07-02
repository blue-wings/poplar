package com.poplar.server.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: FR
 * Time: 5/29/15 3:06 PM
 */
public class ConfigLoader {
    private static Log LOG = LogFactory.getLog(ConfigLoader.class);

    private static Properties PROPERTIES;
    private static ConfigLoader INSTANCE;

    private ConfigLoader() throws IOException {
        this.PROPERTIES = new Properties();
        InputStream InputStream = this.getClass().getResourceAsStream("/poplar.properties");
        PROPERTIES.load(InputStream);
        if(LOG.isDebugEnabled()){
            LOG.debug(PROPERTIES.toString());
        }
    }

    public static void loadProperties() throws IOException {
        if(INSTANCE==null){
            synchronized(ConfigLoader.class){
                if(INSTANCE==null){
                    INSTANCE = new ConfigLoader();
                }
            }
        }
    }

    public static String getStrValue(String key){
        return PROPERTIES.getProperty(key);
    }

    public static int getIntValue(String key){
        return Integer.parseInt(PROPERTIES.getProperty(key));
    }

    public static long getLongValue(String key){
        return Long.parseLong(PROPERTIES.getProperty(key));
    }
}
