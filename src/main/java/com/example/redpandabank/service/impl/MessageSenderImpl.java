package com.example.redpandabank.service.impl;

import com.example.redpandabank.service.MessageSender;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Service
@Scope("prototype")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageSenderImpl implements MessageSender {
    static final String PARSE_MODE = "HTML";
    @Value("${urlString}")
    String urlString;
    @Value("${apiToken}")
    String apiToken;

    @Override
    public void sendMessageViaURL(Long chatId, String content) {
        String newUrlString = String.format(urlString, apiToken, chatId, content, PARSE_MODE);
        sendUrl(newUrlString);
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

    @Override
    public EditMessageText sendEditMessageWithInline(Long userId,
                                                     Integer messageId,
                                                     InlineKeyboardMarkup keyboard,
                                                     String content) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(userId);
        editMessageText.setMessageId(messageId);
        editMessageText.setReplyMarkup(keyboard);
        editMessageText.setText(content);
        editMessageText.setParseMode(PARSE_MODE);
        return editMessageText;
    }

    //eксперимент
    @Override
    public EditMessageText sendEditMessageWithInlineWithoutMessageId(Long userId,
                                                                     InlineKeyboardMarkup keyboard,
                                                                     String content) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(userId);
        editMessageText.setReplyMarkup(keyboard);
        editMessageText.setText(content);
        editMessageText.setParseMode(PARSE_MODE);
        return editMessageText;
    }

    @Override
    public EditMessageText sendEditMessage(Long userId, Integer messageId, String content) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(userId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(content);
        editMessageText.setParseMode(PARSE_MODE);
        return editMessageText;
    }

    @Override
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
