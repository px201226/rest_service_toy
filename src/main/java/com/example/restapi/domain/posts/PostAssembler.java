package com.example.restapi.domain.posts;


import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.comments.CommentModel;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserModel;
import com.example.restapi.web.CommentsController;
import com.example.restapi.web.PostController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostAssembler extends RepresentationModelAssemblerSupport<Post, PostModel> {

    public PostAssembler() {
        super(PostController.class, PostModel.class);
    }


    @Override
    public PostModel toModel(Post entity) {
       return PostModel.builder()
               .id(entity.getId())
               .likes(Long.valueOf(entity.getLike().size()))
               .content(entity.getContent())
               .comments((Long.valueOf(entity.getComments().size())))
               .modifyDate(entity.getModifiedDate().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm")))
               .userEmail(entity.getUser().getEmail())
               .userNickName(entity.getUser().getNickName())
               .build()
               .add(linkTo(PostController.class).slash(entity.getId()).withSelfRel())
               .add(linkTo(PostController.class).slash(entity.getId()).slash("comments").withRel("comments"));
    }

    @Override
    public CollectionModel<PostModel> toCollectionModel(Iterable<? extends Post> entities)
    {
        CollectionModel<PostModel> models = super.toCollectionModel(entities);
        return models;
    }
}
