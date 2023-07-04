package com.example.redpandabank.service;

import com.example.redpandabank.strategy.mainCommandHandler.MainCommandHandler;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class TelegramBot extends TelegramLongPollingBot {
    final List<MainCommandHandler> handlers;

    public TelegramBot(List<MainCommandHandler> handlers) {
        this.handlers = handlers;
    }

    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        //return new MessageSenderImpl().sendMessage(601575485L, "plug");
        MainCommandHandler mainCommandHandler = handlers.stream()
                .filter(handler -> handler.isApplicable(update))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(
                                "Can't find implementation for request in TelegramBot class"));
        return mainCommandHandler.handle(update);
    }

    @Override
    public String getBotUsername() {
        return "RedPandaBank";
    }

    @Override
    public String getBotToken() {
        return "5805955090:AAGLsfOLTsysmTHthxORc2GUb1WYmxS7Fs0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("update is recieved!");
        MainCommandHandler mainCommandHandler = handlers.stream()
                .filter(handler -> handler.isApplicable(update))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(
                                "Can't find implementation for request in TelegramBot class"));
        try {
            execute(mainCommandHandler.handle(update));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
