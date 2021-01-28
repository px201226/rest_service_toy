package com.example.restapi.domain.comments;

import com.example.restapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDto {

    private Long id;
    private Long postId;
    private String content;
    private String userEmail;
    private String userNickName;
    private String modifyDate;
    private Boolean isWriter;

    public CommentResponseDto setIsWriter(boolean bool){
        isWriter = bool;
        return this;
    }

    public CommentResponseDto(Comment comment, Optional<User> loginUser){
      this.id = comment.getId();
      this.postId = comment.getPost().getId();
      this.content = comment.getContent();
      this.userEmail = comment.getUser().getEmail();
      this.userNickName = comment.getUser().getNickName();
      this.modifyDate = comment.getModifiedDate().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm"));
      this.isWriter = isEqualUserEmail(comment.getUser(), loginUser);
    }

    public boolean isEqualUserEmail(User user, Optional<User> loginUser){
        return loginUser.map(u -> user.getEmail().equals(u.getEmail()))
                .orElse(false);
    }
}
