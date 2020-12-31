package com.example.restapi.web.common;

import com.example.restapi.config.security.JwtTokenProvider;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.domain.user.profile.DreamProfiles;
import com.example.restapi.domain.user.profile.category.BodyType;
import com.example.restapi.domain.user.profile.category.LocationCategory;
import com.example.restapi.domain.user.profile.category.TallType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@Ignore
public class BaseControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected PasswordEncoder passwordEncoder;

@Autowired
private MatchingManager matchingManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private User user;
    private final String EMAIL = "px2008@naver.com";
    private final String PASSWORD = "aa1aa1";
    private final String NAME = "nickName";
    private final List<String> ROLES = Collections.singletonList("ROLE_USER");

    public User getAuthUser(){
        user = userRepository.save(User.builder()
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .detailProfiles(detailProfiles())
                .dreamProfiles(dreamProfiles())
                .roles(ROLES)
                .build());
        return user;
    }

    public String getJwtToken(User user){
        return jwtTokenProvider.createToken(user.getEmail(),user.getRoles());
    }

    public  List<User> getAuthUsers(int size){
        List<User> users = new ArrayList<>();
        System.out.println("usercount="+ userRepository.findAll().size());
        for (int i = 0; i < size; i++) {
            User build = User.builder()
                    .email("px100"+ i + "@naver.com")
                    .password(passwordEncoder.encode(PASSWORD))
                    .dreamProfiles(dreamProfiles())
                    .detailProfiles(detailProfiles())
                    .roles(ROLES)
                    .lastMatchingDate(matchingManager.getNextMatchingDate(LocalDate.now()))
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

}
