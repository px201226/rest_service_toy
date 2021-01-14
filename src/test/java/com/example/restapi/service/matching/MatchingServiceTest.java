package com.example.restapi.service.matching;

import com.example.restapi.domain.matching.result.MatchingResult;
import com.example.restapi.domain.matching.result.MatchingResultRepository;
import com.example.restapi.domain.matching.participant.Participant;
import com.example.restapi.domain.matching.participant.ParticipantRepository;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.web.common.BaseControllerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchingServiceTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private MatchingResultRepository matchingResultRepository;

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private MatchingManager matchingManager;

    private User loginUser;

    // given
    int size = 11;

    @After
    public void clean(){
        matchingResultRepository.deleteAllInBatch();
        participantRepository.deleteAllInBatch();
//        userRepository.deleteAllInBatch();

    }
    @Before
    public void userSetting() {

        List<User> users = getAuthUsers(size);
        userRepository.saveAll(users);

        List<Participant> matchingWaitEntities = new ArrayList<>();
        for(int i=0; i<size; i++){
            Participant build = Participant.builder()
                    .user(users.get(i))
                    .nextMatchingDate(matchingManager.getNextMatchingDate(LocalDate.now()))
                    .build();

            matchingWaitEntities.add(build);
        }

       participantRepository.saveAll(matchingWaitEntities);
    }

    @Test
    public void matching() {

        //given
        int expectMatchingResultEntities = size / 2;

        matchingService.matching();
        List<User> all = userRepository.findAll();
        System.out.println(all.size());
        List<Participant> waits = participantRepository.findAll();
        List<MatchingResult> results = matchingResultRepository.findAll();
        int afterWaitEntities = waits.size();
        int afterMatchingEntity = results.size();

        assertEquals(afterWaitEntities, 0);
        assertEquals(afterMatchingEntity, expectMatchingResultEntities);
    }

}