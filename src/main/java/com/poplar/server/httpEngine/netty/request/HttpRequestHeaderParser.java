package com.poplar.server.httpEngine.netty.request;

import com.poplar.server.httpObj.Header;
import com.poplar.server.httpObj.Request;
import com.poplar.server.util.BufferLineReader;
import com.poplar.server.util.Constants;
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
            String key = msgs[0];
            String value= msgs[1];
            if(key.equals(Constants.HttpHeader.CONTENT_TYPE)){
                String[] values = value.split(";");
                headers.put(key.trim(), values[0].trim());
                if(values.length==2){
                    String[] charsets = values[1].split("=");
                    key=charsets[0];
                    value=charsets[1];
                }
            }
            headers.put(key.trim(), value.trim());
            lineBuilder = new StringBuilder();
            if(LOG.isDebugEnabled()){
                LOG.debug("http header "+msgs[0]+" - "+msgs[1]);
            }
        }
    }

}
