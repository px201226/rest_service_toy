package com.example.restapi.web.dto;

import com.example.restapi.domain.posts.Post;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class PostsListResponseDto {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime modifiedDate;
    private String writerEmail;


    public PostsListResponseDto(Post entity){
        this.id = entity.getId();
        this.content = entity.getContent();
        this.author = entity.getUser().getNickName();
        this.modifiedDate = entity.getModifiedDate();
        this.writerEmail = entity.getUser().getEmail();
    }
}
