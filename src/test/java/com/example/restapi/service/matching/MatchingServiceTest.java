//package com.example.restapi.service.matching;
//
//import com.example.restapi.domain.matching.result.MatchingResult;
//import com.example.restapi.domain.matching.result.MatchingResultRepository;
//import com.example.restapi.domain.matching.participant.Participant;
//import com.example.restapi.domain.matching.participant.ParticipantRepository;
//import com.example.restapi.domain.matching.component.MatchingManager;
//import com.example.restapi.domain.user.User;
//import com.example.restapi.domain.user.UserRepository;
//import com.example.restapi.web.common.BaseControllerTest;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//public class MatchingServiceTest extends BaseControllerTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ParticipantRepository participantRepository;
//
//    @Autowired
//    private MatchingResultRepository matchingResultRepository;
//
//    @Autowired
//    private MatchingService matchingService;
//
//    @Autowired
//    private MatchingManager matchingManager;
//
//    private User loginUser;
//
//    // given
//    int size = 11;
//
//    @AfterEach
//    public void clean(){
//        matchingResultRepository.deleteAllInBatch();
//        participantRepository.deleteAllInBatch();
////        userRepository.deleteAllInBatch();
//
//    }
//    @BeforeEach
//    public void userSetting() {
//
//        List<User> users = getAuthUsers(size);
//        userRepository.saveAll(users);
//
//        List<Participant> matchingWaitEntities = new ArrayList<>();
//        for(int i=0; i<size; i++){
//            Participant build = Participant.builder()
//                    .user(users.get(i))
//                    .nextMatchingDate(matchingManager.getNextMatchingDate(LocalDate.now()))
//                    .build();
//
//            matchingWaitEntities.add(build);
//        }
//
//       participantRepository.saveAll(matchingWaitEntities);
//    }
//
//    @Test
//    public void matching() {
//
//        //given
//        int expectMatchingResultEntities = size / 2;
//
//        matchingService.matching();
//        List<User> all = userRepository.findAll();
//        System.out.println(all.size());
//        List<Participant> waits = participantRepository.findAll();
//        List<MatchingResult> results = matchingResultRepository.findAll();
//        int afterWaitEntities = waits.size();
//        int afterMatchingEntity = results.size();
//
//        assertEquals(afterWaitEntities, 0);
//        assertEquals(afterMatchingEntity, expectMatchingResultEntities);
//    }
//
//}