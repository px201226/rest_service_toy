package com.example.restapi.domain.matching.component;

import com.example.restapi.domain.matching.Identifiable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.time.LocalDate;
import java.util.*;


@RequiredArgsConstructor
@Component
public class MatchingManager {

    private final int MATCHING_DAY_OF_WEEK = 6;                         // SATURDAY(토요일)

    private final MatchingCombinator matchingCombinator;

    // Pair<user_id,user_id>
    public <T extends Identifiable> void getMatchedPair(List<T> identifiables) {
        List<Pair<T,T>> pairs = matchingCombinator.getAllPairsAndShuffle(identifiables);

    }

    public <T extends Identifiable> List<Pair<T,T>> matchingRandom(List<T> identifiables) {

        int totalSize = identifiables.size(), count = 0;

        Map<T, List<T>> pairsMapFrom = matchingCombinator.getPairsMapFrom(
                matchingCombinator.getAllPairsFromLists(identifiables));

        Map<T,Boolean> selectStatus = new TreeMap<>();
        List<Pair<T,T>> result = new ArrayList<>();

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        for (Map.Entry<T, List<T>> entry : pairsMapFrom.entrySet()) {
            T key = entry.getKey();
            List<T> values = entry.getValue();

            if(selectStatus.get(key) != null && selectStatus.get(key) == true) continue;

            int randomPickIdx = random.nextInt(values.size());


            T second = values.get(randomPickIdx);

            selectStatus.put(key,true);

            int loop =0;
            while (selectStatus.get(second) != null && selectStatus.get(second) == true) {
                if(loop++ > 200) break;
                if (isAllSelect(totalSize,count)) return result;
                randomPickIdx = random.nextInt(values.size());
                second =  values.get(randomPickIdx);
            }

            selectStatus.put(second,true);
            result.add(Pair.of(key, second));
            count += 2;
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
    private <T extends Identifiable> boolean isAllSelect(int totalSize, int count) {

        if(totalSize % 2 == 0)
            return totalSize == count;
        else
            return totalSize - 1 == count;

    }
}
