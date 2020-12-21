package com.example.restapi.web.dto;


import com.example.restapi.domain.posts.Posts;
import com.example.restapi.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsSaveRequestDto {

    @NotEmpty
    private String content;

    public Posts toEntity(User user){
        return Posts.builder()
                .content(content)
                .likes(0L)
                .user(user)
                .build();
    }
}
