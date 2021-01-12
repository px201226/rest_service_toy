package com.example.restapi.exception.exceptions;


import com.example.restapi.exception.high.NotExistDataException;
import com.example.restapi.exception.high.NotExistParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// http status code
// 2xx -> OK
// 4xx -> client error
// 5xx -> server error
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserNotFoundException extends NotExistDataException {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(){
        super("회원이 아닙니다");
    }
}
