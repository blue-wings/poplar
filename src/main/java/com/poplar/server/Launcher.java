package com.poplar.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: FR
 * Time: 5/18/15 3:44 PM
 */
public class Launcher {
    private static Log LOG = LogFactory.getLog(Launcher.class);

    public static void run() throws Exception {
        ServerContext.getEngine().start();
    }

    public static void stop(){
        ServerContext.getEngine().stop();
    }

    public static void main(String[] args){
        try {
            ServerContext.ini();
            Launcher.run();
        } catch (Exception e) {
            LOG.error("server start failed", e);
        }
    }


}
