package com.example.redpandabank.enums;

import lombok.Getter;

@Getter
public enum Command {
    TO_MAIN_MENU("/toMainMenu"),
    NO_STATE("no state"),
    START("/start"),
    SCHEDULE("Расписание"),
    SCHEDULE_2("/schedule"),
    EDIT_SCHEDULE("Редактировать расписание"),
    ADD_SCHEDULE_EVENT("Добавить новый урок"),
    SAVE_EVENT_NAME("/saveTitle"),
    SAVE_EVENT_TEACHER_NAME("/saveTeacher"),
    EDIT_EVENT_TEACHER_NAME("/editTeacher"),
    SAVE_EVENT_DURATION("/saveDuration"),
    EDIT_EVENT_DURATION("/editEventDuration"),
    SAVE_EVENT_DAY("/saveEventDay"),
    ADD_EVENT_EXTRA_DAY("/addEventExtraDay"),
    SAVE_EVENT_TIME("/addEventTime"),
    SAVE_EXTRA_EVENT_TIME("/saveExtraEventTime"),
    BACK_TO_MAIN_MENU("Главное меню"),
    SAVE_EVENT_MONDAY("/scheduleMonday"),
    SAVE_EVENT_TUESDAY("/scheduleTuesday"),
    SAVE_EVENT_WEDNESDAY("/scheduleWednesday"),
    SAVE_EVENT_THURSDAY("/scheduleThursday"),
    SAVE_EVENT_FRIDAY("/scheduleFriday"),
    SAVE_EVENT_SATURDAY("/scheduleSaturday"),
    SAVE_EVENT_SUNDAY("/scheduleSunday"),
    CHOOSE_EVENT_BY_DAY("Покажи расписание на.."),
    DELETE_EVENT("Удалить урок"),
    DELETE_EVENT_BY_ID("/deleteEventById"),
    RECOVER_EVENT_BY_ID("/recoverData"),
    EDIT_SCHEDULE_EXISTING_EVENT("Изменить уже сохраненный урок"),
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
    SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME("/setExtraDaySpecificStartTime")

    ;
    private final String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
