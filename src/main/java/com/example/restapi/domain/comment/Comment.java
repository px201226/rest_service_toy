package com.example.restapi.domain.comment;


import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.user.User;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
public class Comment extends LocalDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String content;

    //@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
