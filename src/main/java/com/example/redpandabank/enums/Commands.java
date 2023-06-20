package com.example.redpandabank.enums;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public enum Commands {
    TO_MAIN_MENU("/menu"),
    START("/start"),
    SCHEDULE("/schedule"),
    EDIT_SCHEDULE("Edit schedule"),
    ADD_SCHEDULE_EVENT("Add new lesson"),
    EDIT_EVENT_TEACHER_NAME("/editTeacher"),
    EDIT_EVENT_DURATION("/editEventDuration"),
    ADD_EVENT_EXTRA_DAY("/addEventExtraDay"),
    SAVE_EXTRA_EVENT_TIME("/saveExtraEventTime"),
    SAVE_EVENT_MONDAY("/scheduleMonday"),
    SAVE_EVENT_TUESDAY("/scheduleTuesday"),
    SAVE_EVENT_WEDNESDAY("/scheduleWednesday"),
    SAVE_EVENT_THURSDAY("/scheduleThursday"),
    SAVE_EVENT_FRIDAY("/scheduleFriday"),
    SAVE_EVENT_SATURDAY("/scheduleSaturday"),
    SAVE_EVENT_SUNDAY("/scheduleSunday"),
    SET_SAVE_EVENT_NAME_STATE("/setEventNameState"),
    SET_SAVE_EVENT_TIME_STATE("/setSaveEventTimeState"),
    SET_SAVE_EVENT_TEACHER_NAME_STATE("/setSaveEventTeacherName"),
    SET_SAVE_EVENT_DURATION_STATE("/setSaveEventDurationState"),
    SAVE_EVENT_DAY("/SaveEventDay"),
    SAVE_SPECIFIC_EVENT_DAY("/saveSpecificEventDay"),
    SET_ADD_SPECIFIC_EVENT_START_TIME_STATE("/setAddSpecificEventStartTime"),
    CHOOSE_EVENT_BY_DAY("Show schedule for.."),
    DELETE_EVENT("Delete lesson"),
    DELETE_EVENT_BY_ID("/deleteEventById"),
    RECOVER_EVENT_BY_ID("/recoverData"),
    EDIT_SCHEDULE_EXISTING_EVENT("Edit an already saved lesson"),
    EDIT_SPECIFIC_EXISTING_EVENT("/editSpecificExistingEvent"),
    EDIT_SCHEDULE_EVENT_TEACHER("/editScheduleEventTeacher"),
    EDIT_SCHEDULE_EVENT_START_TIME("/editScheduleEventStartTime"),
    EDIT_SCHEDULE_EVENT_DURATION("/editScheduleEventDuration"),
    EDIT_SCHEDULE_EVENT_TITLE("/cancelSaveTitle"),
    EDIT_SCHEDULE_EVENT_FIELD("/editScheduleEventField"),
    SHOW_SPECIFIC_EVENT_START_TIME("/showSpecificEventStartTime"),
    EDIT_SPECIFIC_EVENT_START_TIME("/editSpecificEventStartTime"),
    EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION("/editSpecificChooseOperation"),
    DELETE_SPECIFIC_EVENT_START_TIME("/deleteTimeToLessonSchedule"),
    DELETE_SPECIFIC_EVENT_START_TIME_2("/deleteTimeToLessonSchedule2"),
    ADD_DAY_SPECIFIC_EVENT_START_TIME("/addDaySpecificStartTime"),
    ADD_EXTRA_DAY_SPECIFIC_EVENT_START_TIME("/addExtraDaySpecificStartTime"),
    SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME("/setExtraDaySpecificStartTime"),
    SET_LANGUAGE("/setLanguage"),
    MONDAY(WeekDay.MONDAY.getDay()),
    TUESDAY(WeekDay.TUESDAY.getDay()),
    WEDNESDAY(WeekDay.WEDNESDAY.getDay()),
    THURSDAY(WeekDay.THURSDAY.getDay()),
    FRIDAY(WeekDay.FRIDAY.getDay()),
    SATURDAY(WeekDay.SATURDAY.getDay()),
    SUNDAY(WeekDay.SUNDAY.getDay()),
    START_INIT("/startInit"),
    EDIT_EVENT_DAY("/editScheduleDay");

    private final String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Set<String> getGeneralCommands() {
        Set<String> generalCommands = new HashSet();
        for (int i = 0; i < Commands.values().length; i++) {
            generalCommands.add(Commands.values()[i].getName());
        }
        return generalCommands;
    }
}
