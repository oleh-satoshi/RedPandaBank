package com.example.redpandabank.service;

import com.example.redpandabank.strategy.mainCommandHandler.MainCommandHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class TelegramBot {
    final List<MainCommandHandler> handlers;

    public TelegramBot(List<MainCommandHandler> handlers) {
        this.handlers = handlers;
    }

    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        MainCommandHandler mainCommandHandler = handlers.stream()
                .filter(handler -> handler.isApplicable(update))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException("Can't find implementation for request in TelegramBot class"));
        return mainCommandHandler.handle(update);
    }
}
