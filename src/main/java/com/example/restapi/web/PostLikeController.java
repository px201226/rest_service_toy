package com.example.restapi.web;


import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.service.posts.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(byUserEmailAndPostId);
    }
}
