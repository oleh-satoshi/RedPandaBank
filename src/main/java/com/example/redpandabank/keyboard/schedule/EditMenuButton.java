package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.keyboard.keyboardBuilder.ReplyKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class EditMenuButton {
    public ReplyKeyboardMarkup getScheduleEditMenuButton() {
        return ReplyKeyboardMarkupBuilderImpl.create()
                .row()
                .button(ScheduleButtonEnum.CREATE_EVENT.getName())
                .button(ScheduleButtonEnum.DELETE_EVENT.getName())
                .endRow()
                .row()
                .button(ScheduleButtonEnum.EDIT_EVENT.getName())
                .endRow()
                .row()
                .button(ScheduleButtonEnum.BACK_TO_MAIN_MENU.getName())
                .endRow()
                .build();
    }
}
