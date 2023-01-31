package com.example.redpandabank.keyboard.keyboardBuilder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface InlineKeyboardMarkupBuilder extends KeyboardMarkupBuilder {
    abstract InlineKeyboardMarkup build();
}
