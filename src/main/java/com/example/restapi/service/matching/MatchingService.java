package com.example.restapi.service.matching;


import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.matching.result.MatchingResult;
import com.example.restapi.domain.matching.result.MatchingResultRepository;
import com.example.restapi.domain.matching.participant.Participant;
import com.example.restapi.domain.matching.participant.ParticipantRepository;
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

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
        List<MatchingResult> matchingResults = MatchingResult.getResultsFrom(matchingPairs, nextMatchingDate);

        matchingResultRepository.saveAll(matchingResults);
        participantRepository.deleteAllInBatch();
    }

    @Transactional
    public Optional<MatchingResult> findMatchedResult(String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        if(user.getLastMatchingDate() == null)
            throw new MatchedResultNotFoundException("참가한 적이 없습니다");


        if(LocalDate.now().isBefore(user.getLastMatchingDate()))
            throw new MatchedResultNotFoundException("조회기간이 아닙니다");

        MatchingResult matchingResult = matchingResultRepository.findByUserIdAndMatchingDate(user.getId(), user.getLastMatchingDate())
                .orElseGet(() ->
                        matchingResultRepository.findByAnotherUserIdAndMatchingDate(user.getId(), user.getLastMatchingDate())
                                .orElse(null));

        return Optional.ofNullable(matchingResult);
    }
}
