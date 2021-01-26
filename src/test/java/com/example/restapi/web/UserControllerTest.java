//package com.example.restapi.web;
//
//import com.example.restapi.domain.response.ResponseStatus;
//import com.example.restapi.domain.user.User;
//import com.example.restapi.domain.user.UserRepository;
//import com.example.restapi.domain.user.profile.DetailProfiles;
//import com.example.restapi.domain.user.profile.DreamProfiles;
//import com.example.restapi.domain.user.profile.category.BodyType;
//import com.example.restapi.domain.user.profile.category.LocationCategory;
//import com.example.restapi.domain.user.profile.category.TallType;
//import com.example.restapi.web.common.BaseControllerTest;
//import com.example.restapi.web.common.RestDocsConfiguration;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.hateoas.MediaTypes;
//import org.springframework.http.MediaType;
//
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
//import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
//@Import(RestDocsConfiguration.class)
//public class UserControllerTest extends BaseControllerTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private User loginUser;
//
//    @BeforeEach
//    public void setup() throws Exception {
//        loginUser = getAuthUser();
//    }
//
//    @AfterEach
//    public void cleanUp() {
//        this.userRepository.deleteAll();
//    }
//
//
//    @Test
//    public void successShowUserProfile() throws Exception {
//        // when && then
//        mockMvc.perform(get("/v1/user")
//                .header("X-AUTH-TOKEN", getJwtToken(loginUser))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaTypes.HAL_JSON)
//        )
//                .andDo(document("User",
//                        links(  //링크 문서화
//                        linkWithRel("self").description("link to self"),
//                        linkWithRel("query-events").description("link to query events"),
//                        linkWithRel("update-event").description("link to update an existing event")
//                )))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.resultCode").value(ResponseStatus.SUCCESS.getResultCode()));
//                //.andExpect(jsonPath("$.data.nickName").value(loginUser.getNickName()));
//    }
//
//    @Test
//    public void successUpdateUserProfile() throws Exception {
//        // given
//        DetailProfiles detailProfiles = DetailProfiles.builder()
//                .locationCategory(LocationCategory.SEOUL)
//                .tallType(TallType.TALL)
//                .bodyType(BodyType.SKINNY)
//                .build();
//        // given
//        DreamProfiles dreamProfiles = DreamProfiles.builder()
//                .bodyType(BodyType.CHUBBY)
//                .locationCategory(LocationCategory.SEOUL)
//                .tallType(TallType.TALL)
//                .build();
//
//        User user = User.builder()
//                .detailProfiles(detailProfiles)
//                .dreamProfiles(dreamProfiles)
//                .build();
//        // when && then
//        mockMvc.perform(put("/v1/user")
//                .header("X-AUTH-TOKEN", getJwtToken(loginUser))
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(user))
//                .accept(MediaTypes.HAL_JSON)
//        )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.resultCode").value(ResponseStatus.SUCCESS.getResultCode()));
//            //    .andExpect(jsonPath("$.data.detailProfiles.name").value(user.getDetailProfiles().getName()));
//
//    }
//
//}