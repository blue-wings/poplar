package com.poplar.server;

import com.poplar.server.appExecutor.AppClassScanner;
import com.poplar.server.appExecutor.RouterScanner;
import com.poplar.server.util.ConfigLoader;
import com.poplar.server.nettyCore.NettyServer;

/**
 * User: FR
 * Time: 5/18/15 3:44 PM
 */
public class Launcher {
    public static void run() throws Exception {
        AppClassScanner appClassScanner = new AppClassScanner();
        RouterScanner routerScanner = new RouterScanner();
        ConfigLoader configLoader = new ConfigLoader();
        new NettyServer().serverStart();
    }
}
