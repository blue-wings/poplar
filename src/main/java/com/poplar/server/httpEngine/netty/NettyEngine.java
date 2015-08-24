package com.poplar.server.httpEngine.netty;

import com.poplar.server.ServerContext;
import com.poplar.server.httpEngine.Engine;
import com.poplar.server.httpEngine.netty.monotor.TimeMonitor;
import com.poplar.server.httpEngine.netty.request.HttpRequestHandler;
import com.poplar.server.httpEngine.netty.response.HttpResponseHandler;
import com.poplar.server.util.Config;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * User: FR
 * Time: 5/18/15 3:08 PM
 */
public class NettyEngine implements Engine{

    private static Log LOG = LogFactory.getLog(NettyEngine.class);
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @Override
    public void start(){
        Config config = ServerContext.getConfig();
        final Long readTimeout = config.getStrValue("net.read.timeout")==null?null: config.getLongValue("net.read.timeout");
        final Long writeTimeout = config.getStrValue("net.write.timeout")==null?null: config.getLongValue("net.write.timeout");
        String selectorThreadNum = config.getStrValue("selector.thread.num");
        String workerThreadNum = config.getStrValue("worker.thread.num");
        bossGroup = selectorThreadNum==null?new NioEventLoopGroup():new NioEventLoopGroup(Integer.parseInt(selectorThreadNum));
        workerGroup = workerThreadNum==null?new NioEventLoopGroup():new NioEventLoopGroup(Integer.parseInt(workerThreadNum));
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast("http-timeMonitor", new TimeMonitor(readTimeout, writeTimeout));
                socketChannel.pipeline().addLast("http-request-handler", new HttpRequestHandler());
                socketChannel.pipeline().addLast("http-response-handler", new HttpResponseHandler());
            }
        });
        bootstrap.option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        try {
            String host = config.getStrValue("host");
            int port = config.getIntValue("port");
            ChannelFuture future = bootstrap.bind(host,port).sync();
            LOG.info("netty engine start, host is "+host+", port is "+port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOG.error("netty engine is wrong");
        }finally {
            stop();
        }
    }

    @Override
    public void stop() {
        LOG.info("netty engine is stopping");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
