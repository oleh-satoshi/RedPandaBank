package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.PressableWithArgument;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class InlineEditSpecificEventStartTimeChooseOperationButton implements PressableWithArgument<Lesson> {
    @Override
    public ReplyKeyboard getKeyboard(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Изменить время", Command.EDIT_SCHEDULE_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button("Добавить время", Command.ADD_SPECIFIC_EVENT_START_TIME.getName()
                        + LessonService.COLON_SEPARATOR + lesson.getTitle())
                .endRow()
                .row()
                .button("Удалить время", Command.DELETE_SPECIFIC_EVENT_START_TIME.getName())
                .endRow()
                .build();
    }
}
