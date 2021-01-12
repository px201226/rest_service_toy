package com.example.restapi.exception.exceptions;


import com.example.restapi.exception.high.NotExistDataException;
import com.example.restapi.exception.high.NotExistURIException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// http status code
// 2xx -> OK
// 4xx -> client error
// 5xx -> server error
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MatchedResultNotFoundException extends NotExistDataException {
    public MatchedResultNotFoundException(String message) {
        super(message);
    }
    public MatchedResultNotFoundException(){
        super("매칭되지 않으셨습니다.");
    }
}
