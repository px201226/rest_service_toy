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
public class CommentNotFoundException extends NotExistDataException {
    public CommentNotFoundException(String message) {
        super(message);
    }
    public CommentNotFoundException(){
        super("코멘트가 존재하지 않습니다");
    }
}
