package com.example.restapi.exception.exceptions;

import com.example.restapi.exception.high.NotExistDataException;
import com.example.restapi.exception.high.NotExistParameterException;

public class EmailSigninFailedException extends NotExistParameterException {
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