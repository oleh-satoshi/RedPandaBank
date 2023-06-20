package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
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
public class InlineScheduleChangeEventTitle implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String ENTER_LESSON_NAME_AGAIN = "enter-lesson-name-again";

    public InlineScheduleChangeEventTitle(LessonService lessonService,
                                          ChildService childService,
                                          TranslateService translateService,
                                          MessageSender messageSender) {
        this.lessonService = lessonService;
        this.childService = childService;
        this.translateService = translateService;
        this.messageSender = messageSender;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        String command = UpdateInfo.getText(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Lesson lesson = lessonService.findLessonByTitle(userId, parseCommand(command));
        lessonService.deleteLessonByTitleAndChildId(lesson.getTitle(), userId);
        updateChildWithStateAndSaveTitleEvent(userId);
        String content = translateService.getBySlug(ENTER_LESSON_NAME_AGAIN);
        return messageSender.sendEditMessage(userId, messageId, content);
    }

    private void updateChildWithStateAndSaveTitleEvent(Long userId) {
        Child child = childService.findByUserId(userId);
        child.setState(StateCommands.SAVE_TITLE_EVENT.getState());
        child.setIsSkip(false);
        childService.create(child);
    }

    private String parseCommand(String command) {
        String result = command.split("\"")[1];
        return result;
    }
}
