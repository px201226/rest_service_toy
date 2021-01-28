package com.example.restapi.snippets;

import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.exception.exceptions.CAuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {


    @GetMapping("/resultCode")
    public ResponseEntity getResultCode(){
        ResponseStatus[] values = ResponseStatus.values();
        StringBuilder builder = new StringBuilder("{");
        for(int i=0; i<values.length; i++){
            ResponseStatus value = values[i];
            String format = String.format("\"%s\":\"%s\"", value.getResultCode(), value.getResultMsg());
            builder.append(format);
            if(i != values.length -1) builder.append(",");
        }
        builder.append("}");
        return ResponseEntity.ok(builder.toString());
    }

    @GetMapping("/403error")
    public ResponseEntity getErrorResponse(){
        throw new CAuthenticationEntryPointException("권한이 없습니다");
    }
}
