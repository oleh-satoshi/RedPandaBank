package com.example.redpandabank.model;

import lombok.Getter;

@Getter
public enum Command {
    START("/start"),
    SCHEDULE("Расписание"),
    EDIT_SCHEDULE("Редактировать");
    private final String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
