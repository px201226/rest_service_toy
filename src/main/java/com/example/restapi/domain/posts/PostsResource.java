package com.example.restapi.domain.posts;

import com.example.restapi.domain.user.User;
import com.example.restapi.web.PostController;
import com.example.restapi.web.UserController;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class PostsResource {

    @JsonUnwrapped
    private EntityModel entityModel;
    public PostsResource(Posts content, Link... links) {
        entityModel = EntityModel.of(content, links)
                .add(linkTo(PostController.class).slash(content.getId()).withSelfRel());
    }
}
