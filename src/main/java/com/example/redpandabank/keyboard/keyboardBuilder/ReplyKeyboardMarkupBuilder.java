package com.example.redpandabank.keyboard.keyboardBuilder;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public abstract class ReplyKeyboardMarkupBuilder implements KeyboardMarkupBuilder {
    abstract ReplyKeyboardMarkup build();
}
