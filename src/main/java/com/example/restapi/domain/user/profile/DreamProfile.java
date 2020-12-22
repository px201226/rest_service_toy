package com.example.restapi.domain.user.profile;


import com.example.restapi.domain.user.profile.category.BodyType;
import com.example.restapi.domain.user.profile.category.LocationArea;
import com.example.restapi.domain.user.profile.category.TallType;
import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DreamProfile{

    // 키, 몸무게, 사는곳
    TallType tallType;
    BodyType bodyType;
    LocationArea locationArea;
}
