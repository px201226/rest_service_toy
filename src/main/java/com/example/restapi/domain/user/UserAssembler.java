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
        userModel.setComments(Long.valueOf(entity.getComments().size()));
        userModel.setDetailProfiles(entity.getDetailProfiles());
        userModel.setDreamProfiles(entity.getDreamProfiles());
        userModel.setEmail(entity.getEmail());
        userModel.setId(entity.getId());
        userModel.setNickName(entity.getNickName());
        userModel.setKakaoId(entity.getKakaoId());
        userModel.setLastMatchingDate(entity.getLastMatchingDate());
        userModel.setPosts(Long.valueOf(entity.getPosts().size()));
        userModel.setSexType(entity.getSexType());
        userModel.add(linkTo(UserController.class).slash("").withSelfRel())
                .add(linkTo(methodOn(UserController.class).getPostList(null)).withRel("posts"))
                .add(linkTo(methodOn(UserController.class).getCommentList(null)).withRel("comments"));
             //   .add(linkTo("/docs/index.html#resources-user").withRel("documentation_url"));

        return userModel;

    }

    /* 사용자의 Posts를 HAL로 표현한다.
     *  Lazy Loding 으로 사용 안됨 */
    @Deprecated
    private List<PostModel> toPostModel(List<Post> posts) {
        if (posts.isEmpty())
            return Collections.emptyList();

        return posts.stream()
                .map(post -> PostModel.builder()
                        .id(post.getId())
                        .content(post.getContent())
                        .likes(Long.valueOf(post.getLike().size()))
                        .build()
                        .add(linkTo(
                                methodOn(PostController.class).findById(post.getId(), null)).withSelfRel()))
                .collect(Collectors.toList());
    }


    /* 사용자의 Comment를 HAL로 표현한다.
     *  Lazy Loding 으로 사용 안됨 */
    @Deprecated
    private List<CommentModel> toCommentModel(List<Comment> comments) {
        if (comments.isEmpty()) return Collections.emptyList();

        return comments.stream()
                .map(comment -> CommentModel.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .postId(comment.getPost().getId())
                        .userNickName(comment.getUser().getNickName())
                        .userEmail(comment.getUser().getEmail())
                        .build().add(linkTo(methodOn(CommentsController.class).findById(comment.getPost().getId(), comment.getId(), null)).withSelfRel()))
                .collect(Collectors.toList());
    }
}
