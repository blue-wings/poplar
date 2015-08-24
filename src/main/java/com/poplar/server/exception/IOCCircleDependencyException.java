package com.poplar.server.exception;

/**
 * Created by work on 15-8-21.
 */
public class IOCCircleDependencyException extends RuntimeException {

    public IOCCircleDependencyException() {
    }

    public IOCCircleDependencyException(String message) {
        super(message);
    }

    public IOCCircleDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public IOCCircleDependencyException(Throwable cause) {
        super(cause);
    }

    public IOCCircleDependencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
