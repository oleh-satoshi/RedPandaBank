package com.example.redpandabank.strategy.mainCommandHandler.impl;

import com.example.redpandabank.strategy.mainCommandHandler.MainCommandHandler;
import com.example.redpandabank.strategy.commandStrategy.CommandStrategy;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ReplyCommandHandler implements MainCommandHandler {
    final CommandStrategy commandStrategy;

    public ReplyCommandHandler(CommandStrategy commandStrategy) {
        this.commandStrategy = commandStrategy;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String replyCommand = UpdateInfo.getText(update);
        CommandHandler commandHandler = commandStrategy.get(replyCommand);
        return commandHandler.handle(update);
    }

    @Override
    public boolean isApplicable(Update update) {
        return UpdateInfo.hasReply(update);
    }
}
