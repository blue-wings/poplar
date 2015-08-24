package com.poplar.server.appExecutor;

import com.poplar.server.appExecutor.beans.AnnoBeanContext;
import com.poplar.server.appExecutor.beans.BeanContext;
import com.poplar.server.appExecutor.executor.AppExecutor;

import java.io.IOException;

/**
 * Created by work on 15-8-21.
 */
public class AppContext {

    private AppExecutor appExecutor;
    private BeanContext beanContext;

    public AppContext() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        appExecutor = new AppExecutor();
        beanContext = new AnnoBeanContext();
    }

    public AppExecutor getAppExecutor() {
        return appExecutor;
    }

    public BeanContext getBeanContext() {
        return beanContext;
    }
}
