package com.example.restapi.service.matching;


import com.example.restapi.domain.matching.Participant;
import com.example.restapi.domain.matching.ParticipantRepository;
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
public class MatchingApplyService {

    private final MatchingManager matchingManager;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    @Transactional
    public Participant apply(String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Optional<Participant> byUser = participantRepository.findByUser(user);

        if(byUser.isPresent()){
            throw new AlreadyApplyException();
        }

        Participant build = Participant.builder()
                .user(user)
                .nextMatchingDate(matchingManager.getNextMatchingDate(LocalDate.now()))
                .build();

        user.updateLastMatchingDate(LocalDate.now());

        return participantRepository.save(build);
    }

    @Transactional
    public Participant isApply(String userEmail){
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Optional<Participant> byUser = participantRepository.findByUser(user);
        if(!byUser.isPresent()){
            throw new MatchingApplyNotFoundException();
        }
        return byUser.get();
    }
}
