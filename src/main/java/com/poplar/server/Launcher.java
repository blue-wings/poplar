package com.poplar.server;

import com.poplar.server.appExecutor.AppClassScanner;
import com.poplar.server.nettyCore.NettyServer;
import com.poplar.server.util.ConfigLoader;

/**
 * User: FR
 * Time: 5/18/15 3:44 PM
 */
public class Launcher {
    public static void run() throws Exception {
        AppClassScanner appClassScanner = new AppClassScanner();
        ConfigLoader configLoader = new ConfigLoader();
        new NettyServer().serverStart();
    }
}
