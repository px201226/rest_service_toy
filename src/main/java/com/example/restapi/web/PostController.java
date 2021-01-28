package com.example.restapi.web;


import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.posts.*;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {

    private final ResponseService responseService;
    private final PostsService postsService;
    private final PostAssembler postAssembler;
    private final PostAdapterAssembler postAdapterAssembler;
    private final EntityManager entityManager;

    @GetMapping
    public ResponseEntity getPageableList(Pageable pageable,
                                          PagedResourcesAssembler<PostAdapter> postAdapterAssembler,
                                          PagedResourcesAssembler<Post> postAssembler,
                                          @AuthUser User user) {

        Page<Post> posts = postsService.findAll(pageable);

        Page<PostAdapter> postAdapters = posts.map(p -> p.toAdapter(Optional.ofNullable(user)));
        PagedModel<PostModel> postModels = postAdapterAssembler.toModel(postAdapters, this.postAdapterAssembler);

        return ResponseEntity.ok(postModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id,
                                   @AuthUser User user) {

        Post post = postsService.findById(id);

        PostModel model = postAdapterAssembler.toModel(post.toAdapter(Optional.ofNullable(user)));

        return ResponseEntity.ok(model);
    }


    @PostMapping
    public ResponseEntity save(@Valid @RequestBody PostsSaveRequestDto postsSaveRequestDto,
                               @AuthUser User user,
                               Errors errors) {

        if (errors.hasErrors()) {
            ResponseData<Errors> response = responseService.create(ResponseStatus.INVALID_REQUEST_PARAMETER_ERROR, errors);
            return ResponseEntity.badRequest().body(response);
        }

        // 영속성 유지 안됨 (save 객체) -> getComment가 안됨.
        Post save = postsService.save(postsSaveRequestDto, user.getEmail());
        PostModel model = postAssembler.toModel(save);

        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody PostsUpdateRequestDto requestDto, @AuthUser User user) {
        Post update = postsService.update(id, requestDto, user.getEmail());
        PostModel model = postAssembler.toModel(update);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id, @AuthUser User user) {
        postsService.delete(id, user.getEmail());
        return ResponseEntity.noContent().build();
    }
}
