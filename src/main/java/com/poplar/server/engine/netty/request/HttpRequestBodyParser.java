package com.poplar.server.engine.netty.request;

import com.poplar.server.context.Content;
import com.poplar.server.context.Request;
import com.poplar.server.util.Constants;
import io.netty.buffer.ByteBuf;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.charset.Charset;

/**
 * User: FR
 * Time: 5/25/15 1:31 PM
 */
public class HttpRequestBodyParser {

    private static Log LOG = LogFactory.getLog(HttpRequestBodyParser.class);

    private int processLength=0;
    private byte[] contentBytes;

    public HttpRequestHandler.ProcessState parse(Request request, ByteBuf byteBuf){
        Content bodyContent = request.getContent();
        if(bodyContent==null){
            bodyContent = new Content();
            request.setContent(bodyContent);
        }
        Integer length = request.getHeader().getIntValue(Constants.HttpHeader.CONTENT_LENGTH);
        if(length!=null){
            if(contentBytes==null){
                contentBytes = new byte[length];
            }
            int size = Math.min(byteBuf.capacity(), length-processLength);
            for(int i=0; i<size;i++){
                contentBytes[processLength]=byteBuf.readByte();
                processLength++;
            }
            if(processLength==length){
                String charsetName = request.getHeader().getStrValue(Constants.HttpHeader.CHARSET)==null? Constants.SpecialCharacter.UTF_8 :request.getHeader().getStrValue(Constants.HttpHeader.CHARSET);
                String content = new String(contentBytes, Charset.forName(charsetName));
                bodyContent.setContent(content);
                contentBytes=null;
                processLength=0;
                return HttpRequestHandler.ProcessState.COMPLETE;
            }
        }else {
            contentBytes=null;
            processLength=0;
            return HttpRequestHandler.ProcessState.COMPLETE;
        }
        return HttpRequestHandler.ProcessState.BODY;
    }

}
