package com.example.restapi.web;

import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.UserResource;
import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.domain.user.profile.DetailProfilesResource;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final ResponseService responseService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity getUserProfile(@AuthUser User user){

        String userEmail = user.getEmail();
        User byEmail = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        UserResource resource = new UserResource(byEmail);
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);

        return ResponseEntity.ok(responseData);
    }


    @PutMapping
    public ResponseEntity updateUserProfile(@RequestBody User reqeustUpdateUserDto,
                                            @AuthUser User user){

        User byEmail = userRepository.findByEmail(user.getEmail()).orElseThrow(UserNotFoundException::new);
        byEmail.updateUser(reqeustUpdateUserDto);

        UserResource resource = new UserResource(byEmail);
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }




}
