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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    private RestDocumentationResultHandler document;

    private User loginUser;

    @Before
    public void setup() throws Exception {
        loginUser = getAuthUser();
    }

    @After
    public void cleanUp() {
        this.userRepository.deleteAll();
    }


    @Test
    public void successShowUserProfile() throws Exception {
        // when && then
        mockMvc.perform(get("/v1/user")
                .header("X-AUTH-TOKEN", getJwtToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResponseStatus.SUCCESS.getResultCode()));
                //.andExpect(jsonPath("$.data.nickName").value(loginUser.getNickName()));
    }

    @Test
    public void successUpdateUserProfile() throws Exception {
        // given
        DetailProfiles detailProfiles = DetailProfiles.builder()
                .locationCategory(LocationCategory.SEOUL)
                .tallType(TallType.TALL)
                .bodyType(BodyType.SKINNY)
                .build();
        // given
        DreamProfiles dreamProfiles = DreamProfiles.builder()
                .bodyType(BodyType.CHUBBY)
                .locationCategory(LocationCategory.SEOUL)
                .tallType(TallType.TALL)
                .build();

        User user = User.builder()
                .detailProfiles(detailProfiles)
                .dreamProfiles(dreamProfiles)
                .build();
        // when && then
        mockMvc.perform(put("/v1/user")
                .header("X-AUTH-TOKEN", getJwtToken(loginUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(user))
                .accept(MediaTypes.HAL_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResponseStatus.SUCCESS.getResultCode()));
            //    .andExpect(jsonPath("$.data.detailProfiles.name").value(user.getDetailProfiles().getName()));

    }

}