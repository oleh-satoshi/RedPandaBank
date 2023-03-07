package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import org.telegram.telegrambots.meta.api.objects.Update;

@PackagePrivate
@Component
public class InlineScheduleSaveEventTime implements InlineHandler<Update> {
    final ChildService childService;
    final TranslateService translateService;
    final String ENTER_TIME = "enter-time";

    public InlineScheduleSaveEventTime(ChildService childService,
                                       TranslateService translateService) {
        this.childService = childService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String content;
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.getById(userId).get();
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
        child.setState(State.ADD_EVENT_TIME.getState());
        childService.create(child);
        content = translateService.getBySlug(ENTER_TIME);
        return new MessageSenderImpl().sendEditMessage(userId, messageId, content);
    }
}
