package com.example.redpandabank.service;

import com.example.redpandabank.strategy.commandStrategy.CommandStrategy;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot {
    private CommandStrategy commandStrategy;

    public TelegramBot(CommandStrategy commandStrategy) {
        this.commandStrategy = commandStrategy;
    }

    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String commandMessage = update.getMessage().getText();
            CommandHandler commandHandler = commandStrategy.get(commandMessage);
            return commandHandler.handle(update);
        } else if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("/scheduleMonday")) {
                return new SendMessage().builder()
                        .chatId(update.getCallbackQuery().getMessage().getChatId())
                        .text("получилось!")
                        .build();
            }
        }
        return null;
    }
}
