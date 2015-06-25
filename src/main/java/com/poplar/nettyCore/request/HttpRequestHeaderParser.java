package com.poplar.nettyCore.request;

import com.poplar.context.Header;
import com.poplar.context.Request;
import io.netty.buffer.ByteBuf;
import com.poplar.util.BufferLineReader;

import java.util.Map;

/**
 * User: FR
 * Time: 5/25/15 1:13 PM
 */
public class HttpRequestHeaderParser {

    public HttpRequestHandler.ProcessState parse(Request request, ByteBuf byteBuf){
        BufferLineReader lineReader = new BufferLineReader();
        Header header = request.getHeader();
        if(header==null){
            header = new Header();
            request.setHeader(header);
        }
        Map<String, String> headers = header.getValues();
        String line = lineReader.readLine(byteBuf);
        while (line!=null && line.length()>0){
            String[] msgs = line.split(": ");
            headers.put(msgs[0].trim(), msgs[1].trim());
            System.out.println("http header "+msgs[0]+" - "+msgs[1]);
            line = lineReader.readLine(byteBuf);
        }
        if(line==null || line.length()==0){
            return HttpRequestHandler.ProcessState.BODY;
        }
        return HttpRequestHandler.ProcessState.HEADER;
    }

}
