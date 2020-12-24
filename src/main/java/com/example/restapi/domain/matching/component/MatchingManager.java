package com.example.restapi.domain.matching.component;

import com.example.restapi.domain.matching.Identifiable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;


@RequiredArgsConstructor
@Component
public class MatchingManager {

    private final int MATCHING_DAY_OF_WEEK = 6;                         // SATURDAY(토요일)

    private final MatchingCombinator matchingCombinator;

    // Pair<user_id,user_id>
    public void getMatchedPair(List<Identifiable> identifiables) {
        List<Pair> pairs = matchingCombinator.getAllPairsAndShuffle(identifiables);

    }

    public List<Pair> matchingRandom(List<Identifiable> identifiables) {

        List<Pair> result = new ArrayList<>();

        Map<Identifiable, List<Identifiable>> pairsMapFrom = matchingCombinator.getPairsMapFrom(
                matchingCombinator.getAllPairsFromLists(identifiables));
        Map<Identifiable,Boolean> selectStatus = new HashMap<>();

        int size = identifiables.size();

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        for (Map.Entry<Identifiable, List<Identifiable>> entry : pairsMapFrom.entrySet()) {
            Identifiable key = entry.getKey();
            List<Identifiable> values = entry.getValue();

            if(selectStatus.get(key) != null && selectStatus.get(key) == true) continue;

            int randomPickIdx = random.nextInt(values.size());


            Identifiable second = values.get(randomPickIdx);

            selectStatus.put(key,true);

            while (selectStatus.get(second) != null && selectStatus.get(second) == true) {
                if (isAllSelect(selectStatus)) return result;
                randomPickIdx = random.nextInt(values.size());
                second =  values.get(randomPickIdx);
            }

            selectStatus.put(second,true);

            result.add(Pair.of(key, second));
        }


        return result;
    }


    // 소개팅이 1주일에 특정 요일에 한번 열리므로, 다음 소개팅 날짜를 구한다.
    public LocalDate getNextMatchingDate(LocalDate current) {
        int differ = MATCHING_DAY_OF_WEEK - current.getDayOfWeek().getValue();
        differ = differ < 0 ? 7 + differ : differ;
        int remindDay = differ == 0 ? 7 : differ;

        LocalDate nextMatchingDate = current.plusDays((long) remindDay);
        return nextMatchingDate;
    }


    // todo
    // map key에 객체가 있어서 values만 있으면 존재한 값만 해서 완전탐색을 못하니까.
    // select된 값을 카운트해서 하자
    private boolean isAllSelect( Map<Identifiable,Boolean> isSelect) {

        for(Boolean bool : isSelect.values())
            if(bool.booleanValue() == false)
                return false;

        return true;

    }
}
