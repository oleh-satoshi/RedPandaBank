package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineEditScheduleMenuButton {
    public InlineKeyboardMarkup getInline() {
        String addScheduleEvent = Command.ADD_SCHEDULE_EVENT.getName();
        String editScheduleEvent = Command.EDIT_SCHEDULE_EXISTING_EVENT.getName();
        String deleteScheduleEvent = Command.DELETE_EVENT.getName();

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
                .button("Back", Command.SCHEDULE.getName())
                .endRow()
                .build();
    }
}
