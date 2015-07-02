package com.poplar.server;

import com.poplar.server.appExecutor.AppClassScanner;
import com.poplar.server.engine.Engine;
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
    private static Engine ENGINE;

    public static void run() throws Exception {
        AppClassScanner.scan();
        ConfigLoader.loadProperties();
        String engineType = ConfigLoader.getStrValue("engine")==null?Constants.Service.ENGINE_NETTY:ConfigLoader.getStrValue("engine");
        if(engineType.equals(Constants.Service.ENGINE_NETTY)){
            ENGINE = new NettyEngine();
            ENGINE.start();
        }
    }

    public static void stop(){
        ENGINE.stop();
    }

    public static void main(String[] args){
        try {
            Launcher.run();
        } catch (Exception e) {
            LOG.error("server start failed", e);
        }
    }


}
