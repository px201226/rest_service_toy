package com.example.restapi.web;

import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.comments.CommentResponseDto;
import com.example.restapi.domain.comments.CommentAssembler;
import com.example.restapi.domain.posts.PostAdapter;
import com.example.restapi.domain.posts.PostAdapterAssembler;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserAssembler;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import com.example.restapi.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final ResponseService responseService;
    private final UserRepository userRepository;
    private final UserAssembler userAssembler;
    private final PostAdapterAssembler postAdapterAssembler;
    private final CommentAssembler commentAssembler;

    @GetMapping
    public ResponseEntity getUserProfile(@AuthUser User user) {

        return userRepository.findByEmail(user.getEmail())
                .map(userAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(UserNotFoundException::new);
    }


    @PutMapping
    public ResponseEntity updateUserProfile(@RequestBody UserUpdateRequestDto userUpdateRequestDto,
                                            @AuthUser User user) {

        return userRepository.findByEmail(user.getEmail())
                .map( (u) -> u.updateUser(userUpdateRequestDto))
                .map(userAssembler::toModel)
               // .map( (u) -> responseService.create( ResponseStatus.SUCCESS, u))
                .map(ResponseEntity::ok)
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping("/posts")
    public ResponseEntity getPostList(@AuthUser User user) {

        User find = userRepository.findByEmail(user.getEmail()).orElseThrow(UserNotFoundException::new);

        List<PostAdapter> collect = find.getPosts().stream()
                .map(p -> p.toAdapter(Optional.ofNullable(user)))
                .collect(Collectors.toList());

        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, postAdapterAssembler.toCollectionModel(collect));

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/comments")
    public ResponseEntity getCommentList(@AuthUser User user) {

        User find = userRepository.findByEmail(user.getEmail()).orElseThrow(UserNotFoundException::new);

        List<CommentResponseDto> collect = find.getComments().stream()
                .map(c -> new CommentResponseDto(c,Optional.ofNullable(user)))
                .collect(Collectors.toList());

        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, commentAssembler.toCollectionModel(collect));

        return ResponseEntity.ok(responseData);
    }
}
