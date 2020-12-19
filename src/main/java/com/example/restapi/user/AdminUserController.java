//package com.example.restapi.user;
//
//import com.example.restapi.domain.user.User;
//import com.example.restapi.exception.UserNotFoundException;
//import com.fasterxml.jackson.databind.ser.FilterProvider;
//import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
//import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
//import org.springframework.beans.BeanUtils;
//import org.springframework.http.converter.json.MappingJacksonValue;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/admin")                                   // prefix
//public class AdminUserController {
//    private final UserDaoService userDaoService;
//
//    public AdminUserController(UserDaoService userDaoService){
//        this.userDaoService = userDaoService;
//    }
//
//    @GetMapping("/v1/users")
//    public MappingJacksonValue retrieveAllUsersV1(){
//        List<User> users = userDaoService.findAll();
//
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
//                .filterOutAllExcept("id","name","joinDate","ssn");
//        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);
//
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
//        mappingJacksonValue.setFilters(filterProvider);
//
//        return mappingJacksonValue;
//    }
//
//
//    //@GetMapping("/v1/users/{id}")                                             // prefix를 사용한 버전관리
//    //@GetMapping(value = "/users/{id}/",params = "version=1")                  // ?key=value 파라미터형식을 이용한 버전관리
//    //@GetMapping(value = "/users/{id}", headers = "API-VERSION=1")             // 헤더값을 이용한 버전관리
//    @GetMapping(value = "/users/{id}",produces = "application/vnd.company.appv1+json")  //Mime 타입
//    public MappingJacksonValue retrieveUserV1(@PathVariable Integer id){
//        User user = userDaoService.findById(id);
//        if(user == null){
//            throw new UserNotFoundException(String.format("ID[%s] not found", id));
//        }
//
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
//                .filterOutAllExcept("id","name","joinDate","ssn");
//        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);
//
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
//        mappingJacksonValue.setFilters(filterProvider);
//
//        return mappingJacksonValue;
//    }
//
//    public MappingJacksonValue retrieveUserV2(@PathVariable Integer id){
//        User user = userDaoService.findById(id);
//        if(user == null){
//            throw new UserNotFoundException(String.format("ID[%s] not found", id));
//        }
//
//        // user -> userV2 copy
//        UserV2 userV2 = new UserV2();
//        BeanUtils.copyProperties(user,userV2);
//        userV2.setGrade("제법이다..");
//
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
//                .filterOutAllExcept("id","name","joinDate","grade");
//        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo2",filter);
//
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
//        mappingJacksonValue.setFilters(filterProvider);
//
//        return mappingJacksonValue;
//    }
//}
