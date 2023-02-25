package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
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
public class InlineScheduleChangeEventTitle implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final TranslateService translateService;
    final String ENTER_LESSON_NAME_AGAIN = "enter-lesson-name-again";

    public InlineScheduleChangeEventTitle(LessonService lessonService,
                                          ChildService childService,
                                          TranslateService translateService) {
        this.lessonService = lessonService;
        this.childService = childService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = UpdateInfo.getUserId(update);
        String command = UpdateInfo.getText(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Lesson lesson = lessonService.findLessonByTitle(childId, parseCommand(command));
        lessonService.deleteLessonByTitleAndChildId(lesson.getTitle(), childId);
        Child child = childService.getById(childId).get();
        child.setState(State.SAVE_TITLE_EVENT.getState());
        child.setIsSkip(false);
        childService.create(child);
        String content = translateService.getBySlug(ENTER_LESSON_NAME_AGAIN);
        return new MessageSenderImpl().sendEditMessage(childId, messageId, content);
    }

    private String parseCommand(String command) {
        String result = command.split("\"")[1];
        return result;

    }
}
