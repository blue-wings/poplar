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
public class Config {
    private Log LOG = LogFactory.getLog(Config.class);

    private Properties properties;

    public Config() throws IOException {
        this.properties = new Properties();
        InputStream InputStream = this.getClass().getResourceAsStream("/poplar.properties");
        properties.load(InputStream);
        if(LOG.isDebugEnabled()){
            LOG.debug(properties.toString());
        }
    }

    public String getStrValue(String key){
        return properties.getProperty(key);
    }

    public int getIntValue(String key){
        return Integer.parseInt(properties.getProperty(key));
    }

    public long getLongValue(String key){
        return Long.parseLong(properties.getProperty(key));
    }
}
