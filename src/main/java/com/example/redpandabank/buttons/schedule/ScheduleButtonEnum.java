package com.example.redpandabank.buttons.schedule;

import com.example.redpandabank.model.Command;

public enum ScheduleButtonEnum {
    EDIT(Command.EDIT_SCHEDULE.getName()),
    CREATE_EVENT(Command.ADD_SCHEDULE_EVENT.getName()),
    ADD_EVENT_NAME(Command.ADD_SCHEDULE_EVENT.getName()),
    //SAVE_EVENT_NAME(Command.SAVE_EVENT_NAME.getName()),
    BACK_TO_MAIN_MENU(Command.BACK_TO_MAIN_MENU.getName()),
    ADD_EVENT_DESCRIPTION(Command.SAVE_EVENT_DESCRIPTION.getName());

    private String name;

    ScheduleButtonEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
