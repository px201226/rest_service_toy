package com.example.restapi.web.dto;

import com.example.restapi.domain.posts.Post;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class PostsResponseDto {
    private Long id;
    private String content;
    private String author;
    private Long likes;
    private LocalDateTime modifiedDate;

    public PostsResponseDto(Post entity){
        this.id = entity.getId();
        this.content = entity.getContent();
        this.author = entity.getUser().getDetailProfiles().getName();
        this.likes = entity.getLikes();
        this.modifiedDate = entity.getModifiedDate();
    }
}
