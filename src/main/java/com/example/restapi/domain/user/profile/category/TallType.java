package com.example.restapi.domain.user.profile.category;

public enum TallType {
    SMALL(0,"작음"),
    LITTEL_SMLL(1, "살짝 작음"),
    NORMAL(2,"평균"),
    LITTLE_TALL(3,"살짝 큼"),
    TALL(4,"큼");

    int code;
    String description;

    TallType(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
