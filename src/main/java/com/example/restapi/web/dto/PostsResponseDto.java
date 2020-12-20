package com.example.restapi.web.dto;

import com.example.restapi.domain.posts.Posts;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class PostsResponseDto {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime modifiedDate;
    private String writerEmail;


    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.content = entity.getContent();
        this.author = entity.getUser().getName();
        this.modifiedDate = entity.getModifiedDate();
        this.writerEmail = entity.getUser().getEmail();
    }
}
