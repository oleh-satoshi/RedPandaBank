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
//        // Создаем клавиуатуру
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        replyKeyboardMarkup.setSelective(false);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//
//        // Создаем список строк клавиатуры
//        List<KeyboardRow> keyboard = new ArrayList<>();
//
//        // Первая строчка клавиатуры
//        KeyboardRow firstRow = new KeyboardRow();
//        KeyboardRow secondRow = new KeyboardRow();
//
//        // Добавляем кнопки в первую строку клавиатуры
//        firstRow.add(ScheduleButtonEnum.CREATE_EVENT.getName());
//        firstRow.add("Удалить урок");
//        secondRow.add(ScheduleButtonEnum.BACK_TO_MAIN_MENU.getName());
//
//        // Добавляем все строки клавиатуры в список
//        keyboard.add(firstRow);
//        keyboard.add(secondRow);
//
//        // добавляем список в клавиатуру
//        replyKeyboardMarkup.setKeyboard(keyboard);
//        return replyKeyboardMarkup;
    }
}