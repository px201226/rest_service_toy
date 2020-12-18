package com.example.restapi.domain.user;

import com.example.restapi.web.UserSignController;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class UserResource {

    @JsonUnwrapped
    private EntityModel entityModel;
    public UserResource(User content, Link... links) {
        entityModel = EntityModel.of(content, links)
                .add(linkTo(UserSignController.class).slash("login").withRel("login"));
    }
}
