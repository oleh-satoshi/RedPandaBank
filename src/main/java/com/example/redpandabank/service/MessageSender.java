package com.example.redpandabank.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageSender {
    void sendMessageToTelegram(Long chatId, String content);
}
