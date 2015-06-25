package com.poplar;

import com.poplar.nettyCore.NettyServer;
import org.dom4j.DocumentException;
import com.poplar.util.AppClassScanner;
import com.poplar.util.RouterScanner;

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
