package com.example.redpandabank.strategy.stateStrategy;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.TelegramBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Set;

public interface CommandCheckable {
    default boolean checkCommand(String command,  Child child) {
        return !Command.getGeneralCommands().contains(command);// && !child.getIsSkip();
    }

    default BotApiMethod<?> goBackToTelegramBot(Child child, ChildService childService,
                                                TelegramBot telegramBot, Update update) {
        child.setIsSkip(true);
        childService.create(child);
        return telegramBot.onWebhookUpdateReceived(update);
    }
}
