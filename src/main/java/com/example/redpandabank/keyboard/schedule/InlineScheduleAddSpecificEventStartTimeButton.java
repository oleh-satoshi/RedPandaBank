package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.LessonService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class InlineScheduleAddSpecificEventStartTimeButton implements Pressable {
    @Override
    public ReplyKeyboard getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Понедельник", Command.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.MONDAY.getDay())
                .extraButton("Вторник", Command.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.TUESDAY.getDay())
                .endRow()
                .row()
                .button("Cреда", Command.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.WEDNESDAY.getDay())
                .extraButton("Четверг", Command.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.THURSDAY.getDay())
                .endRow()
                .row()
                .button("Пятница", Command.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.FRIDAY.getDay())
                .extraButton("Суббота", Command.ADD_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.SATURDAY.getDay())
                .endRow()
                .build();    }
}
