package com.example.restapi.domain.comments;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentAdapter {

    private Long id;
    private Long postId;
    private String content;
    private String userEmail;
    private String userNickName;
    private String modifyDate;
    private Boolean isWriter;

    public CommentAdapter setIsWriter(boolean bool){
        isWriter = bool;
        return this;
    }
}
