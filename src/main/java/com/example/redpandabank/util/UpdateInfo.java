package com.example.redpandabank.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateInfo {
    public static Long getUserId(Update update) {
       return update.hasCallbackQuery()
               ? update.getCallbackQuery().getMessage().getChatId()
               : update.getMessage().getChatId();
}

    public static String getText(Update update) {
        return update.hasCallbackQuery() ? update.getCallbackQuery().getMessage().getText()
                : update.getMessage().getText();
    }

    public static boolean hasReply(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    public static String getData(Update update) {
        return update.getCallbackQuery().getData();
    }

    public static Integer getMessageId(Update update) {
        return update.hasCallbackQuery()
                ? update.getCallbackQuery().getMessage().getMessageId()
                : update.getMessage().getMessageId();
    }
}
