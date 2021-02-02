package com.example.restapi.exception.response;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseData<T> extends RepresentationModel<ResponseData<T>> {
    int resultCode;
    String message;

    @JsonUnwrapped
    T data;
}
