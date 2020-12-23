package com.example.restapi.web.common;

import com.example.restapi.config.security.JwtTokenProvider;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.domain.user.profile.DreamProfiles;
import com.example.restapi.domain.user.profile.category.BodyType;
import com.example.restapi.domain.user.profile.category.LocationArea;
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

import javax.swing.plaf.PanelUI;
import javax.validation.constraints.Email;
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

    public DetailProfiles detailProfiles() {
        return DetailProfiles.builder()
                .name(NAME)
                .bodyType(BodyType.SKINNY)
                .tallType(TallType.NORMAL)
                .locationArea(LocationArea.BUSAN)
                .build();
    }

    public DreamProfiles dreamProfiles(){
        return DreamProfiles.builder()
                .bodyType(BodyType.SKINNY)
                .locationArea(LocationArea.BUSAN)
                .tallType(TallType.SMALL)
                .build();
    }

}
