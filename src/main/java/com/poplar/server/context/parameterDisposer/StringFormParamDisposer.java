package com.poplar.server.context.parameterDisposer;

import com.poplar.server.context.Request;
import com.poplar.server.util.Constants;

import java.util.Map;

/**
 * User: FR
 * Time: 7/2/15 3:50 PM
 */
public class StringFormParamDisposer implements Disposer {

    @Override
    public boolean canConvert(Request request) {
        String contentType = request.getHeader().getStrValue(Constants.HttpHeader.CONTENT_TYPE);
        return contentType!=null && contentType.equals(Constants.HttpHeader.CONTENT_TYPE_FORM);
    }

    @Override
    public void convert(Request request){
        if(!canConvert(request)){
            return;
        }
        Map<String,  String> parameters = request.getParameters();
        String content = request.getContent().getContent();
        if(content!=null && content.length()!=0){
            String[] paramPairs = content.split("&");
            for(String paramPair : paramPairs){
                String[] params = paramPair.split("=");
                parameters.put(params[0],params[1]);
            }
        }
    }
}
