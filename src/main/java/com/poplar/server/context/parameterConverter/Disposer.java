package com.poplar.server.context.parameterConverter;

import com.poplar.server.context.Request;

import java.io.UnsupportedEncodingException;

/**
 * User: FR
 * Time: 7/2/15 3:51 PM
 */
public interface Disposer {
    boolean canConvert(Request request);
    void convert(Request request) throws Exception;
}
