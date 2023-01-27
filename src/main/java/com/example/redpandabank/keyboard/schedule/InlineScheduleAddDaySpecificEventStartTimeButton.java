package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.PressableWithArgument;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class InlineScheduleAddDaySpecificEventStartTimeButton implements
        PressableWithArgument<ReplyKeyboard, Lesson> {
    @Override
    public ReplyKeyboard getKeyboard(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Добавить еще одно начало урока", Command.ADD_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button("Добавить еще день на это время", Command.ADD_EXTRA_DAY_SPECIFIC_EVENT_START_TIME.getName())
                .endRow()
                .row()
                .button("Я закончил!", Command.TO_MAIN_MENU.getName())
                .endRow()
                .build();
    }
}
