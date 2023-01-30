package com.example.redpandabank.strategy.inlineStrategy.scheduleInline;

import com.example.redpandabank.model.Lesson;
import com.example.redpandabank.service.LessonService;
import com.example.redpandabank.service.MessageSenderImpl;
import com.example.redpandabank.strategy.inlineStrategy.InlineHandler;
import com.example.redpandabank.util.UpdateInfo;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Component
public class InlineScheduleRecoverEvent implements InlineHandler<Update> {
    final LessonService lessonService;
    final static String SEPARATOR = ":";

    public InlineScheduleRecoverEvent(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public BotApiMethod<?> handle(Update update) {
        Long childId = UpdateInfo.getUserId(update);
        String command = UpdateInfo.getData(update);
        Lesson lesson = lessonService.getById(parseId(command));
        if (lesson.getIsDeleted()) {
            lesson.setIsDeleted(false);
            lessonService.create(lesson);
            new MessageSenderImpl().sendMessageViaURL(childId, "Урок " + lesson.getTitle() + " снова добавлен в твое расписание!%0A%0A");
            new MessageSenderImpl().sendMessageViaURL(childId, lessonService.getInfoLessonByIdAndSendByUrl(lesson.getLessonId()));
        } else {
            new MessageSenderImpl().sendMessageViaURL(childId,"Я уже добавил этот урок в твое расписание, не переживай");
        }
        return null;
    }

    private Long parseId(String command) {
        return Long.valueOf(command.split(SEPARATOR)[1]);
    }
}
