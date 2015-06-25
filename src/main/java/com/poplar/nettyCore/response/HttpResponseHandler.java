package com.poplar.nettyCore.response;

import com.poplar.appInterface.AbstractController;
import com.poplar.context.*;
import com.poplar.context.app.AppRequest;
import com.poplar.context.app.AppResponse;
import com.poplar.util.AppClassScanner;
import com.poplar.util.Constants;
import com.poplar.util.RouterScanner;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * User: FR
 * Time: 6/2/15 5:29 PM
 */
public class HttpResponseHandler extends SimpleChannelInboundHandler<Request> {

    public static Map<String, String> STATIC_EXTENSIONS = new HashMap<String, String>();
    static {
        STATIC_EXTENSIONS.put("js",null);
        STATIC_EXTENSIONS.put("css",null);
        STATIC_EXTENSIONS.put("jpg",null);
        STATIC_EXTENSIONS.put("gif",null);
    }
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        Response response = getResponse(request);
        HttpResponseTranslator.translate(response, channelHandlerContext);
    }

    private Response getResponse(Request request) {
        String url = request.getUrl().indexOf("?")!=-1?request.getUrl().substring(0,request.getUrl().indexOf("?")):request.getUrl();
        String extension = url.indexOf(".")!=-1?url.substring(url.indexOf(".")+1):null;
        if(extension!=null && STATIC_EXTENSIONS.containsKey(extension)){
            return getStaticResponse(request);
        }
        Response response = new Response();
        response.setProtocol(request.getProtocol());
        String classpath = RouterScanner.getClasspath(url);
        if(classpath != null){
            ParameterDisposer.dispose(request);
            AbstractController controller = AppClassScanner.getAppController(classpath);
            AppRequest appRequest = new AppRequest();
            appRequest.setUrl(request.getUrl());
            appRequest.setMethod(request.getMethod());
            appRequest.setProtocol(request.getProtocol());
            Map<String, String> headers = new HashMap<String, String>();
            headers.putAll(request.getHeader().getValues());
            appRequest.setHeaders(headers);
            Map<String, String> params = new HashMap<String, String>();
            params.putAll(request.getParameters());
            appRequest.setParams(params);
            try {
                AppResponse appResponse = controller.onAppRequest(appRequest);
                response.setStatus(Status.getStatus(appResponse.getStatus()));
                Header header = new Header();
                header.setValues(appResponse.getHeaders());
                response.setHeader(header);
                Content content = new Content();
                content.setContent(appResponse.getContent());
                response.setContent(content);
                if(response.getHeader().getValue(Constants.HttpHeader.TRANSFER_ENCODING)==null ||
                        !response.getHeader().getValue(Constants.HttpHeader.TRANSFER_ENCODING).equals(Constants.HttpHeader.TRANSFER_ENCODING_CHUNKED)){
                    response.getHeader().setValue(Constants.HttpHeader.CONTENT_LENGTH, response.getContent().getContent().getBytes().length+"");
                }
            }catch (Throwable e){
                return get500Response(request);
            }
            return response;
        }else{
            return get404Response(request);
        }
    }

    private Response getStaticResponse(Request request){
        Response response = new Response();
        response.setProtocol(request.getProtocol());
        response.setStatus(Status.OK);
        Header header = new Header();
        header.setValue("Content-Type", "text/html");
        String html = "<html> \r\n" +
                "<head> \r\n" +
                "<title>it's not show time, please wait</title> \r\n" +
                "</head> \r\n" +
                "<body> \r\n" +
                "it's not show time, please wait \r\n" +
                "</body> \r\n" +
                "</html> ";
        header.setValue("Content-Length", html.length()+"");
        response.setHeader(header);
        Content content = new Content();
        content.appendContent(html);
        response.setContent(content);
        return response;
    }

    private Response get500Response(Request request){
        Response response = new Response();
        response.setProtocol(request.getProtocol());
        response.setStatus(Status.SERVER_ERROR);
        Header header = new Header();
        header.setValue("Content-Type", "text/html");
        String html = "<html> \r\n" +
                "<head> \r\n" +
                "<title>500</title> \r\n" +
                "</head> \r\n" +
                "<body> \r\n" +
                "server error \r\n" +
                "</body> \r\n" +
                "</html> ";
        header.setValue("Content-Length", html.length()+"");
        response.setHeader(header);
        Content content = new Content();
        content.appendContent(html);
        response.setContent(content);
        return response;
    }

    private Response get404Response(Request request){
        Response response = new Response();
        response.setProtocol(request.getProtocol());
        response.setStatus(Status.NOT_FOUND);
        Header header = new Header();
        header.setValue("Content-Type", "text/html");
        String html = "<html> \r\n" +
                "<head> \r\n" +
                "<title>404</title> \r\n" +
                "</head> \r\n" +
                "<body> \r\n" +
                "url not found \r\n" +
                "</body> \r\n" +
                "</html> ";
        header.setValue("Content-Length", html.length()+"");
        response.setHeader(header);
        Content content = new Content();
        content.appendContent(html);
        response.setContent(content);
        return response;
    }
}
