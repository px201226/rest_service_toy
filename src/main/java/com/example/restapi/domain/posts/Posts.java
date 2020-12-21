package com.example.restapi.domain.posts;


import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.comments.Comments;
import com.example.restapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Posts extends LocalDateTimeEntity {

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
    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Comments> comments;

    public void update(String content) {
        this.content = content;
    }
}
