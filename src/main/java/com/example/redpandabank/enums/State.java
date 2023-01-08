package com.example.redpandabank.enums;

public enum State {
    EDIT_TITLE_EVENT("editTitleEvent");
    String day;

    State(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }
}
