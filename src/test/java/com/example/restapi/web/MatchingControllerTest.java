package com.example.restapi.web;

import com.example.restapi.domain.matching.participant.Participant;
import com.example.restapi.domain.matching.participant.ParticipantRepository;
import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.posts.PostRepository;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.profile.category.SexType;
import com.example.restapi.exception.exceptions.UserNotFoundException;
import com.example.restapi.service.matching.ParticipantService;
import com.example.restapi.service.posts.PostsService;
import com.example.restapi.web.common.BaseControllerTest;
import com.example.restapi.web.common.TestDescription;
import com.example.restapi.web.dto.PostsSaveRequestDto;
import com.example.restapi.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MatchingControllerTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantService participantService;

    @AfterEach
    public void cleanUp() {
        participantRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @TestDescription("다음 매칭날짜 구한다.")
    public void getNextMatchingDay() throws Exception {
        // given

        // when && then
        mockMvc.perform(get("/v1/matching/nextDay")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("matching-nextDay",
                        links(
                                linkWithRel("self").description("Self link")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type")
                        ),
                        responseFields(
                                fieldWithPath("nextDay").description("페이지 조회 사이즈"),
                                fieldWithPath("_links.self.href").description("Self link")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    @TestDescription("매칭에 참가한다.")
    public void applyMatching() throws Exception {
        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007", SexType.MAN);

        // when && then
        mockMvc.perform(post("/v1/matching/apply")
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("participant-apply",

                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma")
                        )
                ))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    @TestDescription("매칭에 중복 참가한다.")
    public void reduntApplyMatching() throws Exception {
        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007", SexType.MAN);
        participantService.apply(joinUser.getEmail());

        // when && then
        mockMvc.perform(post("/v1/matching/apply")
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("participant-apply-overlap",
                        links(
                                linkWithRel("documentation_url").description("문서 링크")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma")
                        ),
                        responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.NUMBER).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("details").type(JsonFieldType.STRING).description("에러상세설명"),
                                fieldWithPath("_links.documentation_url.href").description("문서 링크")
                        )
                ))
                .andExpect(status().is5xxServerError());
    }


    @Test
    @TestDescription("매칭 결과를 확인한다.")
    @Transactional
    public void getMatchingResult() throws Exception {
        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007", SexType.MAN);
        participantService.apply(joinUser.getEmail());

        User user =  userRepository.findByEmail(joinUser.getEmail())
                .orElseThrow(
                        () -> new UserNotFoundException(String.format("사용자(%s)를 찾을 수 없습니다", joinUser.getEmail()))
                );

        user.updateLastMatchingDate(LocalDate.of(2020,1,1));

        // when && then
        mockMvc.perform(get("/v1/matching/result")
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("participant-result",

                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma")
                        ),
                        responseFields(
                                fieldWithPath("isMatching").description("소개팅 매칭 여부"),
                                fieldWithPath("otherKakaoId").description("매칭된 사람의 카카오톡 id"),
                                fieldWithPath("_links.self.href").description("Self link")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    @TestDescription("매칭 결과를 확인하는데 예외가 발생한다.")
    public void getMatchingResultException() throws Exception {
        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007", SexType.MAN);
        participantService.apply(joinUser.getEmail());

        // when && then
        mockMvc.perform(get("/v1/matching/result")
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("participant-result-exception",
                        links(
                                linkWithRel("documentation_url").description("문서 링크")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma")
                        ),
                        responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.NUMBER).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("details").type(JsonFieldType.STRING).description("에러상세설명"),
                                fieldWithPath("_links.documentation_url.href").description("문서 링크")
                        )
                ))
                .andExpect(status().is5xxServerError());
    }

}