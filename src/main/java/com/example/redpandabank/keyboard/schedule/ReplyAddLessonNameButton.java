package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.ReplyKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class ReplyAddLessonNameButton implements Pressable {
    @Override
    public ReplyKeyboard getKeyboard() {
        return ReplyKeyboardMarkupBuilderImpl.create()
                .row()
                .button(Command.SAVE_EVENT_NAME.getName())
                .button(Command.BACK_TO_MAIN_MENU.getName())
                .endRow()
                .build();
    }
}
