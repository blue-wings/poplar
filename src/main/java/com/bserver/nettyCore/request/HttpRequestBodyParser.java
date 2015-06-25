package com.bserver.nettyCore.request;

import com.bserver.context.Content;
import com.bserver.context.Request;
import io.netty.buffer.ByteBuf;
import com.bserver.util.CacheIndex;
import com.bserver.util.Constants;

/**
 * User: FR
 * Time: 5/25/15 1:31 PM
 */
public class HttpRequestBodyParser {

    private int processLength=0;

    public HttpRequestHandler.ProcessState parse(Request request, ByteBuf byteBuf){
        if(CacheIndex.canClientUseLocalCache(request)){
            return HttpRequestHandler.ProcessState.COMPLETE;
        }
        Content bodyContent = request.getContent();
        if(bodyContent==null){
            bodyContent = new Content();
            request.setContent(bodyContent);
        }
        String length = request.getHeader().getValue(Constants.HttpHeader.CONTENT_LENGTH);
        if(length!=null){
            StringBuilder sb = new StringBuilder();
            int size = Math.min(byteBuf.capacity(), Integer.parseInt(length)-processLength);
            for(int i=0; i<size;i++){
                sb.append(((char) byteBuf.readByte()));
                processLength++;
            }
            bodyContent.appendContent(sb.toString());
            System.out.println("http body "+sb.toString());
            if(processLength==Integer.parseInt(length)){
                return HttpRequestHandler.ProcessState.COMPLETE;
            }
        }else {
            return HttpRequestHandler.ProcessState.COMPLETE;
        }
        return HttpRequestHandler.ProcessState.BODY;
    }

}
