package com.example.redpandabank.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface MessageSender {
    void sendMessageToTelegram(Long chatId, String content);

    SendMessage sendMessageToTelegramWithInline(Long chatId, String content, InlineKeyboardMarkup keyboardMarkup);

}
