package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@PackagePrivate
@Component
public class InlineEditSpecificEventDuration implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;

    public InlineEditSpecificEventDuration(LessonService lessonService, ChildService childService) {
        this.lessonService = lessonService;
        this.childService = childService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        String title = parseTitle(UpdateInfo.getText(update));
        Lesson lesson = lessonService.findLessonByTitle(userId, title);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.findByUserId(userId);
        child.setState(State.EDIT_SPECIFIC_SCHEDULE_EVENT_DURATION.getState()
                + LessonService.COLON_SEPARATOR + lesson.getLessonId());
        child.setIsSkip(false);
        childService.create(child);
        String content = "Введи новую длительность для этого урока <i>\"" + lesson.getTitle() + "\"</i> !";;
        return new MessageSenderImpl().sendEditMessage(userId, messageId, content);
    }

    private String parseTitle(String text) {
        return text.split(LessonService.QUOTE_SEPARATOR)[1];
    }
}
