package com.example.restapi.domain.posts;


import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.posts.like.PostLike;
import com.example.restapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Array;
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
        return this.user.getEmail().equals(userEmail);
    }

    public boolean isContainLikeUsers(User user){
        if(user == null) return false;

        for(PostLike postLike : like){
            if(postLike.getUser().getEmail().equals(user.getEmail())) return true;
        }
        return false;
    }

    public void update(String content) {
        this.content = content;
    }

    public PostAdapter toAdapter(Post entity, User user){
        return PostAdapter.builder()
                .id(entity.getId())
                .likes(Long.valueOf(entity.getLike().size()))
                .content(entity.getContent())
                .comments((Long.valueOf(entity.getComments().size())))
                .modifyDate(entity.getModifiedDate().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm")))
                .userEmail(entity.getUser().getEmail())
                .userNickName(entity.getUser().getNickName())
                .isLike(isContainLikeUsers((user)))
                .isWirter(isEqualUserEmail(user.getEmail()))
                .build();
    }
}
