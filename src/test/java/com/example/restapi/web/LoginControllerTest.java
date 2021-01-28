package com.example.restapi.web;

import com.example.restapi.config.AppProperties;
import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.service.user.UserService;
import com.example.restapi.web.common.BaseControllerTest;
import com.example.restapi.web.common.TestDescription;
import com.example.restapi.web.dto.UserSaveRequestDto;
import com.example.restapi.web.dto.UserSaveResponseDto;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AppProperties appProperties;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    @TestDescription("회원가입이 성공한다")
    public void success_join() throws Exception {
        // given
        UserSaveRequestDto userSaveDto = getUserSaveRequestDto("px2007@naver.com","password123","피엑스맛나","px2007");

        // when && then
        mockMvc.perform(post("/v1/join")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userSaveDto))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userSaveDto.getEmail()))
                .andDo(document("user-join",
                        links(
                                linkWithRel("login").description("로그인 링크"),
                                linkWithRel("profile").description("RestDocs 링크")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("ACCEPT HEADER: HAL_JSON"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("CONTENT_TYPE: APPLICAITON_JSON_UTF")
                        ),
                        requestFields(

                                fieldWithPath("email").description("회원의 이메일"),
                                fieldWithPath("password").description("회원의 비밀번호"),
                                fieldWithPath("nickName").description("회원의 닉네임"),
                                fieldWithPath("detailProfiles.tallType").description("회원의 신장"),
                                fieldWithPath("detailProfiles.bodyType").description("회원의 체격"),
                                fieldWithPath("detailProfiles.locationCategory").description("회원의 거주지"),
                                fieldWithPath("dreamProfiles.tallType").description("이상형의 신장"),
                                fieldWithPath("dreamProfiles.bodyType").description("이상형의 체격"),
                                fieldWithPath("dreamProfiles.locationCategory").description("이상형의 거주지"),
                                fieldWithPath("kakaoId").description("회원의 카카오톡 ID")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("CONTENT_TYPE: APPLICAITON_JSON_VALUE")
                        ),
                        responseFields(
                                fieldWithPath("email").description("회원의 이메일"),
                                fieldWithPath("nickName").description("회원의 닉네임"),
                                fieldWithPath("_links.login.href").description("로그인 링크"),
                                fieldWithPath("_links.profile.href").description("Rest Docs 링크")
                        )
                ));

    }

    @Test
    @TestDescription("이미 가입된 이메일로 회원가입 한다.")
    public void redundant_join() throws Exception {

        // given
        UserSaveRequestDto userSaveDto = getUserSaveRequestDto("px2007@naver.com","password123","피엑스맛나","px2007");

        // when && then
        userRepository.save(userSaveDto.toEntity());

        mockMvc.perform(post("/v1/join")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(userSaveDto))
        )
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.resultCode").value(ResponseStatus.REDUNTANT_DATA_ERROR.getResultCode()));
    }

    @Test
    @TestDescription("로그인에 성공한다.")
    public void success_login() throws Exception {

        // given
        UserSaveRequestDto userSaveDto = getUserSaveRequestDto("px2007@naver.com","password123","피엑스맛나","px2007");
        userService.join(userSaveDto);

        //when && then
        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", userSaveDto.getEmail())
                .param("password", userSaveDto.getPassword())
                .param("grant_type", "password")
        )
                .andExpect(status().isOk())
                .andDo(document("oauth-token",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Authorizaiton: Basic")
                        ),
                        requestParameters(
                                parameterWithName("username").description("사용자 Email"),
                                parameterWithName("password").description("사용자 password"),
                                parameterWithName("grant_type").description("Grant_Type")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type")
                        ),
                        responseFields(
                                fieldWithPath("access_token").description("Access_Token"),
                                fieldWithPath("token_type").description("Token_Type"),
                                fieldWithPath("refresh_token").description("Refresh_Token"),
                                fieldWithPath("expires_in").description("Expires_in"),
                                fieldWithPath("scope").description("Scope"),
                                fieldWithPath("jti").description("Jti")
                        )
                ));
    }

    @Test
    @TestDescription("회원이 아닌데 토큰을 받으려고 할때")
    public void getOAuthToekn_BadRequest() throws Exception {
        //when && then
        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", "null")
                .param("password", "null")
                .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }



}