package com.example.restapi.exception.exceptions;


import com.example.restapi.exception.high.OverlappingDataException;

// http status code
// 2xx -> OK
// 4xx -> client error
// 5xx -> server error

public class AlreadyApplyException extends OverlappingDataException {
    public AlreadyApplyException(String message) {
        super(message);
    }
    public AlreadyApplyException(){
        super("이미 참가하셨습니다");
    }
}
