package com.example.restapi.domain.user;

import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.comments.Comments;
import com.example.restapi.domain.posts.Posts;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;

//@JsonIgnoreProperties(value = {"password"})
//@JsonFilter("UserInfo")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")        // swagger2
@Entity                                                         // jpa가 자동으로 테이블로 만들기 위해 선언해야됨
public class User extends LocalDateTimeEntity {

    @Id                                                         // jpa가 자동으로 테이블을 만들기 위해 선언해야됨
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message="이메일 형식이 올바르지 않습니다.")
    @Column(nullable = false, unique = true)
    private String email;

    // @JsonIgnore                                               //Json 응답에 숨김
    @Size(min = 2)
    private String password;

    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Posts> posts;

    @OneToMany(mappedBy = "user")
    private List<Comments> comments;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

}
