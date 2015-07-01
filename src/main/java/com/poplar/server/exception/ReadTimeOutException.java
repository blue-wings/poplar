package com.poplar.server.exception;

/**
 * User: FR
 * Time: 6/30/15 2:32 PM
 */
public class ReadTimeOutException extends RuntimeException {

    public ReadTimeOutException() {
    }

    public ReadTimeOutException(String message) {
        super(message);
    }

    public ReadTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadTimeOutException(Throwable cause) {
        super(cause);
    }

    public ReadTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
