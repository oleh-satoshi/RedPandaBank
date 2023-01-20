package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.LessonService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineAddExtraDaySpecificStartTimeButton {
    public InlineKeyboardMarkup getInline() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Monday", Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.MONDAY.getDay())
                .extraButton("Tuesday", Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.TUESDAY.getDay())
                .endRow()
                .row()
                .button("Wednesday", Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.WEDNESDAY.getDay())
                .extraButton("Thursday", Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.THURSDAY.getDay())
                .endRow()
                .row()
                .button("Friday", Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.FRIDAY.getDay())
                .extraButton("Saturday", Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + WeekDay.SATURDAY.getDay())
                .endRow()
                .build();
    }
}