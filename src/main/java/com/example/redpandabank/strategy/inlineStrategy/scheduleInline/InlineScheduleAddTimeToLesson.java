package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.StateCommands;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSender;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddTimeToLesson implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String ENTER_TIME_FOR_LESSON = "enter-time-for-lesson";

    public InlineScheduleAddTimeToLesson(LessonService lessonService,
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
        Long childId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Lesson lesson = lessonService.findLessonByTitle(childId,
                parseTitle(UpdateInfo.getData(update)));
        Child child = childService.findByUserId(childId);
        setAddStartTimeForUser(lesson, child);
        String response = getResponse(lesson);
        childService.create(child);
        return messageSender.sendEditMessage(childId, messageId, response);
    }

    private static void setAddStartTimeForUser(Lesson lesson, Child child) {
        child.setState(StateCommands.ADD_SPECIFIC_EVENT_START_TIME.getState()
                + Separator.COLON_SEPARATOR + lesson.getTitle());
        child.setIsSkip(false);
    }

    private String getResponse(Lesson lesson) {
        return translateService.getBySlug(ENTER_TIME_FOR_LESSON)
                + " <i>\"" + lesson.getTitle() + "\"</i>:";
    }

    private String parseTitle(String data) {
        return data.split(Separator.COLON_SEPARATOR)[1];
    }
}
