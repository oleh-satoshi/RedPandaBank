package com.example.redpandabank.service;

public interface MessageSender {
    void sendToTelegram(Long chatId, String content);
}
