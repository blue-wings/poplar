package com.bserver.context;

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
}
