package com.example.restapi.domain.posts;


import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Posts extends LocalDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "내용을 입력하세요.")
    private String content;

    @NotEmpty(message = "내용을 입력하세요.")
    private Integer likes;

    //@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)      // post : user = n : 1
    private User user;

}
