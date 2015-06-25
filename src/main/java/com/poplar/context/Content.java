package com.poplar.context;

/**
 * User: FR
 * Time: 5/18/15 5:16 PM
 */
public class Content {

    private String content;

    public String getContent() {
        return content;
    }

    public void appendContent(String content) {
        if(this.content == null){
            this.content = content;
        }else {
            this.content += content;
        }
    }

    public void setContent(String content) {
        this.content = content;
    }
}
