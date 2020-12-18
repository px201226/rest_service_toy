package com.example.restapi.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseStatus {
    SUCCESS(0, "성공하였습니다."),
    INVALID_REQUEST_PARAMETER_ERROR(10,"잘못된 요청 파라메터 에러입니다."),
    NOT_EXIST_PARAMETER_ERROR(11, "필수요청 파라미터가 없습니다."),
    NOT_EXIST_DATA(12, "데이터를 찾을 수 없습니다."),
    UNKNOWN_ERROR(100, "기타에러입니다.");

    int resultCode;
    String resultMsg;
}