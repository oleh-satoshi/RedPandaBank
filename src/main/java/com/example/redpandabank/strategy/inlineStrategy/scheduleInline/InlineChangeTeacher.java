package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@PackagePrivate
@Component
public class InlineChangeTeacher implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;

    public InlineChangeTeacher(LessonService lessonService, ChildService childService) {
        this.lessonService = lessonService;
        this.childService = childService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        Child child = childService.getById(childId);
        child.setState(State.SAVE_TEACHER_NAME.getState());
        child.setIsSkip(false);
        childService.create(child);
        return new MessageSenderImpl().sendEditMessage(childId, messageId, "Попробуй снова ввести имя учителя, будь внимателен:");
    }
}
