package com.poplar.server.context.parameterConverter;

import com.poplar.server.context.Request;
import com.poplar.server.util.Constants;

import java.net.URLDecoder;
import java.util.Map;

/**
 * User: FR
 * Time: 7/2/15 3:49 PM
 */
public class UrlParamDisposer implements Disposer {

    @Override
    public boolean canConvert(Request request) {
        return true;
    }

    @Override
    public void convert(Request request) throws Exception {
        if(!canConvert(request)){
            return;
        }
        Map<String,  String> parameters = request.getParameters();
        String charsetName = request.getHeader().getStrValue(Constants.HttpHeader.CHARSET)==null? Constants.SpecialCharacter.UTF_8 :request.getHeader().getStrValue(Constants.HttpHeader.CHARSET);
        String url = request.getUrl();
        if(url.indexOf("?")!=-1){
            String paramSegment = url.substring(url.indexOf("?")+1);
            String[] kvSegments = paramSegment.split("&");
            for(String kvSegment : kvSegments){
                String[] kv = kvSegment.split("=");
                String value = URLDecoder.decode(kv[1], charsetName);
                parameters.put(kv[0],value);
            }
        }
    }
}
