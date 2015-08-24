package com.poplar.app.aop;

import com.poplar.server.appExecutor.anno.Aop;
import com.poplar.server.appExecutor.anno.Around;

/**
 * Created by work on 15-8-24.
 */
@Aop
public class Performance {

    @Around(position = "com.poplar.app.service.Index*")
    public void p(){
        System.out.println("time in milliseconds is "+System.currentTimeMillis());
    }
}
