package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.Separator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddTimeToLesson implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final TranslateService translateService;
    final String ENTER_TIME_FOR_LESSON = "enter-time-for-lesson";


    public InlineScheduleAddTimeToLesson(LessonService lessonService,
                                         ChildService childService,
                                         TranslateService translateService) {
        this.lessonService = lessonService;
        this.childService = childService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = update.getCallbackQuery().getFrom().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        Lesson lesson = lessonService.findLessonByTitle(childId,
                parseTitle(update.getCallbackQuery().getData()));
        Child child = childService.findByUserId(childId);
        child.setState(State.ADD_SPECIFIC_EVENT_START_TIME.getState()
                + Separator.COLON_SEPARATOR + lesson.getTitle());
        child.setIsSkip(false);
        childService.create(child);
        String response = translateService.getBySlug(ENTER_TIME_FOR_LESSON) +  " <i>\"" + lesson.getTitle() + "\"</i>:";
        return new MessageSenderImpl().sendEditMessage(childId, messageId, response);
    }

    private String parseTitle(String data) {
        return data.split(Separator.COLON_SEPARATOR)[1];
    }
}
