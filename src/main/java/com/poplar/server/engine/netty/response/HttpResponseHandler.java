package com.poplar.server.engine.netty.response;

import com.poplar.server.appExecutor.Executor;
import com.poplar.server.context.Request;
import com.poplar.server.context.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: FR
 * Time: 6/2/15 5:29 PM
 */
public class HttpResponseHandler extends SimpleChannelInboundHandler<Request> {

    private static Log LOG = LogFactory.getLog(HttpResponseHandler.class);

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        Response response = Executor.execute(request);
        HttpResponseTranslator.translate(response, channelHandlerContext);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("server error", cause);
    }

}
