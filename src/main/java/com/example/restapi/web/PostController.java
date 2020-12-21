package com.example.restapi.web;


import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.posts.Posts;
import com.example.restapi.domain.posts.PostsResource;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.service.posts.PostsService;
import com.example.restapi.web.dto.PostsResponseDto;
import com.example.restapi.web.dto.PostsSaveRequestDto;
import com.example.restapi.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {

    private final ResponseService responseService;
    private final PostsService postsService;

    @GetMapping
    public ResponseEntity getList(){
        List<Posts> allDesc = postsService.findAllDesc();
        List<PostsResource> collect = allDesc.stream().map(PostsResource::new).collect(Collectors.toList());
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, collect);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        Posts byId = postsService.findById(id);
        PostsResource postsResource = new PostsResource(byId);
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, postsResource);
        return ResponseEntity.ok(responseData);
    }


    @PostMapping
    public ResponseEntity save(@Valid @RequestBody PostsSaveRequestDto postsSaveRequestDto,
                               @AuthUser User user,
                               Errors errors){

        if(errors.hasErrors()) {
            ResponseData<Errors> response = responseService.create(ResponseStatus.INVALID_REQUEST_PARAMETER_ERROR, errors);
            return ResponseEntity.badRequest().body(response);
        }

        Posts save = postsService.save(postsSaveRequestDto, user.getEmail());
        PostsResource postsResource = new PostsResource(save);
        ResponseData<PostsResource> responseData = responseService.create(ResponseStatus.SUCCESS, postsResource);

        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,@Valid @RequestBody PostsUpdateRequestDto requestDto, @AuthUser User user){
        Posts update = postsService.update(id, requestDto, user.getEmail());
        PostsResource postsResource = new PostsResource(update);
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, postsResource);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id, @AuthUser User user){
        postsService.delete(id, user.getEmail());
        return ResponseEntity.noContent().build();
    }
}
