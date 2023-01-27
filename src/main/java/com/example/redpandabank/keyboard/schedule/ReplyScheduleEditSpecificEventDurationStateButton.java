package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class ReplyScheduleEditSpecificEventDurationStateButton implements Pressable {
    @Override
    public ReplyKeyboard getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Я ошибся, давай еще раз!", Command.EDIT_SCHEDULE_EVENT_DURATION.getName())
                .endRow()
                .row()
                .button("Я закончил!", Command.TO_MAIN_MENU.getName())
                .endRow()
                .build();
    }
}
