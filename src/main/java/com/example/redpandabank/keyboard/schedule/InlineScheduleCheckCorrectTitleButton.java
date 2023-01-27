package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.PressableWithArgument;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class InlineScheduleCheckCorrectTitleButton implements
        PressableWithArgument<ReplyKeyboard, Lesson> {

    @Override
    public ReplyKeyboard getKeyboard(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Я написал с ошибкой, давай попробуем еще раз", Command.EDIT_SCHEDULE_EVENT_TITLE.getName())
                .endRow()
                .row()
                .button("Дальше", Command.SAVE_EVENT_TEACHER_NAME.getName()
                        + LessonService.COLON_SEPARATOR + lesson.getTitle())
                .endRow()
                .build();
    }
}
