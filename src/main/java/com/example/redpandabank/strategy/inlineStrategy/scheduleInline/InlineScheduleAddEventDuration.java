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

import java.util.List;

@PackagePrivate
@Component
public class InlineScheduleAddEventDuration implements InlineHandler<Update> {
    final LessonService lessonService;
    final ChildService childService;

    public InlineScheduleAddEventDuration(LessonService lessonService,
                                          ChildService childService) {
        this.lessonService = lessonService;
        this.childService = childService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        String response;
        Long userId = UpdateInfo.getUserId(update);
        Integer messageId = UpdateInfo.getMessageId(update);
        Child child = childService.getById(userId);
        if (child.getIsSkip()) {
            child.setIsSkip(false);
        }
        List<Lesson> lessons = lessonService.findAllByChildIdWithoutLessonSchedule(userId);
        Lesson lesson = lessons.get(lessons.size() - 1);
        response = "Сколько минут идёт урок <i>\"" + lesson.getTitle() + "\"</i>?";
        child.setState(State.SAVE_DURATION.getState());
        childService.create(child);
        return new MessageSenderImpl().sendEditMessage(userId, messageId, response);
    }
}
