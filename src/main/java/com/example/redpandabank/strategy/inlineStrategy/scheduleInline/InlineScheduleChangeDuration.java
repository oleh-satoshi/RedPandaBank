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
public class InlineScheduleChangeDuration implements InlineHandler<Update> {
    final ChildService childService;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String ENTER_LESSON_DURATION_AGAIN = "enter-lesson-duration-again";

    public InlineScheduleChangeDuration(ChildService childService,
                                        TranslateService translateService,
                                        MessageSender messageSender) {
        this.childService = childService;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        updateDurationForUser(userId);
        String content = translateService.getBySlug(ENTER_LESSON_DURATION_AGAIN);
        return messageSender.sendEditMessage(userId, messageId, content);
    }

    private void updateDurationForUser(Long userId) {
        Child child = childService.findByUserId(userId);
        child.setState(StateCommands.SAVE_EVENT_DURATION.getState());
        child.setIsSkip(false);
        childService.create(child);
    }
}
