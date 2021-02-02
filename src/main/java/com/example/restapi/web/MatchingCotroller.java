package com.example.restapi.web;

import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.matching.participant.Participant;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.matching.participant.ParticipantAssembler;
import com.example.restapi.domain.matching.participant.ParticipantModel;
import com.example.restapi.domain.matching.result.MatchingResult;
import com.example.restapi.exception.response.ResponseData;
import com.example.restapi.exception.response.ResponseService;
import com.example.restapi.exception.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.service.matching.ParticipantService;
import com.example.restapi.service.matching.MatchingService;
import com.example.restapi.web.dto.MatchingResultResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/matching")
public class MatchingCotroller {

    private final MatchingManager matchingManager;
    private final ParticipantService participantService;
    private final MatchingService matchingService;
    private final ParticipantAssembler participantAssembler;
    private final ResponseService responseService;

    @PostMapping(value = "/apply")
    public ResponseEntity apply(@AuthUser User user){
        participantService.apply(user.getEmail());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/nextDay")
    public ResponseEntity getNextMatchingDay() throws IOException {
        LocalDateTime nextDay = matchingManager.getNextMatchingDate(LocalDate.now()).atStartOfDay();
        EntityModel<Map> entityModel = getNextDayEntityModel(nextDay);
        return ResponseEntity.ok(entityModel);
    }


    @GetMapping(value = "/result")
    public ResponseEntity getMatchingResult(@AuthUser User user){

        Optional<MatchingResult> matchedResult = matchingService.findMatchedResult(user.getEmail());
        MatchingResultResponseDto matchingResultResponseDto =
                new MatchingResultResponseDto(matchedResult, user.getEmail());

        EntityModel<MatchingResultResponseDto> entityModel = EntityModel.of(matchingResultResponseDto);
        entityModel.add(
                linkTo(methodOn(MatchingCotroller.class).getMatchingResult(null)).withSelfRel()
        );

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping(value = "/test")
    public ResponseEntity matching(){
        matchingService.matching();
        return ResponseEntity.ok("sucess");
    }

    private EntityModel<Map> getNextDayEntityModel(LocalDateTime nextDay) throws IOException {
        Map<String, LocalDateTime> map = new HashMap<>();
        map.put("nextDay", nextDay);
        EntityModel<Map> entityModel = EntityModel.of(map);
        entityModel.add(linkTo(methodOn(MatchingCotroller.class).getNextMatchingDay()).withSelfRel());
        return entityModel;
    }

}
