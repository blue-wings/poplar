package com.poplar.server.context.parameterDisposer;

import com.poplar.server.context.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * User: FR
 * Time: 7/2/15 4:24 PM
 */
public class ParameterDisposer {

    private static List<Disposer> DISPOSERS = new ArrayList<Disposer>();
    static {
        DISPOSERS.add(new UrlParamDisposer());
        DISPOSERS.add(new StringFormParamDisposer());
        DISPOSERS.add(new JsonParamDisposer());
        DISPOSERS.add(new MultipartParamDisposor());
    }
    public static void dispose(Request request) throws Exception {
        for (Disposer disposer : DISPOSERS){
            disposer.convert(request);
        }
    }
}
