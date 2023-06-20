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
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddEventDuration implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String DURATION_FOR_LESSON = "duration-of-lesson";

    public InlineScheduleAddEventDuration(LessonService lessonService,
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
        String response;
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.findByUserId(userId);
        checkIsSkip(child);
        List<Lesson> lessons = lessonService.findAllByUserId(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        response = translateService.getBySlug(DURATION_FOR_LESSON);
        setStateForUser(child);
        return messageSender.sendEditMessage(userId, messageId, response);
    }

    private static void checkIsSkip(Child child) {
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
    }

    private void setStateForUser(Child child) {
        child.setState(StateCommands.SAVE_EVENT_DURATION.getState());
        childService.create(child);
    }
}
