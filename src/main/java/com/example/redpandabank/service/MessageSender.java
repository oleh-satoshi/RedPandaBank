package com.example.redpandabank.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface MessageSender {
    void sendMessageViaURL(Long chatId, String content);

    SendMessage sendMessageWithInline(Long chatId, String content, ReplyKeyboard keyboard);

    SendMessage sendMessageWithReply(Long chatId, String content, ReplyKeyboard keyboard);

    SendMessage sendMessage(Long chatId, String content);

    EditMessageText sendEditMessage(Long userId, Integer messageId, String content);

    String replaceSpace(String text);

    EditMessageText sendEditMessageWithInline(Long userId, Integer messageId,
                                              InlineKeyboardMarkup keyboard,
                                              String content);

    EditMessageText sendEditMessageWithInlineWithoutMessageId(Long userId,
                                                              InlineKeyboardMarkup keyboard,
                                                              String content);
}
