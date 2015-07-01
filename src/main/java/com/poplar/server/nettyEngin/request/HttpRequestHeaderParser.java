package com.poplar.server.nettyEngin.request;

import com.poplar.server.context.Header;
import com.poplar.server.context.Request;
import com.poplar.server.util.BufferLineReader;
import io.netty.buffer.ByteBuf;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * User: FR
 * Time: 5/25/15 1:13 PM
 */
public class HttpRequestHeaderParser {

    private static Log LOG = LogFactory.getLog(HttpRequestHeaderParser.class);

    private StringBuilder lineBuilder = new StringBuilder();

    public HttpRequestHandler.ProcessState parse(Request request, ByteBuf byteBuf){
        BufferLineReader lineReader = new BufferLineReader();
        Header header = request.getHeader();
        if(header==null){
            header = new Header();
            request.setHeader(header);
        }
        Map<String, String> headers = header.getValues();
        while (true){
            boolean isWhileLine = lineReader.readLine(byteBuf);
            String line = lineReader.getLine();
            lineBuilder.append(line);
            if(!isWhileLine){
                return HttpRequestHandler.ProcessState.HEADER;
            }
            line = lineBuilder.toString();
            if(line.length()==0){
                lineBuilder = new StringBuilder();
                return HttpRequestHandler.ProcessState.BODY;
            }
            String[] msgs = line.toString().split(": ");
            headers.put(msgs[0].trim(), msgs[1].trim());
            lineBuilder = new StringBuilder();
            if(LOG.isDebugEnabled()){
                LOG.debug("http header "+msgs[0]+" - "+msgs[1]);
            }
        }
    }

}
