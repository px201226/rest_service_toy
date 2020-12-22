package com.example.restapi.domain.matching.component;

import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class MatchingManager {

    private final int MATCHING_DAY = 6;                         // SATURDAY


    // 소개팅이 1주일에 특정 요일에 한번 열리므로, 다음 소개팅 날짜를 구한다.
    public LocalDate getNextMatchingDate(LocalDate current){
        int differ =  MATCHING_DAY - current.getDayOfWeek().getValue();
        differ = differ < 0 ? 7 + differ : differ;
        int remindDay = differ == 0 ? 7 : differ;
        LocalDate nextMatchingDate = current.plusDays((long) remindDay);
        return nextMatchingDate;
    }
}
