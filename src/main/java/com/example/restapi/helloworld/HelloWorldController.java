package com.example.restapi.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

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


    @GetMapping(path = "/helloworld")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language", required=false)  Locale locale) {
        return messageSource.getMessage("greeting.message", null, locale);
    }

}
