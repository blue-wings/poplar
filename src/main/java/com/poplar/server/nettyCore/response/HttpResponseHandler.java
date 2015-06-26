package com.poplar.server.nettyCore.response;

import com.poplar.server.appExecutor.Executor;
import com.poplar.server.context.Request;
import com.poplar.server.context.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * User: FR
 * Time: 6/2/15 5:29 PM
 */
public class HttpResponseHandler extends SimpleChannelInboundHandler<Request> {


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        Response response = Executor.execute(request);
        HttpResponseTranslator.translate(response, channelHandlerContext);
    }

}
