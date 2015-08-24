package com.poplar.server.httpEngine.netty.monotor;

import com.poplar.server.exception.ReadTimeOutException;
import com.poplar.server.exception.WriteTimeoutException;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: FR
 * Time: 7/1/15 2:13 PM
 */
public class TimeMonitor extends ChannelHandlerAdapter {

    private static Log LOG = LogFactory.getLog(TimeMonitor.class);

    private Long readTimeout;
    private Long writeTimeout;
    private Long firstReadTime;
    private Long firstWriteTime;

    public TimeMonitor(Long readTimeout, Long writeTimeout) {
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(firstWriteTime!=null){
            firstWriteTime=null;
        }
        if(readTimeout!=null && firstReadTime==null){
            firstReadTime=System.currentTimeMillis();
        }
        if(firstReadTime!=null && (System.currentTimeMillis()-firstReadTime)>readTimeout.longValue()){
            ctx.fireExceptionCaught(new ReadTimeOutException("read time out"));
            ctx.close();
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(firstReadTime!=null) {
            firstReadTime=null;
        }
        if(writeTimeout!=null && firstWriteTime==null){
            firstWriteTime=System.currentTimeMillis();
        }
        if((System.currentTimeMillis()-firstWriteTime)>writeTimeout.longValue()){
            ctx.fireExceptionCaught(new WriteTimeoutException("write time out"));
            ctx.close();
        }else {
            ctx.write(msg, promise);
        }
    }

}
