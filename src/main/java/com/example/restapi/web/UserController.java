package com.example.restapi.web;

import com.example.restapi.common.DocumentLinkToRef;
import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.comments.CommentModel;
import com.example.restapi.domain.comments.CommentResponseDto;
import com.example.restapi.domain.comments.CommentAssembler;
import com.example.restapi.domain.posts.PostAdapter;
import com.example.restapi.domain.posts.PostAdapterAssembler;

import com.example.restapi.domain.posts.PostModel;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserAssembler;
import com.example.restapi.domain.user.UserModel;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import com.example.restapi.exception.response.ResponseData;
import com.example.restapi.exception.response.ResponseService;
import com.example.restapi.exception.response.ResponseStatus;
import com.example.restapi.service.user.UserService;
import com.example.restapi.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final ResponseService responseService;
    private final UserRepository userRepository;
    private final UserAssembler userAssembler;
    private final PostAdapterAssembler postAdapterAssembler;
    private final CommentAssembler commentAssembler;
    private final UserService userService;

    @GetMapping
    public ResponseEntity getUserProfile(@AuthUser User user) {

        ResponseEntity<UserModel> userModelResponseEntity = userRepository.findByEmail(user.getEmail())
                .map(userAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(UserNotFoundException::new);

        return userModelResponseEntity;
    }


    @PutMapping
    public ResponseEntity updateUserProfile(@RequestBody UserUpdateRequestDto userUpdateRequestDto,
                                            @AuthUser User user) {

        return userRepository.findByEmail(user.getEmail())
                .map((u) -> u.updateUser(userUpdateRequestDto))
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

        CollectionModel<PostModel> models = postAdapterAssembler.toCollectionModel(collect);

        return ResponseEntity.ok(models);
    }

    @GetMapping("/comments")
    public ResponseEntity getCommentList(@AuthUser User user) {

        User find = userRepository.findByEmail(user.getEmail()).orElseThrow(UserNotFoundException::new);

        List<CommentResponseDto> collect = find.getComments().stream()
                .map(c -> new CommentResponseDto(c, Optional.ofNullable(user)))
                .collect(Collectors.toList());

        CollectionModel<CommentModel> commentModels = commentAssembler.toCollectionModel(collect);

        return ResponseEntity.ok(commentModels);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity findByUserEmail(@PathVariable String email) {
        userService.loadUserByUsername(email);
        Map<String, Boolean> map = new HashMap<>();
        map.put("found", true);
        EntityModel<Map> entityModel = EntityModel.of(map);
        entityModel.add(linkTo(methodOn(UserController.class).findByUserEmail(email)).withSelfRel())
                .add(linkTo(DocumentLinkToRef.class).slash("/dd").withRel("documentation_url"));
        return ResponseEntity.ok(entityModel);
    }
}
