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
    final String addScheduleEvent = Command.ADD_SCHEDULE_EVENT.getName();
    final String editScheduleEvent = Command.EDIT_SCHEDULE_EXISTING_EVENT.getName();
    final String deleteScheduleEvent = Command.DELETE_EVENT.getName();

    public InlineScheduleEditMenuButton(TranslateService translateService) {
        this.translateService = translateService;
    }

    public InlineKeyboardMarkup getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(addScheduleEvent, addScheduleEvent)
                .endRow()
                .row()
                .button(editScheduleEvent, editScheduleEvent)
                .endRow()
                .row()
                .button(deleteScheduleEvent, deleteScheduleEvent)
                .endRow()
                .row()
                .button(translateService.getBySlug(BACK), Command.SCHEDULE.getName())
                .endRow()
                .build();
    }
}
