package com.example.restapi.service.matching;

import com.example.restapi.domain.matching.MatchingWaitEntity;
import com.example.restapi.domain.matching.MatchingWaitEntityRepository;
import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import com.example.restapi.domain.user.UserRepository;
import com.example.restapi.web.common.BaseControllerTest;
import org.junit.Before;
import org.junit.Test;
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

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchingServiceTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchingWaitEntityRepository matchingWaitEntityRepository;

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private MatchingManager matchingManager;

    private User loginUser;
    @Before
    public void userSetting() {

        // given
        int size = 10;
        List<User> users = getAuthUsers(10);
        userRepository.saveAll(users);

        List<MatchingWaitEntity> matchingWaitEntities = new ArrayList<>();
        for(int i=0; i<size; i++){
            MatchingWaitEntity build = MatchingWaitEntity.builder()
                    .user(users.get(i))
                    .nextMatchingDate(matchingManager.getNextMatchingDate(LocalDate.now()))
                    .build();

            matchingWaitEntities.add(build);
        }

        System.out.println("mmmmmmmmmmmmmmm1");
        System.out.println("mmmmmmmmmmmmmmm2");
       matchingWaitEntityRepository.saveAll(matchingWaitEntities);
        System.out.println("mmmmmmmmmmmmmmm3");
    }

    @Test
    public void matching() {
        System.out.println("111111111111111");
        matchingService.matching();

        List<MatchingWaitEntity> all = matchingWaitEntityRepository.findAll();
        System.out.println(Arrays.toString(all.toArray()));
    }

}