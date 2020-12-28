package com.example.restapi.domain.comments;


import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.posts.PostModel;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentModel  extends RepresentationModel<CommentModel> {

    private Long id;
    private Long postId;
    private String content;
    private String userEmail;
    private String userNickName;

}
