package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class InlineScheduleMenuButton implements Pressable {
    @Override
    public ReplyKeyboard getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(Command.CHOOSE_EVENT_BY_DAY.getName(), Command.CHOOSE_EVENT_BY_DAY.getName())
                .endRow()
                .row()
                .button(Command.EDIT_SCHEDULE.getName(), Command.EDIT_SCHEDULE.getName())
                .endRow()
                .build();
    }
}
