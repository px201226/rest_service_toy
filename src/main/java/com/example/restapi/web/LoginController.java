package com.example.restapi.web;

import com.example.restapi.domain.response.ResponseData;
import com.example.restapi.domain.response.ResponseService;
import com.example.restapi.config.security.JwtTokenProvider;
import com.example.restapi.domain.auth.TokenWrapper;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.GuestResource;
import com.example.restapi.exception.exceptions.EmailSigninFailedException;
import com.example.restapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping(value = "/login")
    public ResponseEntity signin( @RequestParam String email,
                                  @RequestParam String password) {

        User user = userRepository.findByEmail(email).orElseThrow(EmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
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

        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        User joinUser = userService.join(user);
        GuestResource resource = new GuestResource(joinUser);
        ResponseData<GuestResource> response = responseService.create(ResponseStatus.SUCCESS, resource);
        return ResponseEntity.ok(response);
    }

}