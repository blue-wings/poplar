package com.poplar.server.engine.netty;

import com.poplar.server.engine.Engine;
import com.poplar.server.engine.netty.monotor.TimeMonitor;
import com.poplar.server.engine.netty.request.HttpRequestHandler;
import com.poplar.server.engine.netty.response.HttpResponseHandler;
import com.poplar.server.util.ConfigLoader;
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
        final Long readTimeout = ConfigLoader.getStrValue("net.read.timeout")==null?null:ConfigLoader.getLongValue("net.read.timeout");
        final Long writeTimeout = ConfigLoader.getStrValue("net.write.timeout")==null?null:ConfigLoader.getLongValue("net.write.timeout");
        String selectorThreadNum = ConfigLoader.getStrValue("selector.thread.num");
        String workerThreadNum = ConfigLoader.getStrValue("worker.thread.num");
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
            String host = ConfigLoader.getStrValue("host");
            int port = ConfigLoader.getIntValue("port");
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
