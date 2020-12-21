package com.example.restapi.domain.comments;

import com.example.restapi.domain.posts.Posts;
import com.example.restapi.web.CommentsController;
import com.example.restapi.web.PostController;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class CommentsResource {

    @JsonUnwrapped
    private EntityModel entityModel;
    public CommentsResource(Comments content, Link... links) {
        entityModel = EntityModel.of(content, links)
                .add(linkTo(CommentsController.class).slash(content.getId()).withSelfRel());
    }
}
