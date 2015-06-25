package com.bserver;

import com.bserver.nettyCore.NettyServer;
import org.dom4j.DocumentException;
import com.bserver.util.AppClassScanner;
import com.bserver.util.RouterScanner;

import java.io.IOException;

/**
 * User: FR
 * Time: 5/18/15 3:44 PM
 */
public class MainServer {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, DocumentException, IOException {
        AppClassScanner appClassScanner = new AppClassScanner();
        RouterScanner routerScanner = new RouterScanner();
        Config config = new Config();
        new NettyServer().serverStart();
    }
}
