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

public class CommentControllerTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostsService postsService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentsService commentsService;

    @AfterEach
    public void cleanUp() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @TestDescription("댓글 목록 조회 성공")
    public void getCommentList() throws Exception {
        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007", SexType.MAN);
        Post post = savePost("Test conent1", joinUser.getEmail());

        for (int i = 1; i <= 2; i++) {
            CommentsSaveRequestDto commentsSaveRequestDto = getCommentSaveRequestDto("test Comment" + i);
            commentsService.save(commentsSaveRequestDto,post.getId(), joinUser.getEmail());
        }

        /* Path Parameters 를 사용할 때는
         * MockMvcRequestBuilders.put 이 아니라 RestDocumentationRequestBuilders.put 을 사용한다
         * 컴파일 에러 뜸.. */
        // when && then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/posts/{id}/comments", post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("comment-retreive",
                        links(
                                linkWithRel("documentation_url").description("문서 링크")
                                //linkWithRel("self").description("Self link"),
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON")
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
                                fieldWithPath("_embedded.commentModels.[].id").description("댓글 Id"),
                                fieldWithPath("_embedded.commentModels.[].postId").description("게시물 Id"),
                                fieldWithPath("_embedded.commentModels.[].content").description("댓글의 내용"),
                                fieldWithPath("_embedded.commentModels.[].userEmail").description("댓글 작성자 이메일"),
                                fieldWithPath("_embedded.commentModels.[].userNickName").description("댓글 작성자 닉네임"),
                                fieldWithPath("_embedded.commentModels.[].modifyDate").description("댓글의 마지막 수정일"),
                                fieldWithPath("_embedded.commentModels.[].isWriter").description("댓글 작성자 여부"),
                                fieldWithPath("_embedded.commentModels.[]._links.self.href").description("댓글 Self link"),
                                fieldWithPath("_links.documentation_url.href").description("문서 링크")
                        )
                ))
                .andExpect(status().isOk());
    }


    @Test
    @TestDescription("댓글 작성 성공한다.")
    public void writePost() throws Exception {

        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007",SexType.MAN);
        Post post = savePost("Test conent1", joinUser.getEmail());

        CommentsSaveRequestDto commentsSaveRequestDto = getCommentSaveRequestDto("test Comment");

        // when && then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/v1/posts/{id}/comments",post.getId())
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(commentsSaveRequestDto))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("comment-write",
                        links(
                                linkWithRel("self").description("Self link"),
                                linkWithRel("documentation_url").description("문서 링크")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        pathParameters(
                                parameterWithName("id").description("게시물 Id")
                        ),
                        requestFields(
                                fieldWithPath("content").description("댓글 내용")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("댓글 Id"),
                                fieldWithPath("postId").description("게시물 Id"),
                                fieldWithPath("content").description("댓글의 내용"),
                                fieldWithPath("userEmail").description("댓글 작성자 이메일"),
                                fieldWithPath("userNickName").description("댓글 작성자 닉네임"),
                                fieldWithPath("modifyDate").description("댓글의 마지막 수정일"),
                                fieldWithPath("isWriter").description("댓글 작성자 여부"),
                                fieldWithPath("_links.self.href").description("댓글 Self link"),
                                fieldWithPath("_links.documentation_url.href").description("문서 링크")
                        )
                ))
                .andExpect(status().isOk());
    }


    @Test
    @TestDescription("댓글을 수정한다")
    public void updatePost() throws Exception {

        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007",SexType.MAN);
        Post post = savePost("Test conent1", joinUser.getEmail());

        CommentsSaveRequestDto commentsSaveRequestDto = getCommentSaveRequestDto("test Comment");
        Comment comment = commentsService.save(commentsSaveRequestDto, post.getId(), joinUser.getEmail());

        CommentsUpdateRequestDto commentsUpdateRequestDto = CommentsUpdateRequestDto.builder().content("update comment").build();

        /* Path Parameters 를 사용할 때는
         * MockMvcRequestBuilders.put 이 아니라 RestDocumentationRequestBuilders.put 을 사용한다
         * 컴파일 에러 뜸.. */
        // when && then
        mockMvc.perform(RestDocumentationRequestBuilders.put("/v1/posts/{postId}/comments/{commentId}",post.getId(),comment.getId())
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(commentsUpdateRequestDto))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("comment-update",
                        links(
                                linkWithRel("self").description("Self link"),
                                linkWithRel("documentation_url").description("문서 링크")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        pathParameters(
                                parameterWithName("postId").description("게시물 Id"),
                                parameterWithName("commentId").description("댓글 Id")
                        ),
                        requestFields(
                                fieldWithPath("content").description("댓글 내용")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("댓글 Id"),
                                fieldWithPath("postId").description("게시물 Id"),
                                fieldWithPath("content").description("댓글의 내용"),
                                fieldWithPath("userEmail").description("댓글 작성자 이메일"),
                                fieldWithPath("userNickName").description("댓글 작성자 닉네임"),
                                fieldWithPath("modifyDate").description("댓글의 마지막 수정일"),
                                fieldWithPath("isWriter").description("댓글 작성자 여부"),
                                fieldWithPath("_links.self.href").description("댓글 Self link"),
                                fieldWithPath("_links.documentation_url.href").description("문서 링크")
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    @TestDescription("게시물을 삭제한다")
    public void deletePost() throws Exception {

        // given
        User joinUser = getJoinUser("px2007@naver.com", "password123", "피엑스맛나", "px2007",SexType.MAN);
        Post post = savePost("Test conent1", joinUser.getEmail());

        CommentsSaveRequestDto commentsSaveRequestDto = getCommentSaveRequestDto("test Comment");
        Comment comment = commentsService.save(commentsSaveRequestDto, post.getId(), joinUser.getEmail());



        /* Path Parameters 를 사용할 때는
         * MockMvcRequestBuilders.put 이 아니라 RestDocumentationRequestBuilders.put 을 사용한다
         * 컴파일 에러 뜸.. */
        // when && then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/v1/posts/{postId}/comments/{commentId}",post.getId(),comment.getId())
                .header(HttpHeaders.AUTHORIZATION, getAccessToken(joinUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(document("comment-delete",
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

    private Post savePost(String content, String userEmail){
        PostsSaveRequestDto build = PostsSaveRequestDto.builder().content(content).build();
        return postsService.save(build, userEmail);
    }

    private CommentsSaveRequestDto getCommentSaveRequestDto(String content){
        return CommentsSaveRequestDto.builder().content(content).build();
    }

}