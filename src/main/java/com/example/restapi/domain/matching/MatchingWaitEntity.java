package com.example.restapi.domain.matching;

import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class MatchingWaitEntity extends LocalDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnore
    private User user;

    private LocalDate nextMatchingDate;

    MatchingWaitEntity toEntity(User user){
        return MatchingWaitEntity.builder()
                .user(user)
                .nextMatchingDate(LocalDate.now())
                .build();
    }

}
