package com.poplar.server.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufProcessor;

/**
 * User: FR
 * Time: 5/25/15 1:40 PM
 */
public class BufferLineReader implements ByteBufProcessor {
    private StringBuilder sb = new StringBuilder();

    //TODO how to do for read half line
    public String readLine(ByteBuf byteBuf){
        sb = new StringBuilder();
        int i = byteBuf.forEachByte(this);
        if (i == -1) {
            return null;
        }
        byteBuf.readerIndex(i + 1);
        return sb.toString();
    }

    @Override
    public boolean process(byte value) throws Exception {
        char nextByte = (char) value;
        if (nextByte == Constants.CR) {
            return true;
        }
        if (nextByte == Constants.LF) {
            return false;
        }
        sb.append(nextByte);
        return true;
    }

}
