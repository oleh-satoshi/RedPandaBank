package com.example.redpandabank.enums;

public enum State {
    NO_STATE(Command.NO_STATE.getName()),
    SAVE_TITLE_EVENT(Command.SAVE_EVENT_NAME.getName()),
    SAVE_TEACHER_NAME(Command.SAVE_EVENT_TEACHER_NAME.getName()),
    SAVE_DURATION(Command.SAVE_EVENT_DURATION.getName()),
    SAVE_EVENT_DAY(Command.SAVE_EVENT_DAY.getName()),
    ADD_EVENT_TIME(Command.SAVE_EVENT_TIME.getName()),
    EDIT_SPECIFIC_EVENT_FIELD(Command.EDIT_SPECIFIC_EVENT_FIELD.getName()),
    EDIT_SPECIFIC_EVENT_TEACHER_NAME(Command.EDIT_SPECIFIC_EVENT_TEACHER_NAME.getName()),
    EDIT_SPECIFIC_EVENT_START_TIME(Command.EDIT_SPECIFIC_EVENT_START_TIME.getName()),
    EDIT_SPECIFIC_EVENT_START_TIME_STEP2(Command.EDIT_SPECIFIC_EVENT_START_TIME_STEP2.getName()),
    ADD_SPECIFIC_EVENT_START_TIME(Command.ADD_SPECIFIC_EVENT_START_TIME.getName()),
    EDIT_SPECIFIC_SCHEDULE_EVENT_DURATION(Command.EDIT_SPECIFIC_SCHEDULE_EVENT_DURATION.getName());

    String state;

    State(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
