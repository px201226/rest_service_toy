package com.example.restapi.domain.matching;

import com.example.restapi.domain.matching.component.MatchingManager;
import org.junit.Test;

import java.time.LocalDate;

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
}