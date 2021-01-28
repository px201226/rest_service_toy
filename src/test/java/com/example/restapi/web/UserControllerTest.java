package com.example.restapi.web;

import com.example.restapi.domain.response.ResponseStatus;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.domain.user.profile.DreamProfiles;
import com.example.restapi.domain.user.profile.category.BodyType;
import com.example.restapi.domain.user.profile.category.LocationCategory;
import com.example.restapi.domain.user.profile.category.TallType;
import com.example.restapi.web.common.BaseControllerTest;
import com.example.restapi.web.common.RestDocsConfiguration;

import com.example.restapi.web.common.TestDescription;
import com.example.restapi.web.dto.UserSaveRequestDto;
import com.example.restapi.web.dto.UserSaveResponseDto;
import com.example.restapi.web.dto.UserUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    @TestDescription("유저 정보 조회")
    public void successShowUserProfile() throws Exception {
        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007");

        // when && then
        mockMvc.perform(get("/v1/user")
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("user-profile",
                        links(
                                linkWithRel("self").description("Self link"),
                                linkWithRel("posts").description("내가 쓴 게시물 목록"),
                                linkWithRel("comments").description("내가 쓴 댓글 목록")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("회원 Primary Key"),
                                fieldWithPath("email").description("회원의 이메일"),
                                fieldWithPath("nickName").description("회원의 닉네임"),
                                fieldWithPath("posts").description("회원이 작성한 게시물 수"),
                                fieldWithPath("comments").description("회원이 작성한 댓글 수"),
                                fieldWithPath("detailProfiles.tallType").description("회원의 신장"),
                                fieldWithPath("detailProfiles.bodyType").description("회원의 체격"),
                                fieldWithPath("detailProfiles.locationCategory").description("회원의 거주지"),
                                fieldWithPath("dreamProfiles.tallType").description("이상형의 신장"),
                                fieldWithPath("dreamProfiles.bodyType").description("이상형의 체격"),
                                fieldWithPath("dreamProfiles.locationCategory").description("이상형의 거주지"),
                                fieldWithPath("lastMatchingDate").type(LocalDate.class).description("회원의 마지막 참가 소개팅 날짜").optional(),
                                fieldWithPath("kakaoId").description("회원의 카카오톡 ID"),
                                fieldWithPath("_links.self.href").description("Self link"),
                                fieldWithPath("_links.posts.href").description("내가 쓴 게시물 조회 링크"),
                                fieldWithPath("_links.comments.href").description("내가 쓴 댓글 조회 링크")
                        )
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickName").value(joinUser.getNickName()));
    }


    @Test
    @TestDescription("유저 프로필을 수정한다.")
    public void successUpdateUserProfile() throws Exception {

        // given
        DetailProfiles detailProfiles = DetailProfiles.builder()
                .locationCategory(LocationCategory.SEOUL)
                .tallType(TallType.TALL)
                .bodyType(BodyType.SKINNY)
                .build();
        DreamProfiles dreamProfiles = DreamProfiles.builder()
                .bodyType(BodyType.CHUBBY)
                .locationCategory(LocationCategory.SEOUL)
                .tallType(TallType.TALL)
                .build();

        String kakaoId = "pknubot";

        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007");
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .detailProfiles(detailProfiles)
                .dreamProfiles(dreamProfiles)
                .kakaoId(kakaoId)
                .build();

        // when && then
        mockMvc.perform(put("/v1/user")
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userUpdateRequestDto))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("user-modify",
                        links(
                                linkWithRel("self").description("Self link"),
                                linkWithRel("posts").description("내가 쓴 게시물 목록"),
                                linkWithRel("comments").description("내가 쓴 댓글 목록")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("nickName").description("회원 닉네임"),
                                fieldWithPath("kakaoId").description("회원의 카카오톡 ID"),
                                subsectionWithPath("dreamProfiles").description("회원의 상세 프로필"),
                                subsectionWithPath("detailProfiles").description("회원이 이상형 프로필")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("회원 Primary Key"),
                                fieldWithPath("email").description("회원의 이메일"),
                                fieldWithPath("nickName").description("회원의 닉네임"),
                                fieldWithPath("posts").description("회원이 작성한 게시물 수"),
                                fieldWithPath("comments").description("회원이 작성한 댓글 수"),
                                fieldWithPath("detailProfiles.tallType").description("회원의 신장"),
                                fieldWithPath("detailProfiles.bodyType").description("회원의 체격"),
                                fieldWithPath("detailProfiles.locationCategory").description("회원의 거주지"),
                                fieldWithPath("dreamProfiles.tallType").description("이상형의 신장"),
                                fieldWithPath("dreamProfiles.bodyType").description("이상형의 체격"),
                                fieldWithPath("dreamProfiles.locationCategory").description("이상형의 거주지"),
                                fieldWithPath("lastMatchingDate").type(LocalDate.class).description("회원의 마지막 참가 소개팅 날짜").optional(),
                                fieldWithPath("kakaoId").description("회원의 카카오톡 ID"),
                                fieldWithPath("_links.self.href").description("Self link"),
                                fieldWithPath("_links.posts.href").description("내가 쓴 게시물 조회 링크"),
                                fieldWithPath("_links.comments.href").description("내가 쓴 댓글 조회 링크")
                        )
                ))
                .andExpect(status().isOk());

    }

}