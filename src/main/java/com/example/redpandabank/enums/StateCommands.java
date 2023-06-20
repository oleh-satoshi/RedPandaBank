package com.example.redpandabank.enums;

public enum StateCommands {
    NO_STATE("no_state"),
    JUST_CREATED("just_created"),
    SAVE_TITLE_EVENT("/saveTitle"),
    SAVE_EVENT_TEACHER_NAME("/saveTeacher"),
    SAVE_EVENT_DURATION("/saveDuration"),
    SAVE_SPECIFIC_EVENT_DAY("/saveSpecificEventDay"),
    EDIT_SPECIFIC_EVENT_FIELD("/editScheduleEventField"),
    EDIT_SPECIFIC_EVENT_TEACHER_NAME("/editScheduleEventTeacherName"),
    EDIT_SPECIFIC_EVENT_START_TIME("/editSpecificStartTimeOfEvent"),
    EDIT_SPECIFIC_EVENT_START_TIME_STEP2("/setTimeToLessonSchedule"),
    ADD_SPECIFIC_EVENT_START_TIME("/addStartTimeToLessonSchedule"),
    EDIT_SPECIFIC_SCHEDULE_EVENT_DURATION("/editSpecificScheduleEventDuration");


    String state;

    StateCommands(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
