package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.util.Separator;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineScheduleAddExtraDaySpecificStartTimeButton implements Pressable {
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(WeekDay.MONDAY.getDay(), Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.MONDAY.getDay())
                .extraButton(WeekDay.TUESDAY.getDay(), Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.TUESDAY.getDay())
                .endRow()
                .row()
                .button(WeekDay.WEDNESDAY.getDay(), Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.WEDNESDAY.getDay())
                .extraButton(WeekDay.THURSDAY.getDay(), Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.THURSDAY.getDay())
                .endRow()
                .row()
                .button(WeekDay.FRIDAY.getDay(), Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.FRIDAY.getDay())
                .extraButton(WeekDay.SATURDAY.getDay(), Command.SET_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName()
                        + Separator.COLON_SEPARATOR + WeekDay.SATURDAY.getDay())
                .endRow()
                .build();
    }
}
