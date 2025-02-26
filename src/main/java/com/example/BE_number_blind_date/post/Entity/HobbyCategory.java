package com.example.BE_number_blind_date.post.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum HobbyCategory {
    SPORTS("운동"),
    READING("독서"),
    MOVIES("영화 감상"),
    MUSIC("음악 감상"),
    COOKING("요리"),
    TRAVEL("여행");


    private final String name;

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static HobbyCategory fromString(String value) {
        return Arrays.stream(HobbyCategory.values())
                .filter(h -> h.name.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 취미 값: " + value));
    }

}