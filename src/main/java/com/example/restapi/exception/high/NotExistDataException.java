package com.example.restapi.exception.high;

// 404
public class NotExistDataException extends RuntimeException {
    public NotExistDataException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotExistDataException(String msg) {
        super(msg);
    }

    public NotExistDataException() {
        super("데이터가 존재하지 않습니다");
    }
}