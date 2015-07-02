package com.poplar.server.exception;

/**
 * User: FR
 * Time: 7/2/15 2:18 PM
 */
public class AppClassScanException extends RuntimeException {
    public AppClassScanException() {
    }

    public AppClassScanException(String message) {
        super(message);
    }

    public AppClassScanException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppClassScanException(Throwable cause) {
        super(cause);
    }

    public AppClassScanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
