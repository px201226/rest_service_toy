package com.example.restapi.web;


import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.comments.Comments;
import com.example.restapi.domain.comments.CommentsResource;
import com.example.restapi.domain.posts.Posts;
import com.example.restapi.domain.posts.PostsResource;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.service.comments.CommentsService;
import com.example.restapi.service.posts.PostsService;
import com.example.restapi.web.dto.CommentsSaveRequestDto;
import com.example.restapi.web.dto.CommentsUpdateRequestDto;
import com.example.restapi.web.dto.PostsSaveRequestDto;
import com.example.restapi.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentsController {

    private final ResponseService responseService;
    private final CommentsService commentsService;
    private final PostsService postsService;

    @GetMapping("/v1/posts/{postId}/comments")
    public ResponseEntity getList(@PathVariable Long postId) {
        Posts posts = postsService.findById(postId);
        List<Comments> allDesc = posts.getComments();
        List<CommentsResource> collect = allDesc.stream().map(CommentsResource::new).collect(Collectors.toList());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, collect);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity getList(@PathVariable Long postId,
                                  @PathVariable Long commentId) {
        Comments byId = commentsService.findById(postId, commentId);
        CommentsResource resource = new CommentsResource(byId);
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/v1/posts/{postId}/comments")
    public ResponseEntity save(@Valid @RequestBody CommentsSaveRequestDto requestDto,
                               @PathVariable Long postId,
                               @AuthUser User user,
                               Errors errors) {

        if (errors.hasErrors()) {
            ResponseData<Errors> response = responseService.create(ResponseStatus.INVALID_REQUEST_PARAMETER_ERROR, errors);
            return ResponseEntity.badRequest().body(response);
        }

        Comments save = commentsService.save(requestDto, postId, user.getEmail());
        CommentsResource resource = new CommentsResource(save);
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);

        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity update(@Valid @RequestBody CommentsUpdateRequestDto requestDto,
                                 @PathVariable Long postId,
                                 @PathVariable Long commentId,
                                 @AuthUser User user,
                                 Errors errors) {

        if (errors.hasErrors()) {
            ResponseData<Errors> response = responseService.create(ResponseStatus.INVALID_REQUEST_PARAMETER_ERROR, errors);
            return ResponseEntity.badRequest().body(response);
        }

        Comments update = commentsService.update(requestDto, postId, commentId, user.getEmail());
        CommentsResource resource = new CommentsResource(update);
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity delete(@PathVariable Long postId,
                                 @PathVariable Long commentId,
                                 @AuthUser User user) {

        commentsService.delete(postId,commentId, user.getEmail());
        return ResponseEntity.noContent().build();

    }
}
