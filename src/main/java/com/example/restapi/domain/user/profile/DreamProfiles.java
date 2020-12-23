package com.example.restapi.domain.user.profile;


import com.example.restapi.domain.user.profile.category.BodyType;
import com.example.restapi.domain.user.profile.category.LocationArea;
import com.example.restapi.domain.user.profile.category.TallType;
import com.sun.xml.bind.v2.model.core.ID;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Setter
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DreamProfiles {

    // 키, 몸무게, 사는곳
    @Enumerated(EnumType.STRING)
    @Column(name = "dream_tall_type")
    TallType tallType;

    @Enumerated(EnumType.STRING)
    @Column(name = "dream_body_type")
    BodyType bodyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "dream_locationArea")
    LocationArea locationArea;
}
