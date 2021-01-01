package com.example.restapi.exception.exceptions;

import com.example.restapi.exception.high.NotExistDataException;

public class EmailSigninFailedException extends NotExistDataException {
    public EmailSigninFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public EmailSigninFailedException(String msg) {
        super(msg);
    }

    public EmailSigninFailedException() {
        super("회원이 아닙니다");
    }
}