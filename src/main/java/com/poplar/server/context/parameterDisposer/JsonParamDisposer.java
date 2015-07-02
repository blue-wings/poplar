package com.poplar.server.context.parameterDisposer;

import com.poplar.server.context.Request;
import com.poplar.server.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.Map;

/**
 * User: FR
 * Time: 7/2/15 3:50 PM
 */
public class JsonParamDisposer implements Disposer {

    @Override
    public boolean canConvert(Request request) {
        String contentType = request.getHeader().getStrValue(Constants.HttpHeader.CONTENT_TYPE);
        return contentType!=null && contentType.equals(Constants.HttpHeader.CONTENT_TYPE_JSON);
    }

    @Override
    public void convert(Request request) throws IOException {
        if(!canConvert(request)){
            return;
        }
        Map<String,  String> parameters = request.getParameters();
        String content = request.getContent().getContent();
        if(StringUtils.isEmpty(content)){
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> params = objectMapper.readValue(content, new TypeReference<Map<String, String>>() {});
        parameters.putAll(params);
    }
}
