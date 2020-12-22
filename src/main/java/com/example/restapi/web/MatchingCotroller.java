package com.example.restapi.web;

import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.matching.MatchingWaitEntity;
import com.example.restapi.domain.matching.MatchingWaitEntityResource;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.service.matching.MatchingEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/matching")
public class MatchingCotroller {

    private final MatchingManager matchingManager;
    private final MatchingEntityService matchingEntityService;
    private final ResponseService responseService;

    @GetMapping
    public ResponseEntity isApply(@AuthUser User user){
        MatchingWaitEntity apply = matchingEntityService.isApply(user.getEmail());
        MatchingWaitEntityResource resource = new MatchingWaitEntityResource(apply);

        ResponseData<MatchingWaitEntityResource> responseData = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping
    public ResponseEntity apply(@AuthUser User user){
        MatchingWaitEntity apply = matchingEntityService.apply(user.getEmail());

        return ResponseEntity.ok(apply);
    }
}
