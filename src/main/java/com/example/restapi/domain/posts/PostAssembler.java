package com.example.restapi.domain.posts;


import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.comments.CommentModel;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserModel;
import com.example.restapi.web.PostController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

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
               .likes(entity.getLikes())
               .content(entity.getContent())
               //.comments(toCommentModel(entity.getComments()))
               .userEmail(entity.getUser().getEmail())
               .userNickName(entity.getUser().getNickName())
               .build()
               .add(linkTo(PostController.class).slash(entity.getId()).withSelfRel());
    }

    @Override
    public CollectionModel<PostModel> toCollectionModel(Iterable<? extends Post> entities)
    {

        CollectionModel<PostModel> models = super.toCollectionModel(entities);
        return models;
    }


    private List<CommentModel> toCommentModel(List<Comment> comments) {
        if (comments.isEmpty())
            return Collections.emptyList();

        return comments.stream()
                .map(comment ->  CommentModel.builder()
                        .id(comment.getId())
                        .postId(comment.getPost().getId())
                        .content(comment.getContent())
                        .userEmail(comment.getUser().getEmail())
                        .userNickName(comment.getUser().getNickName())
                        .build())
                .collect(Collectors.toList());
    }

}
