package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class InlineScheduleAddEventDay implements Pressable {
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(WeekDay.MONDAY.getDay(), Command.SAVE_EVENT_DAY.getName() + WeekDay.MONDAY.getDay())
                .extraButton(WeekDay.TUESDAY.getDay(), Command.SAVE_EVENT_DAY.getName() + WeekDay.TUESDAY.getDay())
                .endRow()
                .row()
                .button(WeekDay.WEDNESDAY.getDay(), Command.SAVE_EVENT_DAY.getName() + WeekDay.WEDNESDAY.getDay())
                .extraButton(WeekDay.THURSDAY.getDay(), Command.SAVE_EVENT_DAY.getName() + WeekDay.THURSDAY.getDay())
                .endRow()
                .row()
                .button(WeekDay.FRIDAY.getDay(), Command.SAVE_EVENT_DAY.getName() + WeekDay.FRIDAY.getDay())
                .extraButton(WeekDay.SATURDAY.getDay(), Command.SAVE_EVENT_DAY.getName() + WeekDay.SATURDAY.getDay())
                .endRow()
                .build();
    }
}
