package com.example.restapi.domain.matching.component;

import com.example.restapi.domain.matching.Identifiable;
import com.example.restapi.domain.user.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
        int size = 10;
        for(int i=1; i<=10; i++){
            System.out.println("size=" + 4);
            List<Identifiable> identifies = getIdentifies(i);
            List<Pair> pairs = matchingManager.matchingRandom(identifies);
            System.out.println(Arrays.toString(pairs.toArray()));
            assertEquals(identifies.size() / 2, pairs.size());
        }

    }

    public List<Identifiable> getIdentifies(int size){

        List<Identifiable> identifiables = new ArrayList<>();

        for(int i=1; i<=size; i++) {
            identifiables.add(User.builder().id(Long.valueOf(i)).build());
        }

        return identifiables;
    }

}