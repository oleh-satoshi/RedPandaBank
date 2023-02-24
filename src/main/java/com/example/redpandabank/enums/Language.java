package com.example.redpandabank.enums;

public enum Language {
    ENGLISH("ENG"),
    UKRAINIAN("UKR");

    String language;

    Language(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
