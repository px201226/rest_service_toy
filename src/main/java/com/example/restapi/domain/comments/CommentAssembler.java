package com.example.restapi.domain.comments;


import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.posts.PostModel;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserModel;
import com.example.restapi.web.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentAssembler extends RepresentationModelAssemblerSupport<Comment, CommentModel> {


    public CommentAssembler() {
        super(CommentsController.class, CommentModel.class);
    }

    @Override
    public CommentModel toModel(Comment entity) {
        return  CommentModel.builder()
                .id(entity.getId())
                .postId(entity.getPost().getId())
                .content(entity.getContent())
                .userEmail(entity.getUser().getEmail())
                .userNickName(entity.getUser().getNickName())
                .build();
    }

    @Override
    public CollectionModel<CommentModel> toCollectionModel(Iterable<? extends Comment> entities)
    {
        CollectionModel<CommentModel> models = super.toCollectionModel(entities);
        return models;
    }

}
