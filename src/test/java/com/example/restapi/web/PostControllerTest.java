package com.example.restapi.web;

import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.posts.PostRepository;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.profile.category.SexType;
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

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostsService postsService;

    @AfterEach
    public void cleanUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @TestDescription("게시물 목록 조회 성공")
    public void getPostList() throws Exception {
        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007", SexType.MAN);

        for (int i = 1; i <= 10; i++) {
            PostsSaveRequestDto postsSaveRequestDto = getPostsSaveRequestDto("test Post" + i);
            postsService.save(postsSaveRequestDto, joinUser.getEmail());
        }

        // when && then
        mockMvc.perform(get("/v1/posts?page=0&size=2&sort=id,desc")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("post-retreive",
                        links(
                                linkWithRel("self").description("Self link"),
                                linkWithRel("first").description("첫번째 페이지 링크").optional(),
                                linkWithRel("next").description("다음 페이지 링크").optional(),
                                linkWithRel("last").description("마지막 페이지 링크").optional()
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON")
                        ),
                        requestParameters(
                                parameterWithName("page").description("조회할 페이지 번호"),
                                parameterWithName("size").description("페이지 당 게시물 갯수"),
                                parameterWithName("sort").description("정렬 방식")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type")
                        ),
                        responseFields(
                                fieldWithPath("_embedded.postModels.[].id").description("게시물 Id"),
                                fieldWithPath("_embedded.postModels.[].content").description("게시물의 내용"),
                                fieldWithPath("_embedded.postModels.[].likes").description("게시물 좋아요 수"),
                                fieldWithPath("_embedded.postModels.[].userEmail").description("게시물 작성자 이메일"),
                                fieldWithPath("_embedded.postModels.[].userNickName").description("게시물 작성자 닉네임"),
                                fieldWithPath("_embedded.postModels.[].modifyDate").description("게시물의 마지막 수정일"),
                                fieldWithPath("_embedded.postModels.[].comments").description("게시물 댓글 수"),
                                fieldWithPath("_embedded.postModels.[].isLike").description("현재 로그인한 유저의 좋아요 여부"),
                                fieldWithPath("_embedded.postModels.[].isWriter").description("현재 로그인한 유저의 작성자 여부"),
                                fieldWithPath("_embedded.postModels.[]._links.self.href").description("게시물 Self link"),
                                fieldWithPath("_embedded.postModels.[]._links.comments.href").description("게시물 댓글 조회 link"),
                                fieldWithPath("_links.self.href").description("Self link"),
                                fieldWithPath("_links.first.href").description("첫번째 페이지 링크").optional(),
                                fieldWithPath("_links.next.href").description("다음 페이지 링크").optional(),
                                fieldWithPath("_links.last.href").description("마지막 페이지 링크").optional(),
                                fieldWithPath("page.size").description("페이지 조회 사이즈"),
                                fieldWithPath("page.totalElements").description("페이지 요소의 총 갯수"),
                                fieldWithPath("page.totalPages").description("총 페이지 수"),
                                fieldWithPath("page.number").description("현재 페이지 번호")
                        )
                ))
                .andExpect(status().isOk());
    }


    @Test
    @TestDescription("게시물 작성 성공한다.")
    public void writePost() throws Exception {
        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007",SexType.MAN);

        PostsSaveRequestDto postsSaveRequestDto = getPostsSaveRequestDto("Test Content1");

        // when && then
        mockMvc.perform(post("/v1/posts")
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(postsSaveRequestDto))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("post-write",
                        links(
                                linkWithRel("self").description("Self link"),
                                linkWithRel("comments").description("댓글 목록 조회 링크").optional()
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("content").description("게시물 내용")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시물 Id"),
                                fieldWithPath("content").description("게시물의 내용"),
                                fieldWithPath("likes").description("게시물 좋아요 수"),
                                fieldWithPath("userEmail").description("게시물 작성자 이메일"),
                                fieldWithPath("userNickName").description("게시물 작성자 닉네임"),
                                fieldWithPath("modifyDate").description("게시물의 마지막 수정일"),
                                fieldWithPath("comments").description("게시물 댓글 수"),
                                fieldWithPath("_links.self.href").description("Self link"),
                                fieldWithPath("_links.comments.href").description("첫번째 페이지 링크").optional()
                        )
                ))
                .andExpect(status().isOk());
    }


    @Test
    @TestDescription("게시물 수정 성공한다")
    public void updatePost() throws Exception {
        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007",SexType.MAN);

        Long id = 0L;
        for (int i = 1; i <= 10; i++) {
            PostsSaveRequestDto postsSaveRequestDto = getPostsSaveRequestDto("test Post" + i);
            Post save = postsService.save(postsSaveRequestDto, joinUser.getEmail());
            id = save.getId();
        }

        PostsUpdateRequestDto postsUpdateRequestDto = getPostUpdateRequestDto("change post content");


        /* Path Parameters 를 사용할 때는
         * MockMvcRequestBuilders.put 이 아니라 RestDocumentationRequestBuilders.put 을 사용한다
         * 컴파일 에러 뜸.. */
        // when && then
        mockMvc.perform(RestDocumentationRequestBuilders.put("/v1/posts/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(postsUpdateRequestDto))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("post-update",
                        links(
                                linkWithRel("self").description("Self link"),
                                linkWithRel("comments").description("댓글 목록 조회 링크").optional()
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        pathParameters(
                                parameterWithName("id").description("수정할 게시물 Id")
                        ),
                        requestFields(
                                fieldWithPath("content").description("게시물 내용")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시물 Id"),
                                fieldWithPath("content").description("게시물의 내용"),
                                fieldWithPath("likes").description("게시물 좋아요 수"),
                                fieldWithPath("userEmail").description("게시물 작성자 이메일"),
                                fieldWithPath("userNickName").description("게시물 작성자 닉네임"),
                                fieldWithPath("modifyDate").description("게시물의 마지막 수정일"),
                                fieldWithPath("comments").description("게시물 댓글 수"),
                                fieldWithPath("_links.self.href").description("Self link"),
                                fieldWithPath("_links.comments.href").description("첫번째 페이지 링크").optional()
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    @TestDescription("게시물을 삭제한다")
    public void deletePost() throws Exception {
        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007",SexType.MAN);

        PostsSaveRequestDto postsSaveRequestDto = getPostsSaveRequestDto("test Post");
        Post save = postsService.save(postsSaveRequestDto, joinUser.getEmail());


        /* Path Parameters 를 사용할 때는
         * MockMvcRequestBuilders.put 이 아니라 RestDocumentationRequestBuilders.put 을 사용한다
         * 컴파일 에러 뜸.. */
        // when && then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/v1/posts/{id}", save.getId())
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("post-delete",
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

    private PostsUpdateRequestDto getPostUpdateRequestDto(String content) {
        return PostsUpdateRequestDto.builder().content(content).build();
    }

    private PostsSaveRequestDto getPostsSaveRequestDto(String content) {
        return PostsSaveRequestDto.builder().content(content).build();
    }

}