package com.example.restapi.service.matching;


import com.example.restapi.domain.matching.*;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.exception.exceptions.MatchedResultNotFoundException;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MatchingService {
    private final MatchingManager matchingManager;
    private final MatchingWaitEntityRepository matchingWaitEntityRepository;
    private final MatchingResultRepository matchingResultRepository;
    private final UserRepository userRepository;


    @Transactional
    public void matching() {

        List<MatchingWaitEntity> all = matchingWaitEntityRepository.findAll();
        List<User> users = all.stream().map(
                (x) -> x.getUser()).collect(Collectors.toList());

        List<Pair<User, User>> matchingPairs = matchingManager.getMatchingRandomPairsFrom(users);

        LocalDate nextMatchingDate = matchingManager.getNextMatchingDate(LocalDate.now());
        List<MatchingResult> matchingResults = MatchingResult.getResultsTo(matchingPairs, nextMatchingDate);

   //     matchedDateRepository.save(MatchedDateHistory.builder().matchedDate(nextMatchingDate).build());
        matchingResultRepository.saveAll(matchingResults);
        matchingWaitEntityRepository.deleteAllInBatch();
    }

    @Transactional
    public User findMatchedResult(String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        MatchingResult matchingResult = matchingResultRepository.findByUserIdAndMatchingDate(user.getId(), user.getLastMatchingDate())
                .orElseThrow(MatchedResultNotFoundException::new);

        return matchingResult.getAnotherUser();
    }
}
