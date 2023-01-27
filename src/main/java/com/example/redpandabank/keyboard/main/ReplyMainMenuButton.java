package com.example.redpandabank.keyboard.main;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.Pressable;
import com.example.redpandabank.keyboard.keyboardBuilder.ReplyKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class ReplyMainMenuButton  implements Pressable {
    @Override
    public ReplyKeyboard getKeyboard() {
        return ReplyKeyboardMarkupBuilderImpl.create()
                .row()
                .button(Command.SCHEDULE.getName())
                .button("тут будут еще кнопки")
                .endRow()
                .build();    }
}
