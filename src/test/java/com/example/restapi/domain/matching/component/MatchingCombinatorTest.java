package com.example.restapi.domain.matching.component;

import com.example.restapi.domain.matching.Identifiable;
import com.example.restapi.domain.user.User;
import org.junit.Test;
import org.springframework.data.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MatchingCombinatorTest {

    private final MatchingCombinator matchingCombinator = new MatchingCombinator();

    @Test
    public void corret_shuffle_pairOfList(){

        // given
        List<Pair> answers = new ArrayList<>();
        int size = 10;
        List<Identifiable> users = getIdentifies(size);

        // when && then
        matchingCombinator.getAllCombinations(users,answers,new boolean[size],0,2 );
        String origin = Arrays.toString(answers.toArray());
        String shuffle = Arrays.toString(matchingCombinator.shufflePairs(answers).toArray());
        System.out.println(origin);
        assertNotEquals(origin,shuffle);

        origin = Arrays.toString(answers.toArray());
        shuffle = Arrays.toString(matchingCombinator.shufflePairs(answers).toArray());
        System.out.println(origin);
        assertNotEquals(origin,shuffle);
    }

    @Test
    public void corret_listToMap(){

        // given
        List<Pair> answers = new ArrayList<>();

        int size = 10;
        List<Identifiable> users = getIdentifies(size);

        // when && then
        matchingCombinator.getAllCombinations(users,answers,new boolean[size],0,2 );
        Map<Identifiable, List<Identifiable>> pairsMapFrom = matchingCombinator.getPairsMapFrom(answers);

        for(Map.Entry<Identifiable, List<Identifiable>>  e : pairsMapFrom.entrySet()){
            System.out.print("key=" + e.getKey().getIdentify());
            System.out.println(" size = " + e.getValue().size());
            System.out.println(Arrays.toString(e.getValue().toArray()));
        }
    }

    @Test
    public void corret_AllComb_Count(){

        // given
        List<Pair> answers = new ArrayList<>();
        int size = 10;
        List<Identifiable> users = getIdentifies(size);

        // when
        matchingCombinator.getAllCombinations(users,answers,new boolean[size],0,2 );

        // then
        assertEquals(answers.size(), combination(size,2));

    }

    public List<Identifiable> getIdentifies(int size){

        List<Identifiable> identifiables = new ArrayList<>();

        for(int i=1; i<=size; i++) {
            identifiables.add(User.builder().id(Long.valueOf(i)).build());
        }

        return identifiables;
    }

    private int combination(int n, int r) {
        if(n == r || r == 0)
            return 1;
        else
            return combination(n - 1, r - 1) + combination(n - 1, r);
    }

}