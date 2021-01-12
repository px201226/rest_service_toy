package com.example.restapi.domain.matching;

import com.example.restapi.web.MatchingCotroller;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ParticipantResource {

    @JsonUnwrapped
    private EntityModel entityModel;
    public ParticipantResource(Participant content, Link... links) {
        entityModel = EntityModel.of(content, links)
                .add(linkTo(MatchingCotroller.class).slash("").withSelfRel());
    }
}
