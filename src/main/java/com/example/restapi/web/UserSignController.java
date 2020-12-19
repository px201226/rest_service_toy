package com.example.restapi.web;

import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.config.security.JwtTokenProvider;
import com.example.restapi.domain.auth.Token;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.UserResource;
import com.example.restapi.exception.EmailSigninFailedException;
import com.example.restapi.exception.UserNotFoundException;
import com.example.restapi.service.user.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserSignController {


    private final ResponseService responseService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/login")
    public ResponseEntity signin( @RequestParam String email,
                                  @RequestParam String password) {

        User user = userRepository.findByEmail(email).orElseThrow(EmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new EmailSigninFailedException("아이디/비밀번호가 틀립니다.");

        String jwtToken = jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
        Token token = Token.builder()
                .jwtToken(jwtToken)
                .build();

        ResponseData<Token> response = responseService.create(ResponseStatus.SUCCESS,token);
        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/join")
    public ResponseEntity signup(
            @Valid @RequestBody User user,
            Errors errors) {

        if(errors.hasErrors()) {
            ResponseData<Errors> response = responseService.create(ResponseStatus.INVALID_REQUEST_PARAMETER_ERROR, errors);
            return ResponseEntity.badRequest().body(response);
        }

        User createUser = userService.join(user);
        EntityModel entityModel = EntityModel.of(createUser);
        UserResource userResource = new UserResource(createUser);
        ResponseData<UserResource> response = responseService.create(ResponseStatus.SUCCESS, userResource);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/profile")
    public ResponseEntity getUserProfile(){
        System.out.println("qqqqqqqqqq");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        System.out.println("qqqqqqqqqq" + id);
        ResponseData rsponseData = responseService.create(
                ResponseStatus.SUCCESS
                ,userRepository.findByEmail(id).orElseThrow(UserNotFoundException::new));
        return ResponseEntity.ok(rsponseData);
    }
}