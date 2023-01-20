package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilder;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineAddTeacherName {
    public InlineKeyboardMarkup getInline(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Изменить имя учителя", Command.EDIT_EVENT_TEACHER_NAME.getName())
                .endRow()
                .row()
                .button("Дальше", Command.SAVE_EVENT_DURATION.getName() + LessonService.COLON_SEPARATOR + lesson.getTitle())
                .endRow()
                .build();
    }
}
