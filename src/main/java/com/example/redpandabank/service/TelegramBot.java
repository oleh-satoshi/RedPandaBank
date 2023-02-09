package com.example.redpandabank.service;

import com.example.redpandabank.enums.Command;
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

import java.util.Optional;

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
        Boolean hasReply = UpdateInfo.hasReply(update);
        Boolean hasCallback = UpdateInfo.hasCallBack(update);
        String replyCommand;

        if (hasReply || hasCallback) {
            Long userId = UpdateInfo.getUserId(update);
            Optional<Child> childOptional = childService.getById(userId);
            if (childOptional.isPresent()
                && !childOptional.get().getState().equals(State.NO_STATE.getState())
                && !childOptional.get().getIsSkip()) {
                    StateHandler stateHandler = stateStrategy.get(childOptional.get());
                    return stateHandler.handle(update, this);
            } else if (hasReply) {
                replyCommand = UpdateInfo.getText(update);
                CommandHandler commandHandler = commandStrategy.get(replyCommand);
                return commandHandler.handle(update);
            } else if (hasCallback && childOptional.isPresent()) {
                replyCommand = UpdateInfo.getData(update);
                InlineHandler inlineHandler = inlineStrategy.get(replyCommand);
                return inlineHandler.handle(update);
            }
            return null;
        }
        return null;
    }
}
