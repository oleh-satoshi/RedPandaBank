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
public class InlineScheduleEditSpecificEventDuration implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final TranslateService translateService;
    final MessageSender messageSender;
    final String NEW_DURATION_FOR_LESSON = "new-duration-for-lesson";

    public InlineScheduleEditSpecificEventDuration(LessonService lessonService,
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
        String title = parseTitle(UpdateInfo.getText(update));
        Lesson lesson = lessonService.findLessonByTitle(userId, title);
        Integer messageId = UpdateInfo.getMessageId(update);
        setEditSpecificDurationState(userId, lesson);
        String content = getContent(lesson);
        return messageSender.sendEditMessage(userId, messageId, content);
    }

    private void setEditSpecificDurationState(Long userId, Lesson lesson) {
        Child child = childService.findByUserId(userId);
        child.setState(StateCommands.EDIT_SPECIFIC_SCHEDULE_EVENT_DURATION.getState()
                + Separator.COLON_SEPARATOR + lesson.getId());
        child.setIsSkip(false);
        childService.create(child);
    }

    private String getContent(Lesson lesson) {
        return translateService.getBySlug(NEW_DURATION_FOR_LESSON)
                + " <i>\"" + lesson.getTitle() + "\"</i> !";
    }

    private String parseTitle(String text) {
        return text.split(Separator.QUOTE_SEPARATOR)[1];
    }
}
