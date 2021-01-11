package com.example.restapi.domain.posts;


import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.posts.like.PostLike;
import com.example.restapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Post extends LocalDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)      // post : user = Main(주) : sub(자식)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "post",  cascade = {CascadeType.ALL})
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<PostLike> like;

    public boolean isEqualUserEmail(String userEmail){

        return userEmail.equals(getUser().getEmail());
    }

    public boolean isEqualUserEmail(Optional<User> loginUser){

        return loginUser.map(u -> user.getEmail().equals(u.getEmail()))
                .orElse(false);
    }

    public boolean isContainLikeUsers(Optional<User> loginUser){

        return loginUser.map(u -> {
            for (PostLike postLike : like) {
                if (postLike.getUser().getEmail().equals(u.getEmail())) return true;
            }
            return false;
        }).orElse(false);

    }

    public void update(String content) {
        this.content = content;
    }

    public PostAdapter toAdapter(Optional<User> loginUser){
        return PostAdapter.builder()
                .id(getId())
                .likes(Long.valueOf(getLike().size()))
                .content(getContent())
                .comments((Long.valueOf(getComments().size())))
                .modifyDate(getModifiedDate().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm")))
                .userEmail(getUser().getEmail())
                .userNickName(getUser().getNickName())
                .isLike(isContainLikeUsers(loginUser))
                .isWriter(isEqualUserEmail(loginUser))
                .build();
    }
}
