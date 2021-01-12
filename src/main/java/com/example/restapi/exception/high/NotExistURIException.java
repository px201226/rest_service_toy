package com.example.restapi.exception.high;

// 404
public class NotExistURIException extends RuntimeException {
    public NotExistURIException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotExistURIException(String msg) {
        super(msg);
    }

    public NotExistURIException() {
        super("올바르지않은 경로입니다");
    }
}