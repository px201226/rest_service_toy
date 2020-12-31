package com.example.restapi.web;

import com.example.restapi.config.security.JwtTokenProvider;
import com.example.restapi.domain.matching.MatchingWaitEntity;
import com.example.restapi.domain.matching.MatchingWaitEntityRepository;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.domain.user.profile.category.BodyType;
import com.example.restapi.domain.user.profile.category.LocationCategory;
import com.example.restapi.domain.user.profile.category.TallType;
import com.example.restapi.web.common.BaseControllerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.util.List;

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

    public DetailProfiles detailProfiles() {
        return DetailProfiles.builder()
                .bodyType(BodyType.CHUBBY)
                .tallType(TallType.NORMAL)
                .locationCategory(LocationCategory.BUSAN)
                .build();
    }

    @Before
    public void setup() throws Exception {
        loginUser = getAuthUser();
    }

    @After
    public void cleanUp() {
        this.matchingWaitEntityRepository.deleteAll();
        this.userRepository.deleteAll();

    }

    @Test
    public void expectSuccessApply() throws Exception {
        // when && then
        mockMvc.perform(post("/v1/matching/apply")
                .header("X-AUTH-TOKEN", jwtTokenProvider.createToken(loginUser.getEmail(),loginUser.getRoles()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void success_Multy_apply() throws Exception {
        // givin
        List<User> authUsers = getAuthUsers(10);

        // when && then
        for(int i=0; i<10; i++) {
            User user = authUsers.get(i);
            mockMvc.perform(post("/v1/matching/apply")
                    .header("X-AUTH-TOKEN", jwtTokenProvider.createToken(user.getEmail(), user.getRoles()))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaTypes.HAL_JSON)
            )
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }
    @Test
    public void expectReduntApply() throws Exception {
        // given
        MatchingWaitEntity build = MatchingWaitEntity.builder()
                .user(loginUser)
                .nextMatchingDate(matchingManager.getNextMatchingDate(LocalDate.now()))
                .build();

        matchingWaitEntityRepository.save(build);

        // when && then
        mockMvc.perform(post("/v1/matching/apply")
                .header("X-AUTH-TOKEN", jwtTokenProvider.createToken(loginUser.getEmail(),loginUser.getRoles()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

}