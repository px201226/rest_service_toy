package com.example.restapi.domain.matching.component;

import com.example.restapi.domain.user.User;
import org.junit.Test;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class MatchingManagerTest {

    private final MatchingManager matchingManager = new MatchingManager(new MatchingCombinator());

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
    public void corret_random_matching(){
        int size = 1010;
       for(int i=1; i<=size; i++){
            List<User> users = getUsers(size);
            List<Pair<User,User>> pairs = matchingManager.matchingRandom(users);
            //System.out.println(Arrays.toString(pairs.toArray()));
           System.out.println(i);
            assertEquals(users.size() / 2, pairs.size());
        }

    }

    public List<User> getUsers(int size){

        List<User> users = new ArrayList<>();

        for(int i=1; i<=size; i++) {
            users.add(User.builder().id(Long.valueOf(i)).build());
        }

        return users;
    }

}