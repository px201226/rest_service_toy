package com.example.restapi.domain.posts;

import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.comments.CommentModel;
import com.example.restapi.domain.user.UserModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

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
    private String userEmail;c
    private String userNickName;
    private List<CommentModel> comments;
}
