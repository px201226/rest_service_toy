package com.example.restapi.domain.matching;

public interface ObjectiveComparable<T> extends MatchingScoreComparable<T> {

    @Override
    int compareObjective(T comparable);

}
