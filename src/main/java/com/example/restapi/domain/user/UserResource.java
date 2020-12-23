package com.example.restapi.domain.user;


import com.example.restapi.domain.user.profile.DreamProfiles;
import com.example.restapi.web.UserController;
import com.example.restapi.web.UserDetailProfilesController;
import com.example.restapi.web.UserDreamProfilesController;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.security.core.userdetails.UserDetails;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class UserResource {

    @JsonUnwrapped
    private EntityModel entityModel;

    public UserResource(User content, Link... links) {
        entityModel = EntityModel.of(content, links)
                .add(linkTo(UserController.class).slash("").withSelfRel())
                .add(linkTo(methodOn(UserDetailProfilesController.class).getUserProfile(null)).withRel("detailProfiles"))
                .add(linkTo(methodOn(UserDreamProfilesController.class).getDreamProfile(null)).withRel("dreamProfiles"));
    }
}
