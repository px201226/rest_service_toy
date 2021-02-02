package com.example.restapi.web;

import com.example.restapi.domain.comments.Comment;
import com.example.restapi.domain.comments.CommentRepository;
import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.posts.PostRepository;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.domain.user.profile.category.SexType;
import com.example.restapi.service.comments.CommentsService;
import com.example.restapi.service.posts.PostsService;
import com.example.restapi.web.common.BaseControllerTest;
import com.example.restapi.web.common.TestDescription;
import com.example.restapi.web.dto.CommentsSaveRequestDto;
import com.example.restapi.web.dto.CommentsUpdateRequestDto;
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

public class PostLikeControllerTest extends BaseControllerTest {

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
    @TestDescription("게시물 좋아여 여부 조회")
    public void isLike() throws Exception {
        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007", SexType.MAN);
        Post post = savePost("Test conent1", joinUser.getEmail());

        /* Path Parameters 를 사용할 때는
         * MockMvcRequestBuilders.put 이 아니라 RestDocumentationRequestBuilders.put 을 사용한다
         * 컴파일 에러 뜸.. */
        // when && then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/like/{id}", post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("postLike-retreive",
                        links(
                                linkWithRel("self").description("Self link")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        pathParameters (
                                parameterWithName("id").description("게시물 Id")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type")
                        ),
                        responseFields(
                                fieldWithPath("isLike").description("게시물 좋아요 여부"),
                                fieldWithPath("_links.self.href").description("Self link")
                        )
                ))
                .andExpect(status().isOk());
    }


    @Test
    @TestDescription("게시물 좋아요 성공")
    public void writePost() throws Exception {

        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007",SexType.MAN);
        Post post = savePost("Test conent1", joinUser.getEmail());

        // when && then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/v1/like/{id}",post.getId())
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("postLike-post",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        pathParameters(
                                parameterWithName("id").description("게시물 Id")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma")
                        )
                ))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    @TestDescription("게시물 좋아요 취소")
    public void deletePost() throws Exception {

        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007",SexType.MAN);
        Post post = savePost("Test conent1", joinUser.getEmail());

        /* Path Parameters 를 사용할 때는
         * MockMvcRequestBuilders.put 이 아니라 RestDocumentationRequestBuilders.put 을 사용한다
         * 컴파일 에러 뜸.. */
        // when && then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/v1/like/{id}",post.getId())
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("postLike-delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        pathParameters(
                                parameterWithName("id").description("게시물 Id")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma")
                        )
                ))
                .andExpect(status().is2xxSuccessful());
    }

    private Post savePost(String content, String userEmail){
        PostsSaveRequestDto build = PostsSaveRequestDto.builder().content(content).build();
        return postsService.save(build, userEmail);
    }


}