package com.example.restapi.domain.matching;


import com.example.restapi.domain.user.User;
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
public class MatchingResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    @OneToOne
//    @JoinColumn(name="user_id", insertable = false, updatable = false)
//    private User user;
//
//    @OneToOne
//    @JoinColumn(name="another_user_id",insertable = false, updatable = false)
//    private User anotherUser;

    private LocalDate matchingDate;
}
