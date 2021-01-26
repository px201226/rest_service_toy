//package com.example.restapi.web;
//
//import com.example.restapi.domain.posts.Post;
//import com.example.restapi.domain.posts.PostRepository;
//import com.example.restapi.domain.user.User;
//import com.example.restapi.domain.user.UserRepository;
//import com.example.restapi.web.common.BaseControllerTest;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.MediaTypes;
//import org.springframework.http.MediaType;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class PostLikeControllerTest extends BaseControllerTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    private User loginUser;
//    private Post post;
//    @BeforeEach
//    public void setup() throws Exception {
//        loginUser = getAuthUser();
//        post = postRepository.save(Post.builder().user(loginUser).content("test").build());
//    }
//
//    @AfterEach
//    public void cleanUp() {
//        this.userRepository.deleteAll();
//        this.postRepository.deleteAll();
//    }
//
//
//    @Test
//    public void expect_postLike() throws Exception {
//        // when && then
//        mockMvc.perform(post("/v1/like/"+ post.getId())
//                .header("X-AUTH-TOKEN", getJwtToken(loginUser))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaTypes.HAL_JSON)
//        )
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful());
//
//        mockMvc.perform(get("/v1/like/"+ post.getId())
//                .header("X-AUTH-TOKEN", getJwtToken(loginUser))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaTypes.HAL_JSON)
//        )
//                .andDo(print())
//                .andExpect(content().string("true"));
//    }
//
//    @Test
//    public void expect_postUnLike() throws Exception {
//        // when && then
//        mockMvc.perform(delete("/v1/like/"+ post.getId())
//                .header("X-AUTH-TOKEN", getJwtToken(loginUser))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaTypes.HAL_JSON)
//        )
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful());
//
//        mockMvc.perform(get("/v1/like/"+ post.getId())
//                .header("X-AUTH-TOKEN", getJwtToken(loginUser))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaTypes.HAL_JSON)
//        )
//                .andDo(print())
//                .andExpect(content().string("false"));
//    }
//}