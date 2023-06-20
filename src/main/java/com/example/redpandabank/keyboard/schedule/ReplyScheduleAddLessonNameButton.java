package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.builder.ReplyKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
public class ReplyScheduleAddLessonNameButton implements Pressable {
    @Override
    public ReplyKeyboard getKeyboard() {
        return ReplyKeyboardMarkupBuilderImpl.create()
                .row()
                .button(StateCommands.SAVE_TITLE_EVENT.getState())
                .button(Commands.TO_MAIN_MENU.getName())
                .endRow()
                .build();
    }
}
