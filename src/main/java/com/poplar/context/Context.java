package com.poplar.context;

/**
 * User: FR
 * Time: 5/18/15 5:45 PM
 */
public class Context {

    private ThreadLocal<Context> contextThreadLocal = new ThreadLocal<Context>();

    public Context() {
        contextThreadLocal.set(this);
    }

    public Context get(){
        return contextThreadLocal.get();
    }
}
