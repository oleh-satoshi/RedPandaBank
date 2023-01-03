package com.example.redpandabank.model;

import lombok.Getter;

@Getter
public enum Command {
    START("/start"),
    SCHEDULE("Расписание"),
    EDIT_SCHEDULE("Редактировать"),
    ADD_SCHEDULE_EVENT("Новый урок"),
    SAVE_EVENT_NAME("/saveName"),
    SAVE_EVENT_TEACHER_NAME("/saveTeacher"),
    SAVE_EVENT_DURATION("/saveDuration"),
    SAVE_EVENT_SCHEDULE("/saveSchedule"),
    BACK_TO_MAIN_MENU("Главное меню"),
    SAVE_EVENT_MONDAY("/scheduleMonday"),
    SAVE_EVENT_TUESDAY("/scheduleTuesday"),
    SAVE_EVENT_WEDNESDAY("/scheduleWednesday"),
    SAVE_EVENT_THURSDAY("/scheduleThursday"),
    SAVE_EVENT_FRIDAY("/scheduleFriday"),
    SAVE_EVENT_SATURDAY("/scheduleSaturday"),
    SAVE_EVENT_SUNDAY("/scheduleSunday"),
    CHOOSE_EVENT_BY_DAY("Выбрать день");
    private final String name;

    Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
