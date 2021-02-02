package com.example.restapi.web;

import com.example.restapi.config.AuthUser;
import com.example.restapi.exception.response.ResponseData;
import com.example.restapi.exception.response.ResponseService;
import com.example.restapi.exception.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.profile.DreamProfiles;
import com.example.restapi.domain.user.profile.DreamProfilesResource;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user/dreamProfiles")
public class UserDreamProfilesController {

    private final ResponseService responseService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity getDreamProfile(@AuthUser User user){
        String userEmail = user.getEmail();
        User byEmail = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        DreamProfilesResource resource = new DreamProfilesResource(byEmail.getDreamProfiles());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }


    @PutMapping
    public ResponseEntity updateDreamProfile(@RequestBody DreamProfiles dreamProfiles,
                                            @AuthUser User user){

        User byEmail = userRepository.findByEmail(user.getEmail()).orElseThrow(UserNotFoundException::new);
        byEmail.updateDreamProfiles(dreamProfiles);

        DreamProfilesResource resource = new DreamProfilesResource(byEmail.getDreamProfiles());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }




}
