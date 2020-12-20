package com.example.restapi.exception.high;


public class InvalidRequestParamaterException extends RuntimeException {
    public InvalidRequestParamaterException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidRequestParamaterException(String msg) {
        super(msg);
    }

    public InvalidRequestParamaterException() {
        super();
    }
}