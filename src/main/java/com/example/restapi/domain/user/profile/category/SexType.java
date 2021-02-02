package com.example.restapi.domain.user.profile.category;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;


public enum SexType {
    MAN( "남자"),
    WOMAN("여자");

    private static final Map<String, SexType> stringToEnum =
            Stream.of(values()).collect(toMap(Objects::toString, e -> e));

    private String description;

    SexType(String description) {
        this.description = description;
    }

    @JsonCreator
    public static SexType fromText(String text){
        return stringToEnum.get(text);
    }


}
