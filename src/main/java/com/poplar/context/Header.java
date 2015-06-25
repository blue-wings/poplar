package com.poplar.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: FR
 * Time: 5/18/15 5:16 PM
 */
public class Header {

    private Map<String, String> values = new ConcurrentHashMap<String, String>();

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public void setValue(String headerKey, String value){
        values.put(headerKey, value);
    }

    public String getValue(String headerKey){
        return values.get(headerKey);
    }
}
