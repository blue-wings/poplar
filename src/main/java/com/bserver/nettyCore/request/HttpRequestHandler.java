package com.bserver.nettyCore.request;

import com.bserver.context.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * User: FR
 * Time: 5/18/15 3:37 PM
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private HttpRequestControlParser httpRequestControlParser = new HttpRequestControlParser();
    private HttpRequestHeaderParser httpRequestHeaderParser = new HttpRequestHeaderParser();
    private HttpRequestBodyParser httpRequestBodyParser = new HttpRequestBodyParser();

    private Request request;

    private ProcessState processState = ProcessState.CONTROL;
    public enum ProcessState{
        CONTROL,
        HEADER,
        BODY,
        COMPLETE
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        if(request == null){
            request = new Request();
        }
        if(processState == ProcessState.CONTROL){
            processState = httpRequestControlParser.parse(request, byteBuf);
        }
        if(processState == ProcessState.HEADER){
            processState = httpRequestHeaderParser.parse(request, byteBuf);
        }
        if(processState == ProcessState.BODY) {
            processState = httpRequestBodyParser.parse(request, byteBuf);
        }
        if(processState != ProcessState.COMPLETE){
            return;
        }
        channelHandlerContext.fireChannelRead(request);
        request = null;
        processState=ProcessState.CONTROL;
    }

}
