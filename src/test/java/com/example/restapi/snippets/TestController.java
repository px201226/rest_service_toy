package com.example.restapi.snippets;

import com.example.restapi.exception.response.ResponseStatus;
import com.example.restapi.exception.exceptions.CAuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TestController {


    @GetMapping("/resultCode")
    public ResponseEntity getResultCode(){
        ResponseStatus[] values = ResponseStatus.values();

        Map<Integer, String> map = new HashMap<>();
        for(int i=0; i<values.length; i++){
            ResponseStatus value = values[i];
            map.put(value.getResultCode(), value.getResultMsg());
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/403error")
    public ResponseEntity getErrorResponse(){
        throw new CAuthenticationEntryPointException("권한이 없습니다");
    }
}
