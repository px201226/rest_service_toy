package com.example.restapi.domain.user;

import com.example.restapi.web.LoginController;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public class GuestResource {

    @JsonUnwrapped
    private EntityModel entityModel;
    public GuestResource(User content, Link... links) {
        entityModel = EntityModel.of(content, links)
                .add(linkTo(LoginController.class).slash("login").withRel("login"));
    }
}
