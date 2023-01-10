package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.keyboard.keyboardBuilder.ReplyKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuButton {
    public InlineKeyboardMarkup getScheduleMenuButton() {
                return InlineKeyboardMarkupBuilderImpl.create()
                .row()
                .button(ScheduleButtonEnum.CHOOSE_EVENT_BY_DAY.getName(), ScheduleButtonEnum.CHOOSE_EVENT_BY_DAY.getName())
                .endRow()
                .row()
                .button(ScheduleButtonEnum.EDIT.getName(), ScheduleButtonEnum.EDIT.getName())
                .endRow()
                .build();

    }
}
