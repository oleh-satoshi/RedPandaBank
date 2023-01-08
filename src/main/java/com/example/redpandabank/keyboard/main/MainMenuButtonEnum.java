package com.example.redpandabank.keyboard.main;

import com.example.redpandabank.enums.Command;

public enum MainMenuButtonEnum {
    SCHEDULE(Command.SCHEDULE.getName());
    private String name;

    MainMenuButtonEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
