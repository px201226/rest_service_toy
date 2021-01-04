package com.example.restapi.domain.posts;


import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.posts.like.PostLike;
import com.example.restapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    private Long likes;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)      // post : user = Main(주) : sub(자식)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "post",  cascade = {CascadeType.ALL})
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<PostLike> like = new HashSet<>();

    public boolean isEqualUserEmail( String userEmail){
        return this.user.getEmail().equals(userEmail);
    }

    public void update(String content) {
        this.content = content;
    }
}
