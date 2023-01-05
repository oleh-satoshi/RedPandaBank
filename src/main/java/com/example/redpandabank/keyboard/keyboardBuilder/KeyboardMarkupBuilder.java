package com.example.redpandabank.keyboard.keyboardBuilder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface KeyboardMarkupBuilder {

    void setChatId(Long chatId);

    KeyboardMarkupBuilder setText(String text);

    KeyboardMarkupBuilder row();

    KeyboardMarkupBuilder endRow();

    SendMessage build();

}