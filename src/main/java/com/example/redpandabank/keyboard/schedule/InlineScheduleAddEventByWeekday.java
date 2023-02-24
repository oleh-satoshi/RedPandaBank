package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddEventByWeekday implements Pressable {
    @Override
    public ReplyKeyboard getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(WeekDay.MONDAY.getDay(), "/scheduleMonday")
                .extraButton(WeekDay.TUESDAY.getDay(), "/scheduleTuesday")
                .endRow()
                .row()
                .button(WeekDay.WEDNESDAY.getDay(), "/scheduleWednesday")
                .extraButton(WeekDay.THURSDAY.getDay(), "/scheduleThursday")
                .endRow()
                .row()
                .button(WeekDay.FRIDAY.getDay(), "/scheduleThursday")
                .extraButton(WeekDay.SATURDAY.getDay(), "/scheduleSaturday")
                .endRow()
                .build();
    }
}
