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

    public <T extends Identifiable> List<Pair<T,T>> shufflePairs(List<Pair<T,T>> pairs){
        Collections.shuffle(pairs);
        return pairs;
    }

    public <T extends Identifiable> List<Pair<T,T>> getAllPairsFromLists(List<T> identifiables){
        List<Pair<T,T>> pairs = new ArrayList<>();
        getAllCombinations(identifiables,pairs, new boolean[identifiables.size()], 0 , 2);
        return pairs;
    }

    public <T extends Identifiable> List<Pair<T,T>> getAllPairsAndShuffle(List<T> identifiables){

        List<Pair<T,T>> pairs = new ArrayList<>();
        getAllCombinations(identifiables,pairs, new boolean[identifiables.size()], 0 , 2);
        shufflePairs(pairs);
        return pairs;

    }

    public <T extends Identifiable> Map<T,List<T>> getPairsMapFrom(List<Pair<T,T>> pairs){

        Map<T,List<T>> map = new TreeMap<>();

        Iterator<Pair<T,T>> iterator = pairs.iterator();

        while(iterator.hasNext()){
            Pair<T,T> pair = iterator.next();
            List<T> pairsFromMap = map.getOrDefault(pair.getFirst(), new ArrayList<>());
            pairsFromMap.add(pair.getSecond());
            map.put(pair.getFirst(), pairsFromMap );
        }

        return map;
    }

    private  <T extends Identifiable>  void getAllCombinations(List<T> identifiables, List<Pair<T,T>> combinations, boolean isSelect[], int idx, int r){

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
    private <T extends Identifiable> Pair<T, T> getResultCombPair(List<T> identifiables, boolean isSelect[]){
        T first = null, second = null;
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
