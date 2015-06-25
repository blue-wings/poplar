package com.bserver.nettyCore.request;

import com.bserver.context.Request;
import io.netty.buffer.ByteBuf;
import com.bserver.util.BufferLineReader;

/**
 * User: FR
 * Time: 5/25/15 2:01 PM
 */
public class HttpRequestControlParser {
    public HttpRequestHandler.ProcessState parse(Request request, ByteBuf byteBuf){
        String control = new BufferLineReader().readLine(byteBuf);
        if (control.length() < 3) {
            // Invalid initial line - ignore.
            control = new BufferLineReader().readLine(byteBuf);
            if(control==null || control.length()==0){
                return HttpRequestHandler.ProcessState.CONTROL;
            }
        }
        String[] msgs = control.split(" ");
        String method = msgs[0];
        String url = msgs[1];
        String protocol = msgs[2];
        request.setMethod(method);
        request.setUrl(url);
        request.setProtocol(protocol);
        System.out.println("http control msg is [method : " +method+"][url : "+url+"][protocol : "+protocol+"]" );
        return HttpRequestHandler.ProcessState.HEADER;
    }
}
