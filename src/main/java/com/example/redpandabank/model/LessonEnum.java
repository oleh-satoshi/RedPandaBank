package com.example.redpandabank.model;

enum LessonEnum {
    MATH("Math"),
    ENGLISH("English"),
    UNKNOWN("unknown");
    private String value;

    LessonEnum(String value) {
        this.value = value;
    }
}