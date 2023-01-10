package com.example.redpandabank.strategy.inlineStrategy;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class InlinePlug implements InlineHandler<Update> {
    @Override
    public BotApiMethod<?> handle(Update update) {
        String firstName = update.getCallbackQuery().getMessage().getChat().getFirstName();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String response = firstName + ", такой команды нет!";
        SendMessage sendMessage = SendMessage.builder()
                .text(response)
                .chatId(chatId)
                .build();
        return sendMessage;
    }
}
