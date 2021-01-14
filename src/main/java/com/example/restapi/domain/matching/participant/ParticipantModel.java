package com.example.restapi.domain.matching.participant;

import com.example.restapi.domain.comments.CommentModel;
import com.example.restapi.domain.posts.PostModel;
import com.example.restapi.domain.user.profile.DetailProfiles;
import com.example.restapi.domain.user.profile.DreamProfiles;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParticipantModel extends RepresentationModel<ParticipantModel> {
    private String userEmail;
    private LocalDate nextMatchingDate;

}
