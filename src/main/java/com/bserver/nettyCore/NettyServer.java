package com.bserver.nettyCore;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import com.bserver.nettyCore.request.HttpRequestHandler;
import com.bserver.nettyCore.response.HttpResponseHandler;


/**
 * User: FR
 * Time: 5/18/15 3:08 PM
 */
public class NettyServer {

    public void serverStart(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
//                socketChannel.pipeline().addLast("read-timeout", new ReadTimeoutHandler(10));
//                socketChannel.pipeline().addLast("write-timeout", new WriteTimeoutHandler(10));
                socketChannel.pipeline().addLast("http-request-handler", new HttpRequestHandler());
                socketChannel.pipeline().addLast("http-response-handler", new HttpResponseHandler());


//                socketChannel.pipeline().addLast("read-timeout", new ReadTimeoutHandler(10));
//                socketChannel.pipeline().addLast("write-timeout", new WriteTimeoutHandler(10));
//                socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
//                socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
//                socketChannel.pipeline().addLast("http-encode", new HttpResponseEncoder());
//                socketChannel.pipeline().addLast("http-handler", new HttpRequestHandler());
            }
        });
        bootstrap.option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture future = bootstrap.bind("10.235.176.24",8100).sync();
            System.out.println("bserver start");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.err.println("bserver is wrong");
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
