package com.poplar.server;

import com.poplar.server.appExecutor.AppClassScanner;
import com.poplar.server.engine.netty.NettyEngine;
import com.poplar.server.util.ConfigLoader;
import com.poplar.server.util.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: FR
 * Time: 5/18/15 3:44 PM
 */
public class Launcher {
    private static Log LOG = LogFactory.getLog(Launcher.class);

    public static void run() throws Exception {
        AppClassScanner appClassScanner = new AppClassScanner();
        ConfigLoader configLoader = new ConfigLoader();
        String engine = ConfigLoader.getStrValue("engine");
        if(engine.equals(Constants.Service.ENGINE_NETTY)){
            NettyEngine.serverStart();
        }
    }
}
