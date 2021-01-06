package com.example.restapi.domain.posts;

import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.comments.CommentModel;
import com.example.restapi.domain.posts.like.PostLike;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostModel extends RepresentationModel<PostModel> {
    private Long id;
    private String content;
    private Long likes;
    private String userEmail;
    private String userNickName;
    private String modifyDate;
    private Long comments;
    private Boolean isLike;
    private Boolean isWriter;

}
