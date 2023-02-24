package com.example.redpandabank.strategy.mainCommandHandler.impl;

import com.example.redpandabank.enums.Command;
import com.example.redpandabank.enums.State;
import com.example.redpandabank.strategy.mainCommandHandler.MainCommandHandler;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.strategy.stateStrategy.StateHandler;
import com.example.redpandabank.strategy.stateStrategy.StateStrategy;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class StateMainCommandHandler implements MainCommandHandler {
    final StateStrategy stateStrategy;
    final ChildService childService;
    Optional<Child> childOptional;

    public StateMainCommandHandler(StateStrategy stateStrategy, ChildService childService) {
        this.stateStrategy = stateStrategy;
        this.childService = childService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        childOptional = childService.getById(userId);
        StateHandler stateHandler = stateStrategy.get(childOptional.get());
        return stateHandler.handle(update);    }

    @Override
    public boolean isApplicable(Update update) {
        return childOptional.isPresent()
                && !childOptional.get().getState().equals(State.NO_STATE.getState())
                && !childOptional.get().getIsSkip()
                && !Command.getGeneralCommands().contains(UpdateInfo.getText(update));
    }
}
