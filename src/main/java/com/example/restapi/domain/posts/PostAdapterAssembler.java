package com.example.restapi.domain.posts;


import com.example.restapi.web.PostController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PostAdapterAssembler extends RepresentationModelAssemblerSupport<PostAdapter, PostModel> {

    public PostAdapterAssembler() {
        super(PostController.class, PostModel.class);
    }


    @Override
    public PostModel toModel(PostAdapter entity) {
       return PostModel.builder()
               .id(entity.getId())
               .likes(entity.getLikes())
               .content(entity.getContent())
               .comments(entity.getComments())
               .modifyDate(entity.getModifyDate())
               .userEmail(entity.getUserEmail())
               .userNickName(entity.getUserNickName())
               .isLike(entity.getIsLike())
               .isWriter(entity.getIsWirter())
               .build()
               .add(linkTo(PostController.class).slash(entity.getId()).withSelfRel())
               .add(linkTo(PostController.class).slash(entity.getId()).slash("comments").withRel("comments"));
    }

    @Override
    public CollectionModel<PostModel> toCollectionModel(Iterable<? extends PostAdapter> entities)
    {
        CollectionModel<PostModel> models = super.toCollectionModel(entities);
        return models;
    }
}
