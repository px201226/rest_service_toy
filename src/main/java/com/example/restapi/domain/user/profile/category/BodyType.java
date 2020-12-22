package com.example.restapi.domain.user.profile.category;

public enum BodyType {
    SKINNY(0,"날씬"),
    SLIM(1, "슬림"),
    NORMAL(2,"보통"),
    LITTLE_CHUBBY(3,"약간 통통"),
    CHUBBY(4,"통통"),
    MUSCULAR(5, "근육질");

    int code;
    String description;

    BodyType(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
