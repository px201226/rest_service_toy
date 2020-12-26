package com.example.restapi.service.matching;


import com.example.restapi.domain.matching.*;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
        List<User> collect = all.stream().map((x) -> x.getUser()).collect(Collectors.toList());
        List<Pair<User, User>> pairs = matchingManager.matchingRandom(collect);

        List<MatchingResult> matchingResults = new ArrayList<>();
        LocalDate nextMatchingDate = matchingManager.getNextMatchingDate(LocalDate.now());

        Iterator<Pair<User, User>> iterator = pairs.iterator();
        while (iterator.hasNext()) {
            Pair<User, User> next = iterator.next();
            MatchingResult build = MatchingResult.builder()
                    .user(next.getFirst())
                    .anotherUser(next.getSecond())
                    .matchingDate(nextMatchingDate)
                    .build();

            matchingResults.add(build);
        }


        List<MatchingResult> matchingResults1 = matchingResultRepository.saveAll(matchingResults);

        List<MatchingResult> all1 = matchingResultRepository.findAll();
        System.out.println("matching----------");
        System.out.println(Arrays.toString(matchingResults.toArray()));
        System.out.println(Arrays.toString(all1.toArray()));
        matchingWaitEntityRepository.deleteAllInBatch();
    }
}
