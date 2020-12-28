package com.example.restapi.web;

import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserAdapter;
import com.example.restapi.domain.user.UserAssembler;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.domain.user.profile.DetailProfilesResource;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final ResponseService responseService;
    private final UserRepository userRepository;
    private final UserAssembler userAssembler;

    @GetMapping
    public ResponseEntity getUserProfile(@AuthUser User user) {

        return userRepository.findByEmail(user.getEmail())
                .map(userAssembler::toModel)
                .map( (u) -> responseService.create( ResponseStatus.SUCCESS, u))
                .map(ResponseEntity::ok)
                .orElseThrow(UserNotFoundException::new);
    }


    @PutMapping
    public ResponseEntity updateUserProfile(@RequestBody User reqeustUpdateUserDto,
                                            @AuthUser User user) {

        return userRepository.findByEmail(user.getEmail())
                .map( (u) -> u.updateUser(reqeustUpdateUserDto))
                .map(userAssembler::toModel)
                .map( (u) -> responseService.create( ResponseStatus.SUCCESS, u))
                .map(ResponseEntity::ok)
                .orElseThrow(UserNotFoundException::new);
    }

}
