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
                .button("Monday", WeekDay.MONDAY.getDay())
                .extraButton("Tuesday",WeekDay.TUESDAY.getDay())
                .extraButton("Wednesday", WeekDay.WEDNESDAY.getDay())
                .endRow()
                .row()
                .button("Thursday",WeekDay.THURSDAY.getDay())
                .extraButton("Friday", WeekDay.FRIDAY.getDay())
                .extraButton("Saturday",WeekDay.SATURDAY.getDay())
                .endRow()
                .row()
                .button("Back", Command.SCHEDULE.getName())
                .endRow()
                .build();
    }
}
