/*
package com.example.restapi.web.dto;


import com.example.restapi.domain.posts.Posts;
import com.example.restapi.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsSaveRequestDto {

    private String content;
    private String userEmail;

    public Posts toEntity(User user){
        return Posts.builder()
                .content(content)
                .user(user)
                .build();
    }
}
*/
