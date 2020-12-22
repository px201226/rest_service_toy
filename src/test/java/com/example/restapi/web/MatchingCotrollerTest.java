package com.example.restapi.web;

import com.example.restapi.config.security.JwtTokenProvider;
import com.example.restapi.domain.matching.MatchingWaitEntity;
import com.example.restapi.domain.matching.MatchingWaitEntityRepository;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.web.common.BaseControllerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchingCotrollerTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchingWaitEntityRepository matchingWaitEntityRepository;

    @Autowired
    private MatchingManager matchingManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private User loginUser;

    @Before
    public void setup() throws Exception {
        loginUser = User.builder()
                .email("px2008@naver.com")
                .password(passwordEncoder.encode("aa1aa1"))
                .name("이기수")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        userRepository.save(loginUser);
    }
    @After
    public void cleanUp() {
        this.userRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void expectSuccessApply() throws Exception {
        // when && then
        mockMvc.perform(post("/v1/matching")
                .header("X-AUTH-TOKEN", jwtTokenProvider.createToken(loginUser.getEmail(),loginUser.getRoles()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void expectReduntApply() throws Exception {
        // given
        MatchingWaitEntity build = MatchingWaitEntity.builder()
                .user(loginUser)
                .nextMatchingDate(matchingManager.getNextMatchingDate(LocalDate.now()))
                .build();

        matchingWaitEntityRepository.save(build);

        // when && then
        mockMvc.perform(post("/v1/matching")
                .header("X-AUTH-TOKEN", jwtTokenProvider.createToken(loginUser.getEmail(),loginUser.getRoles()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

}