package com.poplar.server.appExecutor.beans;

import java.io.IOException;

/**
 * Created by work on 15-8-20.
 */
public interface BeanContext {
    void loadBeans() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException;
    Object getByName(String name);
    void removeByName(String name);
    void clear();
}
