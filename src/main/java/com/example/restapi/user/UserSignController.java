package com.example.restapi.user;

import com.example.restapi.common.response.ResponseData;
import com.example.restapi.common.response.ResponseService;
import com.example.restapi.config.security.JwtTokenProvider;
import com.example.restapi.exception.EmailSigninFailedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserSignController {

    private final ResponseService responseService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @GetMapping(value = "/signin")
    public ResponseEntity<Object> signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                         @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
        User user = userRepository.findByName(id).orElseThrow(EmailSigninFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new EmailSigninFailedException();

        String data = jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles());
        ResponseData<String> successResponse = responseService.createSuccessResponse(data);
        return ResponseEntity.ok(successResponse);

    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public ResponseEntity<Object> signup(@ApiParam(value = "회원ID : 이메일", required = true) @RequestBody User user) {

        User createUser = userRepository.save(User.builder()
                .name(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        ResponseData<User> successResponse = responseService.createSuccessResponse(user);
        return ResponseEntity.ok(successResponse);
    }
}