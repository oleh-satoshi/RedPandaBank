package com.example.redpandabank.keyboard.keyboardBuilder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface KeyboardMarkupBuilder {

    KeyboardMarkupBuilder row();

    KeyboardMarkupBuilder endRow();

}