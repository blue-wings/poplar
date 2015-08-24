package com.poplar.server.httpEngine.netty.request;

import com.poplar.server.httpObj.Request;
import com.poplar.server.util.BufferLineReader;
import com.poplar.server.util.Constants;
import io.netty.buffer.ByteBuf;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: FR
 * Time: 5/25/15 2:01 PM
 */
public class HttpRequestControlParser {
    private Log LOG = LogFactory.getLog(HttpRequestControlParser.class);

    private StringBuilder lineBuilder = new StringBuilder();

    public HttpRequestHandler.ProcessState parse(Request request, ByteBuf byteBuf){
        BufferLineReader lineReader = new BufferLineReader();
        while (true){
            boolean isWhileLine = lineReader.readLine(byteBuf);
            lineBuilder.append(lineReader.getLine());
            if(!isWhileLine){
                return HttpRequestHandler.ProcessState.CONTROL;
            }
            if (lineBuilder.length() < 3) {
                lineBuilder = new StringBuilder();
                continue;
            }
            String[] msgs = lineBuilder.toString().split(Constants.SpecialCharacter.SPACE);
            String method = msgs[0];
            String url = msgs[1];
            String protocol = msgs[2];
            request.setMethod(method);
            request.setUrl(url);
            request.setProtocol(protocol);
            if(LOG.isDebugEnabled()){
                LOG.debug("http control msg is [method : " +method+"][url : "+url+"][protocol : "+protocol+"]" );
            }
            lineBuilder = new StringBuilder();
            return HttpRequestHandler.ProcessState.HEADER;
        }
    }
}
