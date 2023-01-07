package com.example.redpandabank.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface MessageSender {
    void sendMessageViaURL(Long chatId, String content);

    SendMessage sendMessageWithInline(Long chatId, String content, InlineKeyboardMarkup keyboardMarkup);

    SendMessage sendMessageViaMessageSender(Long chatId, String content);
}
