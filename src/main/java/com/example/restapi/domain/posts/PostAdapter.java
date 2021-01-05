package com.example.restapi.domain.posts;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostAdapter extends RepresentationModel<PostAdapter> {
    private Long id;
    private String content;
    private Long likes;
    private String userEmail;
    private String userNickName;
    private String modifyDate;
    private Long comments;
    private Boolean isLike;

    public PostAdapter setIsLike(boolean bool){
        isLike = bool;
        return this;
    }
}
