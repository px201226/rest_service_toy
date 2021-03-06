package com.example.restapi.domain.matching.participant;

import com.example.restapi.domain.LocalDateTimeEntity;
import com.example.restapi.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Participant extends LocalDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    private LocalDate nextMatchingDate;

    Participant toEntity(User user){
        return Participant.builder()
                .user(user)
                .nextMatchingDate(LocalDate.now())
                .build();
    }

}
