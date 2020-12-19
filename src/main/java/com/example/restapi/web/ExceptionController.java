package com.example.restapi.web;

import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.exception.CAuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public ResponseEntity entrypointException() {
        throw new CAuthenticationEntryPointException();
    }
}