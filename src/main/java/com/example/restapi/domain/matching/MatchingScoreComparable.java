package com.example.restapi.domain.matching;

public interface MatchingScoreComparable<T> {
    int compareObjective(T comparable);
}
