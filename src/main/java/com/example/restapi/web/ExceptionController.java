package com.example.restapi.web;

import com.example.restapi.exception.exceptions.CAuthenticationEntryPointException;
import com.example.restapi.exception.high.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @RequestMapping(value = "/entrypoint")
    public ResponseEntity entrypointException2() {
        throw new CAuthenticationEntryPointException("권한이 없습니다");
    }


    @RequestMapping(value = "/invalid")
    public ResponseEntity entrypointException3() {
        throw new UnauthorizedException("토큰이 유효하지 않습니다.");
    }

    @RequestMapping(value = "/expired")
    public ResponseEntity entrypointException4() {
        throw new UnauthorizedException("토큰 유효기간이 지났습니다");
    }

    @RequestMapping(value = "/other")
    public ResponseEntity entrypointException5() {
        throw new UnauthorizedException("토큰에러");
    }

}