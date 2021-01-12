package com.example.restapi.domain.matching;

import com.example.restapi.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingResultRepository extends JpaRepository<MatchingResult,Long> {

    Optional<MatchingResult> findByUserIdAndMatchingDate(Long userId,LocalDate matchingDate);
    Optional<MatchingResult> findByAnotherUserIdAndMatchingDate(Long anotherUserId,LocalDate matchingDate);
}
