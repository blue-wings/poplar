package com.poplar.server.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * User: FR
 * Time: 6/5/15 4:29 PM
 */
public class IOUtils {

    public static void writeAndFlush(String message, ChannelHandlerContext channelHandlerContext){
       writeAndFlush(message.getBytes(), channelHandlerContext);
    }

    public static void writeAndFlush(byte[] messageBytes, ChannelHandlerContext channelHandlerContext){
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();
        for(Byte b : messageBytes){
            byteBuf.writeByte(b);
        }
        channelHandlerContext.writeAndFlush(byteBuf);
    }
}
