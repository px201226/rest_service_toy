package com.example.restapi.web.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String content;
    private String userEmail;
    @Builder
    public PostsUpdateRequestDto(String content, String userEmail){
        this.content = content;
        this.userEmail = userEmail;
    }
}
