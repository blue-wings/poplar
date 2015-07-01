package com.poplar.server.engine.netty.request;

import com.poplar.server.context.Content;
import com.poplar.server.context.Request;
import io.netty.buffer.ByteBuf;
import com.poplar.server.util.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: FR
 * Time: 5/25/15 1:31 PM
 */
public class HttpRequestBodyParser {

    private static Log LOG = LogFactory.getLog(HttpRequestBodyParser.class);

    private int processLength=0;

    public HttpRequestHandler.ProcessState parse(Request request, ByteBuf byteBuf){
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
            if(LOG.isDebugEnabled()){
                LOG.debug("http body "+sb.toString());
            }
            if(processLength==Integer.parseInt(length)){
                return HttpRequestHandler.ProcessState.COMPLETE;
            }
        }else {
            return HttpRequestHandler.ProcessState.COMPLETE;
        }
        return HttpRequestHandler.ProcessState.BODY;
    }

}
