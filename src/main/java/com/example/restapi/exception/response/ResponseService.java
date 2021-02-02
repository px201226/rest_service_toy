package com.example.restapi.exception.response;

import org.springframework.stereotype.Service;
@Service // 해당 Class가 Service임을 명시합니다.
public class ResponseService {

    public <T> ResponseData<T> create(ResponseStatus state, T data){
        return ResponseData.<T>builder()
                .resultCode(state.getResultCode())
                .message(state.resultMsg)
                .data(data)
                .build();
    }
}
