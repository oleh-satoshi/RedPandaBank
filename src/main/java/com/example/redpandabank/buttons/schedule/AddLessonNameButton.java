package com.example.redpandabank.buttons.schedule;

import com.example.redpandabank.model.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddLessonNameButton {
    public ReplyKeyboardMarkup getScheduleMenuButton() {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(false);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow firstRow = new KeyboardRow();
        KeyboardRow secondRow = new KeyboardRow();

        // Добавляем кнопки в первую строку клавиатуры
        firstRow.add(ScheduleButtonEnum.ADD_EVENT_NAME.getName());
        secondRow.add(ScheduleButtonEnum.BACK_TO_MAIN_MENU.getName());

        // Добавляем все строки клавиатуры в список
        keyboard.add(firstRow);
        keyboard.add(secondRow);

        // добавляем список в клавиатуру
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}
