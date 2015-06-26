package com.poplar.server.context;

import com.poplar.server.util.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * User: FR
 * Time: 5/18/15 2:59 PM
 */
public class Request {

    //control party
    private String method;
    private String url;
    private String protocol;


    private Header header;
    private Content content;
    private Status status;

    private Map<String, String> parameters = new HashMap<String, String>();

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getParameter(String name){
        return parameters.get(name);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }


    public void dispose() throws UnsupportedEncodingException {
        String  contentType = this.getHeader().getValue(Constants.HttpHeader.CONTENT_TYPE);
        Map<String,  String> parameters = this.getParameters();
        String url = this.getUrl();
        if(url.indexOf("?")!=-1){
            String paramSegment = url.substring(url.indexOf("?")+1);
            String[] kvSegments = paramSegment.split("&");
            for(String kvSegment : kvSegments){
                String[] kv = kvSegment.split("=");
                String value = URLDecoder.decode(kv[1], "UTF-8");
                parameters.put(kv[0],value);
            }
        }
        if(contentType!=null && contentType.contains(Constants.HttpHeader.CONTENT_TYPE_MULTIPART)){

        }else{
            String content = this.getContent().getContent();
            if(content!=null && content.length()!=0){
                String[] paramPairs = content.split("&");
                for(String paramPair : paramPairs){
                    String[] params = paramPair.split("=");
                    parameters.put(params[0],params[1]);
                }
            }
        }
        this.setParameters(parameters);
    }
}
