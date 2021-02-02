package com.example.restapi.web.common;

import com.example.restapi.config.AppProperties;
import com.example.restapi.config.security.JwtTokenProvider;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.domain.user.profile.DreamProfiles;
import com.example.restapi.domain.user.profile.category.BodyType;
import com.example.restapi.domain.user.profile.category.LocationCategory;
import com.example.restapi.domain.user.profile.category.SexType;
import com.example.restapi.domain.user.profile.category.TallType;
import com.example.restapi.service.user.UserService;
import com.example.restapi.web.dto.UserSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.restdocs.hypermedia.LinksSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserService userService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private MatchingManager matchingManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AppProperties appProperties;


    public UserSaveRequestDto getUserSaveRequestDto(String email, String password, String nickName, String kakaoId, SexType sexType) {
        return UserSaveRequestDto.builder()
                .email(email)
                .password(password)
                .detailProfiles(detailProfiles())
                .dreamProfiles(dreamProfiles())
                .nickName(nickName)
                .kakaoId(kakaoId)
                .sexType(sexType)
                .build();
    }

    public User getJoinUser(String email, String password, String nickName, String kakaoId, SexType sexType) {
        UserSaveRequestDto build = UserSaveRequestDto.builder()
                .email(email)
                .password(password)
                .detailProfiles(detailProfiles())
                .dreamProfiles(dreamProfiles())
                .nickName(nickName)
                .kakaoId(kakaoId)
                .sexType(sexType)
                .build();
         userService.join(build);
         return build.toEntity();
    }

    public String getAccessToken(User user) throws Exception {
        ResultActions perform = mockMvc.perform(post("/oauth/token")
                        .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                        .param("username", user.getEmail())
                        .param("password", user.getPassword())
                        .param("grant_type", "password"));

        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        System.out.println("dd");
        System.out.println(contentAsString);
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return "Bearer " + parser.parseMap(contentAsString).get("access_token").toString();
    }

    private DetailProfiles detailProfiles() {
        return DetailProfiles.builder()
                .bodyType(BodyType.SKINNY)
                .tallType(TallType.NORMAL)
                .locationCategory(LocationCategory.BUSAN)
                .build();
    }

    private DreamProfiles dreamProfiles() {
        return DreamProfiles.builder()
                .bodyType(BodyType.CHUBBY)
                .locationCategory(LocationCategory.BUSAN)
                .tallType(TallType.TALL)
                .build();
    }

}
