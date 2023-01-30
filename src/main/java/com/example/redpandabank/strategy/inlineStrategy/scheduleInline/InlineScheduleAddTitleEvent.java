package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@PackagePrivate
@Component
public class InlineScheduleAddTitleEvent implements InlineHandler<Update> {
    final ChildService childService;

    public InlineScheduleAddTitleEvent(ChildService childService) {
        this.childService = childService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.getById(userId);
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
        response = "Введи название урока:";
        child.setState(State.SAVE_TITLE_EVENT.getState());
        childService.create(child);
        return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
    }
}
