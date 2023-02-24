package com.example.redpandabank.enums;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum Command {
    TO_MAIN_MENU("/menu"),
    NO_STATE("no_state"),
    START("/start"),
    SCHEDULE("/schedule"),
    EDIT_SCHEDULE("Edit schedule"),
    ADD_SCHEDULE_EVENT("Add new lesson"),
    SAVE_EVENT_NAME("/saveTitle"),
    SAVE_EVENT_TEACHER_NAME("/saveTeacher"),
    EDIT_EVENT_TEACHER_NAME("/editTeacher"),
    SAVE_EVENT_DURATION("/saveDuration"),
    EDIT_EVENT_DURATION("/editEventDuration"),
    SAVE_EVENT_DAY("/saveEventDay"),
    ADD_EVENT_EXTRA_DAY("/addEventExtraDay"),
    SAVE_EVENT_TIME("/addEventTime"),
    SAVE_EXTRA_EVENT_TIME("/saveExtraEventTime"),
    SAVE_EVENT_MONDAY("/scheduleMonday"),
    SAVE_EVENT_TUESDAY("/scheduleTuesday"),
    SAVE_EVENT_WEDNESDAY("/scheduleWednesday"),
    SAVE_EVENT_THURSDAY("/scheduleThursday"),
    SAVE_EVENT_FRIDAY("/scheduleFriday"),
    SAVE_EVENT_SATURDAY("/scheduleSaturday"),
    SAVE_EVENT_SUNDAY("/scheduleSunday"),
    CHOOSE_EVENT_BY_DAY("Show schedule for.."),
    DELETE_EVENT("Delete lesson"),
    DELETE_EVENT_BY_ID("/deleteEventById"),
    RECOVER_EVENT_BY_ID("/recoverData"),
    EDIT_SCHEDULE_EXISTING_EVENT("Edit an already saved lesson"),
    EDIT_SPECIFIC_EXISTING_EVENT("/editSpecificExistingEvent"),
    EDIT_SPECIFIC_EVENT_FIELD("/editScheduleEventField"),
    EDIT_SCHEDULE_EVENT_TEACHER("/editScheduleEventTeacher"),
    EDIT_SCHEDULE_EVENT_START_TIME("/editScheduleEventStartTime"),
    EDIT_SCHEDULE_EVENT_DURATION("/editScheduleEventDuration"),
    EDIT_SCHEDULE_EVENT_TITLE("/cancelSaveTitle"),
    EDIT_SCHEDULE_EVENT_FIELD("/editScheduleEventField"),
    EDIT_SPECIFIC_EVENT_TEACHER_NAME("/editScheduleEventTeacherName"),
    SHOW_SPECIFIC_EVENT_START_TIME("/showSpecificEventStartTime"),
    EDIT_SPECIFIC_EVENT_START_TIME("/editSpecificEventStartTime"),
    EDIT_SPECIFIC_EVENT_START_TIME_CHOOSE_OPERATION("/editSpecificChooseOperation"),
    EDIT_SPECIFIC_EVENT_START_TIME_STEP2("/setTimeToLessonSchedule"),
    ADD_SPECIFIC_EVENT_START_TIME("/addTimeToLessonSchedule"),
    DELETE_SPECIFIC_EVENT_START_TIME("/deleteTimeToLessonSchedule"),
    DELETE_SPECIFIC_EVENT_START_TIME_2("/deleteTimeToLessonSchedule2"),
    ADD_DAY_SPECIFIC_EVENT_START_TIME("/addDaySpecificStartTime"),
    ADD_EXTRA_DAY_SPECIFIC_EVENT_START_TIME("/addExtraDaySpecificStartTime"),
    SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME("/setExtraDaySpecificStartTime"),
    EDIT_SPECIFIC_SCHEDULE_EVENT_DURATION("/editSpecificScheduleEventDuration"),
    SET_LANGUAGE("/setLanguage"),
    START_INIT("/startInit");

    private final String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Set<String> getGeneralCommands() {
        Set<String> generalCommands = new HashSet();
        for (int i = 0; i < Command.values().length; i++) {
            generalCommands.add(Command.values()[i].getName());
        }
        return generalCommands;
    }
}
