package com.example.redpandabank.keyboard.schedule;

import com.example.redpandabank.keyboard.keyboardBuilder.ReplyKeyboardMarkupBuilderImpl;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddLessonNameButton {
    public ReplyKeyboardMarkup getScheduleMenuButton() {
        return ReplyKeyboardMarkupBuilderImpl.create()
                .row()
                .button(ScheduleButtonEnum.ADD_EVENT_NAME.getName())
                .button(ScheduleButtonEnum.BACK_TO_MAIN_MENU.getName())
                .endRow()
                .build();
    }
}
