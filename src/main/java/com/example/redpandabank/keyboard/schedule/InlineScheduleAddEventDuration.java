package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class InlineScheduleAddEventDuration implements Pressable {
    @Override
    public ReplyKeyboard getKeyboard() {
        return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button("Изменить длительность урока", Command.EDIT_EVENT_DURATION.getName())
                .endRow()
                .row()
                .button("Дальше", Command.SAVE_EVENT_DAY.getName())
                .endRow()
                .build();
    }
}
