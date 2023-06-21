package com.example.redpandabank.strategy.mainCommandHandler.impl;

import com.example.redpandabank.enums.Commands;
import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.strategy.commandStrategy.CommandStrategy;
import com.example.redpandabank.strategy.commandStrategy.handler.CommandHandler;
import com.example.redpandabank.strategy.mainCommandHandler.MainCommandHandler;
import com.example.redpandabank.util.UpdateInfo;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ReplyCommandHandler implements MainCommandHandler {
    final CommandStrategy commandStrategy;
    final ChildService childService;

    public ReplyCommandHandler(CommandStrategy commandStrategy,
                               ChildService childService) {
        this.commandStrategy = commandStrategy;
        this.childService = childService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String replyCommand = UpdateInfo.getText(update);
        CommandHandler commandHandler = commandStrategy.get(replyCommand);
        return commandHandler.handle(update);
    }

    @Override
    public boolean isApplicable(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        String incomingCommand = UpdateInfo.getText(update);
        Optional<Child> user = Optional.ofNullable(childService.findByUserId(userId));
        if (incomingCommand.equals(Commands.START.getName())) {
            return true;
        }
        if (user.isPresent()) {
            return UpdateInfo.hasReply(update)
                    && user.get().getState().equals(StateCommands.NO_STATE.getState());
        }
        return false;
    }
}
