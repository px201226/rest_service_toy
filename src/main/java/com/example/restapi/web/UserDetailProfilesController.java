package com.example.restapi.web;

import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.profile.DetailProfilesResource;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user/profiles")
public class UserDetailProfilesController {

    private final ResponseService responseService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity getUserProfile(@AuthUser User user){
        String userEmail = user.getEmail();
        User byEmail = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        DetailProfilesResource resource = new DetailProfilesResource(byEmail.getDetailProfiles());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }


    @PutMapping
    public ResponseEntity updateUserProfile(@RequestBody DetailProfiles detailProfiles,
                                            @AuthUser User user,
                                            String ns){

        User byEmail = userRepository.findByEmail(user.getEmail()).orElseThrow(UserNotFoundException::new);
        byEmail.updateMyDetailProfiles(detailProfiles);

        DetailProfilesResource resource = new DetailProfilesResource(byEmail.getDetailProfiles());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }




}
