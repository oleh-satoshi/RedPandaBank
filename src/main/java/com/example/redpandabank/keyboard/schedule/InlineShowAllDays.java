package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.enums.WeekDay;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineShowAllDays {
    public InlineKeyboardMarkup getInline() {
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
                .build();
    }
}
