package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.enums.State;
import com.example.redpandabank.model.Child;
import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.ChildService;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.TranslateService;
import com.example.redpandabank.service.impl.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class InlineScheduleAddEventDuration implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;
    final TranslateService translateService;
    final String DURATION_FOR_LESSON = "duration-of-lesson";

    public InlineScheduleAddEventDuration(LessonService lessonService,
                                          ChildService childService,
                                          TranslateService translateService) {
        this.lessonService = lessonService;
        this.childService = childService;
        this.translateService = translateService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.getById(userId).get();
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
        List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        response = translateService.getBySlug(DURATION_FOR_LESSON);
        child.setState(State.SAVE_DURATION.getState());
        childService.create(child);
        return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
    }
}
