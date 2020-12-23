package com.example.restapi.domain.user.profile;

import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.web.UserController;
import com.example.restapi.web.UserDetailProfilesController;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class DetailProfilesResource {

    @JsonUnwrapped
    private EntityModel entityModel;

    public DetailProfilesResource(DetailProfiles content, Link... links) {
        entityModel = EntityModel.of(content, links)

                .add(linkTo(methodOn(UserDetailProfilesController.class).getUserProfile(null)).withRel("show"))
                .add(linkTo(methodOn(UserDetailProfilesController.class).updateUserProfile(null,null,null)).withRel("update"));
    }
}
