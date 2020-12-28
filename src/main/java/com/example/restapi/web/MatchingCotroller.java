package com.example.restapi.web;

import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.matching.MatchingWaitEntity;
import com.example.restapi.domain.matching.MatchingWaitEntityResource;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.service.matching.MatchingApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/matching")
public class MatchingCotroller {

    private final MatchingManager matchingManager;
    private final MatchingApplyService matchingApplyService;
    private final ResponseService responseService;

    @GetMapping(value = "/apply")
    public ResponseEntity isApply(@AuthUser User user){
        MatchingWaitEntity apply = matchingApplyService.isApply(user.getEmail());
        MatchingWaitEntityResource resource = new MatchingWaitEntityResource(apply);

        ResponseData<MatchingWaitEntityResource> responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping(value = "/apply")
    public ResponseEntity apply(@AuthUser User user){
        MatchingWaitEntity apply = matchingApplyService.apply(user.getEmail());

        return ResponseEntity.ok(apply);
    }

    @GetMapping(value = "/time")
    public ResponseEntity remineTimeSeconds(){
        LocalDateTime endTime = matchingManager.getNextMatchingDate(LocalDate.now()).atStartOfDay();
        LocalDateTime startTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime,endTime);

        return ResponseEntity.ok(duration.getSeconds());
    }
}
