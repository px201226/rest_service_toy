package com.example.restapi.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data                                               // getter, setter
@AllArgsConstructor                                 // 모든 필드의 생성자
@NoArgsConstructor                                  // 디폴트 생성자 생성안함
public class HelloWorldBaen {
    private int age;
    private String message;

}
