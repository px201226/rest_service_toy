package com.example.restapi.web.dto;

import com.example.restapi.domain.matching.result.MatchingResult;
import com.example.restapi.domain.posts.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;


@Getter
public class MatchingResultResponseDto {
    private Boolean isMatching;
    private String otherKakaoId;

    public MatchingResultResponseDto(Optional<MatchingResult> matchingResult, String userEmail){

        if(!matchingResult.isPresent()){
            isMatching = false;
            return;
        }

        isMatching = true;
        if(matchingResult.get().getUser().getEmail().equals(userEmail)){
            otherKakaoId = matchingResult.get().getAnotherUser().getKakaoId();
        }else{
            otherKakaoId = matchingResult.get().getUser().getKakaoId();
        }
    }
}
