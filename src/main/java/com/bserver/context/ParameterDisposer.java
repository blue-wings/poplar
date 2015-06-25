package com.bserver.context;

import com.bserver.util.Constants;

import java.util.Map;

/**
 * User: FR
 * Time: 5/25/15 5:44 PM
 */
public class ParameterDisposer {

    public static void dispose(Request request){
        String  contentType = request.getHeader().getValue(Constants.HttpHeader.CONTENT_TYPE);
        Map<String,  String> parameters = request.getParameters();
        String url = request.getUrl();
        if(url.indexOf("?")!=-1){
            String paramSegment = url.substring(url.indexOf("?")+1);
            String[] kvSegments = paramSegment.split("&");
            for(String kvSegment : kvSegments){
                String[] kv = kvSegment.split("=");
                parameters.put(kv[0],kv[1]);
            }
        }
        if(contentType!=null && contentType.contains(Constants.HttpHeader.CONTENT_TYPE_MULTIPART)){

        }else{
            String content = request.getContent().getContent();
            if(content!=null && content.length()!=0){
                String[] paramPairs = content.split("&");
                for(String paramPair : paramPairs){
                    String[] params = paramPair.split("=");
                    parameters.put(params[0],params[1]);
                }
            }
        }
        request.setParameters(parameters);
    }
}
