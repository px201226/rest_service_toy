package com.example.restapi.exception.high;

public class RedundantDataException extends RuntimeException {
    public RedundantDataException(String msg, Throwable t) {
        super(msg, t);
    }

    public RedundantDataException(String msg) {
        super(msg);
    }

    public RedundantDataException() {
        super();
    }
}