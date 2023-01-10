package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;

public enum ScheduleButtonEnum {
    EDIT(Command.EDIT_SCHEDULE.getName()),
    CREATE_EVENT(Command.ADD_SCHEDULE_EVENT.getName()),
    ADD_EVENT_NAME(Command.SAVE_EVENT_NAME.getName()),
    BACK_TO_MAIN_MENU(Command.BACK_TO_MAIN_MENU.getName()),
    CHOOSE_EVENT_BY_DAY(Command.CHOOSE_EVENT_BY_DAY.getName()),
    DELETE_EVENT(Command.DELETE_EVENT.getName()),
    DELETE_EVENT_BY_ID(Command.DELETE_EVENT_BY_ID.getName()),
    EDIT_EVENT(Command.EDIT_SCHEDULE_EVENT.getName());

    private String name;

    ScheduleButtonEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
