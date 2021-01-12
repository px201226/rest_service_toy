package com.example.restapi.exception.exceptions;


import com.example.restapi.exception.high.RedundantDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// http status code
// 2xx -> OK
// 4xx -> client error
// 5xx -> server error
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlreadyApplyException extends RedundantDataException {
    public AlreadyApplyException(String message) {
        super(message);
    }
    public AlreadyApplyException(){
        super("이미 참가하셨습니다");
    }
}
