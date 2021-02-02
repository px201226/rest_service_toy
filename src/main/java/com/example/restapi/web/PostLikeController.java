package com.example.restapi.web;


import com.example.restapi.config.AuthUser;
import com.example.restapi.exception.response.ResponseService;
import com.example.restapi.domain.user.User;
import com.example.restapi.service.posts.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/like")
public class PostLikeController {

    private final PostLikeService postLikeService;
    private final ResponseService responseService;

    @PostMapping(value = "/{postId}")
    public ResponseEntity like(@PathVariable Long postId, @AuthUser User user){
        postLikeService.like(postId,user.getEmail());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{postId}")
    public ResponseEntity unLike(@PathVariable Long postId, @AuthUser User user){
        postLikeService.unLike(postId,user.getEmail());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity status(@PathVariable Long postId, @AuthUser User user){

        Boolean byUserEmailAndPostId = postLikeService.findByUserEmailAndPostId(user.getEmail(), postId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("isLike", byUserEmailAndPostId);
        EntityModel<Map> entityModel = EntityModel.of(map);
        entityModel.add(linkTo(PostLikeController.class).slash(postId).withSelfRel());

        return ResponseEntity.ok(entityModel);
    }
}
