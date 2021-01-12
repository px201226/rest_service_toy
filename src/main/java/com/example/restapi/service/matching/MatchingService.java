package com.example.restapi.service.matching;


import com.example.restapi.domain.matching.*;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.exception.exceptions.MatchedResultNotFoundException;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MatchingService {

    private final MatchingManager matchingManager;
    private final ParticipantRepository participantRepository;
    private final MatchingResultRepository matchingResultRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());


    @Transactional
    public void matching() {

        List<Participant> all = participantRepository.findAll();
        List<User> users = all.stream().map(
                (x) -> x.getUser()).collect(Collectors.toList());
        logger.info("matching Paritipant count = " + users.size());
        List<Pair<User, User>> matchingPairs = matchingManager.getMatchingRandomPairsFrom(users);

        LocalDate nextMatchingDate = matchingManager.getNextMatchingDate(LocalDate.now());
        //List<MatchingResult> matchingResults = MatchingResult.getResultsTo(matchingPairs, nextMatchingDate);

        List<MatchingResult> matchingResults = MatchingResult.getResultsFrom(matchingPairs, LocalDate.now());

   //     matchedDateRepository.save(MatchedDateHistory.builder().matchedDate(nextMatchingDate).build());
        matchingResultRepository.saveAll(matchingResults);
        participantRepository.deleteAllInBatch();
    }

    @Transactional
    public MatchingResult findMatchedResult(String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        MatchingResult matchingResult = matchingResultRepository.findByUserIdAndMatchingDate(user.getId(), user.getLastMatchingDate())
                .orElse(
                        matchingResultRepository.findByAnotherUserIdAndMatchingDate(user.getId(), user.getLastMatchingDate())
                .orElseThrow(MatchedResultNotFoundException::new));

        return matchingResult;
    }
}
