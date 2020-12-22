package com.example.restapi.service.matching;


import com.example.restapi.domain.matching.MatchingWaitEntity;
import com.example.restapi.domain.matching.MatchingWaitEntityRepository;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.exception.exceptions.AlreadyApplyException;
import com.example.restapi.exception.exceptions.MatchingApplyNotFoundException;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MatchingEntityService {

    private final MatchingManager matchingManager;
    private final MatchingWaitEntityRepository matchingWaitEntityRepository;
    private final UserRepository userRepository;

    @Transactional
    public MatchingWaitEntity apply(String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Optional<MatchingWaitEntity> byUser = matchingWaitEntityRepository.findByUser(user);

        if(byUser.isPresent()){
            throw new AlreadyApplyException();
        }

        MatchingWaitEntity build = MatchingWaitEntity.builder()
                .user(user)
                .nextMatchingDate(matchingManager.getNextMatchingDate(LocalDate.now()))
                .build();

        return matchingWaitEntityRepository.save(build);
    }

    @Transactional
    public MatchingWaitEntity isApply(String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Optional<MatchingWaitEntity> byUser = matchingWaitEntityRepository.findByUser(user);
        if(!byUser.isPresent()){
            throw new MatchingApplyNotFoundException();
        }
        return byUser.get();
    }
}
