package com.example.redpandabank.keyboard.keyboardBuilder;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface ReplyKeyboardMarkupBuilder extends KeyboardMarkupBuilder {
    abstract ReplyKeyboardMarkup build();
}
