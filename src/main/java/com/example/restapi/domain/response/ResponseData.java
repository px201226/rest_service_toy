package com.example.restapi.domain.response;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseData<T> {
    int resultCode;
    String message;

    @JsonUnwrapped
    T data;
}
