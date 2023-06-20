package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleChangeTeacher implements InlineHandler<Update> {
    final ChildService childService;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String ENTER_TEACHER_NAME_AGAIN = "enter-teacher-name-again";

    public InlineScheduleChangeTeacher(ChildService childService,
                                       TranslateService translateService,
                                       MessageSender messageSender) {
        this.childService = childService;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        updateChildWithTeacherName(userId);
        String content = translateService.getBySlug(ENTER_TEACHER_NAME_AGAIN);
        return messageSender.sendEditMessage(userId, messageId, content);
    }

    private void updateChildWithTeacherName(Long userId) {
        Child child = childService.findByUserId(userId);
        child.setState(StateCommands.SAVE_EVENT_TEACHER_NAME.getState());
        child.setIsSkip(false);
        childService.create(child);
    }
}
