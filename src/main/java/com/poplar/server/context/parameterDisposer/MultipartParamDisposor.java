package com.poplar.server.context.parameterDisposer;

import com.poplar.server.context.Request;

/**
 * User: FR
 * Time: 7/2/15 3:53 PM
 */
public class MultipartParamDisposor implements Disposer {

    @Override
    public boolean canConvert(Request request) {
        return false;
    }

    @Override
    public void convert(Request request) throws Exception{

    }
}
