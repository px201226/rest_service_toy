package com.example.restapi.domain.user.profile;

import com.example.restapi.web.UserDetailProfilesController;
import com.example.restapi.web.UserDreamProfilesController;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class DreamProfilesResource {

    @JsonUnwrapped
    private EntityModel entityModel;

    public DreamProfilesResource(DreamProfiles content, Link... links) {
        entityModel = EntityModel.of(content, links)

                .add(linkTo(methodOn(UserDreamProfilesController.class).getDreamProfile(null)).withRel("show"))
                .add(linkTo(methodOn(UserDreamProfilesController.class).updateDreamProfile(null,null)).withRel("update"));
    }
}
