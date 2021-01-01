package com.example.restapi.domain.user;


import com.example.restapi.domain.posts.PostModel;
import com.example.restapi.domain.posts.Post;
import com.example.restapi.web.PostController;
import com.example.restapi.web.UserController;
import com.example.restapi.web.UserDetailProfilesController;
import com.example.restapi.web.UserDreamProfilesController;
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
        System.out.println("dddddddddddddddd");
        UserModel userModel = new UserModel();
       // userModel.setComments(entity.getComments());
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
        System.out.println("dddddddddddddddd");
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
}
