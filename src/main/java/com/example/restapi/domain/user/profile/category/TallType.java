package com.example.restapi.domain.user.profile.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Getter
public enum TallType {
    SMALL(0, "작음"),
    LITTEL_SMLL(1, "살짝 작음"),
    NORMAL(2, "평균"),
    LITTLE_TALL(3, "살짝 큼"),
    TALL(4, "큼");

    private static final Map<String, TallType> stringToEnum =
            Stream.of(values()).collect(toMap(Objects::toString, e -> e));

    private int code;
    private String description;

    TallType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static TallType fromText(String text){
        return stringToEnum.get(text);
    }


    public int getMatchingScore(TallType other){
        int total = values().length;
        int matchedDeltha = total - Math.abs(this.code - other.code);
        return (int) ( matchedDeltha / (double) total * 100);
    }
}
