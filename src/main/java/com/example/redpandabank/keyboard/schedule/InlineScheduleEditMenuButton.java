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
public class InlineScheduleEditMenuButton implements Pressable {
    final TranslateService translateService;
    final String BACK = "back";
    final String ADD_NEW_LESSON = "add-new-lesson";
    final String EDIT_SAVED_LESSON = "edit-saved-lesson";
    final String DELETE_LESSON = "delete-lesson";

    public InlineScheduleEditMenuButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(translateService.getBySlug(ADD_NEW_LESSON), Command.ADD_SCHEDULE_EVENT.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(EDIT_SAVED_LESSON), Command.EDIT_SCHEDULE_EXISTING_EVENT.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(DELETE_LESSON), Command.DELETE_EVENT.getName())
                .endRow()
                .row()
                .button(translateService.getBySlug(BACK), Command.SCHEDULE.getName())
                .endRow()
                .build();
    }
}
