package com.poplar.server.nettyCore.response;

import com.poplar.server.appExecutor.Executor;
import com.poplar.server.appExecutor.appInterface.AbstractController;
import com.poplar.server.context.*;
import com.poplar.server.context.app.AppRequest;
import com.poplar.server.context.app.AppResponse;
import com.poplar.server.appExecutor.AppClassScanner;
import com.poplar.server.util.Constants;
import com.poplar.server.appExecutor.RouterScanner;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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
