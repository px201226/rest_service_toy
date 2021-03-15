package com.example.restapi.snippets;

import com.example.restapi.domain.user.profile.category.BodyType;
import com.example.restapi.domain.user.profile.category.LocationCategory;
import com.example.restapi.domain.user.profile.category.TallType;
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
public class CommonCodeController {


    @GetMapping("/result_code")
    public ResponseEntity getResultCode(){
        ResponseStatus[] values = ResponseStatus.values();

        Map<Integer, String> map = new HashMap<>();
        for(int i=0; i<values.length; i++){
            ResponseStatus value = values[i];
            map.put(value.getResultCode(), value.getResultMsg());
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/body_type")
    public ResponseEntity getBodyType(){
        BodyType[] values = BodyType.values();

        Map<String, String> map = new HashMap<>();
        for(int i=0; i<values.length; i++){
            BodyType value = values[i];
            map.put(value.toString(), value.getDescription());
        }
        System.out.println("mmmmmmmmmmmmmmmmmmm");
        return ResponseEntity.ok(map);
    }

    @GetMapping("/location_category")
    public ResponseEntity getLocationCategorys(){
        LocationCategory[] values = LocationCategory.values();

        Map<String, String> map = new HashMap<>();
        for(int i=0; i<values.length; i++){
            LocationCategory value = values[i];
            map.put(value.toString(), value.getDescription());
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/tall_type")
    public ResponseEntity getTallType(){
        TallType[] values = TallType.values();

        Map<String, String> map = new HashMap<>();
        for(int i=0; i<values.length; i++){
            TallType value = values[i];
            map.put(value.toString(), value.getDescription());
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/403error")
    public ResponseEntity getErrorResponse(){
        throw new CAuthenticationEntryPointException("권한이 없습니다");
    }
}
