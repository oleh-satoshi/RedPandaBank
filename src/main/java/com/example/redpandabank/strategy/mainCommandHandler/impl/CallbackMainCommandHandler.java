package com.example.redpandabank.strategy.mainCommandHandler.impl;

import com.example.redpandabank.strategy.mainCommandHandler.MainCommandHandler;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.strategy.inlineStrategy.InlineStrategy;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class CallbackMainCommandHandler implements MainCommandHandler {
    final InlineStrategy inlineStrategy;
    final ChildService childService;

    public CallbackMainCommandHandler(InlineStrategy inlineStrategy,
                                      ChildService childService) {
        this.inlineStrategy = inlineStrategy;
        this.childService = childService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String replyCommand = UpdateInfo.getData(update);
        InlineHandler inlineHandler = inlineStrategy.get(replyCommand);
        return inlineHandler.handle(update);
    }

    @Override
    public boolean isApplicable(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        Optional<Child> childOptional = childService.getById(userId);
        return UpdateInfo.hasCallBack(update) && childOptional.isPresent();
    }
}
