package com.bserver.context.app;

import com.bserver.context.Header;
import com.bserver.context.Request;

import java.util.Map;

/**
 * User: FR
 * Time: 5/18/15 2:59 PM
 */
public class AppRequest {

    //control party
    private String method;
    private String url;
    private String protocol;

    private Map<String, String> headers;
    private Map<String, String> params;

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

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getParamValue(String param){
        return params.get(param);
    }

    public String getHeader(String headerKey){
        return headers.get(headerKey);
    }
}
