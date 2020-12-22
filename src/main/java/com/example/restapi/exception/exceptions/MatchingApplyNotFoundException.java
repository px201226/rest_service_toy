package com.example.restapi.exception.exceptions;


import com.example.restapi.exception.high.NotExistDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// http status code
// 2xx -> OK
// 4xx -> client error
// 5xx -> server error
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MatchingApplyNotFoundException extends NotExistDataException {
    public MatchingApplyNotFoundException(String message) {
        super(message);
    }
    public MatchingApplyNotFoundException(){
        super("지원하지 않으셧습니다");
    }
}
