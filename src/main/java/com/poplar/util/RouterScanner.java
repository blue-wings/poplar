package com.poplar.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: FR
 * Time: 5/26/15 4:32 PM
 */
public class RouterScanner {

    private static Map<String, String> URL_CLASSPATH_MAP = new HashMap<String, String>();

    public RouterScanner() throws DocumentException {
        this.scan();
    }

    public void scan() throws DocumentException {
        String routerPath = this.getClass().getResource("/").getPath()+"/router.xml";
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(routerPath));
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for(Element element : elementList){
            URL_CLASSPATH_MAP.put(element.attributeValue("url"), element.attributeValue("classpath"));
        }
    }

    public static String getClasspath(String url){
        return URL_CLASSPATH_MAP.get(url);
    }

}
