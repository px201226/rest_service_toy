package com.example.restapi.domain.user.profile.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;


@Getter
public enum BodyType {
    SKINNY(0, "날씬"),
    SLIM(1, "슬림"),
    NORMAL(2, "보통"),
    LITTLE_CHUBBY(3, "약간 통통"),
    CHUBBY(4, "통통");
   // MUSCULAR(5, "근육질");

    private static final Map<String, BodyType> stringToEnum =
            Stream.of(values()).collect(toMap(Objects::toString, e -> e));

    private int code;
    private String description;


    BodyType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static BodyType fromText(String text){
        return stringToEnum.get(text);
    }

    public int getMatchingScore(BodyType other){
        int total = values().length;
        int matchedDeltha = total - Math.abs(this.code - other.code);
        return (int) ( matchedDeltha / (double) total * 100);
    }

}
