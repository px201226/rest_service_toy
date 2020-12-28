package com.example.restapi.web;


import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.comments.CommentAssembler;
import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.service.comments.CommentsService;
import com.example.restapi.service.posts.PostsService;
import com.example.restapi.web.dto.CommentsSaveRequestDto;
import com.example.restapi.web.dto.CommentsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentsController {

    private final ResponseService responseService;
    private final CommentsService commentsService;
    private final PostsService postsService;
    private final CommentAssembler commentAssembler;

    @GetMapping("/v1/posts/{postId}/comments")
    public ResponseEntity getList(@PathVariable Long postId) {
        Post post = postsService.findById(postId);
        List<Comment> allDesc = post.getComments();
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, commentAssembler.toCollectionModel(allDesc));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity getList(@PathVariable Long postId,
                                  @PathVariable Long commentId) {
        Comment byId = commentsService.findById(postId, commentId);
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, commentAssembler.toModel(byId));
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

        Comment save = commentsService.save(requestDto, postId, user.getEmail());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, commentAssembler.toModel(save));

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

        Comment update = commentsService.update(requestDto, postId, commentId, user.getEmail());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, commentAssembler.toModel(update));
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
