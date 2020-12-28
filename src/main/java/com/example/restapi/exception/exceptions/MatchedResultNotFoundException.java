package com.example.restapi.exception.exceptions;


import com.example.restapi.exception.high.NotExistDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// http status code
// 2xx -> OK
// 4xx -> client error
// 5xx -> server error
@ResponseStatus(HttpStatus.NOT_FOUND)

public class MatchedResultNotFoundException extends NotExistDataException {
    public MatchedResultNotFoundException(String message) {
        super(message);
    }
    public MatchedResultNotFoundException(){
        super("매칭되지 않으셨습니다.");
    }
}
