package com.example.redpandabank.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Component
@Scope(value = "prototype")
public class MessageSenderImpl implements MessageSender {
    String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=%s";
    final static String API_TOKEN = "5805955090:AAGJcLtZpPhcAmT6XxtU-6ZnRVhMikJAxe8";
    final static String PARSE_MODE = "HTML";

    @Override
    public void sendMessageViaURL(Long chatId, String content) {
        urlString = String.format(urlString, API_TOKEN, chatId, content, PARSE_MODE);
        sendUrl(urlString);
    }

    @Override
    public SendMessage sendMessageWithInline(Long chatId, String content, ReplyKeyboard keyboard) {
        return new SendMessage().builder()
                .chatId(chatId)
                .text(content)
                .parseMode(PARSE_MODE)
                .replyMarkup(keyboard)
                .build();
    }

    @Override
    public SendMessage sendMessageWithReply(Long chatId, String content, ReplyKeyboard keyboard) {
        return new SendMessage().builder()
                .chatId(chatId)
                .text(content)
                .parseMode(PARSE_MODE)
                .replyMarkup(keyboard)
                .build();
    }

    @Override
    public SendMessage sendMessage(Long chatId, String content) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(content)
                .parseMode(PARSE_MODE)
                .build();
    }

    public EditMessageText sendEditMessageWithInline(Long userId, Integer messageId,
                                                     InlineKeyboardMarkup keyboard, String content) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(userId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(keyboard);
        editMessageText.setText(content);
        editMessageText.setParseMode(PARSE_MODE);
        return editMessageText;
    }

    public EditMessageText sendEditMessage(Long userId, Integer messageId, String content) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(userId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(content);
        editMessageText.setParseMode(PARSE_MODE);
        return editMessageText;
    }

    public String replaceSpace(String text) {
        return text.replaceAll(" ", "%20");
    }

    private void sendUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Can't send message! ", e);
        }
    }
}
