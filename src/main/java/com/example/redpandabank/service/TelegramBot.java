package com.example.redpandabank.service;

import com.example.redpandabank.strategy.commandStrategy.CommandStrategy;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.strategy.inlineStrategy.InlineStrategy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot {
    private final CommandStrategy commandStrategy;
    private final InlineStrategy inlineStrategy;

    public TelegramBot(CommandStrategy commandStrategy, InlineStrategy inlineStrategy) {
        this.commandStrategy = commandStrategy;
        this.inlineStrategy = inlineStrategy;
    }

    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        String commandMessage;
        if (update.hasMessage() && update.getMessage().hasText()) {
            commandMessage = update.getMessage().getText();
            CommandHandler commandHandler = commandStrategy.get(commandMessage);
            return commandHandler.handle(update);
        } else if (update.hasCallbackQuery()) {
            commandMessage = update.getCallbackQuery().getData();
            InlineHandler inlineHandler = inlineStrategy.get(commandMessage);
            return inlineHandler.handle(update);
        }
        return null;
    }
}
