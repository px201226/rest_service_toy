package com.example.restapi.exception;

import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.exception.high.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@ControllerAdvice           // 모든 컨트롤러가 실행되기 전에 이 컨트롤러가 실행된다.
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final ResponseService responseService;

    // validation exception
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(), "Validation Failed", ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)                  // Exception 클래스가 발생하면 실행된다.
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
            ExceptionResponse exceptionResponse =
                    new ExceptionResponse(LocalDateTime.now(), ex.getMessage() +" dd" + ex.getCause(), request.getDescription(false));

            return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidRequestParamaterException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity invalidRequestParamaterException(WebRequest request, InvalidRequestParamaterException e) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), e.getMessage(), request.getDescription(false));

        ResponseData<ExceptionResponse> responseData = responseService.create(
                com.example.restapi.domain.response.ResponseStatus.INVALID_REQUEST_PARAMETER_ERROR,
                exceptionResponse);

        return new ResponseEntity(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotExistURIException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity notExistDataException(WebRequest request, NotExistURIException e) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), e.getMessage(), request.getDescription(false));

        ResponseData<ExceptionResponse> responseData = responseService.create(
                com.example.restapi.domain.response.ResponseStatus.NOT_EXIST_URI,
                exceptionResponse);

        return new ResponseEntity(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotExistParameterException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity notExistParameterException(WebRequest request, NotExistParameterException e) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), e.getMessage(), request.getDescription(false));

        ResponseData<ExceptionResponse> responseData = responseService.create(
                com.example.restapi.domain.response.ResponseStatus.NOT_EXIST_PARAMETER_ERROR,
                exceptionResponse);

        return new ResponseEntity(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(RedundantDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity redundantDataException(WebRequest request, RedundantDataException e) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), e.getMessage(), request.getDescription(false));

        ResponseData<ExceptionResponse> responseData = responseService.create(
                com.example.restapi.domain.response.ResponseStatus.REDUNTANT_DATA_ERROR,
                exceptionResponse);

        return new ResponseEntity(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NotExistDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity notExistDataException (WebRequest request, NotExistDataException e) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), e.getMessage(), request.getDescription(false));

        ResponseData<ExceptionResponse> responseData = responseService.create(
                com.example.restapi.domain.response.ResponseStatus.NOT_EXIST_DATA,
                exceptionResponse);

        return new ResponseEntity(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceAcessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity serviceAcessDeniedException(WebRequest request, ServiceAcessDeniedException e) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), e.getMessage(), request.getDescription(false));

        ResponseData<ExceptionResponse> responseData = responseService.create(
                com.example.restapi.domain.response.ResponseStatus.SERVICE_ACCESS_DENIED_ERROR,
                exceptionResponse);

        return new ResponseEntity(responseData, HttpStatus.FORBIDDEN);
    }


/*
    @ExceptionHandler(UserNotFoundException.class)      // UserNotFoundException 클래스가 발생하면 실행된다.
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request){

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        ResponseData<ExceptionResponse> responseData = responseService.create(
                com.example.restapi.domain.response.ResponseStatus.NOT_EXIST_DATA,
                exceptionResponse);

        return new ResponseEntity(responseData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity emailSigninFailed(WebRequest request, EmailSigninFailedException e) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), e.getMessage(), request.getDescription(false));

        ResponseData<ExceptionResponse> responseData = responseService.create(
                com.example.restapi.domain.response.ResponseStatus.NOT_EXIST_DATA,
                exceptionResponse);

        return new ResponseEntity(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public ResponseEntity authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(LocalDateTime.now(), "100", "권한이 없습니다");

        ResponseData<ExceptionResponse> responseData = responseService.create(
                com.example.restapi.domain.response.ResponseStatus.UNKNOWN_ERROR,
                exceptionResponse);
        System.out.println("완료");
        return new ResponseEntity(responseData, HttpStatus.BAD_REQUEST);
    }*/
}
