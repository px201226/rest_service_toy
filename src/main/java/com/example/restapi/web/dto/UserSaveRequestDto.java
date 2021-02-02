package com.example.restapi.web.dto;


import com.example.restapi.domain.posts.Post;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.domain.user.profile.DreamProfiles;
import com.example.restapi.domain.user.profile.category.SexType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequestDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickName;

    @NotEmpty
    private String kakaoId;

    private DreamProfiles dreamProfiles;

    private DetailProfiles detailProfiles;

    private SexType sexType;

    public User toEntity(){
        return User.builder()
                .email(getEmail())
                .password(getPassword())
                .kakaoId(getKakaoId())
                .nickName(getNickName())
                .dreamProfiles(getDreamProfiles())
                .detailProfiles(getDetailProfiles())
                .SexType(getSexType())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}
