package com.example.restapi.common.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> {
    int resultCode;
    String resultMsg;
    T data;
}
