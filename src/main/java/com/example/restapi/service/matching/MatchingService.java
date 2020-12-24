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
        List<Identifiable> collect = all.stream().map((x) -> x.getUser()).collect(Collectors.toList());
        List<Pair> pairs = matchingManager.matchingRandom(collect);
        List<MatchingResult> matchingResults = new ArrayList<>();
        LocalDate nextMatchingDate = matchingManager.getNextMatchingDate(LocalDate.now());
        System.out.println("22222");

        Iterator<Pair> iterator = pairs.iterator();
        while (iterator.hasNext()) {
            Pair next = iterator.next();
            MatchingResult build = MatchingResult.builder()
                    .user(all.get(next.getFirst().hashCode()).getUser())
                    .anotherUser(all.get(next.getSecond().hashCode()).getUser())
                    .matchingDate(nextMatchingDate)
                    .build();

            matchingResults.add(build);
        }
        System.out.println("3333333");
        matchingResultRepository.saveAll(matchingResults);
        matchingWaitEntityRepository.deleteAllInBatch();
    }
}
