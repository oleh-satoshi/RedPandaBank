package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddTitleEvent implements InlineHandler<Update> {
    final ChildService childService;
    final TranslateService translateService;
    final String ENTER_LESSON_NAME = "enter-lesson-name";

    public InlineScheduleAddTitleEvent(ChildService childService, TranslateService translateService) {
        this.childService = childService;
        this.translateService = translateService;
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
        response = translateService.getBySlug(ENTER_LESSON_NAME);
        child.setState(State.SAVE_TITLE_EVENT.getState());
        childService.create(child);
        return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
    }
}
