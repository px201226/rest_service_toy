package com.example.restapi.domain.posts;


import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;


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
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public boolean isEqualUserEmail( String userEmail){
        return this.user.getEmail().equals(userEmail);
    }

    public void update(String content) {
        this.content = content;
    }
}
