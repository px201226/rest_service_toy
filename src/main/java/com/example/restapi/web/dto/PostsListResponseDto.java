/*
package com.example.restapi.web.dto;

import com.example.restapi.domain.posts.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
public class PostsListResponseDto {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime modifiedDate;
    private String writerEmail;


    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.content = entity.getContent();
        this.author = entity.getUser().getName();
        this.modifiedDate = entity.getModifiedDate();
        this.writerEmail = entity.getUser().getEmail();
    }
}
*/
