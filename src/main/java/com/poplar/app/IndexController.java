package com.poplar.app;

import com.poplar.server.appExecutor.appInterface.AbstractController;
import com.poplar.server.context.app.AppRequest;
import com.poplar.server.context.app.AppResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * User: FR
 * Time: 5/18/15 5:13 PM
 */
public class IndexController extends AbstractController {
    @Override
    public AppResponse onAppRequest(AppRequest request) {
        AppResponse response = new AppResponse();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "text/html;charset=UTF-8");
//        headers.put("Transfer-Encoding", "chunked");
        headers.put("Content-Encoding","gzip");
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
        response.setStatus(200);
        return response;
    }
}
