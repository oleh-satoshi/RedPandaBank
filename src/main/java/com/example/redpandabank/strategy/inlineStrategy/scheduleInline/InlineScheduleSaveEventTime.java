package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@PackagePrivate
@Component
public class InlineScheduleSaveEventTime implements InlineHandler<Update> {
    final ChildService childService;
    final TranslateService translateService;
    final String ENTER_TIME = "enter-time";
    final MessageSender messageSender;
    final LessonService lessonService;

    public InlineScheduleSaveEventTime(ChildService childService,
                                       TranslateService translateService,
                                       MessageSender messageSender, LessonService lessonService) {
        this.childService = childService;
        this.translateService = translateService;
        this.messageSender = messageSender;
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.findByUserId(userId);
        resetSkipFlagIfSkipped(child);
        Lesson lesson = getLastLesson(userId);
        setEventTimeStateForUser(child, lesson);
        String content = translateService.getBySlug(ENTER_TIME);

        return messageSender.sendEditMessage(userId, messageId, content);
    }

    private Lesson getLastLesson(Long userId) {
        List<Lesson> lessons = lessonService.findAllByUserId(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        return lesson;
    }

    private void setEventTimeStateForUser(Child child, Lesson lesson) {
        child.setState(StateCommands.ADD_SPECIFIC_EVENT_START_TIME.getState()
                + Separator.COLON_SEPARATOR
                + lesson.getTitle());
        childService.create(child);
    }

    private static void resetSkipFlagIfSkipped(Child child) {
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
    }
}
