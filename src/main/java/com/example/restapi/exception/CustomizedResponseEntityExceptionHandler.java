package com.example.restapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestController
@ControllerAdvice           // 모든 컨트롤러가 실행되기 전에 이 컨트롤러가 실행된다.
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), "Validation Failed", ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)                  // Exception 클래스가 발생하면 실행된다.
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
            ExceptionResponse exceptionResponse =
                    new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

            return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)      // UserNotFoundException 클래스가 발생하면 실행된다.
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

}
