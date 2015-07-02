package com.poplar.server.engine.netty.response;

import com.poplar.server.util.ConfigLoader;
import com.poplar.server.context.Response;
import com.poplar.server.util.CompressUtil;
import com.poplar.server.util.Constants;
import com.poplar.server.util.IOUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * User: FR
 * Time: 5/25/15 1:31 PM
 */
public class HttpResponseTranslator {

    private static Log LOG = LogFactory.getLog(HttpResponseTranslator.class);

    public static void translate(Response response, ChannelHandlerContext channelHandlerContext) throws UnsupportedEncodingException {
        if (response.getHeader().getStrValue(Constants.HttpHeader.TRANSFER_ENCODING) != null &&
                response.getHeader().getStrValue(Constants.HttpHeader.TRANSFER_ENCODING).equals(Constants.HttpHeader.TRANSFER_ENCODING_CHUNKED)) {
            TrunkedBodyTranslator.translate(response, channelHandlerContext);
        } else {
            FixedLengthBodyTranslator.translate(response, channelHandlerContext);
        }
    }

    private static class FixedLengthBodyTranslator {
        public static void translate(Response response, ChannelHandlerContext channelHandlerContext) throws UnsupportedEncodingException {
            byte[] contentBytes = null;
            if (response.getHeader().getStrValue(Constants.HttpHeader.CONTENT_ENCODING) != null) {
                String content = response.getContent().getContent();
                contentBytes = CompressUtil.compressByGzip(content);
                response.getHeader().setValue(Constants.HttpHeader.CONTENT_LENGTH, contentBytes.length + "");
            } else {
                contentBytes = response.getContent().getContent().getBytes("UTF-8");
            }
            writeHead2Client(response, channelHandlerContext);
            if(LOG.isDebugEnabled()){
                LOG.debug(response.getContent().getContent());
            }
            IOUtils.writeAndFlush(contentBytes, channelHandlerContext);
        }
    }

    private static class TrunkedBodyTranslator {
        public static void translate(Response response, ChannelHandlerContext channelHandlerContext) throws UnsupportedEncodingException {
            byte[] contentBytes = null;
            if (response.getHeader().getStrValue(Constants.HttpHeader.CONTENT_ENCODING) != null) {
                //TODO to more efficient, can write to response while compressing. Now is more structured
                contentBytes = CompressUtil.compressByGzip(response.getContent().getContent());
            } else {
                contentBytes = response.getContent().getContent().getBytes("UTF-8");
            }
            writeHead2Client(response, channelHandlerContext);
            int chunkSize = ConfigLoader.getIntValue("transfer-encoding-size");
            int index = 0;
            while ((contentBytes.length - index) > 0) {
                if ((contentBytes.length - index) > chunkSize) {
                    byte[] contentSegmentBytes = new byte[chunkSize];
                    System.arraycopy(contentBytes, index, contentSegmentBytes, 0, chunkSize);
                    writeChunkSegment(chunkSize, contentSegmentBytes, channelHandlerContext, response);
                    index += chunkSize;
                } else {
                    byte[] contentSegmentBytes = new byte[contentBytes.length - index];
                    System.arraycopy(contentBytes, index, contentSegmentBytes, 0, contentBytes.length - index);
                    writeChunkSegment(contentBytes.length - index, contentSegmentBytes, channelHandlerContext, response);
                    index += contentBytes.length - index;
                }
            }
            writeChunkSegment(0, null, channelHandlerContext, response);
        }
    }

    private static void writeHead2Client(Response response, ChannelHandlerContext channelHandlerContext) {
        StringBuilder sb = new StringBuilder();
        sb.append(response.getProtocol() + " " + response.getStatus().getCode() + " " + response.getStatus().getDes() + "\r\n");
        for (Map.Entry<String, String> entry : response.getHeader().getValues().entrySet()) {
            sb.append(entry.getKey() + ":" + entry.getValue() + "\r\n");
        }
        sb.append("\r\n");
        if(LOG.isDebugEnabled()){
            LOG.debug(sb.toString());
        }
        IOUtils.writeAndFlush(sb.toString(), channelHandlerContext);
    }

    private static void writeChunkSegment(int chunkSize, byte[] contentSegmentBytes, ChannelHandlerContext channelHandlerContext, Response response) {
        IOUtils.writeAndFlush(Integer.toHexString(chunkSize), channelHandlerContext);
        IOUtils.writeAndFlush("\r\n", channelHandlerContext);
        if (contentSegmentBytes != null) {
            IOUtils.writeAndFlush(contentSegmentBytes, channelHandlerContext);
        }
        IOUtils.writeAndFlush("\r\n", channelHandlerContext);
    }

}
