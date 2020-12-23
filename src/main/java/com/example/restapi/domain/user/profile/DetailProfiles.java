package com.example.restapi.domain.user.profile;


import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.profile.category.BodyType;
import com.example.restapi.domain.user.profile.category.LocationArea;
import com.example.restapi.domain.user.profile.category.TallType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Setter
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DetailProfiles {

    @NotEmpty
    private String name;                                    // 닉네임

    // 키, 몸무게, 사는곳
    @Enumerated(EnumType.STRING)
    TallType tallType;

    @Enumerated(EnumType.STRING)
    BodyType bodyType;

    @Enumerated(EnumType.STRING)
    LocationArea locationArea;
}
