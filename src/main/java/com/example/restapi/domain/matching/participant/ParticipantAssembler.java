package com.example.restapi.domain.matching.participant;


import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.comments.CommentModel;
import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.posts.PostModel;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserModel;
import com.example.restapi.web.*;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ParticipantAssembler extends RepresentationModelAssemblerSupport<Participant, ParticipantModel> {


    public ParticipantAssembler() {
        super(MatchingCotroller.class, ParticipantModel.class);
    }

    @Override
    public ParticipantModel toModel(Participant entity) {

        return ParticipantModel.builder()
                .userEmail(entity.getUser().getEmail())
                .nextMatchingDate(entity.getNextMatchingDate())
                .build();

    }

}
