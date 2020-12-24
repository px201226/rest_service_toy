package com.example.restapi.domain.user.profile;


import com.example.restapi.domain.matching.ObjectiveComparable;
import com.example.restapi.domain.user.profile.category.BodyType;
import com.example.restapi.domain.user.profile.category.LocationCategory;
import com.example.restapi.domain.user.profile.category.TallType;
import lombok.*;

import javax.persistence.*;


@Setter
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DreamProfiles implements ObjectiveComparable<DetailProfiles> {

    // 키, 몸무게, 사는곳
    @Enumerated(EnumType.STRING)
    @Column(name = "dream_tall_type")
    private TallType tallType;

    @Enumerated(EnumType.STRING)
    @Column(name = "dream_body_type")
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "dream_locationArea")
    private LocationCategory locationCategory;

    @Override
    public int compareObjective(DetailProfiles comparable) {
        int score = tallType.getMatchingScore(comparable.getTallType())
                + bodyType.getMatchingScore(comparable.getBodyType())
                + locationCategory.getMatchingScore(comparable.getLocationCategory());

        return score;
    }
}
