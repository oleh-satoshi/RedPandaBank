package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.service.TranslateService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleDeleteSpecificEventStartTime2Button implements Pressable {
    final TranslateService translateService;
    final String DELETE_ANOTHER_LESSON_START = "delete-another-lesson-start";
    final String DONE = "done";

    public InlineScheduleDeleteSpecificEventStartTime2Button(TranslateService translateService) {
        this.translateService = translateService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(DELETE_ANOTHER_LESSON_START),
                        Command.DELETE_SPECIFIC_EVENT_START_TIME.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(DONE),
                        Command.TO_MAIN_MENU.getName())
                .endRow()
                .build();    }
}
