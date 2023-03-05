package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.PressableWithArgument;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddTeacherNameButton implements
        PressableWithArgument<ReplyKeyboard, Lesson> {
    final TranslateService translateService;
    final String CHANGE_TEACHER_NAME = "change-teacher-name";
    final String NEXT = "next";

    public InlineScheduleAddTeacherNameButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(CHANGE_TEACHER_NAME),
                        Command.EDIT_EVENT_TEACHER_NAME.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(NEXT),
                        Command.SAVE_EVENT_DURATION.getName()
                        + Separator.COLON_SEPARATOR + lesson.getLessonId())
                .endRow()
                .build();
    }
}
