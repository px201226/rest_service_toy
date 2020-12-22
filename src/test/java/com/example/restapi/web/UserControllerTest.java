package com.example.restapi.web;

import com.example.restapi.config.AuthUser;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.web.common.BaseControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    private RestDocumentationResultHandler document;

    @Before
    public void setUp() {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint())
        );
    }

    @Before
    public void setup() throws Exception {
        userRepository.save(User.builder()
                .email("px2008@naver.com")
                .password(passwordEncoder.encode("aa1aa1"))
                .name("이기수")
                .roles(Collections.singletonList("ROLE_USER"))
                .build());
    }

    @After
    public void cleanUp() {
        this.userRepository.deleteAll();
    }

    @Test
    public void expect_success_join() throws Exception {
        long epochTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();

        // given
        User userSaveDto = User.builder()
                .email("px2009@naver.com")
                .password(passwordEncoder.encode("aa1aa1"))
                .name("name" + epochTime)
                .build();

        // when && then
        mockMvc.perform(post("/v1/join")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(userSaveDto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(0));
                /*.andDo(document("join",
                        links(
//                                linkWithRel("login").description("로그인 링크"),
//                                linkWithRel("profile").description("RestDocs 링크")

                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("ACCEPT HEADER: HAL_JSON"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("CONTENT_TYPE: APPLICAITON_JSON_UTF")
                        ),
                        requestFields(
                                fieldWithPath("id").description("회원의 권한"),
                                fieldWithPath("email").description("회원의 학번"),
                                fieldWithPath("password").description("회원의 비밀번호"),
                                fieldWithPath("name").description("회원의 이름"),
                                fieldWithPath("roles").description("회원의 권한"),
                                fieldWithPath("posts").description("회원의 권한"),
                                fieldWithPath("comments").description("회원의 권한"),
                                fieldWithPath("createdDate").description("회원의 권한"),
                                fieldWithPath("modifiedDate").description("회원의 권한")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("CONTENT_TYPE: APPLICAITON_JSON_VALUE")
                        ),
                        responseFields(


                                fieldWithPath("data.id").description("회원의 PRIMARY KEY ID"),
                                fieldWithPath("data.password").description("회원의 비밀번호"),
                                fieldWithPath("data.comments").description("회원의 이름")
                        )
                ));*/

    }

    // 중복회원가입
    @Test
    public void expect_redundant_join() throws Exception {
        long epochTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();

        // given
        User userSaveDto = User.builder()
                .email("px2008@naver.com")
                .password(passwordEncoder.encode("aa1aa1"))
                .name("name" + epochTime)
                .build();

        // when && then
        mockMvc.perform(post("/v1/join")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(userSaveDto))
        )
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.resultCode").value(ResponseStatus.REDUNTANT_DATA_ERROR.getResultCode()));
    }


}