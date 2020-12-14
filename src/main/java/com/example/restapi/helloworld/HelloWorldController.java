package com.example.restapi.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


    /*
     * Object return REST API
     * 리턴 할 클래스는 getter, setter가 존재해야만 JSON으로 컨버트할 수 있다.
     * */
    @GetMapping("/hello-world-bean")
    public HelloWorldBaen helloworld(){
        return new HelloWorldBaen(123,"Hello Wrold Bean");
    }

    /*
    * PathVariable 실습, 변수에 curly brace{} 로 감싸고
    * @PathVariable 어노테이션으로 사용한다. */
    @GetMapping("/hello/{name}")
    public HelloWorldBaen hellowordbean(@PathVariable String name){
        return new HelloWorldBaen(123,name);
    }

}
