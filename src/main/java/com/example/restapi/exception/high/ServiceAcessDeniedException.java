package com.example.restapi.exception.high;


public class ServiceAcessDeniedException extends RuntimeException {
    public ServiceAcessDeniedException(String msg, Throwable t) {
        super(msg, t);
    }

    public ServiceAcessDeniedException(String msg) {
        super(msg);
    }

    public ServiceAcessDeniedException() {
        super();
    }
}