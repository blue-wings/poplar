package com.poplar.server.exception;

/**
 * User: FR
 * Time: 6/2/15 5:58 PM
 */
public class VerifyException extends RuntimeException {

    public VerifyException() {
    }

    public VerifyException(String message) {
        super(message);
    }

    public VerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerifyException(Throwable cause) {
        super(cause);
    }

    public VerifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
