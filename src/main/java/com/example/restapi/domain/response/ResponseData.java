package com.example.restapi.domain.response;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseData<T> {
    int resultCode;
    String resultMsg;
    T data;
}
