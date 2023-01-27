package com.example.redpandabank.service;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.strategy.commandStrategy.CommandStrategy;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.strategy.inlineStrategy.InlineStrategy;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.strategy.stateStrategy.StateStrategy;
import com.example.redpandabank.util.UpdateInfo;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.time.LocalTime;

@PackagePrivate
@Component
public class TelegramBot {
    final CommandStrategy commandStrategy;
    final InlineStrategy inlineStrategy;
    final StateStrategy stateStrategy;
    final ChildService childService;

    public TelegramBot(CommandStrategy commandStrategy, InlineStrategy inlineStrategy,
                       StateStrategy stateStrategy, ChildService childService) {
        this.commandStrategy = commandStrategy;
        this.inlineStrategy = inlineStrategy;
        this.stateStrategy = stateStrategy;
        this.childService = childService;
    }

    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Boolean hasReply = update.hasMessage() && update.getMessage().hasText();
        Boolean hasCallback = update.hasCallbackQuery();
        String replyCommand;
            if (hasReply || hasCallback) {
            Long userId = UpdateInfo.getUserId(update);
            Child child = childService.getById(userId);
            if (!child.getState().equals(State.NO_STATE.getState())
                    && !child.getIsSkip()) {
                StateHandler stateHandler = stateStrategy.get(child);
                return stateHandler.handle(update, this);
            }

            if (hasReply) {
                replyCommand = update.getMessage().getText();
                CommandHandler commandHandler = commandStrategy.get(replyCommand);
                return commandHandler.handle(update);
            } else if (hasCallback) {
                replyCommand = update.getCallbackQuery().getData();
                InlineHandler inlineHandler = inlineStrategy.get(replyCommand);
                return inlineHandler.handle(update);
            }
            return null;
        }
        return null;
    }
}
