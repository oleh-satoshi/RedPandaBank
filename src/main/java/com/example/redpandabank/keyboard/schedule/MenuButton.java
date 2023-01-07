package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.keyboard.keyboardBuilder.ReplyKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class MenuButton {
    public ReplyKeyboardMarkup getScheduleMenuButton() {
        return ReplyKeyboardMarkupBuilderImpl.create()
                .row()
                .button(ScheduleButtonEnum.CHOOSE_EVENT_BY_DAY.getName())
                .button(ScheduleButtonEnum.EDIT.getName())
                .endRow()
                .row()
                .button(ScheduleButtonEnum.BACK_TO_MAIN_MENU.getName())
                .endRow()
                .build();

    }
}
