//package com.example.restapi.domain.matching.component;
//
//import com.example.restapi.domain.user.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.data.util.Pair;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class MatchingCombinatorTest {
//
//    private final MatchingCombinator matchingCombinator = new MatchingCombinator();
//
//    @Test
//    public void corret_shuffle_pairOfList(){
//
//        // given
//        int size = 10;
//        List<User> users = getIdentifies(size);
//
//        // when && then
//        List<Pair<User, User>> allPairsAndShuffle = matchingCombinator.getAllPairsAndShuffle(users);
//        String origin = Arrays.toString(allPairsAndShuffle.toArray());
//        String shuffle = Arrays.toString(matchingCombinator.shufflePairs(allPairsAndShuffle).toArray());
//        System.out.println(origin);
//        assertNotEquals(origin,shuffle);
//
//        origin = Arrays.toString(allPairsAndShuffle.toArray());
//        shuffle = Arrays.toString(matchingCombinator.shufflePairs(allPairsAndShuffle).toArray());
//        System.out.println(origin);
//        assertNotEquals(origin,shuffle);
//    }
//
//    @Test
//    public void corret_listToMap(){
//
//        int size = 10;
//        List<User> users = getIdentifies(size);
//
//        // when && then
//        List<Pair<User, User>> allPairsFromLists = matchingCombinator.getAllPairsFromLists(users);
//        Map<User, List<User>> pairsMapFrom = matchingCombinator.getPairsMapFrom(allPairsFromLists);
//
//        for(Map.Entry<User, List<User>>  e : pairsMapFrom.entrySet()){
//            System.out.print("key=" + e.getKey().getIdentify());
//            System.out.println(" size = " + e.getValue().size());
//            System.out.println(Arrays.toString(e.getValue().toArray()));
//        }
//    }
//
//    @Test
//    public void corret_AllComb_Count(){
//
//        int size = 10;
//        List<User> users = getIdentifies(size);
//
//        // when
//        List<Pair<User, User>> allPairsFromLists = matchingCombinator.getAllPairsFromLists(users);
//
//        // then
//        assertEquals(allPairsFromLists.size(), combination(size,2));
//
//    }
//
//    public List<User> getIdentifies(int size){
//
//        List<User> identifiables = new ArrayList<>();
//
//        for(int i=1; i<=size; i++) {
//            identifiables.add(User.builder().id(Long.valueOf(i)).build());
//        }
//
//        return identifiables;
//    }
//
//    private int combination(int n, int r) {
//        if(n == r || r == 0)
//            return 1;
//        else
//            return combination(n - 1, r - 1) + combination(n - 1, r);
//    }
//
//
//}