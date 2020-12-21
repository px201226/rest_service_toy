package com.example.restapi.web.dto;

import com.example.restapi.domain.comments.Comments;
import com.example.restapi.domain.posts.Posts;
import com.example.restapi.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsSaveRequestDto {

    @NotEmpty
    private String content;

    public Comments toEntity(User user, Posts posts){
        return Comments.builder()
                .content(content)
                .user(user)
                .posts(posts)
                .build();
    }
}
