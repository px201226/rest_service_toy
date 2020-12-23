package com.example.restapi.domain.matching;

import com.example.restapi.domain.matching.component.MatchingManager;
import com.example.restapi.domain.user.User;
import org.junit.Test;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class MatchingManagerTest {

    private final MatchingManager matchingManager = new MatchingManager();

    @Test
    public void corret_nextMatchingDay(){
        LocalDate nextMatchingDate;

        int[] table = new int[]{5,5,5,5,12,12,12,12,12,12,12,19};
        for(int i=1; i<=table.length; i++) {
            nextMatchingDate = matchingManager.getNextMatchingDate(LocalDate.of(2020, 12, i));
            assertEquals(nextMatchingDate, LocalDate.of(2020, 12, table[i - 1]));
        }
    }

    @Test
    public void corret_shuffle_pairOfList(){
        // given
        List<Pair<Long,Long>> answers = new ArrayList<>();
        int size = 10;
        List<User> users = new ArrayList<>();

        for(int i=0; i<size; i++) {
            users.add(User.builder().id(Long.valueOf(i)).build());
        }

        // when && then
        matchingManager.getAllCombination(users,answers,new boolean[size],0,2 );
        String origin = Arrays.toString(answers.toArray());
        String shuffle = Arrays.toString(matchingManager.shuffleList(answers).toArray());
        assertNotEquals(origin,shuffle);

        origin = Arrays.toString(answers.toArray());
        shuffle = Arrays.toString(matchingManager.shuffleList(answers).toArray());
        assertNotEquals(origin,shuffle);
    }

    @Test
    public void corret_AllComb_Count(){

        // given
        List<Pair<Long,Long>> answers = new ArrayList<>();
        int size = 10;
        List<User> users = new ArrayList<>();

        for(int i=0; i<size; i++) {
            users.add(User.builder().id(Long.valueOf(i)).build());
        }

        // when
        matchingManager.getAllCombination(users,answers,new boolean[size],0,2 );

        // then
        assertEquals(answers.size(), combination(size,2));


    }

    private int combination(int n, int r) {
        if(n == r || r == 0)
            return 1;
        else
            return combination(n - 1, r - 1) + combination(n - 1, r);
    }
}