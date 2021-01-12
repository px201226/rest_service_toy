package com.example.restapi.web;

import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.matching.MatchingResult;
import com.example.restapi.domain.matching.Participant;
import com.example.restapi.domain.matching.ParticipantResource;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.exception.high.ServiceAcessDeniedException;
import com.example.restapi.service.matching.MatchingApplyService;
import com.example.restapi.service.matching.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/matching")
public class MatchingCotroller {

    private final MatchingManager matchingManager;
    private final MatchingApplyService matchingApplyService;
    private final MatchingService matchingService;
    private final ResponseService responseService;

    @GetMapping(value = "/apply")
    public ResponseEntity isApply(@AuthUser User user){
        Participant apply = matchingApplyService.isApply(user.getEmail());
        ParticipantResource resource = new ParticipantResource(apply);

        ResponseData<ParticipantResource> responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping(value = "/apply")
    public ResponseEntity apply(@AuthUser User user){
        Participant apply = matchingApplyService.apply(user.getEmail());

        return ResponseEntity.ok(apply);
    }

    @GetMapping(value = "/nextDay")
    public ResponseEntity getNextMatchingDay(){
        LocalDateTime endTime = matchingManager.getNextMatchingDate(LocalDate.now()).atStartOfDay();
//        LocalDateTime startTime = LocalDateTime.now();
//        Duration duration = Duration.between(startTime,endTime);

        return ResponseEntity.ok(endTime);
    }

    @GetMapping(value = "/result")
    public ResponseEntity getMatchingResult(@AuthUser User user){

        MatchingResult matchingResult = Optional.ofNullable(user)
                .map(u -> matchingService.findMatchedResult(u.getEmail()))
                .orElseThrow(ServiceAcessDeniedException::new);

        return ResponseEntity.ok(matchingResult);
    }

    @GetMapping(value = "/test")
    public ResponseEntity matching(){
        matchingService.matching();
        return ResponseEntity.ok("sucess");
    }
}
