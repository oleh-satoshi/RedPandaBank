package com.example.redpandabank.keyboard.main;

import com.example.redpandabank.keyboard.keyboardBuilder.ReplyKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainMenuButton {
    public ReplyKeyboardMarkup getMainMenuButton() {
        return ReplyKeyboardMarkupBuilderImpl.create()
                .row()
                .button(MainMenuButtonEnum.SCHEDULE.getName())
                .button("тут будут еще кнопки")
                .endRow()
                .build();
    }
}
