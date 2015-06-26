package com.poplar.server.appExecutor;

import com.poplar.server.context.*;
import com.poplar.server.context.app.AppRequest;
import com.poplar.server.context.app.AppResponse;
import com.poplar.server.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * User: FR
 * Time: 6/26/15 9:58 AM
 */
public class Executor {

    public static Map<String, String> STATIC_EXTENSIONS = new HashMap<String, String>();

    static {
        STATIC_EXTENSIONS.put("js", null);
        STATIC_EXTENSIONS.put("css", null);
        STATIC_EXTENSIONS.put("jpg", null);
        STATIC_EXTENSIONS.put("gif", null);
    }

    public static Response execute(Request request) {
        String url = request.getUrl().indexOf("?") != -1 ? request.getUrl().substring(0, request.getUrl().indexOf("?")) : request.getUrl();
        String extension = url.indexOf(".") != -1 ? url.substring(url.indexOf(".") + 1) : null;
        if (extension != null && STATIC_EXTENSIONS.containsKey(extension)) {
            return getStaticResponse(request);
        }
        ControllerProxy controllerProxy = AppClassScanner.getMapping(url);
        if (controllerProxy != null) {
            try {
                request.dispose();
                AppRequest appRequest = assembleAppRequest(request);
                AppResponse appResponse = controllerProxy.invoke(appRequest);
                if(appResponse == null){
                    return getEmptyResponse(request);
                }
                return assembleResponse(request, appResponse);
            } catch (Throwable e) {
                return get500Response(request);
            }
        } else {
            return get404Response(request);
        }
    }

    private static Response assembleResponse(Request request, AppResponse appResponse) {
        Response response = new Response();
        response.setProtocol(request.getProtocol());
        response.setStatus(Status.getStatus(appResponse.getStatus()));
        Header header = new Header();
        header.setValues(appResponse.getHeaders());
        response.setHeader(header);
        Content content = new Content();
        content.setContent(appResponse.getContent());
        response.setContent(content);
        if (response.getHeader().getValue(Constants.HttpHeader.TRANSFER_ENCODING) == null ||
                !response.getHeader().getValue(Constants.HttpHeader.TRANSFER_ENCODING).equals(Constants.HttpHeader.TRANSFER_ENCODING_CHUNKED)) {
            response.getHeader().setValue(Constants.HttpHeader.CONTENT_LENGTH, response.getContent().getContent().getBytes().length + "");
        }
        return response;
    }

    private static AppRequest assembleAppRequest(Request request) {
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
        return appRequest;
    }

    private static Response getStaticResponse(Request request) {
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
        header.setValue("Content-Length", html.length() + "");
        response.setHeader(header);
        Content content = new Content();
        content.appendContent(html);
        response.setContent(content);
        return response;
    }

    private static Response get500Response(Request request) {
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
        header.setValue("Content-Length", html.length() + "");
        response.setHeader(header);
        Content content = new Content();
        content.appendContent(html);
        response.setContent(content);
        return response;
    }

    private static Response get404Response(Request request) {
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
        header.setValue("Content-Length", html.length() + "");
        response.setHeader(header);
        Content content = new Content();
        content.appendContent(html);
        response.setContent(content);
        return response;
    }

    private static Response getEmptyResponse(Request request) {
        Response response = new Response();
        response.setProtocol(request.getProtocol());
        response.setStatus(Status.NOT_FOUND);
        Header header = new Header();
        header.setValue("Content-Type", "text/html");
        header.setValue("Content-Length", "0");
        response.setHeader(header);
        Content content = new Content();
        content.appendContent("");
        response.setContent(content);
        return response;
    }
}
