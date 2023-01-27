package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.keyboard.InlineButton;
import com.example.redpandabank.keyboard.keyboardBuilder.InlineKeyboardMarkupBuilderImpl;
import com.example.redpandabank.keyboard.keyboardBuilder.ReplyKeyboardMarkupBuilderImpl;
import com.example.redpandabank.model.Lesson;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuButton  {
    public InlineKeyboardMarkup getScheduleMenuButton() {
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
