package com.example.restapi.domain.matching.component;

import com.example.restapi.domain.user.User;
import org.springframework.data.util.Pair;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Component
public class MatchingManager {

    private final int MATCHING_DAY = 6;                         // SATURDAY

    // Pair<user_id,user_id>
    public void getMatchedPair(List<User> users){

    }

    public void getAllCombination(List<User> users, List<Pair<Long,Long>> allCombination, boolean isSelect[], int idx, int r){

        if(r==0){
            Pair<Long,Long> pair = getResultCombPair(users, isSelect);
            allCombination.add(pair);
            return;
        }

        for(int i=idx; i<isSelect.length; i++){
            isSelect[i] = true;
            getAllCombination(users,allCombination,isSelect,i+1, r-1);
            isSelect[i] = false;
        }
    }

    public List<Pair<Long,Long>> shuffleList(List<Pair<Long,Long>> list){
        Collections.shuffle(list);
        return list;
    }

    private Pair<Long,Long> getResultCombPair(List<User> users, boolean isSelect[]){
        Long first = null, second = null;
        boolean isFirstFlag = true;

        for(int i=0; i<isSelect.length; i++){
            if(isSelect[i]){
                if(isFirstFlag){
                    isFirstFlag = false;
                    first = users.get(i).getId();
                }else{
                    second = users.get(i).getId();
                    break;
                }
            }
        }
        return Pair.of(first,second);
    }

    // 소개팅이 1주일에 특정 요일에 한번 열리므로, 다음 소개팅 날짜를 구한다.
    public LocalDate getNextMatchingDate(LocalDate current){
        int differ =  MATCHING_DAY - current.getDayOfWeek().getValue();
        differ = differ < 0 ? 7 + differ : differ;
        int remindDay = differ == 0 ? 7 : differ;

        LocalDate nextMatchingDate = current.plusDays((long) remindDay);
        return nextMatchingDate;
    }
}
