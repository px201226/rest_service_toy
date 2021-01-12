package com.example.restapi.domain.matching;


import com.example.restapi.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class MatchingResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne
    @JoinColumn(name="another_user_id")
    private User anotherUser;

    private LocalDate matchingDate;

    @Override
    public String toString() {
        return String.format("(%d,%d)", user.getId(), anotherUser.getId());
    }

    public static List<MatchingResult> getResultsFrom(List<Pair<User, User>> matchingPairs, LocalDate localDate) {

        List<MatchingResult> matchingResults = new ArrayList<>();

        Iterator<Pair<User, User>> iterator = matchingPairs.iterator();
        while (iterator.hasNext()) {
            Pair<User, User> next = iterator.next();
            MatchingResult build = MatchingResult.builder()
                    .user(next.getFirst())
                    .anotherUser(next.getSecond())
                    .matchingDate(localDate)
                    .build();

            matchingResults.add(build);
        }

        return matchingResults;
    }


}
