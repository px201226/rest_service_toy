package com.example.restapi.domain.comment;


import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Data
@Entity
public class Comment extends LocalDateTimeEntity {

    @Id                                                         // jpa가 자동으로 테이블을 만들기 위해 선언해야됨
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String content;

    //@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
