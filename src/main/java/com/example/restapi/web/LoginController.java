package com.example.restapi.web;

import com.example.restapi.domain.matching.MatchingResult;
import com.example.restapi.domain.matching.MatchingResultRepository;
import com.example.restapi.domain.matching.Participant;
import com.example.restapi.domain.matching.ParticipantRepository;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.config.security.JwtTokenProvider;
import com.example.restapi.domain.auth.TokenWrapper;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.GuestResource;
import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.domain.user.profile.DreamProfiles;
import com.example.restapi.domain.user.profile.category.BodyType;
import com.example.restapi.domain.user.profile.category.LocationCategory;
import com.example.restapi.domain.user.profile.category.TallType;
import com.example.restapi.exception.exceptions.EmailSigninFailedException;
import com.example.restapi.service.matching.MatchingService;
import com.example.restapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class LoginController {


    private final ResponseService responseService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final MatchingManager matchingManager;
    private final ParticipantRepository participantRepository;
    private final MatchingService matchingService;
    private final MatchingResultRepository matchingResultRepository;

    @GetMapping(value = "test")

    public void matching() {

        userSetting();
        //given
        int expectMatchingResultEntities = 10 / 2;

        matchingService.matching();
        List<User> all = userRepository.findAll();
        List<Participant> waits = participantRepository.findAll();
        List<MatchingResult> results = matchingResultRepository.findAll();
        int afterWaitEntities = waits.size();
        int afterMatchingEntity = results.size();
        
    }

    public void userSetting() {


        List<User> users = getAuthUsers(10);

        System.out.println("0000000000000");
        List<Participant> matchingWaitEntities = new ArrayList<>();
        for(int i=0; i<10; i++){
            Participant build = Participant.builder()
                    .user(users.get(i))
                    .nextMatchingDate(matchingManager.getNextMatchingDate(LocalDate.now()))
                    .build();

            matchingWaitEntities.add(build);
        }
        System.out.println("1111111111");
        participantRepository.saveAll(matchingWaitEntities);
        System.out.println("2222222222");
    }
    public List<User> getAuthUsers(int size){
        List<User> users = new ArrayList<>();
        System.out.println("usercount="+ userRepository.findAll().size());
        for (int i = 0; i < size; i++) {
            User build = User.builder()
                    .email("px100"+ i + "@naver.com")
                    .password(passwordEncoder.encode("aa1aa1"))
                    .dreamProfiles(dreamProfiles())
                    .detailProfiles(detailProfiles())
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
            users.add(build);
        }

        userRepository.saveAll(users);
        System.out.println("usercount="+ userRepository.findAll().size());
        return users;
    }

    public DetailProfiles detailProfiles() {
        return DetailProfiles.builder()
                .bodyType(BodyType.SKINNY)
                .tallType(TallType.NORMAL)
                .locationCategory(LocationCategory.BUSAN)
                .build();
    }

    public DreamProfiles dreamProfiles(){
        return DreamProfiles.builder()
                .bodyType(BodyType.SKINNY)
                .locationCategory(LocationCategory.BUSAN)
                .tallType(TallType.SMALL)
                .build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity signin( @RequestBody User reqUser) {

        User user = userRepository.findByEmail(reqUser.getEmail()).orElseThrow(EmailSigninFailedException::new);
        if (!passwordEncoder.matches(reqUser.getPassword(), user.getPassword()))
            throw new EmailSigninFailedException("아이디/비밀번호가 틀립니다.");

        String jwtToken = jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
        TokenWrapper tokenWrapper = TokenWrapper.builder()
                .jwtToken(jwtToken)
                .build();

        ResponseData<TokenWrapper> response = responseService.create(ResponseStatus.SUCCESS, tokenWrapper);
        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/join")
    public ResponseEntity join(
            @Valid @RequestBody User user,
            Errors errors) {

        if(errors.hasErrors()) {
            ResponseData<Errors> response = responseService.create(ResponseStatus.INVALID_REQUEST_PARAMETER_ERROR, errors);
            return ResponseEntity.badRequest().body(response);
        }

        User joinUser = userService.join(user);
        GuestResource resource = new GuestResource(joinUser);
       ResponseData<GuestResource> response = responseService.create(ResponseStatus.SUCCESS, resource);;
        return ResponseEntity.ok(joinUser);
    }

}