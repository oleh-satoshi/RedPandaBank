package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.PressableWithArgument;
import com.example.redpandabank.keyboard.builder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleCheckCorrectTitleButton implements
        PressableWithArgument<ReplyKeyboard, Lesson> {
    final TranslateService translateService;
    static final String MADE_MISTAKE = "made-mistake-do-again";
    static final String NEXT = "next";

    public InlineScheduleCheckCorrectTitleButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public ReplyKeyboard getKeyboard(Lesson lesson) {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(MADE_MISTAKE),
                        Commands.EDIT_SCHEDULE_EVENT_TITLE.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(NEXT),
                        Commands.SET_SAVE_EVENT_TEACHER_NAME_STATE.getName()
                        + Separator.COLON_SEPARATOR + lesson.getId())
                .endRow()
                .build();
    }
}
