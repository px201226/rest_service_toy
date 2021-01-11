package com.example.restapi.web;


import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.comments.CommentAdapter;
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

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentsController {

    private final ResponseService responseService;
    private final CommentsService commentsService;
    private final PostsService postsService;
    private final CommentAssembler commentAssembler;

    @GetMapping("/v1/posts/{postId}/comments")
    public ResponseEntity getList(@PathVariable Long postId, @AuthUser User user) {

        Post post = postsService.findById(postId);

        List<CommentAdapter> commentAdapters = post.getComments().stream()
                .map(c -> c.toAdapter(Optional.ofNullable(user)))
                .collect(Collectors.toList());

        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, commentAssembler.toCollectionModel(commentAdapters));

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity findById(@PathVariable Long postId,
                                   @PathVariable Long commentId,
                                   @AuthUser User user) {
        CommentAdapter commentAdapter = commentsService.findById(postId, commentId).toAdapter(Optional.ofNullable(user));
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, commentAssembler.toModel(commentAdapter));
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

        CommentAdapter commentAdapter = commentsService.save(requestDto, postId, user.getEmail()).toAdapter(Optional.of(user));
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, commentAssembler.toModel(commentAdapter));

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

        CommentAdapter commentAdapter = commentsService.update(requestDto, postId, commentId, user.getEmail()).toAdapter(Optional.of(user));
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, commentAssembler.toModel(commentAdapter));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity delete(@PathVariable Long postId,
                                 @PathVariable Long commentId,
                                 @AuthUser User user) {

        commentsService.delete(postId, commentId, user.getEmail());
        return ResponseEntity.noContent().build();

    }
}
