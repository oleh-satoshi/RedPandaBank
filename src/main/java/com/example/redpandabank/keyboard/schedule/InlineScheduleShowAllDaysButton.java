package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.enums.WeekDay;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class InlineScheduleShowAllDaysButton implements Pressable<InlineKeyboardMarkup> {
    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(WeekDay.MONDAY.getDay(), WeekDay.MONDAY.getDay())
                .extraButton(WeekDay.TUESDAY.getDay(),WeekDay.TUESDAY.getDay())
                .extraButton(WeekDay.WEDNESDAY.getDay(), WeekDay.WEDNESDAY.getDay())
                .endRow()
                .row()
                .button(WeekDay.THURSDAY.getDay(),WeekDay.THURSDAY.getDay())
                .extraButton(WeekDay.FRIDAY.getDay(), WeekDay.FRIDAY.getDay())
                .extraButton(WeekDay.SATURDAY.getDay(),WeekDay.SATURDAY.getDay())
                .endRow()
                .row()
                .button("Back", Command.SCHEDULE.getName())
                .endRow()
                .build();
    }
}
