package com.example.restapi.exception.high;


//500
public class OverlappingDataException extends RuntimeException {
    public OverlappingDataException(String msg, Throwable t) {
        super(msg, t);
    }

    public OverlappingDataException(String msg) {
        super(msg);
    }

    public OverlappingDataException() {
        super();
    }
}