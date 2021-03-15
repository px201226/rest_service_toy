package com.example.restapi.exception.response;

import com.example.restapi.common.DocumentLinkToRef;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service // 해당 Class가 Service임을 명시합니다.
public class ResponseService {

    public <T> ResponseData<T> create(ResponseStatus state, T data){
        ResponseData<T> build = ResponseData.<T>builder()
                .resultCode(state.getResultCode())
                .message(state.resultMsg)
                .data(data)
                .build();
        build.add(linkTo(DocumentLinkToRef.class).slash("docs/index.html#overview-errors").withRel("documentation_url"));
        return build;
    }
}
