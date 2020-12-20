package com.example.restapi.exception.high;


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