package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.MessageSender;
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
    final MessageSender messageSender;
    final ChildService childService;
    final TranslateService translateService;
    final String ENTER_LESSON_NAME = "enter-lesson-name";

    public InlineScheduleAddTitleEvent(MessageSender messageSender, ChildService childService,
                                       TranslateService translateService) {
        this.messageSender = messageSender;
        this.childService = childService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.findByUserId(userId);
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
        response = translateService.getBySlug(ENTER_LESSON_NAME);
        setTitleEventState(child);
        return messageSender.sendEditMessage(userId, messageId, response);
    }

    private void setTitleEventState(Child child) {
        child.setState(StateCommands.SAVE_TITLE_EVENT.getState());
        childService.create(child);
    }
}
