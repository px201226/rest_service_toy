package com.example.restapi.domain.matching.component;


import com.example.restapi.domain.matching.Identifiable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.*;


@NoArgsConstructor
@Component
public class MatchingCombinator {

    public List<Pair> shufflePairs(List<Pair> pairs){
        Collections.shuffle(pairs);
        return pairs;
    }

    public List<Pair> getAllPairsFromLists(List<Identifiable> identifiables){
        List<Pair> pairs = new ArrayList<>();
        getAllCombinations(identifiables,pairs, new boolean[identifiables.size()], 0 , 2);
        return pairs;
    }

    public List<Pair> getAllPairsAndShuffle(List<Identifiable> identifiables){

        List<Pair> pairs = new ArrayList<>();
        getAllCombinations(identifiables,pairs, new boolean[identifiables.size()], 0 , 2);
        shufflePairs(pairs);
        return pairs;

    }

    public Map<Identifiable,List<Identifiable>> getPairsMapFrom(List<Pair> pairs){

        Map<Identifiable,List<Identifiable>> map = new HashMap<>();

        Iterator<Pair> iterator = pairs.iterator();

        while(iterator.hasNext()){
            Pair<Identifiable,Identifiable> pair = iterator.next();
            List<Identifiable> pairsFromMap = map.getOrDefault(pair.getFirst(), new ArrayList<>());
            pairsFromMap.add(pair.getSecond());
            map.put(pair.getFirst(), pairsFromMap );
        }

        return map;
    }

    public void getAllCombinations(List<Identifiable> identifiables, List<Pair> combinations, boolean isSelect[], int idx, int r){

        if(r==0){
            Pair pair = getResultCombPair(identifiables, isSelect);
            combinations.add(pair);
            return;
        }

        for(int i=idx; i<isSelect.length; i++){
            isSelect[i] = true;
            getAllCombinations(identifiables,combinations,isSelect,i+1, r-1);
            isSelect[i] = false;
        }
    }

    // getAllCombinations 로 탐색된 Pair를 return 한다.
    private Pair<Identifiable,Identifiable> getResultCombPair(List<Identifiable> identifiables, boolean isSelect[]){
        Identifiable first = null, second = null;
        boolean isFirstFlag = true;

        for(int i=0; i<isSelect.length; i++){
            if(isSelect[i]){
                if(isFirstFlag){
                    isFirstFlag = false;
                    first = identifiables.get(i);
                }else{
                    second = identifiables.get(i);
                    break;
                }
            }
        }
        return Pair.of(first,second);
    }
}
