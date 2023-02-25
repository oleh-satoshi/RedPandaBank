package com.example.redpandabank.keyboard.keyboardBuilder;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReplyKeyboardMarkupBuilderImpl implements KeyboardMarkupBuilder {
    final List<KeyboardRow> keyboard = new ArrayList<>();
    KeyboardRow row;

    private ReplyKeyboardMarkupBuilderImpl(){
    }

    public static ReplyKeyboardMarkupBuilderImpl create() {
        return new ReplyKeyboardMarkupBuilderImpl();
    }

    public static ReplyKeyboardMarkupBuilderImpl create(Long chatId) {
        ReplyKeyboardMarkupBuilderImpl builder = new ReplyKeyboardMarkupBuilderImpl();
        return builder;
    }

    @Override
    public ReplyKeyboardMarkupBuilderImpl row() {
        this.row = new KeyboardRow();
        return this;
    }

    public ReplyKeyboardMarkupBuilderImpl button(String text) {
        row.add(text);
        return this;
    }

    @Override
    public ReplyKeyboardMarkupBuilderImpl endRow() {
        this.keyboard.add(this.row);
        this.row = null;
        return this;
    }

    public ReplyKeyboardMarkup build() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
}
