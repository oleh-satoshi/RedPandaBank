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
public class InlineScheduleSaveEventTime implements InlineHandler<Update> {
    final ChildService childService;

    public InlineScheduleSaveEventTime(ChildService childService) {
        this.childService = childService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.getById(userId).get();
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
        child.setState(State.ADD_EVENT_TIME.getState());
        childService.create(child);
        response = "Напиши просто время числами и раздели часы с минутами двоеточием (:):\n\n" +
                "вот так 8:00,\nили так 9:45,\nили так 10:25";
        return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
    }
}
