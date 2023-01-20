package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
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
public class InlineChangeEventTitle implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;

    public InlineChangeEventTitle(LessonService lessonService, ChildService childService) {
        this.lessonService = lessonService;
        this.childService = childService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getMessage().getChatId();
        String command = update.getCallbackQuery().getMessage().getText();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        Lesson lesson = lessonService.findLessonByTitle(childId, parseCommand(command));
        lessonService.deleteLessonByTitleAndChildId(lesson.getTitle(), childId);
        Child child = childService.getById(childId);
        child.setState(State.SAVE_TITLE_EVENT.getState());
        child.setIsSkip(false);
        childService.create(child);
        return new MessageSenderImpl().sendEditMessage(childId, messageId, "Попробуй снова ввести имя урока, будь внимателен:");
    }

    private String parseCommand(String command) {
        String result = command.split("\"")[1];
        return result;

    }
}
