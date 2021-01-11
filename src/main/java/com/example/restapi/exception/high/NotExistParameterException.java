package com.example.restapi.exception.high;

// 500 Error
public class NotExistParameterException extends RuntimeException {
    public NotExistParameterException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotExistParameterException(String msg) {
        super(msg);
    }

    public NotExistParameterException() {
        super();
    }
}