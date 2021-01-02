package com.example.restapi.domain.user;


import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.comments.CommentModel;
import com.example.restapi.domain.posts.PostModel;
import com.example.restapi.domain.posts.Post;
import com.example.restapi.web.*;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {


    public UserAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(User entity) {

        UserModel userModel = new UserModel();
        userModel.setComments(toCommentModel(entity.getComments()));
        userModel.setDetailProfiles(entity.getDetailProfiles());
        userModel.setDreamProfiles(entity.getDreamProfiles());
        userModel.setEmail(entity.getEmail());
        userModel.setId(entity.getId());
        userModel.setNickName(entity.getNickName());
        userModel.setKakaoId(entity.getKakaoId());
        userModel.setLastMatchingDate(entity.getLastMatchingDate());
        userModel.setPosts(toPostModel(entity.getPosts()));
        userModel.add(linkTo(UserController.class).slash("").withSelfRel())
                .add(linkTo(methodOn(UserDetailProfilesController.class).getUserProfile(null)).withRel("detailProfiles"))
                .add(linkTo(methodOn(UserDreamProfilesController.class).getDreamProfile(null)).withRel("dreamProfiles"));

        return userModel;

    }
    private List<PostModel> toPostModel(List<Post> posts) {
        if (posts.isEmpty())
            return Collections.emptyList();

        return posts.stream()
                .map(post -> PostModel.builder()
                        .id(post.getId())
                        .content(post.getContent())
                        .likes(post.getLikes())
                        .build()
                        .add(linkTo(
                                methodOn(PostController.class).findById(post.getId())).withSelfRel()))
                .collect(Collectors.toList());
    }

    private List<CommentModel> toCommentModel(List<Comment> comments){
        if(comments.isEmpty()) return Collections.emptyList();

        return comments.stream()
                .map(comment -> CommentModel.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postId(comment.getPost().getId())
                .userNickName(comment.getUser().getNickName())
                        .userEmail(comment.getUser().getEmail())
                .build().add(linkTo(methodOn(CommentsController.class).getList(comment.getPost().getId(), comment.getId())).withSelfRel()))
                .collect(Collectors.toList());
    }
}
