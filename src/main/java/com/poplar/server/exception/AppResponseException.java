package com.poplar.server.exception;

/**
 * User: FR
 * Time: 6/26/15 1:59 PM
 */
public class AppResponseException extends RuntimeException {

    public AppResponseException() {
    }

    public AppResponseException(String message) {
        super(message);
    }

    public AppResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppResponseException(Throwable cause) {
        super(cause);
    }

    public AppResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
