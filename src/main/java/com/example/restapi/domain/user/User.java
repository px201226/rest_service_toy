package com.example.restapi.domain.user;

import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.matching.Identifiable;
import com.example.restapi.domain.matching.MatchingScoreComparable;
import com.example.restapi.domain.matching.Participant;
import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.posts.like.PostLike;
import com.example.restapi.domain.user.profile.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

//@JsonIgnoreProperties(value = {"password"})
//@JsonFilter("UserInfo")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity                                                         // jpa가 자동으로 테이블로 만들기 위해 선언해야됨
public class User extends LocalDateTimeEntity
        implements Comparable<User>, Identifiable<Long>, MatchingScoreComparable<User> {

    @Id                                                         // jpa가 자동으로 테이블을 만들기 위해 선언해야됨
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message="이메일 형식이 올바르지 않습니다.")
    @Column(nullable = false, unique = true)
    private String email;

    private String nickName;

    @Size(min = 2)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<PostLike> like;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY )
    private Participant participant;

    @Embedded
    private DetailProfiles detailProfiles;

    @Embedded
    private DreamProfiles dreamProfiles;                              // 이상형 프로필

    private LocalDate lastMatchingDate;

    private String kakaoId;

    // Identifiable interface 구현
    @Override
    public Long getIdentify() {
        return getId();
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }

    @Override
    public int compareTo(User o) {
        return this.getId().compareTo(o.id);
    }

    //MatchingScoreComparable interface 구현
    @Override
    public int compareObjective(User other) {
        return dreamProfiles.compareObjective(other.detailProfiles);
    }

    public User updateMyDetailProfiles(DetailProfiles detailProfiles){
        this.detailProfiles = detailProfiles;
        return this;
    }

    public User updateDreamProfiles(DreamProfiles dreamProfiles) {
        this.dreamProfiles = dreamProfiles;
        return this;
    }

    public User updateUser(User reqeustUpdateUserDto) {
        this.dreamProfiles = reqeustUpdateUserDto.getDreamProfiles();
        this.detailProfiles = reqeustUpdateUserDto.getDetailProfiles();
        this.kakaoId = kakaoId;
        return this;
    }

    public User updateLastMatchingDate(LocalDate localDate){
        this.lastMatchingDate = localDate;
        return this;
    }

}
