package com.poplar.server.exception;

/**
 * User: FR
 * Time: 6/30/15 2:33 PM
 */
public class WriteTimeoutException extends RuntimeException {

    public WriteTimeoutException() {
    }

    public WriteTimeoutException(String message) {
        super(message);
    }

    public WriteTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriteTimeoutException(Throwable cause) {
        super(cause);
    }

    public WriteTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
