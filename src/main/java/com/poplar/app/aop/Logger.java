package com.poplar.app.aop;

import com.poplar.server.appExecutor.anno.Aop;
import com.poplar.server.appExecutor.anno.Before;

/**
 * Created by work on 15-8-21.
 */
@Aop
public class Logger {

    @Before(position = "com.poplar.app.service.Index*")
    public void log(){
        System.out.println("performance log logging...");
    }
}
