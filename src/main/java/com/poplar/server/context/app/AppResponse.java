package com.poplar.server.context.app;

import java.util.Map;

/**
 * User: FR
 * Time: 5/18/15 3:00 PM
 */
public class AppResponse {
    private int status;
    private Map<String, String> headers;
    private String content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
