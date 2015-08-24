package com.poplar.server;

import com.poplar.server.appExecutor.AppContext;
import com.poplar.server.httpEngine.Engine;
import com.poplar.server.httpEngine.netty.NettyEngine;
import com.poplar.server.util.Config;
import com.poplar.server.util.Constants;

import java.io.IOException;

/**
 * Created by work on 15-8-21.
 */
public class ServerContext {

    private static Engine ENGINE;
    private static Config CONFIG;
    private static AppContext APP_CONTEXT;

    public static void ini() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        CONFIG = new Config();
        APP_CONTEXT = new AppContext();
        String engineType = CONFIG.getStrValue("engine")==null? Constants.Service.ENGINE_NETTY: CONFIG.getStrValue("engine");
        if(engineType.equals(Constants.Service.ENGINE_NETTY)){
            ENGINE = new NettyEngine();
        }

    }

    public static Engine getEngine(){
        return ENGINE;
    }

    public static Config getConfig(){
        return CONFIG;
    }

    public static AppContext getAppContext(){
        return APP_CONTEXT;
    }
}
