package com.example.restapi.service.matching;

import com.example.restapi.domain.matching.MatchingResult;
import com.example.restapi.domain.matching.MatchingResultRepository;
import com.example.restapi.domain.matching.MatchingWaitEntity;
import com.example.restapi.domain.matching.MatchingWaitEntityRepository;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.web.common.BaseControllerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchingServiceTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchingWaitEntityRepository matchingWaitEntityRepository;

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
        matchingWaitEntityRepository.deleteAllInBatch();
//        userRepository.deleteAllInBatch();

    }
    @Before
    public void userSetting() {

        List<User> users = getAuthUsers(size);
        userRepository.saveAll(users);

        List<MatchingWaitEntity> matchingWaitEntities = new ArrayList<>();
        for(int i=0; i<size; i++){
            MatchingWaitEntity build = MatchingWaitEntity.builder()
                    .user(users.get(i))
                    .nextMatchingDate(matchingManager.getNextMatchingDate(LocalDate.now()))
                    .build();

            matchingWaitEntities.add(build);
        }

       matchingWaitEntityRepository.saveAll(matchingWaitEntities);
    }

    @Test
    public void matching() {

        //given
        int expectMatchingResultEntities = size / 2;

        matchingService.matching();
        List<User> all = userRepository.findAll();
        System.out.println(all.size());
        List<MatchingWaitEntity> waits = matchingWaitEntityRepository.findAll();
        List<MatchingResult> results = matchingResultRepository.findAll();
        int afterWaitEntities = waits.size();
        int afterMatchingEntity = results.size();

        assertEquals(afterWaitEntities, 0);
        assertEquals(afterMatchingEntity, expectMatchingResultEntities);
    }

}