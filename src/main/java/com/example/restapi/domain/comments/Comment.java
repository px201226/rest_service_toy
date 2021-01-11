package com.example.restapi.domain.comments;
import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Comment extends LocalDateTimeEntity {


    @Id                                                         // jpa가 자동으로 테이블을 만들기 위해 선언해야됨
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Post post;

    public void update(String content) {
        this.content = content;
    }

    public boolean isEqualUserEmail(Optional<User> loginUser){

        return loginUser.map(u -> user.getEmail().equals(u.getEmail()))
                .orElse(false);
    }

    public CommentAdapter toAdapter(Optional<User> loginUser){
        return  CommentAdapter.builder()
                .id(getId())
                .postId(getPost().getId())
                .content(getContent())
                .userEmail(getUser().getEmail())
                .userNickName(getUser().getNickName())
                .modifyDate(getModifiedDate().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm")))
                .isWriter(isEqualUserEmail(loginUser))
                .build();
    }
}
