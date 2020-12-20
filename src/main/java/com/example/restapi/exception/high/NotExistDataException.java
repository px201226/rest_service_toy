package com.example.restapi.exception.high;


public class NotExistDataException extends RuntimeException {
    public NotExistDataException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotExistDataException(String msg) {
        super(msg);
    }

    public NotExistDataException() {
        super();
    }
}