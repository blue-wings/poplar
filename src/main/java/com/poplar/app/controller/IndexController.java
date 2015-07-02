package com.poplar.app.controller;

import com.poplar.server.Launcher;
import com.poplar.server.appExecutor.anno.Controller;
import com.poplar.server.appExecutor.anno.RequestMapping;
import com.poplar.server.context.app.AppRequest;
import com.poplar.server.context.app.AppResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * User: FR
 * Time: 5/18/15 5:13 PM
 */
@Controller
@RequestMapping("/index")
public class IndexController  {

    @RequestMapping("/json")
    public AppResponse getJson(){
        AppResponse response = new AppResponse();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        response.setHeaders(headers);
        String json = "{\"id\":21,\"name\":\"fangrui\"}";
        response.setContent(json);
        return response;
    }

    @RequestMapping("/hello")
    public AppResponse hello(AppRequest request)  {
        AppResponse response = new AppResponse();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "text/html;charset=UTF-8");
//        headers.put("Transfer-Encoding", "chunked");
//        headers.put("Content-Encoding","gzip");
        response.setHeaders(headers);
        if(request.getParamValue("name")!=null){
            String html = "<html> \r\n" +
                    "<head> \r\n" +
                    "<title>HTTP Response Example</title> \r\n" +
                    "</head> \r\n" +
                    "<body> \r\n" +
                    "欢迎 "+request.getParamValue("name")+" \r\n" +
                    "</body> \r\n" +
                    "</html> ";
            response.setContent(html);
        }else {
            String html = "<html> \r\n" +
                    "<head> \r\n" +
                    "<title>HTTP Response Example</title> \r\n" +
                    "</head> \r\n" +
                    "<body> \r\n" +
                    "please enter your name \r\n" +
                    "</body> \r\n" +
                    "</html> ";
            response.setContent(html);
        }
        return response;
    }

}
