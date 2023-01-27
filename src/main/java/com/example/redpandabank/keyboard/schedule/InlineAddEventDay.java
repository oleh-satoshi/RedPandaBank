package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.WeekDay;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilder;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class InlineAddEventDay  implements Pressable {
    @Override
    public ReplyKeyboard getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Monday", Command.SAVE_EVENT_DAY.getName() + WeekDay.MONDAY.getDay())
                .extraButton("Tuesday", Command.SAVE_EVENT_DAY.getName() + WeekDay.TUESDAY.getDay())
                .endRow()
                .row()
                .button("Wednesday", Command.SAVE_EVENT_DAY.getName() + WeekDay.WEDNESDAY.getDay())
                .extraButton("Thursday", Command.SAVE_EVENT_DAY.getName() + WeekDay.THURSDAY.getDay())
                .endRow()
                .row()
                .button("Friday", Command.SAVE_EVENT_DAY.getName() + WeekDay.FRIDAY.getDay())
                .extraButton("Saturday", Command.SAVE_EVENT_DAY.getName() + WeekDay.SATURDAY.getDay())
                .endRow()
                .build();
    }
}
