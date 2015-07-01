package com.poplar.server.nettyEngin;

import com.poplar.server.nettyEngin.monotor.TimeMonitor;
import com.poplar.server.nettyEngin.request.HttpRequestHandler;
import com.poplar.server.nettyEngin.response.HttpResponseHandler;
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
public class NettyServer {

    private static Log LOG = LogFactory.getLog(NettyServer.class);

    public void serverStart(){
        final Long readTimeout = ConfigLoader.getStrValue("net.read.timeout")==null?null:ConfigLoader.getLongValue("net.read.timeout");
        final Long writeTimeout = ConfigLoader.getStrValue("net.write.timeout")==null?null:ConfigLoader.getLongValue("net.write.timeout");
        String selectorThreadNum = ConfigLoader.getStrValue("selector.thread.num");
        String workerThreadNum = ConfigLoader.getStrValue("worker.thread.num");
        EventLoopGroup bossGroup = selectorThreadNum==null?new NioEventLoopGroup():new NioEventLoopGroup(Integer.parseInt(selectorThreadNum));
        EventLoopGroup workerGroup = workerThreadNum==null?new NioEventLoopGroup():new NioEventLoopGroup(Integer.parseInt(workerThreadNum));
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
            ChannelFuture future = bootstrap.bind("10.235.176.24",8100).sync();
            LOG.info("bserver start");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOG.error("bserver is wrong");
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
