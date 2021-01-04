package com.example.restapi.web;


import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.posts.PostAssembler;
import com.example.restapi.domain.posts.PostModel;
import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.service.posts.PostsService;
import com.example.restapi.web.dto.PostsSaveRequestDto;
import com.example.restapi.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {
  private final ResponseService responseService;
    private final PostsService postsService;
    private final PostAssembler postAssembler;
    private final EntityManager entityManager;

    @GetMapping(value = "/test")
    public ResponseEntity getList(){

        List<Post> allDesc = postsService.findAllDesc();
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, postAssembler.toCollectionModel(allDesc));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public ResponseEntity getPageableList(Pageable pageable,PagedResourcesAssembler<Post> assembler ){
        Page<Post> all = postsService.findAll(pageable);
        PagedModel<PostModel> postModels = assembler.toModel(all, postAssembler);
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, all);
        return ResponseEntity.ok(postModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){

        Post byId = postsService.findById(id);
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, postAssembler.toModel(byId));
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

        Post save = postsService.save(postsSaveRequestDto, user.getEmail());
        System.out.println("dddd");
        PostModel model = postAssembler.toModel(save);
        System.out.println("ddddd");
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, model);

        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,@Valid @RequestBody PostsUpdateRequestDto requestDto, @AuthUser User user){
        Post update = postsService.update(id, requestDto, user.getEmail());
        PostModel model = postAssembler.toModel(update);
        ResponseData responseData = responseService.create(ResponseStatus.SUCCESS, model);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id, @AuthUser User user){
        postsService.delete(id, user.getEmail());
        return ResponseEntity.noContent().build();
    }
}
