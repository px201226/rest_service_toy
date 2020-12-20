package com.example.restapi.exception.exceptions;


import com.example.restapi.exception.high.NotExistDataException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// http status code
// 2xx -> OK
// 4xx -> client error
// 5xx -> server error
@ResponseStatus(HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class UserNotFoundException extends NotExistDataException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
